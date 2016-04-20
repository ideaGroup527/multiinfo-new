function Pie(config) {
    Charts.call(this, config.data, config.opt, config.content);

    this.format = function (data) {//转换格式

        var dataAll = {k: [],kv:[]};

        for(var key in data.resDataMap){
            dataAll.k.push(key);

            var _kv={};
            _kv.name=key;
            _kv.value=data.resDataMap[key].resultData.total;
            dataAll.kv.push(_kv);
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
            orient: 'vertical',
            left: 'left',
            data: this.format(config.data).k
        },
        tooltip: {
            trigger: 'item',
            axisPointer: {
                type: 'shadow'
            }
        },
        series : [
            {
                name: config.data.reportTitle,
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:this.format(config.data).kv,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
}