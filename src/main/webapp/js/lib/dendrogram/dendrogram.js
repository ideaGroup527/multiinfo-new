var dendrogram = {
	orientation : 'top',
	border : 1,
	leftOffset : 0,
	bottomOffset :0,
	topOffset : 0,
	rightOffset : 0,
	maxRight : 0,
	LineWidth : 2,
	xticks : 1,
	yticks : 1,
	ytickN : 10,
	colorthreshold :'default',
	labels : [],
	font : "10pt Arial",
	groups : [],
	newGroups : [],
	groupLabel : [],
	xpush : 0,
	skipped : [],
	skippedInfo : {},
	grid :0,
	
	
	draw : function(l) {
		//l is a variable as returned by linkage
		//v is an object with the parameters for build the dendrogram; 
		//accepted parameters are LineWidth; Labels; Font;canvas
		//find the canvas or create it
		//Helena F Deus, 2010-06-11
		//Define parameters
			
			if(dendrogram.orientation=='left' || dendrogram.orientation=='right'  ){
				//var ori_width = dendrogram.width;
				//dendrogram.width=dendrogram.height;
				//dendrogram.height =ori_width; 
			}
			if(typeof(dendrogram.canvas)==='undefined'){
				var canvas = document.createElement('canvas');
				canvas.id = 'dendrogram';
				canvas.width=dendrogram.width;
				canvas.height=dendrogram.height;
				dendrogram.canvas = canvas;
				dendrogram.ctx = canvas.getContext('2d');	
				//dendrogram.ctx.width = 300;
				//dendrogram.ctx.height = 300;
				if(typeof(dendrogram.holder)!=='undefined'){
						var hold = document.getElementById(dendrogram.holder);
						hold.appendChild(canvas);
				}
				else {
						document.body.appendChild(canvas);
				}
			}
			else {
				dendrogram.ctx = dendrogram.canvas.getContext('2d');
			}
		
		//before draw, save ctx
		dendrogram.ctx.save();
		///configure the canvas
			dendrogram.clusterMidpointX = [];//save the midpoint of each cluster; instead of nextMidPoint
			dendrogram.clusterMidpointH = [];


		//rotate the canvas if necessary
			if(dendrogram.orientation=='left'){
				
				dendrogram.maxDeep = dendrogram.canvas.width;
				dendrogram.wallR = dendrogram.canvas.width-dendrogram.leftOffset;
				dendrogram.ceil = dendrogram.rightOffset;
				dendrogram.floor = dendrogram.canvas.height-dendrogram.bottomOffset;
				dendrogram.wallL = dendrogram.topOffset;
				dendrogram.ctx.translate(dendrogram.canvas.width, 0);
				dendrogram.ctx.rotate(Math.PI/2);
			}
			else if(dendrogram.orientation=='right'){
				dendrogram.maxDeep = dendrogram.canvas.width;
				dendrogram.floor = dendrogram.rightOffset;
				dendrogram.ceil = dendrogram.canvas.width-dendrogram.leftOffset;
				dendrogram.wallL = dendrogram.canvas.height-dendrogram.bottomOffset;
				dendrogram.wallR =  dendrogram.topOffset;
				
				dendrogram.ctx.translate(dendrogram.canvas.height, 0);
				dendrogram.ctx.rotate(Math.PI/2);
			}
			else if(dendrogram.orientation=='top') {
				dendrogram.maxDeep = dendrogram.canvas.height;
				dendrogram.floor = (dendrogram.maxDeep-dendrogram.bottomOffset);
				dendrogram.ceil = dendrogram.topOffset;
				dendrogram.wallL = dendrogram.leftOffset;
				dendrogram.wallR = (dendrogram.canvas.width-dendrogram.rightOffset);
			}
			else {
				dendrogram.maxDeep = dendrogram.canvas.height;
				dendrogram.floor = dendrogram.topOffset;
				dendrogram.ceil = (dendrogram.maxDeep-dendrogram.bottomOffset);
				dendrogram.wallL = (dendrogram.canvas.width-dendrogram.rightOffset);
				dendrogram.wallR = dendrogram.leftOffset; 
			}

		
		//canvas grows from bottom to top up tp maxDeepness
			dendrogram.maxDeepeness = (dendrogram.maxDeep-dendrogram.bottomOffset);
		//divide the canvas in group+1 fragments
		//how many groups? what space will be required?
			var n = l.length+1;
			if(typeof(dendrogram.fL)==='undefined'){
				dendrogram.fL = dendrogram.wallL;
			}
			if(typeof(dendrogram.lL)==='undefined') {
				dendrogram.lL = dendrogram.wallR-((dendrogram.wallR-dendrogram.wallL)/(n+1));
			}
		
		//is there a maximum and min position for the canvas? the jump is calculated using those plus n, the number of observations
		if(typeof(dendrogram.groupGap)==='undefined'){
			dendrogram.groupGap = (dendrogram.lL-dendrogram.fL)/(l.length);
		}
		dendrogram.maxRight = dendrogram.fL;
		//separate the 3 vectors input and find the max height
			var v1=[]; var v2=[]; var v3=[];
			dendrogram.max = 0;
			
		
		for (var i=0; i<l.length; i++) {
			v1.push(l[i][0]);
			v2.push(l[i][1]);
			v2.push(l[i][2]);
			
			var localMax = l[i][2];
			if(localMax>=dendrogram.max){
				dendrogram.max = localMax;
			}
		}
		if(dendrogram.colorthreshold==='default'){
			dendrogram.colorthreshold = 0.7*dendrogram.max;
		}
		
		if(dendrogram.grid){
			grid (dendrogram.canvas, 20);
		}
		
		
		//always draw the first cluster (group l.length)
		var cluster = l.length+1;
		//xpush is 0 for the first cluster; 1 for everything else
		dendrogram.xpush = 0;
		dendrogram.drawCluster(l[0], cluster);
		dendrogram.xpush = 1;
		
		var wasCreated=[cluster];//there is 1 more group than the length of l
		var previouslyDrawn = cluster; 
		var lab=0;var drawOrder = [0];var clusterInfo=[];var Links = [];
		
		var i = 0;
		for (var i=1; i<l.length; i++) {
			cluster++;	
			leftGroup = l[i][0];
			rightGroup = l[i][1];
			//L has a link between two groups; of course, to do that, each groups must have already been built; if not, build it 
				//the groups are defined by their position on l, that is how they were grown
				//first ask: does this cluster link to the previous cluster?
				
				if(rightGroup===previouslyDrawn || leftGroup===previouslyDrawn){
					if(leftGroup===previouslyDrawn){
						//what about leftGroup? Does it exist?
						//drawleft of right group first? which one has the shortest distance?
						if(dendrogram.skipped.indexOf(rightGroup)!==-1){
							dendrogram.drawSkipped(rightGroup);
						}
					}
					else {
						if(dendrogram.skipped.indexOf(leftGroup)!==-1){
							//can if be drawn now?
							dendrogram.drawSkipped(leftGroup);
											
						}
					}
					dendrogram.drawCluster(l[i], cluster); 
					previouslyDrawn = cluster;
				}
				else {
					//save this, they will be drawn later
					dendrogram.skipped.push(cluster);	
					dendrogram.skippedInfo[cluster] = l[i];
				}
		}

		//aftaer drawing restore ctx
		//dendrogram.ctx.restore();
	dendrogram.ctx.restore();
	dendrogram.canvasConfigure(dendrogram.ctx);
	},
	
	//draw a "fake" border around the dendrogram region; the left and bottom will be used for the axis values
	canvasConfigure : function (ctx) {
			if(dendrogram.border){
				//ctx.beginPath();
				ctx.moveTo(dendrogram.wallL+300, dendrogram.floor);
				ctx.lineTo(dendrogram.wallL+300, dendrogram.ceil);
				ctx.lineTo(dendrogram.wallR, dendrogram.ceil);
				ctx.stroke();
			}
			// if(dendrogram.yticks){
			//
			// 	dendrogram.ctx.beginPath();
			// 	//divide dendrogram.max in 10 bits
			// 	var ticksDiv = (dendrogram.max/dendrogram.ytickN);
			// 	for (var i=0; i<dendrogram.ytickN; i++) {
            //
			// 		//scale to the appropriate position
			// 		var sh = (((ticksDiv*i)*(dendrogram.floor-dendrogram.ceil-5))/dendrogram.max);
			// 		dendrogram.ctx.moveTo(dendrogram.leftOffset-5, dendrogram.maxDeepeness-sh);
			// 		dendrogram.ctx.lineTo(dendrogram.leftOffset+5, dendrogram.maxDeepeness-sh);
			// 		dendrogram.ctx.stroke();
			// 		numlabel=Math.round((i*ticksDiv)*100)/100;
			// 	    dendrogram.ctx.fillText(numlabel, dendrogram.leftOffset-dendrogram.ctx.measureText(numlabel).width, dendrogram.maxDeepeness-sh);
			// 	}
			//
			// }

	},
	
	drawCluster : function (cl, cluster) {
		//cl is a row from the variable returned by linkage; cluster is the cluster number in linkage cols 1 and 2; i the ith row of cl
		//height will need to be subtracted from max deepness
			
			dendrogram.groups.push(cluster);
			
			var h = cl[2];
							
			//scale the h using max
			var sh = ((h*(dendrogram.floor-dendrogram.ceil-5))/dendrogram.max);

            dendrogram.ctx.beginPath();
			dendrogram.ctx.strokeStyle = "rgb(0,0,0)";
			dendrogram.ctx.fillStyle = "rgb(0,0,0)";
			dendrogram.ctx.lineWidth = dendrogram.LineWidth;
			//start in the middle of the fragment for each group; point 1 is [x,y] from the left, point 2 from the right; point 3 ends line started at point1 and point4 at point2
			//for point 1 and 4 the the line will be shortenned to be placed on top of the group that it is connecting to.
			if(typeof(dendrogram.clusterMidpointX[cl[1]])!=='undefined'){
				leftMidPointX = dendrogram.clusterMidpointX[cl[1]];
				lowerLeftH = dendrogram.clusterMidpointH[cl[1]];
			}
			else {
				leftMidPointX = dendrogram.maxRight+dendrogram.groupGap*dendrogram.xpush;
				lowerLeftH = dendrogram.floor;
				dendrogram.maxRight = leftMidPointX;
                labelH=leftMidPointX;

				//xpush reflects how much to the right the dendrogram is pushed; clusters that do not link directly to the previous cluster push the line forward by 2; other push it only by 1;
				
			}
			
			if(typeof(dendrogram.clusterMidpointX[cl[0]])!=='undefined'){
				rightMidPointX = dendrogram.clusterMidpointX[cl[0]];
				lowerRightH = dendrogram.clusterMidpointH[cl[0]];
				
			}
			else {
				//rightMidPointX = dendrogram.xpush*dendrogram.fL;
				rightMidPointX = dendrogram.maxRight+dendrogram.groupGap;
				lowerRightH = dendrogram.floor;
				dendrogram.maxRight = rightMidPointX;
				
			}

			//if(dendrogram.maxRight<rightMidPointX) {dendrogram.maxRight=rightMidPointX;}
			var point1 = [leftMidPointX, lowerLeftH];
			var point2 = [leftMidPointX, dendrogram.floor-sh];
			var point3 = [rightMidPointX, dendrogram.floor-sh];
			var point4 = [rightMidPointX, lowerRightH];
			//now draw
			dendrogram.ctx.moveTo(point1[0], point1[1]);
			dendrogram.ctx.lineTo(point2[0], point2[1]);
			dendrogram.ctx.lineTo(point3[0], point3[1]);
			dendrogram.ctx.lineTo(point4[0], point4[1]);
			dendrogram.ctx.stroke();
			
			//dendrogram.ctx.fillRect(point1[0], point1[1],dendrogram.ctx.lineWidth, (point2[1]-point1[1]));
			//dendrogram.ctx.fillRect(point2[0], point2[1], (point3[0]-point2[0]), dendrogram.ctx.lineWidth);
			//dendrogram.ctx.fillRect(point3[0], point3[1],dendrogram.ctx.lineWidth, (point4[1]-point3[1]));
			
			
			
			//save the height and midpoint location of the group newly formed
			dendrogram.clusterMidpointX[cluster]=(point2[0]+(point3[0]-point2[0])/2);
			dendrogram.clusterMidpointH[cluster]=(dendrogram.floor-sh);
	},
	
	
	drawSkipped : function(skippedGroup) {
			
			var skippedGroupInfo = dendrogram.skippedInfo[skippedGroup];
			var skippedLeft = skippedGroupInfo[0];var skippedRight = skippedGroupInfo[1];
			
			if(dendrogram.skipped.indexOf(skippedLeft)==-1 && dendrogram.skipped.indexOf(skippedRight)==-1){
				dendrogram.drawCluster(skippedGroupInfo, skippedGroup); 
				//dendrogram.skipped[dendrogram.skipped.indexOf(skippedGroup)];
			}
			else {
				//how to decide which to draw first if they are both not drawn? which on is the shortest?
				if(dendrogram.skipped.indexOf(skippedRight)!==-1 && dendrogram.skipped.indexOf(skippedLeft)!==-1){
					if(dendrogram.skippedInfo[skippedRight][2]>=dendrogram.skippedInfo[skippedLeft][2]){
						//if right is bigger, draw left first
						dendrogram.drawSkipped(skippedLeft);
						dendrogram.drawSkipped(skippedRight);
					}
					else {
						dendrogram.drawSkipped(skippedRight);
						dendrogram.drawSkipped(skippedLeft);
					}
				}
				else if(dendrogram.skipped.indexOf(skippedRight)!==-1){
						dendrogram.drawSkipped(skippedRight);
				}
				else {
						dendrogram.drawSkipped(skippedLeft);
				}

				dendrogram.drawCluster(skippedGroupInfo, skippedGroup);
			}
	}
		

}

function grid (canvas, sep) {
	width = canvas.width;
	height = canvas.height;
	if(typeof(sep)==='undefined') {var sep = 20;}
	var ctx = canvas.getContext('2d');
	ctx.strokeStyle = "rgba(0, 0, 200, 0.5)";
	for (var i=0; i<=width; i=i+sep) {
	ctx.beginPath();
	ctx.moveTo(i,0);//all grid lines start at zero
	ctx.lineTo(i,height);
	ctx.stroke();
	}

	for (var i=0; i<=height; i=i+sep) {
	ctx.beginPath();
	ctx.moveTo(0,i);//all grid lines start at zero
	ctx.lineTo(width, i);
	ctx.stroke();
	}
}