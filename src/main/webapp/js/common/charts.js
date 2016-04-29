/*基类*/
function Charts(data, opt, container) {

    this.data = data;//数据json
    this.opt = opt;//配置json
    this.container = container;//散点图容器的id
    this.option = null;

    this.render = function () {
        //渲染到页面
        var myChart = echarts.init(document.getElementById(this.container)); //这里dom选择需要为js对象
        myChart.setOption(this.option);
    }
}

/*散点图*/
function Scatter(config) {
    Charts.call(this, config.data, config.opt, config.container);
    this.format = function (data) {//转换格式

        var dataAll = [];
        var depend = data.dependentVariable.varietyName,
            independent = data.independentVariable[0].varietyName;

        for (var i = 0; i < data.resDataMap[depend].length; i++) {
            dataAll.push([data.resDataMap[depend][i], data.resDataMap[independent][i]]);
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

/*饼图*/
function Pie(config) {
    Charts.call(this, config.data, config.opt, config.container);

    this.format = function (data) {//转换格式

        var dataAll = {k: [], kv: []};

        for (var key in data.resDataMap) {
            dataAll.k.push(key);

            var _kv = {};
            _kv.name = key;
            _kv.value = data.resDataMap[key].resultData.total;
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
        series: [
            {
                name: config.data.reportTitle,
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: this.format(config.data).kv,
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

/*柱状图*/
function Bar(config) {
    Charts.call(this, config.data, config.opt, config.container);

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

    var dataAll = this.format(config.data);

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
            data: dataAll.sub_key
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
                data: dataAll.xAxis,
                axisLabel: {
                    show: true,
                    interval: 0,
                    formatter: function (val) {
                        return getEchartBarXAxisTitle(val, dataAll.xAxis, null, 1000)
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: config.data.reportTitle | "总量",
                axisLabel: {},
                splitNumber: 20

            }
        ],
        series: dataAll.value
    };
}

/*箱线图*/
function Boxplot(config) {
    Charts.call(this, config.data, config.opt, config.container);
    this.format = function (data) {//转换格式

        var dataAll = {xAxis: [], boxData: [], outliers: []}, j = 0;

        for (var key in data.resDataMap) {
            dataAll.xAxis.push(key);

            var value = data.resDataMap[key];
            dataAll.boxData.push([
                value.resultData.percentiles[0].data,
                value.resultData.percentiles[1].data,
                value.resultData.percentiles[2].data,
                value.resultData.percentiles[3].data,
                value.resultData.percentiles[4].data
            ]);

            for (var i = 0, v = null; (v = value.resultData.errPercentiles[i]) != undefined; i++) {
                dataAll.outliers.push([j, v]);
            }
            j++;
        }
        return dataAll;
    };
    var dataAll = this.format(config.data);
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
            data: dataAll.xAxis,
            boundaryGap: true,
            nameGap: 30,
            splitArea: {
                show: false
            },
            axisLabel: {
                show: true,
                interval: 0,
                formatter: function (val) {
                    return getEchartBarXAxisTitle(val, dataAll.xAxis, null, 1000)
                }
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
                data: dataAll.boxData,
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
            },
            {
                name: 'outlier',
                type: 'scatter',
                data: dataAll.outliers
            }

        ]
    };
}

/*正态分布*/
function NormalCurve(config) {
    Charts.call(this, config.data, config.opt, config.container);

    this.N_POINT = 1000;//点数量

    this.easingFunc = function (x) {
        var q = 1, u = 0;
        var a = 1.0 / (Math.sqrt(2 * Math.PI) * q);
        var b = Math.exp((-1) * ((x - u) * (x - u)) / (2 * q * q));
        return a * b;
    };
    this.format = function () {
        var dataAll = [];
        for (var i = -this.N_POINT; i <= this.N_POINT; i++) {
            var x = i / 100;
            var y = this.easingFunc(x);
            dataAll.push([x, y]);
        }

        return dataAll;
    };
    this.option = {
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

/*折线图*/
function Line(config) {
    Charts.call(this, config.data, config.opt, config.container);

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
            value.type = "line";
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
                value.type = "line";
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

    var dataAll = this.format(config.data);

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
            data: dataAll.sub_key
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
                data: dataAll.xAxis,
                axisLabel: {
                    show: true,
                    interval: 0,
                    formatter: function (val) {
                        return getEchartBarXAxisTitle(val, dataAll.xAxis, null, 1000)
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: config.data.reportTitle | "总量",
                axisLabel: {},
                splitNumber: 20

            }
        ],
        series: dataAll.value
    };
}

/*基础折线图*/
function BasicLine(config) {
    Charts.call(this, config.data, config.opt, config.container);

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
            value.type = "line";
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
                value.type = "line";
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
                axisLabel: {},
                splitNumber: 20

            }
        ],
        series: this.data.value
    };
}

/*丁氏图*/
function DingChart(config) {
    this.data = config.data;
    this.calculateMethod = config.calculateMethod;
    this.container = config.container;
    this.render = function () {

        var col = config.data.colVarList.length,//行数和列数
            row = config.data.rowVarList.length;
        var m_width = 80, m_height = 40; //一小格子的高宽
        var a = m_width / 2, b = m_height / 2;//椭圆长半轴和短半轴

        var width = (col + 1) * m_width,
            height = (row + 1) * m_height;
        var canvas = document.createElement("canvas");
        $(canvas).css({
            'display': 'block',
            'position': "absolute",
            "left": '50%',
            "margin-left": "-" + width / 2 + "px"
        });
        canvas.setAttribute('width', width.toString());
        canvas.setAttribute('height', height.toString());
        document.getElementById(this.container).appendChild(canvas);
        var ctx = canvas.getContext('2d');
        //坐标轴样式配置
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#aaa";
        ctx.textAlign = 'center';

        var curvePoint = new Array(), curvePoint2 = new Array();//曲线的点集合
        for (var i = 0; i < row; i++) {
            curvePoint[i] = new Array();
            curvePoint2[i] = new Array();
        }

        //横线
        for (var i = 1; i <= row + 1; i++) {
            var y = i * m_height;
            ctx.beginPath();
            ctx.moveTo(m_width, y);
            ctx.lineTo(width, y);
            ctx.stroke();
        }
        //纵线
        for (var i = 1; i <= col + 1; i++) {
            var x = i * m_width;
            ctx.beginPath();
            ctx.moveTo(x, m_height);
            ctx.lineTo(x, height);
            ctx.stroke();
        }
        //y轴
        for (var i = 0; i < row; i++) {
            var y = (i + 1) * m_height;
            ctx.save();
            ctx.textAlign = "right";
            this.wrapText(ctx, config.data.rowVarList[i].varietyName, 70, y + m_height, m_width, 15);
            ctx.stroke();
        }
        //x轴
        for (var i = 0; i < col; i++) {
            var x = (i + 1) * m_width;
            ctx.save();
            ctx.textAlign = "center";
            this.wrapText(ctx, config.data.colVarList[i].varietyName, x + m_width / 2, m_height, m_width, 15);
            ctx.stroke();
        }

        var n_point = point = -1;
        var _a, _b;
        //椭圆
        if (this.calculateMethod == 0) { //行
            for (var i = 0; i < row; i++) {
                for (var j = 0; j < col; j++) {

                    _a = a;
                    _b = b * config.data.resData[i][j];
                    _a = _a < .04 ? .04 : _a;
                    _b = _b < .04 ? .04 : _b;
                    if ((++n_point) % col == 0) {
                        point++;
                    }
                    curvePoint[point].push({x: (j + 1) * m_width + a, y: (i + 1) * m_height + b - _b});
                    curvePoint2[point].push({x: (j + 1) * m_width + a, y: (i + 2) * m_height - b + _b});
                    this.EllipseTwo(ctx, (j + 1) * m_width + a, (i + 1) * m_height + b, _a, _b);
                }
            }
        } else if (this.calculateMethod == 1) {  //列
            for (var i = 0; i < col; i++) {
                for (var j = 0; j < row; j++) {
                    _a = a * config.data.resData[j][i];
                    _b = b;
                    _a = _a < .04 ? .04 : _a;
                    _b = _b < .04 ? .04 : _b;

                    if ((++n_point) % row == 0) {
                        point++;
                    }
                    curvePoint[point].push({x: (i + 1) * m_width + a - _a, y: (j + 2) * m_height - _b});
                    curvePoint2[point].push({x: (i + 1) * m_width + a + _a, y: (j + 1) * m_height + _b});
                    this.EllipseTwo(ctx, (i + 1) * m_width + a, (j + 1) * m_height + b, _a, _b);
                }
            }
        } else if (this.calculateMethod == 2) {  //全
//            _a = a * config.data.resData[j][i];
//            _b = b * config.data.resData[j][i];
            for (var i = 0; i < col; i++) {
                for (var j = 0; j < row; j++) {
                    _a = a * config.data.resData[j][i];
                    _b = b * config.data.resData[j][i];
                    _a = _a < .04 ? .04 : _a;
                    _b = _b < .04 ? .04 : _b;
                    if ((++n_point) % row == 0) {
                        point++;
                    }
//            curvePoint[point].push({x: (i + 1) * m_width + a - _a, y: (j + 2) * m_height - _b});
//            curvePoint2[point].push({x: (i + 1) * m_width + a + _a, y: (j + 1) * m_height + _b});
                    this.EllipseTwo(ctx, (i + 1) * m_width + a, (j + 1) * m_height + b, _a, _b);
                }
            }
        } else {
            alert('calculateMethod参数错误！');
            return false;
        }

        //曲线
        var paper = Raphael(this.container, width, height);
        $(paper.canvas).css({
            'display': 'block',
            'z-index': '1000',
            'position': "absolute",
            "left": '50%',
            "margin-left": "-" + width / 2 + "px"
        });
        var tip = paper.rect(10, 20, 100, 60).attr({
            'stroke': "#333",
            "fill": "rgba(0,0,0,0.5)",
            'stroke-width': 1,
            r: 5,
            'z-index': '1000'
        }).hide();

        //曲线-左侧
        for (var n = 0, v = null; (v = curvePoint[n]) != undefined; n++) {
            var p;
            var cirs = [];//圆点集合
            for (var i = 0, ii = v.length; i < v.length; i++) {
                var point = v[i];
                var x = point.x;
                var y = point.y;
                if (!i) {
                    p = ["M", x, y, "C", x, y];
                }
                if (i && i < ii - 1) {
                    var point1 = v[i - 1];
                    var point2 = v[i + 1];
                    var a = this.getAnchors(point1.x, point1.y, x, y, point2.x, point2.y);//获取锚点
                    p = p.concat([a.x1, a.y1, x, y, a.x2, a.y2]);
                }
                cirs.push(paper.circle(x, y).attr({fill: "#D48366", stroke: "#D48366", r: 1}));
            }

            paper.path(p.concat([x, y, x, y])).attr({stroke: "#D48366"});

            //修改cirs层级和事件
            for (var i = 0; i < cirs.length; i++) {
                cirs[i].node.parentNode.appendChild(cirs[i].node);
                cirs[i].hover(function (e) {
                    this.animate({'r': 6}, 100, 'easeInBounce');
                    tip.show().animate({"x": e.layerX, "y": e.layerY}, 200, "wiggle");
                    tip.node.parentNode.appendChild(tip.node)
                }, function () {
                    this.animate({'r': 1}, 100, 'easeInBounce');
                    tip.hide();
                })
            }
        }
        //曲线-右侧
        for (var n = 0, v = null; (v = curvePoint2[n]) != undefined; n++) {
            var p;
            var cirs = [];//圆点集合
            for (var i = 0, ii = v.length; i < v.length; i++) {
                var point = v[i];
                var x = point.x;
                var y = point.y;
                if (!i) {
                    p = ["M", x, y, "C", x, y];
                }
                if (i && i < ii - 1) {
                    var point1 = v[i - 1];
                    var point2 = v[i + 1];
                    var a = this.getAnchors(point1.x, point1.y, x, y, point2.x, point2.y);//获取锚点
                    p = p.concat([a.x1, a.y1, x, y, a.x2, a.y2]);
                }
                cirs.push(paper.circle(x, y).attr({fill: "#D48366", stroke: "#D48366", r: 1}));
            }

            paper.path(p.concat([x, y, x, y])).attr({stroke: "#D48366"});

            //修改cirs层级和事件
            for (var i = 0; i < cirs.length; i++) {
                cirs[i].node.parentNode.appendChild(cirs[i].node);
                cirs[i].hover(function (e) {
                    this.animate({'r': 6}, 100, 'easeInBounce');
                    tip.show().animate({"x": e.layerX, "y": e.layerY}, 200, "wiggle");
                    tip.node.parentNode.appendChild(tip.node)
                }, function () {
                    this.animate({'r': 1}, 100, 'easeInBounce');
                    tip.hide();
                })
            }
        }
        //控制按钮
        var btn = '<svg style="position: absolute;left: 0;z-index: 99999;width: 75px;height: 23px;"><g class="button" cursor="pointer"\
            onmouseup="showCurve(this)">\
            <rect x="20" y="1" rx="5" ry="5"\
            width="52" height="22" fill="#D48366"/>\
            </g></svg>';
        $("#" + this.container).append(btn);
    };
    //画一个椭圆
    this.EllipseTwo = function (context, x, y, a, b) {
        context.save();
        context.fillStyle = "#CC5B58";
        var r = (a > b) ? a : b;
        var ratioX = a / r;
        var ratioY = b / r;
        context.scale(ratioX, ratioY);
        context.beginPath();
        context.arc(x / ratioX, y / ratioY, r, 0, 2 * Math.PI, false);
        context.closePath();
        context.fill();
        context.restore();
    };
    //获取锚点
    this.getAnchors = function (p1x, p1y, p2x, p2y, p3x, p3y) {
        var l1 = (p2x - p1x) / 2,
            l2 = (p3x - p2x) / 2,
            a = Math.atan((p2x - p1x) / Math.abs(p2y - p1y)),
            b = Math.atan((p3x - p2x) / Math.abs(p2y - p3y));
        a = p1y < p2y ? Math.PI - a : a;
        b = p3y < p2y ? Math.PI - b : b;
        var alpha = Math.PI / 2 - ((a + b) % (Math.PI * 2)) / 2,
            dx1 = l1 * Math.sin(alpha + a),
            dy1 = l1 * Math.cos(alpha + a),
            dx2 = l2 * Math.sin(alpha + b),
            dy2 = l2 * Math.cos(alpha + b);
        return {
            x1: p2x - dx1,
            y1: p2y + dy1,
            x2: p2x + dx2,
            y2: p2y + dy2
        };
    };
    //填充文字
    this.wrapText = function (context, text, x, y, maxWidth, lineHeight) {
        var line = [], n = 0;
        for (var i = 0; i < text.length; i++) {
            if (n == 2) break;
            if (i % 6 == 0) {
                line.push(text.substring(i, i + 6));
                n++;
            }
        }
        var len = line.length;
        for (var ii = 0; ii < len; ii++) {
            context.fillText(line[ii], x, y - lineHeight * (len - ii));
        }
    };
}
//显示隐藏丁氏图曲线
function showCurve(obj) {
    $(obj).parent().prev().prev().toggle();
}

/** 针对坐标轴字段溢出的字符串截取
 * @param title             将要换行处理x轴值
 * @param data
 * @param fontSize          x轴数据字体大小，根据图片字体大小设置而定，此处内部默认为12
 * @param barContainerWidth         柱状图初始化所在的外层容器的宽度
 * @param xWidth            柱状图x轴左边的空白间隙 x 的值，详见echarts文档中grid属性，默认80
 * @param x2Width           柱状图x轴邮编的空白间隙 x2 的值，详见echarts文档中grid属性，默认80
 * @param insertContent     每次截取后要拼接插入的内容， 不传则默认为换行符：\n
 * @returns titleStr        截取拼接指定内容后的完整字符串
 */
function getEchartBarXAxisTitle(title, datas, fontSize, barContainerWidth, xWidth, x2Width, insertContent) {

    if (!title || title.length == 0) {
        alert("截取拼接的参数值不能为空！");
        return false;
    }
    if (!datas || datas.length == 0) {
        alert("用于计算柱状图柱子个数的参数datas不合法！");
        return false;
    }
    if (isNaN(barContainerWidth)) {
        alert("初始化所在的容器的宽度不是一个数字");
        return false;
    }
    if (!fontSize) {
        fontSize = 12;
    }
    if (isNaN(xWidth)) {
        xWidth = 80;//默认与echarts的默认值一致
    }
    if (isNaN(x2Width)) {
        x2Width = 80;//默认与echarts的默认值一致
    }
    if (!insertContent) {
        insertContent = "\n";
    }

    var xAxisWidth = parseInt(barContainerWidth) - (parseInt(xWidth) + parseInt(x2Width));//柱状图x轴宽度=统计页面宽度-柱状图x轴的空白间隙(x + x2)
    var barCount = datas.length;                                //x轴单元格的个数（即为获取x轴的数据的条数）
    var preBarWidth = Math.floor(xAxisWidth / barCount);        //统计x轴每个单元格的间隔

    var preBarFontCount = Math.floor(preBarWidth / fontSize);  //柱状图每个柱所在x轴间隔能容纳的字数 = 每个柱子 x 轴间隔宽度 / 每个字的宽度（12px）
    if (preBarFontCount > 3) {    //为了x轴标题显示美观，每个标题显示留两个字的间隙，如：原本一个格能一样显示5个字，处理后一行就只显示3个字
        preBarFontCount -= 2;
    } else if (preBarFontCount <= 3 && preBarFontCount >= 2) {//若每个间隔距离刚好能放两个或者字符时，则让其只放一个字符
        preBarFontCount -= 1;
    }
    var newTitle = "";      //拼接每次截取的内容，直到最后为完整的值
    var titleSuf = "";      //用于存放每次截取后剩下的部分
    var rowCount = Math.ceil(title.length / preBarFontCount);   //标题显示需要换行的次数
    if (rowCount > 1) {       //标题字数大于柱状图每个柱子x轴间隔所能容纳的字数，则将标题换行
        for (var j = 1; j <= rowCount; j++) {
            if (j == 1) {
                newTitle += title.substring(0, preBarFontCount) + insertContent;
                titleSuf = title.substring(preBarFontCount);    //存放将截取后剩下的部分，便于下次循环从这剩下的部分中又从头截取固定长度
            } else {

                var startIndex = 0;
                var endIndex = preBarFontCount;
                if (titleSuf.length > preBarFontCount) {  //检查截取后剩下的部分的长度是否大于柱状图单个柱子间隔所容纳的字数

                    newTitle += titleSuf.substring(startIndex, endIndex) + insertContent;
                    titleSuf = titleSuf.substring(endIndex);    //更新截取后剩下的部分，便于下次继续从这剩下的部分中截取固定长度
                } else if (titleSuf.length > 0) {
                    newTitle += titleSuf.substring(startIndex);
                }
            }
        }
    } else {
        newTitle = title;
    }
    return newTitle;
}
