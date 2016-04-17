var resultTableGenerator = function () {

    var def = $.Deferred();

    var algorithmConfigs = sessionStorage.getItem('PRIVATE_ALGORITHM_CONFIG').split(',');
    var graphConfigs = sessionStorage.getItem('PRIVATE_GRAPH_CONFIG').split(',');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered active');

    var tr = $('<tr>');

    var emptyCell = $('<td>');
    $(tr).append(emptyCell);

    var countHeaderCell = $('<th>');
    $(countHeaderCell).text('N');
    $(tr).append(countHeaderCell);

    algorithmConfigs.map(function (config) {
        var th = $('<th>');
        $(th).attr('data-i18n-type', 'algorithm')
            .attr('data-i18n-tag', config)
            .attr('data-config', config);
        $(tr).append(th);
    });
    $(table).append(tr);

    var tableResult = JSON.parse(sessionStorage.getItem('PRIVATE_TABLE_RESULT'));

    for (var key in tableResult.resDataMap) {
        var params = tableResult.resDataMap[key].resultData;

        var tr = $('<tr>');

        var keyCell = $('<td>');
        $(keyCell).text(key);
        $(tr).append(keyCell);

        var countCell = $('<td>');
        $(countCell).text(params.count);
        $(tr).append(countCell);

        algorithmConfigs.map(function (config) {
            var td = $('<td>');
            $(td).text(params[config]);
            $(tr).append(td);
        });

        $(table).append(tr);
    }

    $('.result-table').append(table);

    return def.resolve().promise();
};