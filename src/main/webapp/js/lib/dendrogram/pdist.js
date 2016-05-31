//include the dependency functions
if(!self.corr){
	$('body').append("<script type='text/javascript' src='js/lib/dendrogram/corr.js'></script>");
}

function pdist(x, method,p) {
	//x is a matrix, method can be euclidean, city block, minkowsi or correlation
	if(typeof(method)==='undefined'){
		var method = 'euclidean';
	}
	if(method==='minkowski' && typeof(p)==='undefined'){
		var p = 2;
	}
	
	if(method==='euclidean'){
		var p = 2;
	}

	if(method==='city block'){
		var p = 1;
	}

	
	//make sure there are more than 2 rows
	if (x.length<2) {
		alert('can\'t calculate pairwise distance between less than 2 points :-(');
		return;
	}
	var pdist = [];
	for (var i=0; i<x.length; i++) {
		//start at row 0, compare with row 1, row 2 and so on; them go to row 1, compare with row 2, row 3 and so on; the next look always starts at i+1 even though it goes only to the point of x.lengh
		if(i+1<x.length)
		{
			for (var n=i+1; n<x.length; n++) {
				
					//calculate the difference between each col of two consecutive rows
					if(x[i].length!==x[n].length){
						alert('row '+i+' and '+n+' do not have the same length !');
						return;
					}
					var dsum = 0;
					
					if (method==='euclidean' || method==='minkowski' || method==='city block') {
						for (var j=0; j<x[i].length; j++) {
							
							var d = Math.pow(Math.abs(x[n][j]-x[i][j]), p);
							dsum += d;
						}
						pdist.push(Math.pow(dsum, 1/p));
					}
					else if (method==='correlation') {
						if(!self.corr){
							console.log('Could not find correlation function anywhere. Try manually adding it to you script: http://compstats.mathbiol-lena.googlecode.com/hg/corr.js');
							return;
						}
						var d = 1-corr(x[n], x[i]);
						pdist.push(d);
					}
					
				
			}
		}
	}

	return pdist;
}