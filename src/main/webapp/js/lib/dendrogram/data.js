var data = {
		bins : {},
		frequenciesData : {},
		colorLookup : {},	
		rawData : {},
		min_color : {},
		max_color : {},
		coloredData : {},
		values : {},
		matrix : {},
		colorMatrix : {},
		ids : {},
		instances : [],
		dendrogram : {x:[], labels: [] },
		repeatedInstances : {},
		instanceLabels : {},
		identifierVar :'',
		
		objectify : function () {
		//turn data as array into object	
		var dataobj = {};
			for (var i=0; i<data.rawData.length; i++) {
				var id = escape(data.rawData[i][data.identifierVar]);
				dataobj[id] = {};
				for (var j in data.rawData[i]) {
					if(j!==data.identifierVar){
						dataobj[id][j] = data.rawData[i][j];	
					}
				}
			}
			data.rawDataObj = dataobj;
		},
		
		executeSPARQLQuery : function (query, next_eval) {
			data.sparqlQuery = query;
			data.sparqlCache = 'cache_'+$.md5(query);
			data.executeSPARQLQuery.nextEval = next_eval; 
			
			
			if(localStorage.getItem(data.sparqlCache)===null){
				$.getJSON(data.sparqlQuery+'&format=json&callback=?', function (queryResult) {
					data.sparqlResult = queryResult;
					localStorage.setItem(data.sparqlCache, $.toJSON(queryResult));
					data.executeSPARQLQuery(data.sparqlQuery, data.executeSPARQLQuery.nextEval);
				});
			}
			else {
				data.sparqlResult = $.parseJSON(localStorage.getItem(data.sparqlCache));
				if(typeof(data.executeSPARQLQuery.nextEval)!=='undefined'){
					eval (data.executeSPARQLQuery.nextEval+'(data.sparqlResult)');
				}
			}
		 },

		colorData : function (varname, min_color, max_color) {
			//this gets the association between a color and a bin
			this.sortNumber = function(a, b) {	return a - b;};  			
			if(typeof(min_color)=='undefined')
			{var min_color = [255,0,0]; data.min_color = min_color; 
			var max_color =[0,255,0]; data.max_color = max_color; 
			}
			var bins = data.frequenciesData[varname][0];
			//we are deadling with 3 variables: red, green and blue
			var redIncrement = (max_color[0]-min_color[0])/Math.max(1,bins.length-1);
			var greenIncrement = (max_color[1]-min_color[1])/Math.max(1,bins.length-1);
			var blueIncrement = (max_color[2]-min_color[2])/Math.max(1,bins.length-1);
			
			//sort the bins for the look up table if the are numeric
			 var binsOrdered = [];
			
			 if(typeof(bins[0]*1)=='number'){
				binsOrdered = bins.sort(this.sortNumber);
			 }
			 else {
				binsOrdered = bins.sort();
			 }
			
			//now create the color array for each of the ninsOrdered
			var colorArray = [];
			
			 for (var i=0; i<binsOrdered.length; i++) {
				colorArray.push([ min_color[0]+(redIncrement*i),  min_color[1]+(greenIncrement*i),  min_color[2]+(blueIncrement*i) ]);
			 }
			 data.colorLookup[varname] = {'bins':binsOrdered, 'colors':colorArray};
			//data.colorLookUp = colorLookUp;
		  return false;
		},

		retrieveData : function (query) {
		  
		  $.getJSON(query+'&format=json&callback=?', function (ans) {
			
			data.rawData = ans;
			//save in local storage
			data.cache = $.toJSON(data.rawData);
			localStorage[data.cacheName] = data.cache;
			run();//go back to the main func
			return false;
			});
		},
			
		collectData : function (varName) {
			if(!self.freq){
				$('head').append('<script type="text/javascript" src="freq.js"></script>');
			}

			
			data.values[varName] = [];data.ids[varName]=[];data.instanceLabels[varName]=[];
			for (var i=0; i<data.rawData.length; i++) {
				//we may have several variable; for each variable, a different color
						if(typeof(data.valiableAlias)!=='undefined') 
							{var var2use =data.valiableAlias[varName];}
						else {
							var var2use =varName;
						}
						var observation = data.rawData[i][var2use];
						data.values[varName].push(observation);

						//also we will want to grow each of the item(unique obs)
						if(isNaN(data.rawData[i][data.identifierVar]*1)){
							var instanceLabel = data.rawData[i][data.identifierVar];
						}
						else {
							var instanceLabel = 'I'+data.rawData[i][data.identifierVar];
						}
						var instID = i;
						
						//detect also if this instance is repeated; it is ok to be repeated for another variable, but not the same
						if(typeof(data.matrix[instID])=='undefined'){
							data.matrix[instID] = {};
						}
						
						data.matrix[instID][varName] = observation;
						
						
						data.ids[varName].push(instID);
						data.instanceLabels[varName].push(instanceLabel);

			}				

			data.frequenciesData[varName] = freq(data.values[varName]);
			return false;
		  		
		},

		paintData : function (varname) {
			
			//now create an array that lists the colors instead of the raw data
			data.coloredData[varname] = [];
					
			for (var i=0; i<data.values[varname].length; i++) {
				var pos = data.colorLookup[varname].bins.indexOf(data.values[varname][i]);
				if(i!==-1){
					var color = data.colorLookup[varname].colors[pos];
					data.coloredData[varname].push(color);
					var idOrd = data.ids[varname][i];
					if(typeof(data.colorMatrix[idOrd])=='undefined'){
						data.colorMatrix[idOrd] = {};
					}
					data.colorMatrix[idOrd][varname] = color;

				}
				else {
					console.log("Something wrong with obs "+data.values[i]);
					//uh ho... what went wrong here?
				}
			}
			return false;
		},
		
		makeValueMatrix : function (rawData, variableSelection, instanceSelection) {
			//organizes the data into labels and a matrix useful for the dendrogram;
			//reset X
			data.dendrogram.x = [];
			data.dendrogram.labels = [];
			
			if(typeof(rawData)==='undefined'){
				rawData = data.rawData;
			}
			if(typeof(variableSelection)==='undefined'){
				variableSelection = data.variableSelection;
			}
			if(typeof(instanceSelection)==='undefined'){
				instanceSelection = data.instanceSelection;
			}
			
			for (var i=0; i<rawData.length; i++) {
				var id = rawData[i][data.identifierVar];
				data.dendrogram.labels.push(id);
				var tmp = [];
				for (var j=0; j<variableSelection.length; j++) {
					var varName = data.variableSelection[j];
					if(typeof(data.valiableAlias)!=='undefined') 
							{var var2use = data.valiableAlias[varName];}
						else {
							var var2use = varName;
						}
					if(typeof(rawData[i][var2use])!=='undefined'){
						tmp.push(rawData[i][var2use]);
					}
				}
				data.dendrogram.x.push(tmp);
			}
			
		}
};