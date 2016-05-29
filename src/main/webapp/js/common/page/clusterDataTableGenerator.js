var dataTableGenerator = function (data) {

    //表格生成
    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered multiinfo-table-display');
    var tablePresentArea = $('.table-present');
    $(tablePresentArea).append(table);

    var grid = data.sheet.dataGrid;

    grid.map(function (row, rowIndex) {
        var tr = $('<tr>');

        if (!rowIndex) {
            //打印第一行第一格『序号』
            $(tr).append($('<td>').clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'serial_number'));
        } else {
            $(tr).append($('<td>').clone().append(
                $('<label>').clone().append(
                    $('<input>').clone().attr('type', 'checkbox').prop('checked', true).val(rowIndex).addClass('row-checkbox')
                ).append(
                    $('<span>').clone().text(rowIndex)
                )
            ))
        }

        if (!sessionStorage.getItem('isFirstRowVar')) {
            //如果第一行不是变量，那么打印后台传过来的变量名
            data.sheet.variety.map(function (variable, index) {
                $(tr).append($('<td>').clone().append(
                    $('<label>').clone().append(
                        $('<input>').clone().attr('type', 'checkbox').val(index + 1)
                    ).append(
                        $('<span>').clone().text(variable.varietyName)
                    )
                ))
            });
        }

        row.map(function (cell, cellIndex) {
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

            //存储值信息
            for (var attr in cell) {
                $(td).attr('data-' + attr, cell[attr]);
            }
            $(td).attr('data-all-params', JSON.stringify(cell));

            if (sessionStorage.getItem('isFirstRowVar') && !rowIndex) {
                $(td).append(
                    $('<label>').clone().append(
                        $('<input>').clone().attr('type', 'checkbox').val(rowIndex + 1).addClass('col-checkbox')
                    ).append(
                        $('<span>').clone().text(cell.data)
                    )
                )
            } else {
                $(td).text(cell.data);
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