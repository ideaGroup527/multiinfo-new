var graphs  = {// this variable will have elements, and methods, to manipulate the graphical object
	vistable : { id : '' },
	tableid : 'tab',
	calorGradientIndexes : ['red', 'green', 'blue'],
	colorGradient : {0 : {min : [255,0,255], max : [255,255,0]},
				1 : { min : [255,255,0], max : [0,255,255]},
				2 : { min: [0,255,255], max : [255,0,255]}
				},
	drawMainTable : function (tableID) {
		if(typeof(tableID)=='undefined' || $('#'+tableID).length<=0){
			{	tableID = 'mainTable';
				$(document.createElement('table')).attr('id', tableID).attr('valign', 'top').appendTo('body');
			}
		
		graphs.tableid = $('#'+mainTable);
		
		};
	
	},

	initTable : function (skeleton_elmt) {
		if($('#'+graphs.tableid).length==0) {
			var tb = $(document.createElement('table'))
				.attr('id',graphs.tableid)
				.attr('style', 'width: 100%')
				.appendTo('#'+skeleton_elmt);
		}
		else {
			var tb = $('#'+graphs.tableid);
		}

		var headerColors = graphs.color2rgb(graphs.colorMap(data.variableSelection.length+1, [0,255,200],[0,255,255]));
		var h = $(document.createElement('tr')).attr('id', 'header').attr('height',data.rowHeight).appendTo('#'+graphs.tableid);
		
		//first the id
		$(document.createElement('td')).attr('id', 'header_id').attr('width',data.colWidth).attr('style','background-color: rgb('+headerColors[0]+')').html(data.identifierVar).appendTo('#header');

		for (var i=0; i<data.variableSelection.length; i++) {
			//then each varname
			var varName = data.variableSelection[i];
			var varName4ID =$.md5(data.variableSelection[i]);
			$(document.createElement('td')).attr('id', 'header_'+varName4ID).attr('width',data.colWidth).attr('style', 'background-color: rgb('+headerColors[i+1]+')').html(varName).appendTo('#header');
			//now the checkbox
			$(document.createElement('input')).attr('type','checkbox').attr('id', 'check_'+varName4ID).attr('control_var',varName).attr('checked', true).appendTo('#header_'+varName4ID).click(function (e) {
				
				var varName = $(this).attr('control_var');
				var checked = $(this).attr('checked');
				if(checked){
					//add the variable to the selection. Does the order matter?
					data.variableSelection.push(varName);
					graphs.remakeVisualization();
				}
				else {
					//recalculate the dendrogram, leaving this variable out; re-build table with the new order
					var ind = data.variableSelection.indexOf(varName);
					data.variableSelection.splice(ind, 1);
					if(data.variableSelection.length>0){
						graphs.remakeVisualization();
					}
					else {
						//dendrogram.ctx.clearRect(0,0,300,300);
						//go back tot he original order in the table and clean the dendrogram
						dendrogram.ctx.clearRect(0,0,dendrogram.width,dendrogram.height);
						graphs.orderTable(data.tableid, data.rawData, data.instance_ids);
					}
					
				}
			});
		}
	},
	
	remakeVisualization : function () {
		//zero values
		dendrogram.groupLabel = [];dendrogram.newGroups = [];
		//remake the matrix
		data.makeValueMatrix();
		//remake the dendrogram
		graphs.makeDendrogram();
		//reorder the table
		
		var newOrder = dendrogram.groupLabel;
		var newOrderIndex = dendrogram.newGroups;
		graphs.orderTable(data.tableid, data.rawData, newOrderIndex);
		graphs.drawPdistTable(dendrogram.p, newOrderIndex);

	},
	
	configureDendrogram : function (canvasid,len,flag) {
		dendrogram.canvasid = 'cvs';
		dendrogram.holder = canvasid;
        if(flag==0){
            dendrogram.width = len*40+100;
            dendrogram.height = len*30;
            dendrogram.groupGap=28;
        }else{
            dendrogram.width = 800;
            dendrogram.height = 600;
        }
		dendrogram.style = '';
		dendrogram.LineWidth = 1;
		dendrogram.orientation = 'left';
		dendrogram.topOffset = 1;
		dendrogram.bottomOffset = 150;
		dendrogram.leftOffset = 50;
		dendrogram.rightOffset = 50;
		dendrogram.border = 1;
		dendrogram.xticks = 0;
		dendrogram.yticks = 0;
		dendrogram.grid = 0;
	},
	
	makeDendrogram : function (rawData,len,flag) {
			
			//data for the dendrogram
			data.makeValueMatrix(rawData);
			//get p dist
			dendrogram.p=pdist(data.dendrogram.x,data.dendrogram.metric);
			//get linkage
			dendrogram.L = linkage(dendrogram.p, data.dendrogram.amalgamation);
			
			//finally draw the dendrogram!! 
			//will need declaring a few variables such that the dendrogram size matchs the table positions
			var n = data.dendrogram.x.length;
			var cW = dendrogram.height; var cH = dendrogram.height; var fL = (3*cW)/(4*n); var lL = cW-(cW/n)/2; var jp = (lL-fL)/(n-1);
            dendrogram.fL = fL;
		    dendrogram.lL = lL;
		    dendrogram.labels = data.dendrogram.labels;
		    //dendrogram.groupGap = jp;
		//save the ctx before rotate
			if(typeof(dendrogram.ctx)!=='undefined'){
				dendrogram.ctx =  dendrogram.canvas.getContext('2d');
				dendrogram.ctx.clearRect(0,0,cW,cH);
			}
			dendrogram.draw(dendrogram.L,len,flag);

			//restore the ctx after rotate
			dendrogram.ctx.restore();
		},

	orderTable : function(tableid, data, order) {
		if(typeof(order)=='undefined'){
			var order = [];
			for (var i in data) {
				order.push(i);
			}
		}

		var rows = $('#'+tableid+' > tbody').children();
		var rowsById = {}
		for (var i=1; i<rows.length; i++) {
			var id =$(rows[i]).attr('id');
			rowsById[id] = rows[i];
			$('#'+id).detach();
		}
		for (var i=0; i<order.length; i++) {
			var id = order[i];
			//now insert after the last
			$(rowsById[id]).appendTo('#'+tableid);
		}
	},

	colorMap : function (L, min_color, max_color) {
		//this gets the association between a color and a bin
			if(typeof(L)=='undefined'){ var L = 10; }
			if(typeof(min_color)=='undefined')
			{var min_color = [255,0,0]; var max_color =[0,255,0]; }
			
			//we are deadling with 3 variables: red, green and blue
			 var redIncrement = (max_color[0]-min_color[0])/(L-1);
			 var greenIncrement = (max_color[1]-min_color[1])/(L-1);
			 var blueIncrement = (max_color[2]-min_color[2])/(L-1);
			
			//now create the color array for each of the ninsOrdered
			var colors = [];
			
			 for (var i=0; i<L; i++) {
				colors.push([ min_color[0]+(redIncrement*i),  min_color[1]+(greenIncrement*i),  min_color[2]+(blueIncrement*i) ]);
			 }
			
			//data.colorLookUp = colorLookUp;
		  return colors;
	
	},

	color2rgb : function (colors) {
		
		var rgb = [];
		for (var i=0; i<colors.length; i++) {
			rgb[i] = Math.round(colors[i][0])+','+Math.round(colors[i][1])+','+Math.round(colors[i][2]);
		}
		return rgb;
	},

	drawPdistTable : function (p, newOrderIndex) {
		//we need an extra row, even if empty, to place this table in the sample position as the data heatmap
		var bins = freq(p);
		var colormap = color.map(bins[0].length,[255,255,255] ,[21,0,255]);
		var colorlookup = color.lookup(bins[0], colormap);
		var square = squareform(p);
		
		//order the matrix according to the way the lines were re-ordered for the dendrogram
		var orderedSquare = [];
		for (var i=0; i<newOrderIndex.length; i++) {
			if(typeof(orderedSquare[i])==='undefined'){
				orderedSquare[i] = [];
			}
			var indI = newOrderIndex[i];
			var line = square[indI];
			//reorder line
			for (var j=0; j<newOrderIndex.length; j++) {
				var indJ = newOrderIndex[j];
				var val = line[indJ];
				orderedSquare[i][j] = val;
			}

		}
		//clear the previously created data, if exists
		$('#pdist').remove();
		
		//make a table with the data;
		$(document.createElement('table')).attr('id','pdist').attr('border', '0').appendTo('#middle');
		$(document.createElement('tr')).attr('id','head').attr('height',data.rowHeight).append($(document.createElement('td')).html('&nbsp;&nbsp;').attr('style', 'height: '+$('#header').height()+'px')).appendTo('#pdist');
		for (var i=0; i<orderedSquare.length; i++) {
			$(document.createElement('tr')).attr('id','square_tr_'+i).attr('height',data.rowHeight).appendTo('#pdist');
			for (var j=0; j<orderedSquare[i].length; j++) {
				var rgbcolor = color.toRgb(colorlookup[orderedSquare[i][j]]);;
				var roundedV = Math.round(orderedSquare[i][j]*100)/100;
				
				$(document.createElement('td')).attr('id', 'square_tr_'+i+'_td'+j).attr('style', 'background-color: rgb('+rgbcolor+')').html(roundedV).appendTo('#square_tr_'+i);	
			}
		}
	},

	drawOptions : function () {
			
			
				$('#options')
					.append($(document.createElement('div'))
						.attr('class', 'rowLabel')
						.append($(document.createElement('label')).html('Dendrogram options'))
						.click(
							function () {
								$('#dm_holder').toggle();
								$('#am_holder').toggle();
								$('#cm_holder').toggle();
							})	
					)
					.append($(document.createElement('div'))
						.attr('id','dm_holder')
						.attr('class', 'row')
						.hide()
						.append($(document.createElement('label')).html('Distance metric'))
						.append($(document.createElement('select'))
							.attr('size','2')
							.click(
								function () {
									var dm =$('#distancemetric').val();
									if(dm!=='new_dm') { 
										$('#dm_function').hide();
										data.dendrogram.metric = dm;
										graphs.remakeVisualization();
									}
									else { 
										$('#dm_function').show();
									}
									

								}	
							)
							.attr('id', 'distancemetric')
							.attr('name',"distancemetric")
							.attr('class','required')
							.append($(document.createElement('option')).attr('value', 'euclidean').attr('selected','on').html('euclidean'))
							.append($(document.createElement('option')).attr('value', 'city block').html('city block'))
							.append($(document.createElement('option')).attr('value', 'correlation').html('correlation'))
							.append($(document.createElement('option')).attr('value', 'minkowski').html('minkowski'))
							.append($(document.createElement('option')).attr('value', 'new_dm').html('new function...'))
						)
					)
					.append($(document.createElement('div'))
						.attr('id', 'am_holder')
						.attr('class', 'row')
						.hide()
						.append($(document.createElement('label')).html('Amalgamation Schema'))
						.append($(document.createElement('select'))
						.attr('size','2')
							.click(
								function () {
									var am =$('#amalgamation').val();
									if(am!=='new_am') { 
										
										$('#am_function').hide();
										data.dendrogram.amalgamation = am;
										graphs.remakeVisualization();
									}
									else { 
										$('#am_function').show();
									}
									

								}	
							)
							.attr('id', 'amalgamation')
							.attr('name',"amalgamation")
							.attr('class','required')
							.append($(document.createElement('option')).attr('value', 'average').attr('selected','on').html('average'))
							.append($(document.createElement('option')).attr('value', 'single').html('single'))
							.append($(document.createElement('option')).attr('value', 'complete').html('complete'))
							.append($(document.createElement('option')).attr('value', 'new_am').html('new function...'))
						))
					.append($(document.createElement('div'))
						.attr('class', 'row')
						.attr('id', 'cm_holder')
						.hide()
						.append($(document.createElement('label')).html('Correlation matrix heatmap'))
						.append($(document.createElement('input'))
							.attr('id','chk_view_dm')
							.attr('name','chk_view_dm')
							.attr('type', 'checkbox')
							.attr('class', 'required')
							.click(
								function () {
									if($('#chk_view_dm').is(':checked')){
										$('#middle').show("slow")
									}
									else {$('#middle').hide();}
								}			
							)			
						)
						
						)

	}
	
}