function linkage(x, method) {
	//find the distance between groups using the metric indicated in method
	//x is the vector returned by pdist
	//supported methods: average (UPGMA), single, complete, 

	Array.prototype.min = function() {
	var min = this[0];
	var len = this.length;
	for (var i = 1; i < len; i++) if (this[i] < min) min = this[i];
	return min;
	}
	Array.prototype.max = function() {
	var max = this[0];
	var len = this.length;
	for (var i = 1; i < len; i++) if (this[i] > max) max = this[i];
	return max;
	}

	if(typeof(method)==='undefined'){
		method = 'average';
	}
	//get the square form matrix
	if(!self.squareform){
		document.write("<script type='text/javascript' src='http://compstats.mathbiol-lena.googlecode.com/hg/squareform.js'>");
	}
	var square = squareform(x);
	var matrix = [];
	var group = 0;
	matrix[group] = [];
	//to copy matrix without changing square because slice works with a single array
	for (var i=0; i<square.length; i++) {
		matrix[group][i] = square[i].slice(0);	
	}
	var groupSize = square.length-2;
	var link = []; var rejectedIndexes = [];var reset = 0;
	
	//every group has its own index as the value in groupIndexes, except for the new groups
	var groupIndexes = [];
	
	for (var i=0; i<square.length; i++) {
		groupIndexes[i] = [i];
	}
	
	while (group<=groupSize) {
	
		//find the absolute minima in the matrix;
			
		lk = findLinkage(matrix[group]);
		link[group] = [lk[0],lk[1],lk[2]];
		
		//prepare the next matrix
		var r = lk[0];
		var c = lk[1];
		var max = lk[3];
		
		//save the rejected indexes in all cases
		rejectedIndexes.push(r, c);
				
		//to build the next matrix, copy the group matrix; 
		matrix[group+1] = [];
			
			for (var i=0; i<matrix[group].length; i++) {
				matrix[group+1][i] = matrix[group][i].slice(0);	
			}
		
		
		//replace the row/col in rejected indexes with max+1; equivalent to deleting these lines/cols because they will not return a min
		for (var i=0; i<matrix[group].length; i++) {
			if([r,c].indexOf(i)!==-1){
				for (var j=0; j<matrix[group][i].length; j++) {
					matrix[group+1][i][j] = max+1;
					matrix[group+1][j][i] = max+1;
					}
				}
			}
			
		
			//now calculate the distance from group rc to the remaining lines/cols
			var newGroupLine = matrix[group+1].length;
			matrix[group+1][newGroupLine]=[];

			//group indexes will not only reflect the line, because that is erroneous since the line grows; it must reflect all the indexes that each line is made up of
				groupIndexes[newGroupLine] = [];
				
				for (var i=0; i<groupIndexes[r].length; i++) {
						groupIndexes[newGroupLine].push(groupIndexes[r][i]);
				}
				for (var i=0; i<groupIndexes[c].length; i++) {
						groupIndexes[newGroupLine].push(groupIndexes[c][i]);
				}
				
			
			//now apply the method to obtain the new lines; if next group (group+1) == group before last (groupSize-1), then there will not be any data and the min will be calculated by averaging the distance to all other original groups included in the cluster 
				for (var j=0; j<=newGroupLine; j++) {
					if(rejectedIndexes.indexOf(j)===-1){
						if(method==='average'){
							var tmp = 0;var tot = 0;
							for (var g=0; g<groupIndexes[newGroupLine].length; g++) {
								for (var h=0; h<groupIndexes[j].length; h++) {
									tmp += square[groupIndexes[newGroupLine][g]][groupIndexes[j][h]];
									tot ++;
								}
							}
							
							var av = tmp/tot;	
							
						}
						else if (method==='single' || method==='complete') {
							var tmp = [];var tot = 0;
							for (var g=0; g<groupIndexes[newGroupLine].length; g++) {
								for (var h=0; h<groupIndexes[j].length; h++) {
									tmp.push(square[groupIndexes[newGroupLine][g]][groupIndexes[j][h]]);
									tot ++;
								}
							}
							if(method==='single'){
								var av = tmp.min();
							}
							else {
								var av = tmp.max();
							}
						}
						
						matrix[group+1][newGroupLine][j] = av;
						matrix[group+1][j][newGroupLine] = av;
						
					}
					else {
						matrix[group+1][newGroupLine][j] = max+1;
						matrix[group+1][j][newGroupLine] = max+1;
					}
				}
				
			
			
		group++;
	}
	return link;
}

function findLinkage(matrix) {
			var r; var c;var mini;
			for (var i=0; i<matrix.length; i++) {
				for (var j=i+1; j<matrix[i].length; j++) {
					if(i!==j){
						local_min = matrix[i][j];
						local_max = matrix[i][j];
						if(typeof(mini)==='undefined' || local_min<=mini){
							mini = local_min;
							//save the row and col
							r = i;
							c = j;
						}
						if(typeof(max)==='undefined' || local_max>=max){
							max = local_max;
						}
					}
				}	
			}
			
	var link = [ r, c, mini, max];
	return link;
}

