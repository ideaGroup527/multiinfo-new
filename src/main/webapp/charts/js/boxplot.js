function Boxplot(config) {
    Charts.call(this, config.data, config.opt, config.content);

    this.format = function (data) {//转换格式

        var dataAll = {xAxis:[], boxData: []};

        for(var key in data.resDataMap){
            dataAll.xAxis.push(key);

            var value = data.resDataMap[key];
            dataAll.boxData.push([
                value.resultData.percentiles[0].data,
                value.resultData.percentiles[1].data,
                value.resultData.percentiles[2].data,
                value.resultData.percentiles[3].data,
                value.resultData.percentiles[4].data
            ]);
        }
        return dataAll;
    };
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
        legend: {
            data: ['line', 'line2', 'line3']
        },
        tooltip: {
            trigger: 'item',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
        },
        xAxis: {
            type: 'category',
            data: this.format(config.data).xAxis,
            boundaryGap: true,
            nameGap: 30,
            splitArea: {
                show: false
            },
            axisLabel: {
                formatter: '{value}'
            },
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            name: '',
            splitArea: {
                show: true
            }
        },
        series: [
            {
                name: 'boxplot',
                type: 'boxplot',
                data: this.format(config.data).boxData,
                tooltip: {
                    formatter: function (param) {
                        return [
                            'Experiment ' + param.name + ': ',
                            'upper: ' + param.data[4],
                            'Q1: ' + param.data[1],
                            'median: ' + param.data[2],
                            'Q3: ' + param.data[3],
                            'lower: ' + param.data[0]
                        ].join('<br/>')
                    }
                }
            }
        ]
    };
}