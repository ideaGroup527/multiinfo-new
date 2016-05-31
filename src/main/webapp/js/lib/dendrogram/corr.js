function corr(x,y, type) {
	//Helena F Deus, 20100609
	//calculate correlation, method defined in type (Pearson, Kendall, Tau)
	//x and y are same length vectors; type can be Pearson only, so far
	
	//x and y should have the same length
	if (x.length!==y.length) {
		console.log('x and y lengths don\'t match :-(');
		return
	}
	if(typeof(type)==='undefined'){
		var type = 'Pearson';
	}

	var N = x.length;
	//calculate the sum of the multiplication
	var sumX = 0;var sumX2 = 0;var sumY = 0;var sumY2 = 0;var sumXxY = 0;
	for (var i=0; i<N; i++) {
		sumXxY += x[i]*y[i];
		sumX += x[i];
		sumX2 += Math.pow(x[i],2);
		sumY += y[i];
		sumY2 += Math.pow(y[i],2);
	}
	var r;
	if(type==='Pearson'){
		r = (N*sumXxY - (sumX*sumY))/Math.sqrt((N*sumX2-Math.pow(sumX,2))*(N*sumY2-Math.pow(sumY,2)));
	}

 return r;
 }