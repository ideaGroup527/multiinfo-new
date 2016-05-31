//freq.js Calculate frequency of bins of observed values
//Helena F. Deus (helenadeus@gmail.com)
//09-08-20

function freq(obs) {
	//freq returns the bins and the frequency of data as a single object where freq[0] is the bins, freq[1] is the absolute frequency and freq[2] is the relative frequency
	
	var bins = [];
	var freq = {};
	for (var j = 0; j < obs.length; j++) {
		 if(!in_array(obs[j], bins))
		{array_push(bins, obs[j]);
		freq[obs[j]] = 1;
		}
		else {
		freq[obs[j]] = freq[obs[j]]+1;	
		}
		
		
	}
	var rel_freq = {};
	for (var k in freq) {
	   rel_freq[k]=  freq[k]/obs.length;
	}
	
	function in_array(needle,haystack,strict)
	{
		var found=false,key,strict=!!strict;for(key in haystack){if((strict&&haystack[key]===needle)||(!strict&&haystack[key]==needle)){found=true;break;}}
		return found;
	}

	function array_push(array)
	{	
		var i,argv=arguments,argc=argv.length;for(i=1;i<argc;i++){array[array.length++]=argv[i];}
		return array.length;
	}

	return (new Array(bins, freq, rel_freq));
}