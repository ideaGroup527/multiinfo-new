var resultTableGenerator = function () {

    var def = $.Deferred();

    var presentArea = $('.result-table');
    console.log(presentArea);

    var resultType = sessionStorage.getItem('PRIVATE_RESULT_TYPE');
    var tableResult = JSON.parse(sessionStorage.getItem('PRIVATE_TABLE_RESULT'));

    switch (resultType) {
        case 'Descriptive_Statistics_Descriptive':
            handleDescriptiveStatisticsDescriptive();
            break;
        case 'Descriptive_Statistics_Frequency':
            var frequencyData = tableResult.resDataMap;
            for (var variable in frequencyData) {
                var container = $('<div>');

                var title = $('<h1>');
                $(title).text(variable);
                $(container).append(title);

                var variableTable = $('<table>');
                $(variableTable).addClass('table table-striped table-bordered active');

                var tr = $('<tr>');

                var emptyCell = $('<th>');
                $(tr).append(emptyCell);

                ['label_frequency', 'label_percentage', 'label_validate_percentage', 'label_accumulation_percentage'].map(function (header, i) {
                    var th = $('<th>');
                    $(th).attr('data-i18n-type', 'page')
                        .attr('data-i18n-tag', header);
                    $(tr).append(th);
                });

                $(variableTable).append(tr);
                var uniqueData = frequencyData[variable].resultData.uniqueData;
                var frequency = frequencyData[variable].resultData.frequencyMap;
                var percentage = frequencyData[variable].resultData.percentage;
                var accumulationPercentage = frequencyData[variable].resultData.accumulationPercentage;

                uniqueData.map(function (unique, i) {
                    var tr = $('<tr>');

                    var dataCell = $('<td>');
                    $(dataCell).text(unique);
                    $(tr).append(dataCell);

                    var frequencyCell = $('<td>');
                    $(frequencyCell).text(frequency[unique]);
                    $(tr).append(frequencyCell);

                    var frequencyCell2 = $('<td>');
                    $(frequencyCell2).text(frequency[unique]);
                    $(tr).append(frequencyCell2);

                    var percentageCell = $('<td>');
                    $(percentageCell).text(percentage[unique]);
                    $(tr).append(percentageCell);

                    var accumulationPercentageCell = $('<td>');
                    $(accumulationPercentageCell).text(accumulationPercentage[unique]);
                    $(tr).append(accumulationPercentageCell);

                    $(variableTable).append(tr);
                });

                $(container).append(variableTable);
                $(presentArea).append(container);
            }
            break;
    }

    return def.resolve().promise();
};

var handleDescriptiveStatisticsDescriptive = function () {

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

    $(presentArea).append(table);

    //绘图区
    if (graphConfigs) {
        graphConfigs.map(function (graph, i) {
            console.log(graph);
            var graphArea = $('<div>');
            $(graphArea).css({
                'height': 800
            }).addClass('col-md-12').attr('id', 'graph_' + i);

            $(presentArea).append(graphArea);

            switch (graph) {
                case 'boxplot':
                    new Boxplot({
                        data: tableResult,
                        opt: "",
                        content: 'graph_' + i,
                        title: '箱线图'
                    }).render();
                    break;
            }
        });
    }
};