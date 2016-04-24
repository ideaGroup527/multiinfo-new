function Bar(config) {
    Charts.call(this, config.data, config.opt, config.content);

    this.format = function (data) {//转换格式

        var dataAll = {xAxis: [], sub_key: [], value: []};

        for (var key in data.resDataMap) {
            dataAll.xAxis.push(key);
        }

        var sub = 0, iSub_key = 0;
        for (var key in data.resDataMap) {
            if (data.resDataMap[key].resultData != null) {

                sub = iSub_key = 1;
                break;
            }
            if (sub == 1) {
                break;
            }

            for (var sub_key in data.resDataMap[key]) {
                dataAll.sub_key.push(sub_key);
                sub = 1;
            }
        }
        if (iSub_key == 1) {

            var value = {};
            value.name = data.reportTitle;
            value.type = "bar";
            var dd = [];
            for (var key in data.resDataMap) {
                dd.push(data.resDataMap[key].resultData.total);
            }
            value.data = dd;
            dataAll.value.push(value);
        } else {

            for (var i = 0, v = null; (v = dataAll.sub_key[i]) != undefined; i++) {
                var value = {};
                value.name = v;
                value.type = "bar";
                var dd = [];
                for (var key in data.resDataMap) {
                    dd.push(data.resDataMap[key][v].resultData.total);
                }
                value.data = dd;
                dataAll.value.push(value);
            }
        }


        return dataAll;
    };

    this.data = this.format(config.data);

    console.log(this.data);
    this.option = {
        title: {
            text: config.title, //主标题文本
            x: 'left', //标题文本的位置
            y: 0,
            textStyle: {
                fontSize: 16,
                fontWeight: 'normal'
            }
        },
        legend: {
            data: this.data.sub_key
        },
        tooltip: {
            trigger: 'item',
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: [
            {
                type: 'category',
                data: this.data.xAxis
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: config.data.reportTitle | "总量",
                axisLabel: {
                },
                splitNumber: 20

            }
        ],
        series: this.data.value
    };
}