var dataTableGenerator = function (data) {
    //标题展示
    $('#title').text('结果展示 - ' + data.sheetNameList[data.currentSheetNo]);

    //表格生成
    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered active');
    var tablePresentArea = $('.table-present');
    $(tablePresentArea).append(table);

    var grid = data.sheet.dataGrid;

    grid.map(function (row, rowIndex) {
        var tr = $('<tr>');

        row.map(function (cell) {
            var td = (!rowIndex) ? $('<th>') : $('<td>');

            var positionDes = cell.positionDes.split(',');

            if (cell.mergedRange) {
                var range = cell.mergedRangeDes.split(':');
                var range_1 = range[0].split(',')[0];
                var range_2 = range[0].split(',')[1];
                var range_3 = range[1].split(',')[0];
                var range_4 = range[1].split(',')[1];

                if ((positionDes[0].charCodeAt() > range_1.charCodeAt() && positionDes[0].charCodeAt() <= range_3.charCodeAt()) ||
                    (positionDes[1] > range_2 && positionDes[1] <= range_4)) {
                    return false;
                } else if (positionDes[0] == range_1 && positionDes[1] == range_2) {
                    $(td).attr('colspan', range_3.charCodeAt() - range_1.charCodeAt() + 1)
                        .attr('rowspan', Number(range_4) - Number(range_2) + 1);
                }
            }

            $(td).text(cell.data);
            //存储值信息
            for (var attr in cell) {
                $(td).attr('data-' + attr, cell[attr]);
            }

            $(tr).append(td);
        });

        $(table).append(tr);
    });

    var variable = data.sheet.variety;
    if (variable) {
        //填充变量名称
        var varLen = variable.length;
        for (var k = 0; k < varLen; k++) {
            var position = variable[k].position;
            var name = variable[k].varietyName;

            $('td[data-positiondes^="' + position + ',"]').attr('data-variety-name', name);
        }
    }
};