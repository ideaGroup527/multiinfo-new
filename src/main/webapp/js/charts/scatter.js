function Scatter(config) {
    Charts.call(this, config.data, config.opt, config.content);
    this.format = function (data) {//转换格式
        
        var dataAll = [];
        var depend = data.dependentVariable.varietyName,
            independent = data.independentVariable[0].varietyName;
        //TODO 这里暂时先写为一个因变量的情况
            
        for (var i = 0; i < data.resDataMap[depend].length; i++) {
            dataAll.push([data.resDataMap[depend][i], data.resDataMap[independent][i]]);
        }
        return dataAll;
    };
    this.option={
        title: {
            text: config.title, //主标题文本
            x: 'center', //标题文本的位置
            y: 0,
            textStyle: {
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        tooltip: {
            formatter: '{a}: ({c})' //鼠标移动到点的提示框 字符串模板http://echarts.baidu.com/option.html#tooltip.formatter
        },
        xAxis: [
            {min: this.data.resDataMap.xAxis.min, max: this.data.resDataMap.xAxis.max} //x轴的最大最小值
        ],
        yAxis: [
            {min: this.data.resDataMap.yAxis.min, max: this.data.resDataMap.yAxis.max} //y轴的最大最小值
        ],
        series: [
            {
                smooth: true,
                name: '点', //系列名称，用于tooltip的显示
                type: "scatter", //设置绘制的为散点图，默认为直线
                data: this.format(this.data),  //设置每一个散点的位置
                markLine: null //图表标线
                //TODO 画线的再添加
            }
        ]
    };
}