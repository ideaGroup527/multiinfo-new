function DingChart(config) {
    this.data = config.data;
    this.calculateMethod = config.calculateMethod;

    this.render = function () {
        var canvas = document.getElementById('canvas');
        var ctx = canvas.getContext('2d');

        var col = config.data.colVarList.length,//行数和列数
            row = config.data.rowVarList.length;
        var m_width = 80, m_height = 40; //一小格子的高宽
        var a = m_width / 2, b = m_height / 2;//椭圆长半轴和短半轴

        var width = (col + 1) * m_width,
            height = (row + 1) * m_height;
        canvas.setAttribute('width', width.toString());
        canvas.setAttribute('height', height.toString());

        //坐标轴样式配置
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#aaa";
        ctx.textAlign = 'center';


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
            ctx.textAlign="right";
            ctx.fillText(config.data.rowVarList[i].varietyName, 70, y + m_height / 2);
            ctx.stroke();
        }
        //x轴
        for (var i = 0; i < col; i++) {
            var x = (i + 1) * m_width;
            ctx.save();
            ctx.textAlign="center";
            ctx.fillText(config.data.colVarList[i].varietyName, x + m_width / 2, 30);
            ctx.stroke();
        }

        //椭圆
        for (var i = 0; i < col; i++) {
            for (var j = 0; j < row; j++) {
                var _a, _b;
                if (this.calculateMethod == 0) {
                    _a = a;
                    _b = b * config.data.resData[j][i];
                } else if (this.calculateMethod == 1) {
                    _a = a * config.data.resData[j][i];
                    _b = b;
                } else if (this.calculateMethod == 2) {
                    _a = a * config.data.resData[j][i];
                    _b = b * config.data.resData[j][i];
                } else {
                    alert('calculateMethod参数错误！');
                    return false;
                }
                _a = _a < .04 ? .04 : _a;
                _b = _b < .04 ? .04 : _b;
                this.EllipseTwo(ctx, (i + 1) * m_width + a, (j + 1) * m_height + b, _a, _b);

            }
        }

        //曲线
        for (var i = 0; i < col-1; i++) {
            for (var j = 0; j < row-1; j++) {
                var _a, _b, _a2, _b2;
                if (this.calculateMethod == 0) {
                    _a = _a2 = a;
                    _b = b * config.data.resData[j][i+1];
                } else if (this.calculateMethod == 1) {
                    _a = a * config.data.resData[j][i];
                    _a2= a * config.data.resData[j][i+1];
                    _b = _b2 = b;
                } else if (this.calculateMethod == 2) {
                    _a = a * config.data.resData[j][i+1];
                    _b = b * config.data.resData[j][i+1];
                } else {
                    alert('calculateMethod参数错误！');
                    return false;
                }

                ctx.strokeStyle = "#f0f";
                ctx.beginPath();
                console.log((i + 1) * m_width + _a, (j + 1) * m_height+b,
                    (i + 2) * m_width + _a2, (j + 2) * m_height + b,
                    (i + 1) * m_width, (j + 1) * m_height);

                ctx.bezierCurveTo((i + 1) * m_width + _a, (j + 1) * m_height+b,
                    (i + 1) * m_width + _a2, (j + 2) * m_height + b,
                    (i + 1) * m_width, (j + 1) * m_height);
                ctx.stroke();
            }
        }

    };
    this.EllipseTwo = function (context, x, y, a, b) {
        context.save();
        context.fillStyle = "#980707";
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
    this.Curve = function () {

    };
}

