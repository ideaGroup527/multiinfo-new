function squareform(x) {
	//x is a vector such as the one returned from pdist, to build a symmetric matrix (diagonal values = 0);
	
	//the x vector translates into a symmetric matrix; the matrix can be built from the top-left to the right bottom until there is no more data to fill; 
	var square = matrixdim(x);

	//now replace the zeros with the data in x
	var k = 0;
	for (var i=0; i<square.length; i++) {
		for (var j=i+1; j<square[i].length; j++) {
			square[i][j] = x[k];
			square[j][i] = x[k];
			k++;
		}
	}
	return square;
	

}

function matrixdim(x) {
	//to discover the dimension of the matrix, fill it with zeros until there is no more data
	var i = 0;var y= [];var square = [];var k = 0;
	while (1) {
		
		square[i] = [];square[i][i] = 0;
		for (var j=0; j<i; j++) {
			square[i][j] = 0;
			square[j][i] = 0;
			k++;
			if(k>=x.length){
				return square;
			}
		}
		i++;
	}
	return square;
}