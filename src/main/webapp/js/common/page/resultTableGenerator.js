var resultTableGenerator = function () {

    var def = $.Deferred();

    var presentArea = $('.result-table');

    var resultType = sessionStorage.getItem('PRIVATE_RESULT_TYPE');
    var tableResult = JSON.parse(sessionStorage.getItem('PRIVATE_TABLE_RESULT'));

    var graphConfigs = sessionStorage.getItem('PRIVATE_GRAPH_CONFIG').split(',');

    //制表区
    switch (resultType) {
        case 'Descriptive_Statistics_Descriptive':
            handleDescriptiveStatisticsDescriptive(tableResult);
            break;
        case 'Descriptive_Statistics_Frequency':
            handleDescriptiveStatisticsFrequency(tableResult);
            break;
        case 'Correlation_Bivariate':
            handleCorrelationBivariate(tableResult);
            break;
    }

    //绘图区
    if (graphConfigs[0] !== "") {
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
                case 'piegraph':
                    new Pie({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        content: 'graph_' + i,//图表容器的id
                        title: '饼图' //图表类型标题
                    }).render();
                    break;
                case 'histogram':
                    new Bar({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        content: 'graph_' + i,//图表容器的id
                        title: '直方图' //图表类型标题
                    }).render();

                    break;
            }
        });
    }

    return def.resolve().promise();
};

var handleDescriptiveStatisticsDescriptive = function (tableResult) {

    var presentArea = $('.result-table');

    var algorithmConfigs = sessionStorage.getItem('PRIVATE_ALGORITHM_CONFIG').split(',');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered table-condensed');

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

};
var handleDescriptiveStatisticsFrequency = function (tableResult) {

    var presentArea = $('.result-table');

    var frequencyData = tableResult.resDataMap;
    console.log(frequencyData);

    for (var variable in frequencyData) {
        var container = $('<div>');
        $(container).addClass('frequency-table');

        var title = $('<h1>');
        $(title).text(variable);
        $(container).append(title);

        var variableTable = $('<table>');
        $(variableTable).addClass('table table-striped table-bordered');

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

            var percentageCell = $('<td>');
            $(percentageCell).text(percentage[unique]);
            $(tr).append(percentageCell);

            var validatePercentageCell = $('<td>');
            $(validatePercentageCell).text(percentage[unique]);
            $(tr).append(validatePercentageCell);

            var accumulationPercentageCell = $('<td>');
            $(accumulationPercentageCell).text(accumulationPercentage[unique]);
            $(tr).append(accumulationPercentageCell);

            $(variableTable).append(tr);
        });

        var lastRow = $('<tr>');

        var lastRow_1_Cell = $('<th>');
        $(lastRow_1_Cell).attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_total');

        var frequencyTotal = 0, percentageTotal = 0;
        uniqueData.map(function (data) {
            frequencyTotal += frequency[data];
            percentageTotal += percentage[data];
        });

        var lastRow_2_Cell = $('<th>');
        $(lastRow_2_Cell).text(frequencyData[variable].resultData.sumFreq);

        var lastRow_3_Cell = $('<th>');
        $(lastRow_3_Cell).text(frequencyData[variable].resultData.sumPercentage);

        var lastRow_4_Cell = $('<th>');
        $(lastRow_4_Cell).text(frequencyData[variable].resultData.sumValidatePercentage);

        var lastRow_5_Cell = $('<th>');
        $(lastRow).append(lastRow_1_Cell)
            .append(lastRow_2_Cell)
            .append(lastRow_3_Cell)
            .append(lastRow_4_Cell)
            .append(lastRow_5_Cell);

        $(variableTable).append(lastRow);

        $(container).append(variableTable);
        $(presentArea).append(container);
    }
};
var handleCorrelationBivariate = function (tableResult) {
    console.log(tableResult);

    var presentArea = $('.result-table');

    var bivariateTableData = tableResult.resDataMap;
    var paramsList = Object.keys(bivariateTableData);

    var resConfigs = Object.keys(bivariateTableData[paramsList[0]][0][paramsList[0]]);

    //配置显示的参数数组
    var algorithmConfigs = sessionStorage.getItem('PRIVATE_ALGORITHM_CONFIG').split(',');

    //Pearson 与Spearman 参数数组
    var tableConfigsList = sessionStorage.getItem('PRIVATE_PLUGIN_CONFIG_COR_BIV').split(',');

    tableConfigsList.map(function (option) {
        console.log(option);
        var container = $('<div>');
        $(container).addClass('frequency-table');

        var header = $('<h1>');

        $(header).attr('data-i18n-type', "page")
            .attr('data-i18n-tag', (option == 'pearson') ? "label_correlation" : "label_correlation_coefficients");

        var table = $('<table>');
        $(table).addClass('table table-striped table-bordered');

        var row = $('<tr>');
        var cell = $('<td>');
        var headerCell = $('<th>');

        //打印第一行
        var headerRow = $(row).clone();
        var emptyHeaderCell = $(headerCell).clone();
        $(emptyHeaderCell).attr('colspan', '2')
            .text((option == 'pearson') ? "" : "Spearman's rho");
        $(headerRow).append(emptyHeaderCell);

        //打印头部
        paramsList.map(function (paramKey) {
            //有几个变量打几个头部
            var headerParamsCell = $(headerCell).clone();
            $(headerParamsCell).text(paramKey);
            $(headerRow).append(headerParamsCell);
        });
        $(table).append(headerRow);

        //打印变量信息 - 有几个变量打几行
        paramsList.map(function (mainKey) {
            var dataRow = $(row).clone();

            //打印变量名
            var variableFirstCell = $(cell).clone();
            $(variableFirstCell).text(mainKey);
            $(dataRow).append(variableFirstCell);

            //打印算法选项名
            var variableSecCell = $(cell).clone();
            algorithmConfigs.map(function (config) {
                if ($.inArray(config, resConfigs) != -1) {
                    if (option == 'pearson') {
                        if ($.inArray(config, ['spearmanR', 'spearmanT']) == -1) {
                            var pearsonConfigWrapper = $('<div>');
                            $(pearsonConfigWrapper).text(config)
                                .attr('data-i18n-tag', config)
                                .attr('data-i18n-type', 'table');
                            $(variableSecCell).append(pearsonConfigWrapper);
                        }
                    } else if (option == 'spearman') {
                        if ($.inArray(config, ['pearsonR', 'pearsonT']) == -1) {
                            var spearmanConfigWrapper = $('<div>');
                            $(spearmanConfigWrapper).text(config)
                                .attr('data-i18n-tag', config)
                                .attr('data-i18n-type', 'table');
                            $(variableSecCell).append(spearmanConfigWrapper);
                        }
                    }
                }
            });
            $(dataRow).append(variableSecCell);

            //打印参数值
            var paramLength = bivariateTableData[mainKey].length;
            for (var i = 0; i < paramLength; i++) {
                var paramValueCell = $(cell).clone();

                algorithmConfigs.map(function (alConfig) {
                    if ($.inArray(alConfig, resConfigs) != -1) {

                        if (option == 'pearson') {
                            if ($.inArray(alConfig, ['spearmanR', 'spearmanT']) == -1) {
                                var pearsonParamValueWrapper = $('<div>');
                                for (var paramVariableKey in bivariateTableData[mainKey][i]) {
                                    $(pearsonParamValueWrapper).html(
                                        (bivariateTableData[mainKey][i][paramVariableKey][alConfig] == 'Infinity') ? '&nbsp;' : bivariateTableData[mainKey][i][paramVariableKey][alConfig]
                                    ).attr('data-config-type', alConfig);
                                }
                                $(paramValueCell).append(pearsonParamValueWrapper);
                            }
                        } else if (option == 'spearman') {
                            if ($.inArray(alConfig, ['pearsonR', 'pearsonT']) == -1) {
                                var spearmanParamValueWrapper = $('<div>');
                                for (var paramVariableKey in bivariateTableData[mainKey][i]) {
                                    $(spearmanParamValueWrapper).html(
                                        (bivariateTableData[mainKey][i][paramVariableKey][alConfig] == 'Infinity') ? '&nbsp;' : bivariateTableData[mainKey][i][paramVariableKey][alConfig]
                                    ).attr('data-config-type', alConfig);
                                }
                                $(paramValueCell).append(spearmanParamValueWrapper);
                            }
                        }
                    }
                });
                $(dataRow).append(paramValueCell);
            }
            $(table).append(dataRow);
        });

        $(container).append(header).append(table);
        $(presentArea).append(container);
    });

    //打印 均值 & 标准偏差 表格
    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered');

    var row = $('<tr>');
    var cell = $('<td>');
    var headerCell = $('<th>');

    //打印第一行
    var headerRow = $(row).clone();
    var emptyHeaderCell = $(headerCell).clone();
    $(headerRow).append(emptyHeaderCell);

    //打印头部
    var basicData = tableResult.basicDataMap;
    //有几个变量打几个头部
    var basicAlgorithmConfig = [];
    algorithmConfigs.map(function (alConfig) {
        if (basicData[paramsList[0]][alConfig]) {
            basicAlgorithmConfig.push(alConfig);
            var headerParamsCell = $(headerCell).clone();
            $(headerParamsCell).attr('data-i18n-type', 'algorithm')
                .attr('data-i18n-tag', alConfig);
            $(headerRow).append(headerParamsCell);
        }
    });
    $(table).append(headerRow);

    //打印数值
    paramsList.map(function (mainKey) {
        var dataRow = $(row).clone();

        var paramName = $(cell).clone();
        $(paramName).text(mainKey);
        $(dataRow).append(paramName);

        basicAlgorithmConfig.map(function (alConfig) {
            var paramValue = $(cell).clone();
            $(paramValue).text(basicData[mainKey][alConfig]);
            $(dataRow).append(paramValue);
        });

        $(table).append(dataRow);
    });

    if (basicAlgorithmConfig.length != 0) {
        $(presentArea).append(table);
    }

};

