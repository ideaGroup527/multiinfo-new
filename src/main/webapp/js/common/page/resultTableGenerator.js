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
        case 'Correlation_Distance':
            handleCorrelationDistance(tableResult);
            break;
        case 'Principal_Component_Analysis':
            handlePrincipalComponentAnalysis(tableResult);
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
                    //箱线图
                    new Boxplot({
                        data: tableResult,
                        opt: "",
                        content: 'graph_' + i,
                        title: '箱线图'
                    }).render();
                    break;
                case 'piegraph':
                    //饼图
                    new Pie({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        content: 'graph_' + i,//图表容器的id
                        title: '饼图' //图表类型标题
                    }).render();
                    break;
                case 'histogram':
                    //直方图
                    new Bar({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        content: 'graph_' + i,//图表容器的id
                        title: '直方图' //图表类型标题
                    }).render();
                    break;
                case 'linechart':
                    //折线图
                    new Line({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        content: 'graph_' + i,//图表容器的id
                        title: '折线图' //图表类型标题
                    }).render();
                    break;
                case 'scatterdiagram':
                    //散点图
                    break;
                case 'dingchart':
                    var dingConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DING_CHART'));

                    new DingChart({
                        data: tableResult,
                        content: 'graph_' + i,//图表容器的id
                        calculateMethod: dingConfig.calculateMethod//0行，1列，2全
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

    //获取存储所有配置的JSON
    var ALL_CONFIGS = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRELATION_BIVARIATE'));
    //配置显示的参数数组
    var algorithmConfigs = ALL_CONFIGS.algorithmConfigs;

    //Pearson 与Spearman 参数数组
    var tableConfigsList = ALL_CONFIGS.correlationCoefficients;

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
var handleCorrelationDistance = function (tableResult) {

    var presentArea = $('.result-table');

    var container = $('<div>');
    $(container).addClass('frequency-table');

    var header = $('<h1>');

    $(header).text('近似矩阵');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered');

    var row = $('<tr>');
    var cell = $('<td>');
    var headerCell = $('<th>');

    console.log(tableResult);

    var ALL_CONFIG = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRELATION_DISTANCE'));

    //获取个案间数据
    var unitData = tableResult.unitDataArr;

    //获取变量间数据
    var resData = tableResult.resDataMap;
    //变量间数据主键值
    var paramKeysList = Object.keys(resData);

    //获取制表方式: 个案间 || 变量间
    var distanceCalcType = ALL_CONFIG.distanceCalcType[0];

    //获取度量类型:Euclidean 距离 || 平方 Euclidean 距离 || Chebyshev || 块 || Minkowski || 设定距离
    var measureMethod = ALL_CONFIG.measureMethod[0];

    //变量长度
    var variableLength = 0;

    //打印头部空白格
    var headerRow = $(row).clone();
    var emptyHeaderCell = $(headerCell).clone();
    $(emptyHeaderCell).attr('rowspan', '2');
    $(headerRow).append(emptyHeaderCell);

    var headerTitle = $(headerCell).clone();

    //个案间 表格生成
    if (distanceCalcType == 'unit') {

        variableLength = unitData.length;

        //打印头部

        $(headerTitle).attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', (measureMethod == 'modifyDistance') ? 'DONTMATCH' : measureMethod)
            .attr('colspan', variableLength)
            .attr('align', 'center')
            .text((measureMethod == 'modifyDistance') ? 'Minkowski(' + ALL_CONFIG.minkowskiP[0] + ',' + ALL_CONFIG.minkowskiQ[0] + ')' : measureMethod);
        $(headerRow).append(headerTitle);

        //打印第二行
        var headerSecRow = $(row).clone();
        for (var i = 0; i < variableLength; i++) {
            var indexCell = $(cell).clone();
            $(indexCell).attr('align', 'center').text(i + 1);
            $(headerSecRow).append(indexCell);
        }
        $(table).append(headerRow).append(headerSecRow);

        unitData.map(function (unit, index) {
            var tr = $(row).clone();

            var td = $(cell).clone();
            $(td).text(index + 1);
            $(tr).append(td);

            unit.map(function (data) {
                var td = $(cell).clone();
                $(td).text(data[(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]);
                $(tr).append(td);
            });
            $(table).append(tr);
        });
    } else if (distanceCalcType == 'variable') {

        //变量间 表格生成
        variableLength = paramKeysList.length;

        //打印头部
        $(headerTitle).attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', (measureMethod == 'modifyDistance') ? 'DONTMATCH' : measureMethod)
            .attr('colspan', variableLength)
            .attr('align', 'center')
            .text((measureMethod == 'modifyDistance') ? 'Minkowski(' + ALL_CONFIG.minkowskiP[0] + ',' + ALL_CONFIG.minkowskiQ[0] + ')' : measureMethod);
        $(headerRow).append(headerTitle);

        //打印第二行
        var headerSecRow = $(row).clone();
        paramKeysList.map(function (mainKey) {
            var keyName = $(cell).clone();
            $(keyName).attr('align', 'center')
                .text(mainKey);
            $(headerSecRow).append(keyName);
        });
        $(table).append(headerRow).append(headerSecRow);

        //打印变量名和值
        paramKeysList.map(function (mainKey) {
            //有几个变量打印几行
            var variableRow = $(row).clone();

            //先打印变量名
            var keyName = $(cell).clone();
            $(keyName).text(mainKey);
            $(variableRow).append(keyName);

            resData[mainKey].map(function (indieValue) {
                paramKeysList.map(function (paramKey) {
                    if (indieValue[paramKey]) {
                        var valueCell = $(cell).clone();
                        $(valueCell).text(indieValue[paramKey][(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]);
                        $(variableRow).append(valueCell);
                    }
                });
                $(table).append(variableRow);
            });
        });
    }

    $(container).append(header).append(table);
    $(presentArea).append(container);
};
var handlePrincipalComponentAnalysis = function (tableResult) {

    //声明放置区域
    var presentArea = $('.result-table');

    //声明DOM 元素
    var container = $('<div>');
    $(container).addClass('frequency-table');

    var tableHeader = $('<h1>');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered table-condensed');

    var row = $('<tr>');
    var headerCell = $('<th>');
    var cell = $('<td>');
    var emptyCell = $(headerCell).clone();

    var block = $('<div>');
    var span = $('<span>');


    //获取参数名列表
    var variableNameList = [];
    tableResult.variableList.map(function (obj) {
        variableNameList.push(obj.varietyName);
    });

    //1 打印『相关矩阵』
    //1.1获取『相关矩阵』值
    var correlationMatrix = tableResult.data.correlationArr;

    var correlationArea = $(container).clone();

    var correlationHeader = $(tableHeader).clone();
    $(correlationHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_correlation_matrix');
    $(correlationArea).append(correlationHeader);

    var correlationMatrixTable = $(table).clone();

    //1.2 打印相关矩阵头栏
    var correlationHeaderRow = $(row).clone();
    $(correlationHeaderRow).append(emptyCell.clone());

    variableNameList.map(function (variable) {
        var variableCell = $(headerCell).clone();
        $(variableCell).text(variable);
        $(correlationHeaderRow).append(variableCell);
    });
    $(correlationMatrixTable).append(correlationHeaderRow);

    //1.3 打印相关矩阵值
    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        var variableCell = $(cell).clone();
        $(variableCell).text(variable);
        $(variableRow).append(variableCell);

        //每行的值
        correlationMatrix[index].map(function (value) {
            var valueCell = $(cell).clone();
            $(valueCell).text(value);
            $(variableRow).append(valueCell);
        });
        $(correlationMatrixTable).append(variableRow);
    });

    //填充到显示区域
    $(correlationArea).append(correlationMatrixTable);
    //如果有行列式则填充
    if (tableResult.data.determinant) {
        $(correlationArea).append(
            $(block).append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_determinant'))
                .append($(span).text('=' + tableResult.data.determinant))
        );
    }
    $(presentArea).append(correlationArea);

    //2. 打印『公因子方差表』
    //2.1 获取『公因子方差』值
    var communalityArr = tableResult.data.communalityArr;

    var communalityArea = $(container).clone();

    var communalityHeader = $(tableHeader).clone();
    $(communalityHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_communalities');
    $(communalityArea).append(communalityHeader);

    var communalityTable = $(table).clone();

    //2.2 打印公因子方差表头栏
    var communalityHeaderRow = $(row).clone();
    $(communalityHeaderRow).append($(emptyCell).clone())
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_initial'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_extract'));
    $(communalityTable).append(communalityHeaderRow);

    //2.3 打印公因子方差值
    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        var variableCell = $(cell).clone();
        $(variableCell).text(variable);
        $(variableRow).append(variableCell);

        communalityArr[index].map(function (value) {
            var valueCell = $(cell).clone();
            $(valueCell).text(value);
            $(variableRow).append(valueCell);
        });
        $(communalityTable).append(variableRow);
    });

    //填充到显示区域
    $(communalityArea).append(communalityTable);
    $(presentArea).append(communalityArea);

    //3 打印『解释的总方差表』

    //3.1 获取相关变量值
    //3.1.1 初始合计
    var eigTotalInit = tableResult.data.eigTotalInit;
    //3.1.2 初始方差
    var varEigInit = tableResult.data.varEigInit;
    //3.1.3 初始累积
    var accEigInit = tableResult.data.accEigInit;
    //3.1.4 提取合计
    var eigTotalExtra = tableResult.data.eigTotalExtra;
    //3.1.5 提取方差
    var varEigExtra = tableResult.data.varEigExtra;
    //3.1.6 提取累积
    var accEigExtra = tableResult.data.accEigExtra;

    var explainTotalVarArea = $(container).clone();

    var explainTotalVarHeader = $(tableHeader).clone();
    $(explainTotalVarHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_total_variance_explained');
    $(explainTotalVarArea).append(explainTotalVarHeader);

    var explainTotalVarTable = $(table).clone();

    var explainTotalVarHeaderRow = $(row).clone();
    $(explainTotalVarHeaderRow).append($(emptyCell).clone().attr('rowspan', '2').attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component'))
        .append($(emptyCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_initial_eigenvalue').attr('colspan', '3').addClass('text-center'))
        .append($(emptyCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_extraction_sums_of_squared_loading').attr('colspan', '3').addClass('text-center'));
    $(explainTotalVarTable).append(explainTotalVarHeaderRow);

    //第二行展示
    var explainTotalVarSecHeaderRow = $(row).clone();
    $(explainTotalVarSecHeaderRow).append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_total').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_variance').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_Cumulative').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_total').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_variance').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_Cumulative').addClass('text-center'));
    $(explainTotalVarTable).append(explainTotalVarSecHeaderRow);


    //3.2 开始打值
    eigTotalInit.map(function (variable, index) {
        var commonRow = $(row).clone();

        //序号
        var titleCell = $(cell).clone();
        $(titleCell).text(index + 1);
        $(commonRow).append(titleCell);

        //值
        $(commonRow).append($(cell).clone().text(variable))
            .append($(cell).clone().text((varEigInit[index]) ? varEigInit[index] : ''))
            .append($(cell).clone().text((accEigInit[index]) ? accEigInit[index] : ''))
            .append($(cell).clone().text((eigTotalExtra[index]) ? eigTotalExtra[index] : ''))
            .append($(cell).clone().text((varEigExtra[index]) ? varEigExtra[index] : ''))
            .append($(cell).clone().text((accEigExtra[index]) ? accEigExtra[index] : ''));
        $(explainTotalVarTable).append(commonRow);
    });

    //填充到显示区域
    $(explainTotalVarArea).append(explainTotalVarTable);
    $(presentArea).append(explainTotalVarArea);

    //4 打印『成分矩阵表』
    //4.1 获取相关值
    var componentMatrix = tableResult.data.componentArr;

    var componentArea = $(container).clone();

    var componentHeader = $(tableHeader).clone();
    $(componentHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component_matrix');
    $(componentArea).append(componentHeader);

    var componentTable = $(table).clone();

    //2.2 打印成份矩阵表头栏
    var componentHeaderRow = $(row).clone();
    $(componentHeaderRow).append($(emptyCell).clone().attr('rowspan', '2'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component').addClass('text-center').attr('colspan', componentMatrix[0].length));
    $(componentTable).append(componentHeaderRow);

    var componentHeaderSecRow = $(row).clone();

    componentMatrix[0].map(function (value, index) {
        $(componentHeaderSecRow).append($(headerCell).clone().text(index + 1).attr('align', 'center'));
    });
    $(componentTable).append(componentHeaderSecRow);

    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        $(variableRow).append($(cell).clone().text(variable));

        componentMatrix[index].map(function (value) {
            $(variableRow).append($(cell).clone().text((value) ? value : " "));
        });

        $(componentTable).append(variableRow);
    });

    //填充到显示区域
    $(componentArea).append(componentTable)
        .append($(block).clone().text('已提取了' + componentMatrix[0].length + '个成份'));
    $(presentArea).append(componentArea);

    //5 KMO和Bartlett检验
    //5.1 获取相关数据
    var kmo = tableResult.data.kmo;

    var kmoArea = $(container).clone();

    var kmoHeader = $(tableHeader).clone();
    $(kmoHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_kmo_bartlett_test');
    $(kmoArea).append(kmoHeader);

    var kmoTable = $(table).clone();

    var kmoFirstRow = $(row).clone();
    $(kmoFirstRow).append($(cell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_kmo_measure'))
        .append($(cell).clone().text(kmo));
    $(kmoTable).append(kmoFirstRow);

    $(kmoArea).append(kmoTable)
        .append($(block).clone().text(tableResult.data.kmoDesc));
    $(presentArea).append(kmoArea);

};