function NormalCurve(config) {
    Charts.call(this, config.data, config.opt, config.content);

    this.N_POINT = 1000;//点数量
    
    this.easingFunc=function (x) {
        var q=1,u=0;
        var a = 1.0/(Math.sqrt(2*Math.PI)*q);
        var b = Math.exp((-1)*((x-u)*(x-u))/(2*q*q));
        return a*b;
    };
    this.format = function () {
        var dataAll = [];
        for (var i = -this.N_POINT; i <= this.N_POINT; i++) {
            var x = i/100;
            var y = this.easingFunc(x);
            dataAll.push([x, y]);
        }

        return dataAll;
    };
    this.option={
        title: {
            text: config.title,
            x: 'center', //标题文本的位置
            y: 0,
            textStyle: {
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        grid: {
            show: true,
            borderWidth: 0,
            backgroundColor: '#fff',
            shadowColor: 'rgba(0, 0, 0, 0.3)',
            shadowBlur: 2
        },
        xAxis: {
            type: 'value',
            show: true,
            min: -5,
            max: 5
        },
        yAxis: {
            type: 'value',
            show: true,
            min: 0,
            max: .5
        },
        series: {
            name: name,
            type: 'line',
            data: this.format(),
            showSymbol: false,
            animationEasing: name,
            animationDuration: 1000
        }
    };
}