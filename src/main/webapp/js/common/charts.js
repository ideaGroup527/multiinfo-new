/*说明：
 表的顺序：
 曲线图1，折线图2，散点图3，箱线图 boxplot 4，饼图 pie 5，正态分布图 normalcurve 6，
 丁氏图 dingchart 7,碎石图 screeplot 8, 主成分因子平面图二维图 pcfp2d 9,主成分因子平面图二维图 pcfp3d 10，
 聚类图 clustering 11, 最优分割 fisher 12, 最优分割的折线图 fisher_cure 13

 */
(function ($) {
    $.fn.charts = function (setting) {
        var defaults = {
            title: "图表标题",
            data: {},
            type: []
        };
        setting = $.extend(defaults, setting);

        var _this = this, _type = 0, _data, _option;
        $.each(setting.type, function (i, v) {
            switch (v) {
                case "cure":
                {
                    _type |= 1;
                    break;
                }
                case "line":
                {
                    _type |= 2;
                    break;
                }
                case "scatter":
                {
                    _type |= 4;
                    break;
                }
                case "boxplot":
                {
                    _type |= 8;
                    break;
                }
                case "pie":
                {
                    _type |= 16;
                    break;
                }
                case "normalcurve":
                {
                    _type |= 32;
                    break;
                }
                case "dingchart":
                {
                    _type |= 64;
                    break;
                }
                case "screeplot":
                {
                    _type |= 128;
                    break;
                }
                case "pcfp2d":
                {
                    _type |= 256;
                    break;
                }
                case "pcfp3d":
                {
                    _type |= 512;
                    break;
                }
                case "clustering":
                {
                    _type |= 513;
                    break;
                }
                case "fisher":
                {
                    _type |= 514;
                    break;
                }
                case "fisher_line":
                {
                    _type |= 515;
                    break;
                }
            }
        });
        _handler();
        function _handler() {
            switch (_type.toString(2)) {
                case "1": //曲线图
                {

                    _lineHandle(setting.data);
                    _render();
                    break;
                }
                case "10"://折线图
                {

                    _cureHandle(setting.data);
                    _render();
                    break;
                }
                case "100"://散点图
                {

                    _scatterHandle(setting.data);
                    _render();
                    break;
                }
                case "1000"://箱线图
                {

                    _boxplotHandle(setting.data);
                    _render();
                    break;
                }
                case "10000"://饼图
                {

                    _pieHandle(setting.data);
                    _render();
                    break;
                }
                case "100000"://正太分布
                {

                    _normalcurveHandle(setting.data);
                    _render();
                    break;
                }
                case "1000000"://丁氏图
                {

                    _dingchartHandle(setting.data, setting.calculateMethod);
                    break;
                }
                case "10000000"://碎石图
                {
                    _screeplotHandle(setting.data);
                    _render();
                    break;
                }
                case "100000000"://主成分因子平面二维图
                {
                    _pcfp2dHandle(setting.data);
                    _render();
                    break;
                }
                case "1000000000"://主成分因子平面三维图
                {
                    _pcfp3dHandle(setting.data);
                    break;
                }
                case "1000000001"://聚类图
                {
                    _clusteringHandle(setting.data);
                    break;
                }
                case "1000000010"://最优分割图
                {
                    _fisherHandle(setting.data);
                    _render();
                    break;
                }
                case "1000000011"://最优分割图的折线图
                {
                    _lineForfisherHandle(setting.data);
                    _render();
                    break;
                }
                case "11"://散点图和曲线图
                {
                    _data = _lineHandle(setting.data);
                    _option = {
                        title: {
                            text: setting.title, //主标题文本
                            x: 'left', //标题文本的位置
                            y: 0,
                            textStyle: {
                                fontSize: 16,
                                fontWeight: 'normal'
                            }
                        },
                        tooltip: {
                            formatter: '{a}: ({c})' //鼠标移动到点的提示框
                        },
                        xAxis: [
                            {min: setting.data.resDataMap.xAxis.min, max: setting.data.resDataMap.xAxis.max} //x轴的最大最小值
                        ],
                        yAxis: [
                            {min: setting.data.resDataMap.yAxis.min, max: setting.data.resDataMap.yAxis.max} //y轴的最大最小值
                        ],
                        series: [
                            {
                                smooth: true,
                                name: '点',
                                type: "line",
                                data: _data
                            },
                            {
                                smooth: true,
                                name: '点',
                                type: "scatter",
                                itemStyle: {
                                    normal: {
                                        color: "#C43B37"
                                    }
                                },
                                data: _data
                            }

                        ]
                    };
                    break;
                }
                default:
                {
                    alert("图表类型错误，请检查!");
                }
            }
        }

        //渲染到页面
        function _render() {
            var myChart = echarts.init(_this[0]);
            myChart.setOption(_option);
        }

        //曲线
        function _cureHandle(data) {
            var dataAll = [];
            var depend = data.dependentVariable.varietyName,
                independent = data.independentVariable[0].varietyName;

            for (var i = 0; i < data.resDataMap[depend].length; i++) {
                dataAll.push([data.resDataMap[depend][i], data.resDataMap[independent][i]]);
            }

            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '{a}: ({c})' //鼠标移动到点的提示框
                },
                xAxis: [
                    {min: setting.data.resDataMap.xAxis.min, max: setting.data.resDataMap.xAxis.max} //x轴的最大最小值
                ],
                yAxis: [
                    {min: setting.data.resDataMap.yAxis.min, max: setting.data.resDataMap.yAxis.max} //y轴的最大最小值
                ],
                series: [
                    {
                        smooth: true,
                        name: '点',
                        type: "line",
                        data: dataAll,
                        markLine: null
                    }
                ]
            };
        }

        //折线
        function _lineHandle(data) {
            var dataAll = [];
            var depend = data.dependentVariable.varietyName,
                independent = data.independentVariable[0].varietyName;

            for (var i = 0; i < data.resDataMap[depend].length; i++) {
                dataAll.push([data.resDataMap[depend][i], data.resDataMap[independent][i]]);
            }
            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '{a}: ({c})' //鼠标移动到点的提示框
                },
                xAxis: [
                    {min: setting.data.resDataMap.xAxis.min, max: setting.data.resDataMap.xAxis.max} //x轴的最大最小值
                ],
                yAxis: [
                    {min: setting.data.resDataMap.yAxis.min, max: setting.data.resDataMap.yAxis.max} //y轴的最大最小值
                ],
                series: [
                    {
                        smooth: false,
                        name: '点',
                        type: "line",
                        data: dataAll,
                        markLine: null
                    }
                ]
            };
        }

        //散点
        function _scatterHandle(data) {
            var dataAll = [];
            var depend = data.dependentVariable.varietyName,
                independent = data.independentVariable[0].varietyName;

            for (var i = 0; i < data.resDataMap[depend].length; i++) {
                dataAll.push([data.resDataMap[depend][i], data.resDataMap[independent][i]]);
            }

            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '{a}: ({c})' //鼠标移动到点的提示框
                },
                xAxis: {
                    scale: true
                },
                yAxis: {
                    scale: true
                },
                series: [
                    {
                        name: '点', //系列名称，用于tooltip的显示
                        type: "scatter", //设置绘制的为散点图，默认为直线
                        data: _data,  //设置每一个散点的位置
                        markLine: null //图表标线
                    }
                ]
            };
        }

        //碎石
        function _screeplotHandle(data) {

            var dataAll = [];
            $.each(data, function (i, v) {
                dataAll.push([i + 1, v]);
            });
            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '{a}: ({c})' //鼠标移动到点的提示框
                },
                xAxis: {
                    scale: true
                },
                yAxis: {
                    scale: true
                },
                series: {
                    name: '特征值',
                    type: "line",
                    data: dataAll
                }

            };
        }

        //饼
        function _pieHandle(data) {

            var dataAll = {k: [], kv: []};

            for (var key in data.resDataMap) {
                dataAll.k.push(key);

                var _kv = {};
                _kv.name = key;
                _kv.value = data.resDataMap[key].resultData.total;
                dataAll.kv.push(_kv);
            }

            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 50,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                legend: {
                    orient: 'vertical',
                    top: '100',
                    left: '300',
                    data: dataAll.k
                },
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                series: [
                    {
                        name: data.reportTitle,
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: dataAll.kv,
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

        //箱线
        function _boxplotHandle(data) {

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
            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
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

        //正态分布
        function _normalcurveHandle(data) {

            var N_POINT = 1000;//点数量

            function easingFunc(x) {
                var q = 1, u = 0;
                var a = 1.0 / (Math.sqrt(2 * Math.PI) * q);
                var b = Math.exp((-1) * ((x - u) * (x - u)) / (2 * q * q));
                return a * b;
            }

            var dataAll = [];
            for (var i = -N_POINT; i <= N_POINT; i++) {
                var x = i / 100;
                var y = easingFunc(x);
                dataAll.push([x, y]);
            }
            _option = {
                title: {
                    text: setting.title,
                    x: 'left',
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
                    type: 'line',
                    data: dataAll,
                    showSymbol: false,
                    animationDuration: 1000
                }
            };
        }

        //丁氏图
        function _dingchartHandle(data, calculateMethod) {

            var col = data.colVarList.length,//行数和列数
                row = data.rowVarList.length;
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
            _this[0].appendChild(canvas);
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
                wrapText(ctx, data.rowVarList[i].varietyName, 70, y + m_height, m_width, 15);
                ctx.stroke();
            }
            //x轴
            for (var i = 0; i < col; i++) {
                var x = (i + 1) * m_width;
                ctx.save();
                ctx.textAlign = "center";
                wrapText(ctx, data.colVarList[i].varietyName, x + m_width / 2, m_height, m_width, 15);
                ctx.stroke();
            }

            var n_point = point = -1;
            var _a, _b;
            //椭圆
            if (calculateMethod == 0) { //行
                for (var i = 0; i < row; i++) {
                    for (var j = 0; j < col; j++) {

                        _a = a;
                        _b = b * data.resData[i][j];
                        _a = _a < .04 ? .04 : _a;
                        _b = _b < .04 ? .04 : _b;
                        if ((++n_point) % col == 0) {
                            point++;
                        }
                        curvePoint[point].push({x: (j + 1) * m_width + a, y: (i + 1) * m_height + b - _b});
                        curvePoint2[point].push({x: (j + 1) * m_width + a, y: (i + 2) * m_height - b + _b});
                        EllipseTwo(ctx, (j + 1) * m_width + a, (i + 1) * m_height + b, _a, _b);
                    }
                }
            } else if (calculateMethod == 1) {  //列
                for (var i = 0; i < col; i++) {
                    for (var j = 0; j < row; j++) {
                        _a = a * data.resData[j][i];
                        _b = b;
                        _a = _a < .04 ? .04 : _a;
                        _b = _b < .04 ? .04 : _b;

                        if ((++n_point) % row == 0) {
                            point++;
                        }
                        curvePoint[point].push({x: (i + 1) * m_width + a - _a, y: (j + 2) * m_height - _b});
                        curvePoint2[point].push({x: (i + 1) * m_width + a + _a, y: (j + 1) * m_height + _b});
                        EllipseTwo(ctx, (i + 1) * m_width + a, (j + 1) * m_height + b, _a, _b);
                    }
                }
            } else if (calculateMethod == 2) {  //全
                for (var i = 0; i < col; i++) {
                    for (var j = 0; j < row; j++) {
                        _a = a * data.resData[j][i];
                        _b = b * data.resData[j][i];
                        _a = _a < .04 ? .04 : _a;
                        _b = _b < .04 ? .04 : _b;
                        if ((++n_point) % row == 0) {
                            point++;
                        }
                        EllipseTwo(ctx, (i + 1) * m_width + a, (j + 1) * m_height + b, _a, _b);
                    }
                }
            } else {
                alert('calculateMethod参数错误！');
                return false;
            }

            //曲线
            var paper = Raphael(_this[0], width, height);
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
                        var a = getAnchors(point1.x, point1.y, x, y, point2.x, point2.y);//获取锚点
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
                        var a = getAnchors(point1.x, point1.y, x, y, point2.x, point2.y);//获取锚点
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

            //画一个椭圆
            function EllipseTwo(context, x, y, a, b) {
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
            }

            //获取锚点
            function getAnchors(p1x, p1y, p2x, p2y, p3x, p3y) {
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
            }

        }

        //主成分因子平面图二维图
        function _pcfp2dHandle(data) {
            var dataAll = [], markpoint = [];
            $.each(data.data.componentArr, function (i, v) {
                dataAll.push([v[0], v[1], data.variableList[i].varietyName]);
            });
            $.each(data.variableList, function (i, v) {
                markpoint.push(v.varietyName);
            });
            console.log(markpoint);
            console.log(dataAll);
            _option = {
                title: {
                    text: setting.title,
                    x: 'left',
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '({c})'
                },
                xAxis: {
                    scale: true
                },
                yAxis: {
                    scale: true
                },
                series: [
                    {
                        name:'点',
                        type: "scatter",
                        data: dataAll,
                        label:{
                            normal:{
                                show:true,
                                position:"right",
                                formatter: function (d) {
                                    return d.data[2];
                                },
                                textStyle:{
                                    color:"#000"
                                }
                            }
                        }
                    }
                ]
            };
        }

        //主成分因子平面3d图
        function _pcfp3dHandle(data) {
            // Give the points a 3D feel by adding a radial gradient
            Highcharts.getOptions().colors = $.map(Highcharts.getOptions().colors, function (color) {
                return {
                    radialGradient: {
                        cx: 0.4,
                        cy: 0.3,
                        r: 0.5
                    },
                    stops: [
                        [0, color],
                        [1, Highcharts.Color(color).brighten(-0.2).get('rgb')]
                    ]
                };
            });

            // Set up the chart
            var chart = new Highcharts.Chart({
                chart: {
                    renderTo: _this.attr('id'),
                    margin: 100,
                    type: 'scatter',
                    options3d: {
                        enabled: true,
                        alpha: 10,
                        beta: 30,
                        depth: 250,
                        viewDistance: 5,

                        frame: {
                            bottom: {size: 1, color: 'rgba(0,0,0,0.02)'},
                            back: {size: 1, color: 'rgba(0,0,0,0.04)'},
                            side: {size: 1, color: 'rgba(0,0,0,0.06)'}
                        }
                    }
                },
                title: {
                    text: setting.title,
                    x: -300,
                    y: 50,
                    style: {
                        "fontSize": "16px"
                    }

                },
                plotOptions: {
                    scatter: {
                        width: 10,
                        height: 10,
                        depth: 10
                    }
                },
                yAxis: {
                    title: null
                },
                xAxis: {
                    gridLineWidth: 1
                },
                zAxis: {},
                legend: {
                    enabled: false
                },
                series: [{
                    name: '点',
                    colorByPoint: true,
                    data: data.data.componentArr
                }]
            });

            // Add mouse events for rotation
            $(chart.container).bind('mousedown.hc touchstart.hc', function (e) {
                e = chart.pointer.normalize(e);

                var posX = e.pageX,
                    posY = e.pageY,
                    alpha = chart.options.chart.options3d.alpha,
                    beta = chart.options.chart.options3d.beta,
                    newAlpha,
                    newBeta,
                    sensitivity = 5; // lower is more sensitive

                $(document).bind({
                    'mousemove.hc touchdrag.hc': function (e) {
                        // Run beta
                        newBeta = beta + (posX - e.pageX) / sensitivity;
                        newBeta = Math.min(100, Math.max(-100, newBeta));
                        chart.options.chart.options3d.beta = newBeta;

                        // Run alpha
                        newAlpha = alpha + (e.pageY - posY) / sensitivity;
                        newAlpha = Math.min(100, Math.max(-100, newAlpha));
                        chart.options.chart.options3d.alpha = newAlpha;

                        chart.redraw(false);
                    },
                    'mouseup touchend': function () {
                        $(document).unbind('.hc');
                    }
                });
            });
        }

        //聚类图
        function _clusteringHandle(data) {

            var len = 5;
            var canvas = $("<canvas>");
            $(canvas).css({
                'display': 'block',
                'position': "absolute",
                "left": '50%',
                "margin-left": "-500px",
                "top": 0
            });
            $(canvas).attr('width', "1000").attr('height', len * 100 + 150);

            //内容
            var vis = new pv.Panel()
                .width(800)
                .height(len * 100)
                .left(100)
                .right(100)
                .top(100)
                .bottom(0)
                .canvas(_this.attr('id'));

            var layout = vis.add(pv.Layout.Cluster)
                .nodes(pv.dom(data)
                    .root("")
                    .sort(function (a, b) {
                        pv.naturalOrder(a.nodeName, b.nodeName)
                    })
                    .nodes()
                )
                .group(true)
                .orient("right");

            layout.link.add(pv.Line)
                .strokeStyle("#2b2b2b")
                .lineWidth(1);

            // layout.node.add(pv.Dot)
            //     .fillStyle(function (n) {
            //         return n.firstChild ? "#aec7e8" : "#ff7f0e"
            //     });

            layout.label.add(pv.Label).textAlign("left").textBaseline('bottom').text(function (t) {
                return Number.isInteger(+t.nodeName) ? "" : t.nodeName;
            });

            vis.render();

            //坐标轴
            $(_this).append(canvas);

            var ctx = canvas[0].getContext('2d');

            //坐标轴样式配置
            ctx.lineWidth = 1;
            ctx.strokeStyle = "#666";
            ctx.textAlign = 'center';

            ctx.beginPath();
            ctx.moveTo(100, 100);
            ctx.lineTo(100, (len + 1) * 100);
            ctx.moveTo(100, 100);
            ctx.lineTo(1000, 100);
            ctx.stroke();


            //label
            for (var i = 1; i <= 4; i++) {
                ctx.beginPath(1000, 100);
                ctx.moveTo(100 + i * (800 / 4), 100);
                ctx.lineTo(100 + i * (800 / 4), 110);
                ctx.stroke();

                wrapText(ctx, "" + Number(1.0 / 4 * i).toFixed(3), 100 + i * (800 / 4), 100, 100, 15);
            }

            //title
            ctx.font = "20px Verdana";
            wrapText(ctx, setting.title, 50, 50, 100, 15);
        }

        //最优分割
        function _fisherHandle(data) {
            var dataAll = [];
            var len = data.optRes.length;
            var ydata = [], xdata = [];
            for (var i = 2; i < len + 2; i++) {
                ydata.push('分割' + i + "段");
            }
            for (var i = 1; i <= len + 1; i++) {
                xdata.push('第' + i + '段');
            }
            for (var i = 0, v = null; (v = data.optRes[i]) != undefined; i++) {
                for (var j = 0, f = null; (f = v.segDataList[j]) != undefined; j++) {
                    for (var n = f.from - 1; n < f.to; n++) {
                        dataAll.push([i, n, j + 1]);
                    }
                }
            }
            dataAll = dataAll.map(function (item) {
                return [item[1], item[0], item[2] || '-'];
            });
            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    position: 'top'
                },
                animation: false,
                grid: {
                    height: '50%',
                    y: '10%'
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    },
                    data: xdata
                },
                yAxis: {
                    type: 'category',
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    },
                    data: ydata
                },
                visualMap: {
                    min: 1,
                    max: 6,
                    calculable: true,
                    orient: 'horizontal',
                    left: 'center',
                    bottom: '15%',
                    color: ["#991b1e", "#e0c06d"]
                },
                series: [{
                    name: '分段',
                    type: 'heatmap',
                    data: dataAll,
                    label: {
                        normal: {
                            show: true
                        }
                    },
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };
        }

        //最优分割的折线图
        function _lineForfisherHandle(data) {
            var dataAll = [], xdata = [];
            for (var i = 0, v = null; (v = data.optRes[i]) != undefined; i++) {
                xdata.push(v.segNum);
                dataAll.push(v.sst);
            }
            _option = {
                title: {
                    text: setting.title, //主标题文本
                    x: 'left', //标题文本的位置
                    y: 0,
                    textStyle: {
                        fontSize: 16,
                        fontWeight: 'normal'
                    }
                },
                tooltip: {
                    formatter: '{a}: ({c})' //鼠标移动到点的提示框
                },
                xAxis: {
                    type: 'category',
                    name: '分割段数',
                    nameLocation: "middle",
                    nameTextStyle: {
                        fontSize: 16
                    },
                    nameGap: "30",
                    data: xdata
                },
                yAxis: {
                    name: '离差平方总和',
                    nameLocation: "middle",
                    nameTextStyle: {
                        fontSize: 16
                    },
                    nameGap: "40"
                },
                series: [
                    {
                        smooth: false,
                        name: '点',
                        type: "line",
                        data: dataAll,
                        markLine: null
                    }
                ]
            };
        }

        /** 针对坐标轴字段溢出的字符串截取
         * @param title             将要换行处理x轴值
         * @param datas
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

        /*填充文字*/
        function wrapText(context, text, x, y, maxWidth, lineHeight) {
            var line = [], n = 0;
            for (var i = 0; i < text.length; i++) {
                if (n == 2) break;
                if (i % maxWidth == 0) {
                    line.push(text.substring(i, i + maxWidth));
                    n++;
                }
            }
            var len = line.length;
            for (var ii = 0; ii < len; ii++) {
                context.fillText(line[ii], x, y - lineHeight * (len - ii));
            }
        }
    }
})(jQuery);