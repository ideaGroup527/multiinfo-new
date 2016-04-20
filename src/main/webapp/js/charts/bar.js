function Bar(config) {
    Charts.call(this, config.data, config.opt, config.content);

    this.format = function (data) {//转换格式

        var dataAll = {k: [],v:[]};

        for(var key in data.resDataMap){
            dataAll.k.push(key);
            dataAll.v.push(data.resDataMap[key].resultData.total);
        }
        return dataAll;
    };
    this.data=this.format(config.data);
    this.option = {
        title: {
            text: config.title, //主标题文本
            x: 'center', //标题文本的位置
            y: 0,
            textStyle: {
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            data:['蒸发量','降水量','平均温度']
        },
        xAxis: [
            {
                type: 'category',
                data: this.format(config.data).k
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: config.data.reportTitle|"总量",
                min: 0,
                max: '{dataMax}',
                interval: 50,
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name:config.data.reportTitle|"总量",
                type:'bar',
                data:this.data.v
            }
        ]
    };
}