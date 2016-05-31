//语言配置
var lang = localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE');
var _String = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_LANGUAGE_STRINGS'));

var resultTableGenerator = function () {

    var def = $.Deferred();

    var presentArea = $('.result-table');

    //结果变量
    var resultType = sessionStorage.getItem('PRIVATE_RESULT_TYPE');
    var tableResult = JSON.parse(sessionStorage.getItem('PRIVATE_TABLE_RESULT'));

    //制图参数
    var graphConfigs = sessionStorage.getItem('PRIVATE_GRAPH_CONFIG').split(',');

    //制表区
    switch (resultType) {
        case 'Descriptive_Statistics_Descriptive':
            //描述统计 - 描述
            handleDescriptiveStatisticsDescriptive(tableResult);
            break;
        case 'Descriptive_Statistics_Frequency':
            //描述统计 - 频率
            handleDescriptiveStatisticsFrequency(tableResult);
            break;
        case 'Correlation_Bivariate':
            //相关分析 - 双变量
            handleCorrelationBivariate(tableResult);
            break;
        case 'Correlation_Distance':
            //相关分析 - 距离
            handleCorrelationDistance(tableResult);
            break;
        case 'Principal_Component_Analysis':
            //降维 - 主成分
            handlePrincipalComponentAnalysis(tableResult);
            break;
        case 'Factor_Analysis':
            //降维 - 因子
            handleFactorAnalysis(tableResult);
            break;
        case 'Correspondence_Analysis':
            //降维 - 对应分析
            handleCorrespondenceAnalysis(tableResult);
            break;
        case 'Gray_Correlation':
            //灰色关联度
            handleGrayCorrelation(tableResult);
            break;
        case 'Related_Variable':
        case 'Independent_Variable':
            //灰色预测 - 关联变量
            handleRelatedVariable(tableResult);
            break;
        case 'Oneway_ANOVA':
            //均值比较 - 单因素方差分析 (哎呀，ANOVA 这名字实在是太好听了~)
            handleANOVA(tableResult);
            break;
        case 'Means':
            handleMeans(tableResult);
            break;
        case 'Simple_Linear_Regression':
            //一元线性回归
            handleSimpleLinearRegression(tableResult);
            break;
        case 'Multi_Linear_Regression':
            //多元线性回归
            handleMultiLinearRegression(tableResult);
            break;
        case 'General_Stepwise_Regression':
            //一般逐步回归
            handleGeneralStepwiseRegression(tableResult);
            break;
        case 'Slip_Stepwise':
            //滑移逐步回归
            handleSlipStepwiseRegression(tableResult);
            break;
        case 'Trend_Stepwise_Regression':
            //趋势逐步回归
            handleTrendStepwiseRegression(tableResult);
            break;
        case 'Optimal_Segmentation':
            //最优分割
            handleOptimalSegmentation(tableResult);
            break;
        case 'Cluster_Analysis':
            handleClusterAnalysis(tableResult);
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

            var graphName = _String['graph'][lang][graph];

            //丁氏图配置
            var dingConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DING_CHART'));


            $('#graph_' + i).charts({
                title: graphName,
                type: [graph],
                data: tableResult,
                calculateMethod: (dingConfig) ? dingConfig.calculateMethod[0] : null,//0行，1列，2全
                ellipsesColor: (dingConfig) ? dingConfig.ellipsesColor[0] : null,
                cureColor: (dingConfig) ? dingConfig.cureColor[0] : null
            });

            //switch (graph) {
            //    case 'boxplot':
            //        //箱线图
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['boxplot'],
            //            data: tableResult
            //        });
            //        break;
            //    case 'piegraph':
            //        //饼图
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['pie'],
            //            data: tableResult
            //        });
            //        break;
            //    case 'histogram':
            //        //直方图
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['bar'],
            //            data: tableResult
            //        });
            //        break;
            //    case 'linechart':
            //        //折线图
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['line'],
            //            data: tableResult
            //        });
            //        break;
            //    case 'scatterdiagram':
            //        //散点图
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['cure'],
            //            data: tableResult
            //        });
            //        break;
            //    case 'dingchart':
            //        //丁氏图
            //        var dingConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DING_CHART'));
            //        $('#graph_' + i).charts({
            //            title: graphName,
            //            type: ['dingchart'],
            //            data: tableResult,
            //            calculateMethod: dingConfig.calculateMethod[0],//0行，1列，2全
            //            ellipsesColor: "#CC5B58",
            //            cureColor: "#D48366"
            //        });
            //        break;
            //    case 'basicline':
            //        //碎石图
            //        $('#graph_' + i).charts({
            //            title: '碎石图',
            //            type: ['screeplot'],
            //            data: tableResult.data.eigTotalInit
            //        });
            //        break;
            //}
        });
    }
    return def.resolve().promise();
};

var handleDescriptiveStatisticsDescriptive = function (tableResult) {

    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
            $(td).text(Number(params[config]).toFixed(numReservation));
            $(tr).append(td);
        });

        $(table).append(tr);
    }

    $(presentArea).append(table);

};
var handleDescriptiveStatisticsFrequency = function (tableResult) {

    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
            $(percentageCell).text(Number(percentage[unique]).toFixed(numReservation));
            $(tr).append(percentageCell);

            var validatePercentageCell = $('<td>');
            $(validatePercentageCell).text(Number(percentage[unique]).toFixed(numReservation));
            $(tr).append(validatePercentageCell);

            var accumulationPercentageCell = $('<td>');
            $(accumulationPercentageCell).text(Number(accumulationPercentage[unique]).toFixed(numReservation));
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
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
                                        (bivariateTableData[mainKey][i][paramVariableKey][alConfig] == 'Infinity') ? '&nbsp;' : Number(bivariateTableData[mainKey][i][paramVariableKey][alConfig]).toFixed(numReservation)
                                    ).attr('data-config-type', alConfig);
                                }
                                $(paramValueCell).append(pearsonParamValueWrapper);
                            }
                        } else if (option == 'spearman') {
                            if ($.inArray(alConfig, ['pearsonR', 'pearsonT']) == -1) {
                                var spearmanParamValueWrapper = $('<div>');
                                for (var paramVariableKey in bivariateTableData[mainKey][i]) {
                                    $(spearmanParamValueWrapper).html(
                                        (bivariateTableData[mainKey][i][paramVariableKey][alConfig] == 'Infinity') ? '&nbsp;' : Number(bivariateTableData[mainKey][i][paramVariableKey][alConfig]).toFixed(numReservation)
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
            $(paramValue).text(Number(basicData[mainKey][alConfig]).toFixed(numReservation));
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

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
                var fixedValue = (Number(data[(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]) != NaN) ?
                    Number(data[(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]).toFixed(numReservation) :
                    data[(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod];
                $(td).text(fixedValue);
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
                        var fixedValue = (Number(indieValue[paramKey][(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]) != NaN) ?
                            Number(indieValue[paramKey][(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod]).toFixed(numReservation) :
                            indieValue[paramKey][(measureMethod == 'modifyDistance') ? 'minkowskiDistace' : measureMethod];
                        $(valueCell).text(fixedValue);
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

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
            $(valueCell).text((Number(value) != NaN) ? Number(value).toFixed(numReservation) : value);
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
        .append($(cell).clone().text((Number(kmo) != NaN) ? Number(kmo).toFixed(numReservation) : kmo));
    $(kmoTable).append(kmoFirstRow);

    $(kmoArea).append(kmoTable)
        .append($(block).clone().text(tableResult.data.kmoDesc));
    $(presentArea).append(kmoArea);

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
            $(valueCell).text((Number(value) != NaN) ? Number(value).toFixed(numReservation) : value);
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
        $(commonRow).append($(cell).clone().text(Number(variable).toFixed(numReservation)))
            .append($(cell).clone().text((varEigInit[index]) ? Number(varEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigInit[index]) ? Number(accEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((eigTotalExtra[index]) ? Number(eigTotalExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((varEigExtra[index]) ? Number(varEigExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigExtra[index]) ? Number(accEigExtra[index]).toFixed(numReservation) : ''));
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
        $(componentHeaderSecRow).append($(headerCell).clone().text('F' + (index + 1)));
    });
    $(componentTable).append(componentHeaderSecRow);

    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        $(variableRow).append($(cell).clone().text(variable));

        componentMatrix[index].map(function (value) {
            $(variableRow).append($(cell).clone().text((value) ? Number(value).toFixed(numReservation) : " "));
        });

        $(componentTable).append(variableRow);
    });

    //填充到显示区域
    $(componentArea).append(componentTable)
        .append($(block).clone().text('已提取了' + componentMatrix[0].length + '个成份'));
    componentMatrix[0].map(function (value, index) {
        var functionString = '<strong>F' + (index + 1) + '</strong> = ';

        variableNameList.map(function (variable, index2) {
            if (index2 != 0 && Number(componentMatrix[index2][index]) > 0) {
                functionString += ' + ';
            } else if (Number(componentMatrix[index2][index]) < 0) {
                functionString += ' - ';
            }
            functionString += Math.abs(Number(componentMatrix[index2][index]).toFixed(numReservation)) + ' x ';
            functionString += variable;
        });

        $(componentArea).append(
            $(block).clone().html(functionString)
        );
    });
    $(presentArea).append(componentArea);

    //制图
    //碎石图
    $(presentArea).append(
        $(block).clone().css('height', '800px').addClass('col-md-12').attr('id', 'graph_screeplot')
    );
    $('#graph_screeplot').charts({
        title: _String['graph'][lang]['scree_plot'],
        data: tableResult.data.eigTotalInit,
        type: ['screeplot']
    });

    if (componentMatrix[0].length == 2) {
        //画2D图
        var graphArea = $('<div>');
        $(graphArea).css({
            'height': 800
        }).addClass('col-md-12').attr('id', 'graph_2d');

        $(presentArea).append(graphArea);

        var graphName = _String['graph'][lang]['pcfp2d'];

        $('#graph_2d').charts({
            title: graphName,
            type: ['pcfp2d'],
            data: tableResult
        });
    } else if (componentMatrix[0].length == 3) {
        //画2D图
        var graphArea = $('<div>');
        $(graphArea).css({
            'height': 800
        }).addClass('col-md-12').attr('id', 'graph_3d');

        $(presentArea).append(graphArea);

        var graphName = _String['graph'][lang]['pcfp3d'];

        $('#graph_3d').charts({
            title: graphName,
            type: ['pcfp3d'],
            data: tableResult
        });
    }

};
var handleFactorAnalysis = function (tableResult) {

    //声明放置区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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
            $(valueCell).text((Number(value) != NaN) ? Number(value).toFixed(numReservation) : value);
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


    //2 打印『解释的总方差表』

    //2.1 获取相关变量值
    //2.1.1 初始合计
    var eigTotalInit = tableResult.data.eigTotalInit;
    //2.1.2 初始方差
    var varEigInit = tableResult.data.varEigInit;
    //2.1.3 初始累积
    var accEigInit = tableResult.data.accEigInit;
    //2.1.4 提取合计
    var eigTotalExtra = tableResult.data.eigTotalExtra;
    //2.1.5 提取方差
    var varEigExtra = tableResult.data.varEigExtra;
    //2.1.6 提取累积
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

    //3 打印『相关系数特征向量』
    var correlationCoeffArea = $(container).clone();
    $(correlationCoeffArea).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_correlation_coefficient_eigen_vector'));
    //3.2 打印表格
    var correlationCoeffTable = $(table).clone();
    //3.2.1 打印标题行
    var titleRow = $(row).clone();
    $(titleRow).append($(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'var_eigen'));
    tableResult.data.eigenvectors.map(function (data, index) {
        $(titleRow).append($(headerCell).clone().text('λ' + (index + 1)));
    });
    $(correlationCoeffTable).append(titleRow);

    //3.2.2 打印值
    variableNameList.map(function (variable, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(variable));

        tableResult.data.eigenvectors.map(function (data, i) {
            $(dataRow).append($(cell).clone().text(data[index]));
        });
        $(correlationCoeffTable).append(dataRow);
    });

    $(correlationCoeffArea).append(correlationCoeffTable);
    $(presentArea).append(correlationCoeffArea);

    //4. 打印『公因子方差表』
    //4.1 获取『公因子方差』值
    var communalityArr = tableResult.data.communalityArr;

    var communalityArea = $(container).clone();

    var communalityHeader = $(tableHeader).clone();
    $(communalityHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_communalities');
    $(communalityArea).append(communalityHeader);

    var communalityTable = $(table).clone();

    //4.2 打印公因子方差表头栏
    var communalityHeaderRow = $(row).clone();
    $(communalityHeaderRow).append($(emptyCell).clone())
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_initial'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_extract'));
    $(communalityTable).append(communalityHeaderRow);

    //4.3 打印公因子方差值
    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        var variableCell = $(cell).clone();
        $(variableCell).text(variable);
        $(variableRow).append(variableCell);

        communalityArr[index].map(function (value) {
            var valueCell = $(cell).clone();
            $(valueCell).text((Number(value) != NaN) ? Number(value).toFixed(numReservation) : value);
            $(variableRow).append(valueCell);
        });
        $(communalityTable).append(variableRow);
    });

    //填充到显示区域
    $(communalityArea).append(communalityTable);
    $(presentArea).append(communalityArea);


    //4.2 开始打值
    eigTotalInit.map(function (variable, index) {
        var commonRow = $(row).clone();

        //序号
        var titleCell = $(cell).clone();
        $(titleCell).text(index + 1);
        $(commonRow).append(titleCell);

        //值
        $(commonRow).append($(cell).clone().text(Number(variable).toFixed(numReservation)))
            .append($(cell).clone().text((varEigInit[index]) ? Number(varEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigInit[index]) ? Number(accEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((eigTotalExtra[index]) ? Number(eigTotalExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((varEigExtra[index]) ? Number(varEigExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigExtra[index]) ? Number(accEigExtra[index]).toFixed(numReservation) : ''));
        $(explainTotalVarTable).append(commonRow);
    });

    //填充到显示区域
    $(explainTotalVarArea).append(explainTotalVarTable);
    $(presentArea).append(explainTotalVarArea);

    //5 打印『因子载荷矩阵』
    //5.1 获取相关值
    var componentMatrix = tableResult.data.componentArr;

    var componentArea = $(container).clone();

    var componentHeader = $(tableHeader).clone();
    $(componentHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_factor_loading_matrix');
    $(componentArea).append(componentHeader);

    var componentTable = $(table).clone();

    //5.2 打印成份矩阵表头栏
    var componentHeaderRow = $(row).clone();
    $(componentHeaderRow).append($(emptyCell).clone().attr('rowspan', '2'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component').addClass('text-center').attr('colspan', componentMatrix[0].length));
    $(componentTable).append(componentHeaderRow);

    var componentHeaderSecRow = $(row).clone();

    componentMatrix[0].map(function (value, index) {
        $(componentHeaderSecRow).append($(headerCell).clone().text('F' + (index + 1)));
    });
    $(componentTable).append(componentHeaderSecRow);

    variableNameList.map(function (variable, index) {
        var variableRow = $(row).clone();

        $(variableRow).append($(cell).clone().text(variable));

        componentMatrix[index].map(function (value) {
            $(variableRow).append($(cell).clone().text((value) ? Number(value).toFixed(numReservation) : " "));
        });

        $(componentTable).append(variableRow);
    });

    //填充到显示区域
    $(componentArea).append(componentTable)
        .append($(block).clone().text('已提取了' + componentMatrix[0].length + '个成份'));
    componentMatrix[0].map(function (value, index) {
        var functionString = '<strong>F' + (index + 1) + '</strong> = ';

        variableNameList.map(function (variable, index2) {
            if (index2 != 0 && Number(componentMatrix[index2][index]) > 0) {
                functionString += ' + ';
            } else if (Number(componentMatrix[index2][index]) < 0) {
                functionString += ' - ';
            }
            functionString += Math.abs(Number(componentMatrix[index2][index]).toFixed(numReservation)) + ' x ';
            functionString += variable;
        });

        $(componentArea).append(
            $(block).clone().html(functionString)
        );
    });
    $(presentArea).append(componentArea);

    //6 打印方差最大正交旋转矩阵
    var varianceData = tableResult.data.orRotaArr;

    var varianceArea = $(container).clone();

    $(varianceArea).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_factor_analysis_variance_matrix'));

    var varianceTable = $(table).clone();

    //6.1 打印标题行
    var varianceHeaderRow = $(row).clone();
    $(varianceHeaderRow).append($(emptyCell).clone().attr('rowspan', '2'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component').addClass('text-center').attr('colspan', componentMatrix[0].length));
    $(varianceTable).append(varianceHeaderRow);

    var varianceHeaderSecRow = $(row).clone();
    varianceData[0].map(function (value, index) {
        $(varianceHeaderSecRow).append($(headerCell).clone().text('F' + (index + 1)));
    });
    $(varianceTable).append(varianceHeaderSecRow);

    //6.2 打印数据行
    variableNameList.map(function (variable, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(variable));

        varianceData.map(function (value, i) {
            $(dataRow).append($(cell).clone().text(Number(value[index]).toFixed(numReservation)));
        });
        $(varianceTable).append(dataRow);
    });

    $(varianceArea).append(varianceTable);
    $(presentArea).append(varianceArea);

    //7 打印『正交因子得分表』
    var orthData = tableResult.data.orFacArr;
    var orthArea = $(container).clone();
    $(orthArea).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_orthogonal_factor_scores'));

    var orthTable = $(table).clone();
    //7.1 打印首行
    var orthTitleRow = $(row).clone();
    $(orthTitleRow).append($(headerCell).clone());

    var colNum = orthData[0].length;
    for (var i = 0; i < colNum; i++) {
        $(orthTitleRow).append($(headerCell).clone().text('F' + (i + 1)));
    }
    $(orthTable).append(orthTitleRow);

    //7.2 打印数据行
    orthData.map(function (data, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(index + 1).css('text-align', 'center'));
        for (var i = 0; i < colNum; i++) {
            $(dataRow).append($(cell).clone().text(Number(data[i]).toFixed(numReservation)));
        }
        $(orthTable).append(dataRow);
    });

    $(orthArea).append(orthTable);
    $(presentArea).append(orthArea);
};

var handleCorrespondenceAnalysis = function (tableResult) {

    //声明放置区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    var SS = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRESPONDENCE_ANALYSIS'));

    //1 打印『概率矩阵』
    var frequencyArea = $(container).clone();
    $(frequencyArea).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_frequency_matrix'));
    var frequencyTable = $(table).clone();
    //1.1 打印标题行
    var titleRow = $(row).clone();
    $(titleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'sample_var')
    );

    SS.variableList.map(function (variable) {
        $(titleRow).append(
            $(headerCell).clone().text(variable.varietyName)
        )
    });

    $(titleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'row_density')
    );
    $(frequencyTable).append(titleRow);

    //1.2 打印数据行
    tableResult.proArr.map(function (data, index) {
        var dataRow = $(row).clone();

        if (tableResult.proArr[index + 1]) {
            $(dataRow).append($(cell).clone().text(index + 1));
        } else {
            $(dataRow).append($(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'col_density'));
        }

        data.map(function (value) {
            $(dataRow).append($(cell).clone().text(Number(value).toFixed(numReservation)));
        });

        $(frequencyTable).append(dataRow);
    });

    $(frequencyArea).append(frequencyTable);
    $(presentArea).append(frequencyArea);

    //2 打印『Z矩阵』
    var zMatrixArea = $(container).clone();
    $(zMatrixArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_z_matrix')
    );
    //2.1 打印表格
    var zMatrixTable = $(table).clone();

    var zMatrixTitleRow = $(row).clone();
    $(zMatrixTitleRow).append($(headerCell).clone());

    SS.variableList.map(function (variable) {
        $(zMatrixTitleRow).append(
            $(headerCell).clone().text(variable.varietyName)
        )
    });

    $(zMatrixTable).append(zMatrixTitleRow);

    tableResult.z.map(function (data, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(index + 1));

        data.map(function (value) {
            $(dataRow).append(
                $(cell).clone().text(Number(value).toFixed(numReservation))
            )
        });

        $(zMatrixTable).append(dataRow);
    });

    $(zMatrixArea).append(zMatrixTable);
    $(presentArea).append(zMatrixArea);

    //4 打印『变量间解释的总方差』表
    //4.1 获取相关变量值
    //4.1.1 初始合计
    var eigTotalInit = tableResult.srPcaDTO.eigTotalInit;
    //4.1.2 初始方差
    var varEigInit = tableResult.srPcaDTO.varEigInit;
    //4.1.3 初始累积
    var accEigInit = tableResult.srPcaDTO.accEigInit;
    //4.1.4 提取合计
    var eigTotalExtra = tableResult.srPcaDTO.eigTotalExtra;
    //4.1.5 提取方差
    var varEigExtra = tableResult.srPcaDTO.varEigExtra;
    //4.1.6 提取累积
    var accEigExtra = tableResult.srPcaDTO.accEigExtra;

    var explainTotalVarArea = $(container).clone();

    var explainTotalVarHeader = $(tableHeader).clone();
    $(explainTotalVarHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_variable_total_variance_explained');
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


    //4.2 开始打值
    eigTotalInit.map(function (variable, index) {
        var commonRow = $(row).clone();

        //序号
        var titleCell = $(cell).clone();
        $(titleCell).text(index + 1);
        $(commonRow).append(titleCell);

        //值
        $(commonRow).append($(cell).clone().text(Number(variable).toFixed(numReservation)))
            .append($(cell).clone().text((varEigInit[index]) ? Number(varEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigInit[index]) ? Number(accEigInit[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((eigTotalExtra[index]) ? Number(eigTotalExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((varEigExtra[index]) ? Number(varEigExtra[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigExtra[index]) ? Number(accEigExtra[index]).toFixed(numReservation) : ''));
        $(explainTotalVarTable).append(commonRow);
    });

    //填充到显示区域
    $(explainTotalVarArea).append(explainTotalVarTable);
    $(presentArea).append(explainTotalVarArea);


    //5 打印『样本间解释的总方差』表
    //5.1 获取相关变量值
    //5.1.1 初始合计
    var eigTotalInit_2 = tableResult.sqPcaDTO.eigTotalInit;
    //5.1.2 初始方差
    var varEigInit_2 = tableResult.sqPcaDTO.varEigInit;
    //5.1.3 初始累积
    var accEigInit_2 = tableResult.sqPcaDTO.accEigInit;
    //5.1.4 提取合计
    var eigTotalExtra_2 = tableResult.sqPcaDTO.eigTotalExtra;
    //5.1.5 提取方差
    var varEigExtra_2 = tableResult.sqPcaDTO.varEigExtra;
    //5.1.6 提取累积
    var accEigExtra_2 = tableResult.sqPcaDTO.accEigExtra;

    var explainTotalVarArea_2 = $(container).clone();

    var explainTotalVarHeader_2 = $(tableHeader).clone();
    $(explainTotalVarHeader_2).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_sample_total_variance_explained');
    $(explainTotalVarArea_2).append(explainTotalVarHeader_2);

    var explainTotalVarTable_2 = $(table).clone();

    var explainTotalVarHeaderRow_2 = $(row).clone();
    $(explainTotalVarHeaderRow_2).append($(emptyCell).clone().attr('rowspan', '2').attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component'))
        .append($(emptyCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_initial_eigenvalue').attr('colspan', '3').addClass('text-center'))
        .append($(emptyCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_extraction_sums_of_squared_loading').attr('colspan', '3').addClass('text-center'));
    $(explainTotalVarTable_2).append(explainTotalVarHeaderRow_2);

    //第二行展示
    var explainTotalVarSecHeaderRow_2 = $(row).clone();
    $(explainTotalVarSecHeaderRow_2).append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_total').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_variance').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_Cumulative').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_total').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_variance').addClass('text-center'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_percent_of_Cumulative').addClass('text-center'));
    $(explainTotalVarTable_2).append(explainTotalVarSecHeaderRow_2);


    //5.2 开始打值
    eigTotalInit_2.map(function (variable, index) {
        var commonRow = $(row).clone();

        //序号
        var titleCell = $(cell).clone();
        $(titleCell).text(index + 1);
        $(commonRow).append(titleCell);

        //值
        $(commonRow).append($(cell).clone().text(Number(variable).toFixed(numReservation)))
            .append($(cell).clone().text((varEigInit_2[index]) ? Number(varEigInit_2[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigInit_2[index]) ? Number(accEigInit_2[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((eigTotalExtra_2[index]) ? Number(eigTotalExtra_2[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((varEigExtra_2[index]) ? Number(varEigExtra_2[index]).toFixed(numReservation) : ''))
            .append($(cell).clone().text((accEigExtra_2[index]) ? Number(accEigExtra_2[index]).toFixed(numReservation) : ''));
        $(explainTotalVarTable_2).append(commonRow);
    });

    //填充到显示区域
    $(explainTotalVarArea_2).append(explainTotalVarTable_2);
    $(presentArea).append(explainTotalVarArea_2);

    //3 打印『变量间协方差矩阵』
    var varCovarianceMatrixArea = $(container).clone();
    $(varCovarianceMatrixArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_variable_covariance_matrix')
    );
    var varCovarianceMatrixTable = $(table).clone();

    var varCovarianceMatrixTitleRow = $(row).clone();
    $(varCovarianceMatrixTitleRow).append($(headerCell).clone());

    SS.variableList.map(function (variable) {
        $(varCovarianceMatrixTitleRow).append(
            $(headerCell).clone().text(variable.varietyName)
        )
    });

    $(varCovarianceMatrixTable).append(varCovarianceMatrixTitleRow);
    //3.1 打印表格
    tableResult.sr.map(function (data, index) {
        var dataRow = $(row).clone();
        $(dataRow).append(
            $(cell).clone().text(SS.variableList[index].varietyName)
        );
        data.map(function (value) {
            $(dataRow).append(
                $(cell).clone().text(Number(value).toFixed(numReservation))
            );
        });

        $(varCovarianceMatrixTable).append(dataRow);
    });

    $(varCovarianceMatrixArea).append(varCovarianceMatrixTable);
    $(presentArea).append(varCovarianceMatrixArea);

    //6 打印『样本间协方差矩阵』
    var samCovarianceMatrixArea = $(container).clone();
    $(samCovarianceMatrixArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_sample_covariance_matrix')
    );
    var samCovarianceMatrixTable = $(table).clone();
    var samCovarianceTitleRow = $(row).clone();
    $(samCovarianceTitleRow).append($(headerCell).clone());
    for (var i = 0; i < tableResult.sq.length; i++) {
        $(samCovarianceTitleRow).append($(headerCell).clone().text(i + 1));
    }
    $(samCovarianceMatrixTable).append(samCovarianceTitleRow);
    //6.1 打印表格
    tableResult.sq.map(function (data, index) {
        var dataRow = $(row).clone();

        $(dataRow).append(
            $(cell).clone().text(index + 1)
        )
        ;
        data.map(function (value) {
            $(dataRow).append(
                $(cell).clone().text(Number(value).toFixed(numReservation))
            );
        });
        $(samCovarianceMatrixTable).append(dataRow);
    });

    $(samCovarianceMatrixArea).append(samCovarianceMatrixTable);
    $(presentArea).append(samCovarianceMatrixArea);

    //7 打印『变量间特征向量』表
    var container_7 = $(container).clone();
    $(container_7).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_variable_characteristic_vector')
    );
    var table_7 = $(table).clone();

    var table_7_row_1 = $(row).clone();
    $(table_7_row_1).append($(headerCell).clone());
    tableResult.srPcaDTO.eigenvectors.map(function (data, index) {
        $(table_7_row_1).append($(headerCell).clone().text('λ' + (index + 1)));
    });
    $(table_7).append(table_7_row_1);

    tableResult.srPcaDTO.eigenvectors[0].map(function (val, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(SS.variableList[index].varietyName));
        tableResult.srPcaDTO.eigenvectors.map(function (data) {
            $(dataRow).append(
                $(cell).clone().text(Number(data[index]).toFixed(numReservation))
            );
        });
        $(table_7).append(dataRow);
    });
    $(container_7).append(table_7);
    $(presentArea).append(container_7);

    //8 打印『样本间特征向量』表
    var container_8 = $(container).clone();
    $(container_8).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_sample_characteristic_vector')
    );
    var table_8 = $(table).clone();

    var table_8_row_1 = $(row).clone();
    $(table_8_row_1).append($(headerCell).clone());
    tableResult.sqPcaDTO.eigenvectors.map(function (data, index) {
        $(table_8_row_1).append($(headerCell).clone().text('λ' + (index + 1)));
    });
    $(table_8).append(table_8_row_1);

    tableResult.sqPcaDTO.eigenvectors[0].map(function (val, index) {
        var dataRow = $(row).clone();
        $(dataRow).append($(cell).clone().text(index + 1));
        tableResult.sqPcaDTO.eigenvectors.map(function (data) {
            $(dataRow).append(
                $(cell).clone().text(Number(data[index]).toFixed(numReservation))
            );
        });
        $(table_8).append(dataRow);
    });
    $(container_8).append(table_8);
    $(presentArea).append(container_8);

    //9 打印『R型因子载荷矩阵』
    var container_9 = $(container).clone();
    $(container_9).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_variable_loading_matrix')
    );
    var table_9 = $(table).clone();
    var table_9_row_1 = $(row).clone();
    $(table_9_row_1).append($(headerCell).clone().attr('rowspan', '2'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component').css('text-align', 'center').attr('colspan', tableResult.srPcaDTO.eigenvectors.length));

    var table_9_row_2 = $(row).clone();
    tableResult.srPcaDTO.componentArr.map(function (data, index) {
        $(table_9_row_2).append($(headerCell).clone().text('F' + (index + 1)));
    });
    $(table_9).append(table_9_row_1).append(table_9_row_2);

    tableResult.srPcaDTO.componentArr[0].map(function (val, index) {
        var dataRow = $(row).clone();
        $(dataRow).append(
            $(cell).clone().text(SS.variableList[index].varietyName)
        );
        tableResult.srPcaDTO.componentArr.map(function (data) {
            $(dataRow).append(
                $(cell).clone().text((data[index] == 'NaN') ? '' : Number(data[index]).toFixed(numReservation))
            );
        });
        $(table_9).append(dataRow);
    });

    $(container_9).append(table_9);
    $(presentArea).append(container_9);

    //10 打印『Q型因子载荷矩阵』
    var container_10 = $(container).clone();
    $(container_10).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_sample_loading_matrix')
    );
    var table_10 = $(table).clone();
    var table_10_row_1 = $(row).clone();
    $(table_10_row_1).append($(headerCell).clone().attr('rowspan', '2'))
        .append($(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_component').css('text-align', 'center').attr('colspan', tableResult.srPcaDTO.eigenvectors.length));

    var table_10_row_2 = $(row).clone();
    tableResult.sqPcaDTO.componentArr.map(function (val, index) {
        $(table_10_row_2).append($(headerCell).clone().text('F' + (index + 1)));
    });
    $(table_10).append(table_10_row_1).append(table_10_row_2);

    tableResult.sqPcaDTO.componentArr[0].map(function (data, index) {
        var dataRow = $(row).clone();

        $(dataRow).append($(cell).clone().text(index + 1));

        tableResult.sqPcaDTO.componentArr.map(function (col, _index) {
            $(dataRow).append($(cell).clone().text(Number(col[index]).toFixed(numReservation)))
        });

        $(table_10).append(dataRow);
    });

    $(container_10).append(table_10);
    $(presentArea).append(container_10);

};

var handleRelatedVariable = function (tableResult) {

    //声明放置区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //是否可预测
    //if (tableResult.examineSuccess) {
    //变量数组
    var variableList = [];
    tableResult.independVarList.map(function (variable) {
        variableList.push(variable.varietyName);
    });

    //变量行
    var variableRow = $(row).clone();
    $(variableRow).append(emptyCell);
    variableList.map(function (variableName) {
        $(variableRow).append($(headerCell).clone().text(variableName));
    });
    $(table).append(variableRow);

    //打印预测值
    var predictionRow = $(row).clone();
    $(predictionRow).append($(cell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_prediction_value'));
    tableResult.resData.map(function (value) {
        if (Number(value) != NaN) {
            $(predictionRow).append($(cell).clone().text(Number(value).toFixed(numReservation)));
        }
    });
    $(table).append(predictionRow);

    //打印表名和表格
    $(container).append($(tableHeader).clone().text(tableResult.predictName)).append(table);
    $(presentArea).append(container);
    //} else {
    //    alert('该数据不符合灰色预测的数据检验要求');
    //    window.history.go(-1);
    //}
};
var handleGrayCorrelation = function (tableResult) {
    //声明放置区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

    //声明DOM 元素
    var container = $('<div>');
    $(container).addClass('frequency-table');

    var tableHeader = $('<h1>');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered table-condensed');

    var row = $('<tr>');
    var headerCell = $('<th>');
    var cell = $('<td>');

    var block = $('<div>');
    var span = $('<span>');

    $(container).append($(tableHeader).attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_gray_correlation_table'));

    //获取本地参数
    var GC = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GRAY_CORRELATION'));
    var variableList = GC.sonSeqArr;
    //后台没传变量名，自行封装一个值-名对象
    var gcObject = {};
    tableResult.correlationArr.map(function (corralation, index) {
        gcObject[corralation] = variableList[index].varietyName;
    });

    //打印变量行
    var variableRow = $(row).clone();
    $(variableRow).append($(headerCell).clone());
    variableList.map(function (variable) {
        $(variableRow).append($(headerCell).clone().text(variable.varietyName));
    });
    $(table).append(variableRow);

    //打印关联度值
    var corrRow = $(row).clone();
    $(corrRow).append($(cell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_correlation'));
    tableResult.correlationArr.map(function (corralation) {
        $(corrRow).append($(cell).clone().text(Number(corralation).toFixed(numReservation)));
    });
    $(table).append(corrRow);

    //打印大小关系
    var relationRow = $(row).clone();
    $(relationRow).append($(cell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_sequence'));
    var sequenceStr = '';
    tableResult.sortCorrelationArr.map(function (sort, index) {
        sequenceStr += Number(sort).toFixed(numReservation);
        if (tableResult.sortCorrelationArr[++index]) {
            sequenceStr += ' > ';
        }
    });
    $(relationRow).append($(cell).clone().text(sequenceStr).attr('colspan', variableList.length));
    $(table).append(relationRow);

    //打印结论
    var conclusionRow = $(row).clone();
    $(conclusionRow).append($(cell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_conclusion'));
    var conclusionStr = '';
    if (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') {
        conclusionStr = '<strong>' + GC.motherSeq[0].varietyName + '</strong>' + ' 受到 ' + '<strong>' + gcObject[tableResult.maxCorrelation] + '</strong>' + ' 的影响最大';
    } else {
        conclusionStr = '<strong>' + gcObject[tableResult.maxCorrelation] + '</strong>' + ' has the greatest effect on ' + '<strong>' + GC.motherSeq[0].varietyName + '</strong>';
    }
    $(conclusionRow).append($(cell).clone().html(conclusionStr).attr('colspan', variableList.length));
    $(table).append(conclusionRow);

    $(container).append(table);
    $(presentArea).append(container);
};

var handleANOVA = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //获取待处理对象
    var handleData = tableResult.resDataMap;

    //获取变量名
    var variableList = Object.keys(handleData);

    //参数列表【骂后台把，变量名太散】
    var ANOVAconfigs = ['sum_of_squares', 'df', 'mean_square', 'f'];

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //打印标题
    $(container).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'modal_title_oneway_anova')
    );

    //打印表格
    //打印第一行
    var headerRow = $(row).clone();
    $(headerRow).append($(emptyCell).clone().attr('colspan', '2'));
    ANOVAconfigs.map(function (config) {
        $(headerRow).append(
            $(headerCell).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', config)
        )
    });
    $(table).append(headerRow);

    //打印变量行
    variableList.map(function (variableName) {
        //存储值
        var variable = handleData[variableName];

        var variableRow = $(row).clone();
        $(variableRow).append(
            $(cell).clone().text(variableName)
        );

        var titleCell = $(cell).clone();
        ['between_groups', 'within_group', 'total'].map(function (title) {
            $(titleCell).append(
                $(block).clone()
                    .attr('data-i18n-type', 'table')
                    .attr('data-i18n-tag', title)
                    .attr('align', 'center')
            );
        });

        //平方和这一块
        var sumSquareCell = $(cell).clone();
        [variable.ssbg, variable.sswg, variable.sst].map(function (value) {
            $(sumSquareCell).append(
                $(block).clone().html(
                    (Number(value) != NaN) ? Number(value).toFixed(numReservation) : value
                )
            )
        });

        //df这一块
        var dfCell = $(cell).clone();
        [variable.dfbg, variable.dfwg, variable.dft].map(function (value) {
            $(dfCell).append(
                $(block).clone().html(
                    (Number(value) != NaN) ? Number(value).toFixed(numReservation) : value
                )
            )
        });

        //均方这一块
        var msCell = $(cell).clone();
        [variable.msbg, variable.mswg].map(function (value) {
            $(msCell).append(
                $(block).clone().html(
                    (Number(value) != NaN) ? Number(value).toFixed(numReservation) : value
                )
            )
        });

        //F值这一块
        var fCell = $(cell).clone();
        $(fCell).html(
            (Number(variable.f) != NaN) ? Number(variable.f).toFixed(numReservation) : variable.f
        )

        $(variableRow).append(titleCell)
            .append(sumSquareCell)
            .append(dfCell)
            .append(msCell)
            .append(fCell);
        $(table).append(variableRow);
    });

    $(container).append(table);
    $(presentArea).append(container);
};
var handleMeans = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //获取待处理对象
    var handleData = tableResult.resDataMap;

    //变量的名字列表
    var objectKeys = Object.keys(handleData);
    //参数的名字列表
    var paramKeys = Object.keys(handleData[objectKeys[0]]);

    //获取显示的算法配置
    var meansConfigs = sessionStorage.getItem('PRIVATE_ALGORITHM_CONFIG').split(',');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //打印标题
    $(container).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_report')
    );

    //打印表格
    //打印参数名行
    var headerRow = $(row).clone();
    $(headerRow).append($(emptyCell).clone().attr('colspan', '2'));
    paramKeys.map(function (param) {
        $(headerRow).append($(headerCell).clone().text(param));
    });
    $(table).append(headerRow);

    //打印变量行
    objectKeys.map(function (variableName) {
        var variableRow = $(row).clone();
        $(variableRow).append($(cell).clone().text(variableName));

        var configCell = $(cell).clone();
        meansConfigs.map(function (config) {
            $(configCell).append($(block).clone().attr('data-i18n-type', 'algorithm').attr('data-i18n-tag', config));
        });
        $(variableRow).append(configCell);

        //打印值
        paramKeys.map(function (param) {
            var valueCell = $(cell).clone();
            meansConfigs.map(function (config) {
                var printValue = (handleData[variableName][param]['resultData'][config]) ? handleData[variableName][param]['resultData'][config] : ' ';
                $(valueCell).append($(block).clone().html(
                    (Number(printValue) != NaN) ? Number(printValue).toFixed(numReservation) : printValue
                ));
            });
            $(variableRow).append(valueCell);
        });
        $(table).append(variableRow);
    });

    $(container).append(table);
    $(presentArea).append(container);
};
var handleSimpleLinearRegression = function (tableResult) {
    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //打印三个表格
    //第一个表格：模型汇总
    //需要打印的变量列表
    var modelSummaryList = ['r', 'rsquare', 'adjustedRSquared'];
    var modelSummaryArea = $(container).clone();
    $(modelSummaryArea).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_model_summary')
    );

    var modelSummaryTable = $(table).clone();
    var titleRow = $(row).clone();
    $(titleRow).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'model')
    );
    modelSummaryList.map(function (name) {
        $(titleRow).append(
            $(headerCell).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', name)
        )
    });
    $(modelSummaryTable).append(titleRow);
    //变量值
    var valueRow = $(row).clone();
    $(valueRow).append(
        $(cell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'linear')
    );
    modelSummaryList.map(function (key) {
        $(valueRow).append(
            $(cell).clone().text(
                (Number(tableResult[key]) != NaN) ? Number(tableResult[key]).toFixed(numReservation) : tableResult[key]
            )
        );
    });
    $(modelSummaryTable).append(valueRow);
    var SLRconfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_SINGLE_LINEAR_REGRESSION'));
    $(modelSummaryArea).append(modelSummaryTable)
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_dependent_variable_is'))
            .append($(span).clone().text(SLRconfig.dependentVariable[0].varietyName)))
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_independent_variable_is'))
            .append($(span).clone().text(SLRconfig.independentVariable[0].varietyName)));

    $(presentArea).append(modelSummaryArea);

    //第二格表格：ANOVA表
    var ANOVAList = ['sum_of_squares', 'mean_square', 'f'];
    var ANOVAArea = $(container).clone();
    $(ANOVAArea).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_anova')
    );
    var ANOVATable = $(table).clone();
    var ANOVATitleRow = $(row).clone();
    $(ANOVATitleRow).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'model')
    );
    ANOVAList.map(function (name) {
        $(ANOVATitleRow).append(
            $(headerCell).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', name)
        );
    });
    $(ANOVATable).append(ANOVATitleRow);

    var ANOVAValueRow = $(row).clone();
    $(ANOVAValueRow).append(
        $(cell).clone().append(
            $(block).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', 'regression')
        ).append(
            $(block).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', 'residual')
        )
    );
    ANOVAList.map(function (value) {
        switch (value) {
            case 'sum_of_squares':
                $(ANOVAValueRow).append(
                    $(cell).clone().append(
                        $(block).clone().text(Number(tableResult.regressionSumSquares).toFixed(numReservation))
                    ).append(
                        $(block).clone().text(Number(tableResult.sumSquaredErrors).toFixed(numReservation))
                    )
                );
                break;
            case 'mean_square':
                $(ANOVAValueRow).append(
                    $(cell).clone().append(
                        $(block).clone().text(Number(tableResult.regressionSumSquares).toFixed(numReservation))
                    ).append(
                        $(block).clone().text(Number(tableResult.meanSquareError).toFixed(numReservation))
                    )
                );
                break;
            case 'f':
                $(ANOVAValueRow).append(
                    $(cell).clone().append(
                        $(block).clone().text(Number(tableResult.f).toFixed(numReservation))
                    )
                );
                break;
        }
    });
    $(ANOVATable).append(ANOVAValueRow);
    $(ANOVAArea).append(ANOVATable)
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_dependent_variable_is'))
            .append($(span).clone().text(SLRconfig.dependentVariable[0].varietyName)))
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_independent_variable_is'))
            .append($(span).clone().text(SLRconfig.independentVariable[0].varietyName)));
    $(presentArea).append(ANOVAArea);

    //第三个表格：系数表
    var coefficientArea = $(container).clone();
    $(coefficientArea).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_coefficients')
    );
    var coefficientTable = $(table).clone();
    var coTitleFirstRow = $(row).clone();
    $(coTitleFirstRow).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'model')
            .attr('rowspan', '2')
    ).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'unstandardized_coefficients')
            .attr('colspan', '2')
    ).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 't')
            .attr('rowspan', '2')
    );
    var coTitleSecondRow = $(row).clone();
    $(coTitleSecondRow).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'b')
    ).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'regressionStandardError')
    );
    $(coefficientTable).append(coTitleFirstRow).append(coTitleSecondRow);

    var valueFirstRow = $(row).clone();
    $(valueFirstRow).append(
        $(cell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'constant')
    ).append(
        $(cell).clone()
            .text(Number(tableResult.regressionParameters[0]).toFixed(numReservation))
    ).append(
        $(cell).clone()
            .text(Number(tableResult.regressionParametersStandardErrors[0]).toFixed(numReservation))
    ).append(
        $(cell).clone()
            .text(Number(tableResult.ttests[0]).toFixed(numReservation))
    );

    var valueSecondRow = $(row).clone();
    $(valueSecondRow).append(
        $(cell).clone().text(SLRconfig.independentVariable[0].varietyName)
    ).append(
        $(cell).clone()
            .text(Number(tableResult.regressionParameters[1]).toFixed(numReservation))
    ).append(
        $(cell).clone()
            .text(Number(tableResult.regressionParametersStandardErrors[1]).toFixed(numReservation))
    ).append(
        $(cell).clone()
            .text(Number(tableResult.ttests[1]).toFixed(numReservation))
    );
    $(coefficientTable).append(valueFirstRow).append(valueSecondRow);
    $(coefficientArea).append(coefficientTable)
        .append($(block).clone().css({
            'font-weight': '700',
            'margin-bottom': '10px'
        })
            .append($(span).clone().text(SLRconfig.dependentVariable[0].varietyName))
            .append($(span).clone().text(' = '))
            .append($(span).clone().text(Number(tableResult.regressionParameters[0]).toFixed(numReservation)))
            .append((Number(tableResult.regressionParameters[1]) > 0) ? ' + ' : ' - ')
            .append($(span).clone().text(Number(tableResult.regressionParameters[1]).toFixed(numReservation)))
            .append($(span).clone().text(' × '))
            .append($(span).clone().text(SLRconfig.independentVariable[0].varietyName))
    )
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_dependent_variable_is'))
            .append($(span).clone().text(SLRconfig.dependentVariable[0].varietyName)))
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_independent_variable_is'))
            .append($(span).clone().text(SLRconfig.independentVariable[0].varietyName)));
    $(presentArea).append(coefficientArea);
};
var handleMultiLinearRegression = function (tableResult) {
    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //打印三个表格
    //第一个表格：模型汇总
    //需要打印的变量列表
    var modelSummaryList = ['r', 'rsquared', 'adjustedRSquared', 'regressionStandardError', 'f'];
    var modelSummaryArea = $(container).clone();
    $(modelSummaryArea).append(
        $(tableHeader).clone()
            .attr('data-i18n-type', 'page')
            .attr('data-i18n-tag', 'label_model_summary')
    );

    var modelSummaryTable = $(table).clone();
    var titleRow = $(row).clone();
    $(titleRow).append(
        $(headerCell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'model')
    );
    modelSummaryList.map(function (name) {
        $(titleRow).append(
            $(headerCell).clone()
                .attr('data-i18n-type', 'table')
                .attr('data-i18n-tag', name)
        )
    });
    $(modelSummaryTable).append(titleRow);
    //变量值
    var valueRow = $(row).clone();
    $(valueRow).append(
        $(cell).clone()
            .attr('data-i18n-type', 'table')
            .attr('data-i18n-tag', 'linear')
    );
    modelSummaryList.map(function (key) {
        $(valueRow).append(
            $(cell).clone().text(
                (Number(tableResult[key]) != NaN) ? Number(tableResult[key]).toFixed(numReservation) : tableResult[key]
            )
        );
    });
    $(modelSummaryTable).append(valueRow);
    var MLRconfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_MULTI_LINEAR_REGRESSION'));
    var independentVariablesName = '';
    MLRconfig.independentVariable.map(function (variable, index) {
        independentVariablesName += (MLRconfig.independentVariable[index + 1]) ? variable.varietyName + ',' : variable.varietyName;
    });
    $(modelSummaryArea).append(modelSummaryTable)
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_dependent_variable_is'))
            .append($(span).clone().text(MLRconfig.dependentVariable[0].varietyName)))
        .append($(block).clone()
            .append($(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_independent_variable_is'))
            .append($(span).clone().text(independentVariablesName)));

    $(presentArea).append(modelSummaryArea);

    //2 打印表『系数』
    var container_2 = $(container).clone();
    $(container_2).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_coefficients')
    );
    var table_2 = $(table).clone();
    //2.1 打印标题行
    var titleRow_1 = $(row).clone();
    $(titleRow_1).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model').attr('rowspan', '2')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'unstandardized_coefficients').attr('colspan', '2')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 't').attr('rowspan', '2')
    );
    var titleRow_2 = $(row).clone();
    $(titleRow_2).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'regressionStandardError')
    );
    $(table_2).append(titleRow_1).append(titleRow_2);

    //2.2 打印数据行
    tableResult.regressionParameters.map(function (data, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append(
                $(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant')
            );
        } else {
            $(dataRow).append(
                $(cell).clone().text(MLRconfig.independentVariable[index - 1].varietyName)
            )
        }

        $(dataRow).append(
            $(cell).clone().text(Number(data).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(tableResult.regressionParametersStandardErrors[index]).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(tableResult.ttests[index]).toFixed(numReservation))
        );

        $(table_2).append(dataRow);
    });

    $(container_2).append(table_2);

    var equationString = '';
    tableResult.regressionParameters.map(function (value, index) {
        if (index == 0) {
            equationString += '' + Number(value).toFixed(numReservation);
        } else {
            if (value > 0) {
                equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + MLRconfig.independentVariable[index - 1].varietyName;
            } else {
                equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + MLRconfig.independentVariable[index - 1].varietyName;
            }
        }
    });

    $(container_2).append(
        $(block).clone().html('<strong>' + MLRconfig.dependentVariable[0].varietyName + '</strong>')
            .append($(span).clone().text(' = ' + equationString))
    );

    $(presentArea).append(container_2);


};

var handleGeneralStepwiseRegression = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //要打印两个表格：1.模型汇总表 2.系数表
    //1.模型汇总表
    $(container).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_model_summary'));
    var modelTable = $(table).clone();
    var modelTitleRow = $(row).clone();
    //1.1 打印第一行，标题：模型，R，R方，标准误差，F
    $(modelTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'r')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'rsquared')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'regressionStandardError')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'f')
    );

    //1.2 打印数据行
    var modelDataRow = $(row).clone();
    $(modelDataRow).append(
        $(cell).clone().text('1')
    ).append(
        $(cell).clone().text(Number(Math.sqrt(tableResult.rsquared)).toFixed(numReservation))
    ).append(
        $(cell).clone().text(Number(tableResult.rsquared).toFixed(numReservation))
    ).append(
        $(cell).clone().text(Number(tableResult.regressionStandardError).toFixed(numReservation))
    ).append(
        $(cell).clone().text(Number(tableResult.f).toFixed(numReservation))
    );
    $(modelTable).append(modelTitleRow).append(modelDataRow);
    $(container).append(modelTable);

    //2.系数表
    $(container).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_coefficients'));
    var coeffTable = $(table).clone();
    //2.1 打印标题行
    var coeffTitleRow = $(row).clone();
    $(coeffTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model').attr('rowspan', '2')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'unstandardized_coefficients')
    );
    var coeffSecTitleRow = $(row).clone();
    $(coeffSecTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
    );
    $(coeffTable).append(coeffTitleRow).append(coeffSecTitleRow);

    //2.2 打印数据
    var config = JSON.parse(sessionStorage.PRIVATE_CONFIG_GENERAL_STEPWISE_REGRESSION);
    var independentVariable = config.independentVariable;

    tableResult.regressionParameters.map(function (value, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append($(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant'))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        } else {
            $(dataRow).append($(cell).clone().text(independentVariable[index - 1].varietyName))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        }

        $(coeffTable).append(dataRow);
    });
    $(container).append(coeffTable);

    var equationString = '';
    tableResult.regressionParameters.map(function (value, index) {
        if (index == 0) {
            equationString += '' + Number(value).toFixed(numReservation);
        } else {
            if (value > 0) {
                equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + independentVariable[index - 1].varietyName;
            } else if (value < 0) {
                equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + independentVariable[index - 1].varietyName;
            } else {
                return;
            }
        }
    });

    $(container).append(
        $(block).clone().html('<strong>' + config.dependentVariable[0].varietyName + '</strong>')
            .append($(span).clone().text(' = ' + equationString))
    );

    $(presentArea).append(container);
};

var handleSlipStepwiseRegression = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    //1 打印『模型汇总』表
    var modelSummaryArea = $(container).clone();
    $(modelSummaryArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_model_summary')
    );

    var modelSummaryTable = $(table).clone();
    //1.1 打印标题行
    var modelSummaryTitleRow = $(row).clone();
    $(modelSummaryTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'r')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'rsquared')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'regressionStandardError')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'f')
    );
    $(modelSummaryTable).append(modelSummaryTitleRow);

    //1.2 打印数据行
    ['forward', 'backward'].map(function (title) {

        var modelDataRow = $(row).clone();

        $(modelDataRow).append(
            $(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', title)
        ).append(
            $(cell).clone().text(Number(Math.sqrt(tableResult[(title == 'forward') ? 'preData' : 'backData'].rsquared)).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(tableResult[(title == 'forward') ? 'preData' : 'backData'].rsquared).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(tableResult[(title == 'forward') ? 'preData' : 'backData'].regressionStandardError).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(tableResult[(title == 'forward') ? 'preData' : 'backData'].f).toFixed(numReservation))
        );

        $(modelSummaryTable).append(modelDataRow)
    });

    $(modelSummaryArea).append(modelSummaryTable);
    $(presentArea).append(modelSummaryArea);

    //2 打印『前移回归系数』表
    var forwardRegressionArea = $(container).clone();
    $(forwardRegressionArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_forward_regression_coefficients')
    );

    var forwardRegressionTable = $(table).clone();
    //2.1 打印标题行
    var FRTitleRow = $(row).clone();
    $(FRTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model').attr('rowspan', '2')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'unstandardized_coefficients')
    );
    var FRTitleSecRow = $(row).clone();
    $(FRTitleSecRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
    );
    $(forwardRegressionTable).append(FRTitleRow).append(FRTitleSecRow);

    //2.2 打印数据行
    var config = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_SLIP_STEPWISE_REGRESSION'));
    var independentVariable = config.independentVariable;

    tableResult.preData.regressionParameters.map(function (value, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append($(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant'))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        } else {
            $(dataRow).append($(cell).clone().text(independentVariable[index - 1].varietyName))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        }
        $(forwardRegressionTable).append(dataRow);
    });

    var equationString = '';
    tableResult.preData.regressionParameters.map(function (value, index) {
        if (index == 0) {
            equationString += '' + Number(value).toFixed(numReservation);
        } else {
            if (value > 0) {
                equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + independentVariable[index - 1].varietyName;
            } else if (value < 0) {
                equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + independentVariable[index - 1].varietyName;
            } else {
                return;
            }
        }
    });

    $(forwardRegressionArea).append(forwardRegressionTable)
        .append(
        $(block).clone().html('<strong>' + config.dependentVariable[0].varietyName + '</strong>')
            .append($(span).clone().text(' = ' + equationString))
    );
    $(presentArea).append(forwardRegressionArea);

    //3 打印『前移预测』
    var forwardPredictArea = $(container).clone();
    var key = Object.keys(tableResult.preForecast);

    $(forwardPredictArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_forward_predict')
    ).append(
        $(block).clone().append(
            $(span).clone().html(
                (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') ?
                '<strong>' + key[0] + '</strong>' + ' 年的 ' + '<strong>' + config.dependentVariable[0].varietyName + '</strong>' + ' 的预测值是：' + Number(tableResult.preForecast[key[0]]).toFixed(numReservation) :
                'The predictive value of ' + '<strong>' + config.dependentVariable[0].varietyName + '</strong>' + ' in ' + '<strong>' + key[0] + '</strong>' + ' is: ' + Number(tableResult.preForecast[key[0]]).toFixed(numReservation))
        )
    );
    $(presentArea).append(forwardPredictArea);

    //4 打印『后移回归系数』表
    var backwardRegressionArea = $(container).clone();
    $(backwardRegressionArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_backward_regression_coefficients')
    );

    var backwardRegressionTable = $(table).clone();
    //4.1 打印标题行
    var BRTitleRow = $(row).clone();
    $(BRTitleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'model').attr('rowspan', '2')
    ).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'unstandardized_coefficients')
    );
    var BRTitleSecRow = $(row).clone();
    $(BRTitleSecRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
    );
    $(backwardRegressionTable).append(BRTitleRow).append(BRTitleSecRow);

    //4.2 打印数据行
    tableResult.backData.regressionParameters.map(function (value, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append($(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant'))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        } else {
            $(dataRow).append($(cell).clone().text(independentVariable[index - 1].varietyName))
                .append($(cell).clone().text(Number(value).toFixed(numReservation)));
        }
        $(backwardRegressionTable).append(dataRow);
    });

    var _equationString = '';
    tableResult.backData.regressionParameters.map(function (value, index) {
        if (index == 0) {
            _equationString += '' + Number(value).toFixed(numReservation);
        } else {
            if (value > 0) {
                _equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + independentVariable[index - 1].varietyName;
            } else {
                _equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + independentVariable[index - 1].varietyName;
            }
        }
    });

    $(backwardRegressionArea).append(backwardRegressionTable)
        .append(
        $(block).clone().html('<strong>' + config.dependentVariable[0].varietyName + '</strong>')
            .append($(span).clone().text(' = ' + _equationString))
    );
    $(presentArea).append(backwardRegressionArea);


    //5 打印『后移预测』
    var backwardPredictArea = $(container).clone();
    var _key = Object.keys(tableResult.backForecast);

    $(backwardPredictArea).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_backward_predict')
    ).append(
        $(block).clone().append(
            $(span).clone().html(
                (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') ?
                '<strong>' + _key[0] + '</strong>' + ' 年的 ' + '<strong>' + config.dependentVariable[0].varietyName + '</strong>' + ' 的预测值是：' + Number(tableResult.backForecast[_key[0]]).toFixed(numReservation) :
                'The predictive value of ' + '<strong>' + config.dependentVariable[0].varietyName + '</strong>' + ' in ' + '<strong>' + _key[0] + '</strong>' + ' is: ' + Number(tableResult.backForecast[_key[0]]).toFixed(numReservation))
        )
    );
    $(presentArea).append(backwardPredictArea);
};

var handleTrendStepwiseRegression = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

    //声明DOM 元素
    var container = $('<div>');
    $(container).addClass('frequency-table');

    var tableHeader = $('<h1>');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered table-condensed');

    var row = $('<tr>');
    var headerCell = $('<th>');
    var cell = $('<td>');

    var block = $('<div>');
    var span = $('<span>');

    //1 打印『前移趋势回归模型汇总』
    var container_1 = $(container).clone();
    $(container_1).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_model_summary_of_forward_trend_regression')
    );

    var table_1 = $(table).clone();
    var table_1_titleRow = $(row).clone();
    ['forward_step', 'r', 'rsquared', 'regressionStandardError', 'f'].map(function (name) {
        $(table_1_titleRow).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', name)
        );
    });
    $(table_1).append(table_1_titleRow);

    tableResult.trendWiseDTO.map(function (data, index) {
        var dataRow = $(row).clone();
        $(dataRow).append(
            $(cell).clone().text(index + 1)
        ).append(
            $(cell).clone().text(Number(Math.sqrt(data.preData.rsquared)).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.preData.rsquared).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.preData.regressionStandardError).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.preData.f).toFixed(numReservation))
        );

        $(table_1).append(dataRow);
    });

    $(container_1).append(table_1);
    $(presentArea).append(container_1);

    //2 打印前移趋势回归系数表
    var container_2 = $(container).clone();
    $(container_2).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_forward_trend_regression_coefficients')
    );

    var table_2 = $(table).clone();
    var table_2_titleRow = $(row).clone();
    var table_2_titleRow_2 = $(row).clone();

    $(table_2_titleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'forward_step').attr('rowspan', '2')
    );
    tableResult.trendWiseDTO.map(function (step, index) {
        $(table_2_titleRow).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'forward_' + (index + 1) + "_steps")
        );
        $(table_2_titleRow_2).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
        );
    });
    $(table_2).append(table_2_titleRow).append(table_2_titleRow_2);

    var TSRConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_TREND_STEPWISE_REGRESSION'));

    tableResult.trendWiseDTO[0].preData.regressionParameters.map(function (data, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append(
                $(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant')
            );
        } else {
            $(dataRow).append(
                $(cell).clone().text(TSRConfig.independentVariable[index - 1].varietyName)
            );
        }

        tableResult.trendWiseDTO.map(function (step) {
            $(dataRow).append(
                $(cell).clone().text(Number(step.preData.regressionParameters[index]).toFixed(numReservation))
            );
        });

        $(table_2).append(dataRow);
    });

    $(container_2).append(table_2);
    for (var i = 0; i < tableResult.trendWiseDTO.length; i++) {

        var equationString = '';
        tableResult.trendWiseDTO[i].preData.regressionParameters.map(function (value, index) {
            if (index == 0) {
                equationString += '' + Number(value).toFixed(numReservation);
            } else {
                if (value > 0) {
                    equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + TSRConfig.independentVariable[index - 1].varietyName;
                } else if (value < 0) {
                    equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + TSRConfig.independentVariable[index - 1].varietyName;
                } else {
                    return;
                }
            }
        });

        $(container_2).append(
            $(block).clone().html('<span data-i18n-type="table" data-i18n-tag="forward_' + (i + 1) + '_steps"></span>: <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong>')
                .append($(span).clone().text(' = ' + equationString))
        );
    }
    $(presentArea).append(container_2);

    //3 打印『预测值』
    var container_3 = $(container).clone();
    $(container_3).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_predictive_value')
    );

    var forwardKey = Object.keys(tableResult.preForecast)[0];

    var predictValueArray = [];    //这个是要计算1：1：1的情况留下的数组

    for (var i = 0; i < tableResult.trendWiseDTO.length; i++) {

        predictValueArray.push(tableResult.trendWiseDTO[i].preForecast[forwardKey]);

        var predictString = '';
        if (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') {
            predictString = '预测 <strong>' + forwardKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong>为: ' + Number(tableResult.trendWiseDTO[i].preForecast[forwardKey]).toFixed(numReservation);
        } else {
            predictString = 'The predictive value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + forwardKey + '</strong> is: ' + Number(tableResult.trendWiseDTO[i].preForecast[forwardKey]).toFixed(numReservation);
        }
        $(container_3).append(
            $(block).clone().append(
                $(span).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'forward_' + (i + 1) + '_steps')
            ).append(
                $(span).clone().text(': ')
            ).append(
                $(span).clone().html(predictString)
            )
        );
    }

    var forwardAverageWeightValue = 0;
    predictValueArray.map(function (val) {
        forwardAverageWeightValue += val;
    });
    forwardAverageWeightValue /= 3;

    var trendPredictString_1 = '';
    var trendPredictString_2 = '';

    if (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') {
        trendPredictString_1 = '前移趋势回归按5:3:2的权重得到 <strong>' + forwardKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> 为' + Number(tableResult.preForecast[forwardKey]).toFixed(numReservation);
        trendPredictString_2 = '前移趋势回归按1:1:1的权重得到 <strong>' + forwardKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> 为' + Number(forwardAverageWeightValue).toFixed(numReservation);
    } else {
        trendPredictString_1 = 'Using the Weight ratio of 5:3:2, the forward trend regression returns the value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + forwardKey + '</strong> is: ' + Number(tableResult.preForecast[forwardKey]).toFixed(numReservation);
        trendPredictString_2 = 'Using the Weight ratio of 1:1:1, the forward trend regression returns the value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + forwardKey + '</strong> is: ' + Number(forwardAverageWeightValue).toFixed(numReservation);
    }

    $(container_3).append(
        $('<br>').clone()
    ).append(
        $(block).clone().html(trendPredictString_1)
    ).append(
        $(block).clone().html(trendPredictString_2)
    );

    $(presentArea).append(container_3);

    //4 打印『后移趋势回归模型汇总』
    var container_4 = $(container).clone();
    $(container_4).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_model_summary_of_backward_trend_regression')
    );

    var table_4 = $(table).clone();
    var table_4_titleRow = $(row).clone();
    ['backward_step', 'r', 'rsquared', 'regressionStandardError', 'f'].map(function (name) {
        $(table_4_titleRow).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', name)
        );
    });
    $(table_4).append(table_4_titleRow);

    tableResult.trendWiseDTO.map(function (data, index) {
        var dataRow = $(row).clone();
        $(dataRow).append(
            $(cell).clone().text(index + 1)
        ).append(
            $(cell).clone().text(Number(Math.sqrt(data.backData.rsquared)).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.backData.rsquared).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.backData.regressionStandardError).toFixed(numReservation))
        ).append(
            $(cell).clone().text(Number(data.backData.f).toFixed(numReservation))
        );

        $(table_4).append(dataRow);
    });

    $(container_4).append(table_4);
    $(presentArea).append(container_4);


    //5 打印『后移趋势回归系数』表
    var container_5 = $(container).clone();
    $(container_5).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_backward_trend_regression_coefficients')
    );

    var table_5 = $(table).clone();
    var table_5_titleRow = $(row).clone();
    var table_5_titleRow_2 = $(row).clone();

    $(table_5_titleRow).append(
        $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'backward_step').attr('rowspan', '2')
    );
    tableResult.trendWiseDTO.map(function (step, index) {
        $(table_5_titleRow).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'backward_' + (index + 1) + "_steps")
        );
        $(table_5_titleRow_2).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'b')
        );
    });
    $(table_5).append(table_5_titleRow).append(table_5_titleRow_2);

    tableResult.trendWiseDTO[0].preData.regressionParameters.map(function (data, index) {
        var dataRow = $(row).clone();

        if (index == 0) {
            $(dataRow).append(
                $(cell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'constant')
            );
        } else {
            $(dataRow).append(
                $(cell).clone().text(TSRConfig.independentVariable[index - 1].varietyName)
            );
        }

        tableResult.trendWiseDTO.map(function (step) {
            $(dataRow).append(
                $(cell).clone().text(Number(step.backData.regressionParameters[index]).toFixed(numReservation))
            );
        });

        $(table_5).append(dataRow);
    });

    $(container_5).append(table_5);
    for (var i = 0; i < tableResult.trendWiseDTO.length; i++) {

        var _equationString = '';
        tableResult.trendWiseDTO[i].backData.regressionParameters.map(function (value, index) {
            if (index == 0) {
                _equationString += '' + Number(value).toFixed(numReservation);
            } else {
                if (value > 0) {
                    _equationString += ' + ' + Number(value).toFixed(numReservation) + ' × ' + TSRConfig.independentVariable[index - 1].varietyName;
                } else if (value < 0) {
                    _equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + TSRConfig.independentVariable[index - 1].varietyName;
                } else {
                    return;
                }
            }
        });

        $(container_5).append(
            $(block).clone().html('<span data-i18n-type="table" data-i18n-tag="backward_' + (i + 1) + '_steps"></span>: <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong>')
                .append($(span).clone().text(' = ' + _equationString))
        );
    }
    $(presentArea).append(container_5);

    //6 打印『推测值』
    var container_6 = $(container).clone();
    $(container_6).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_estimated_value')
    );

    var backKey = Object.keys(tableResult.backForecast)[0];

    var estimatedValueArray = [];    //这个是要计算1：1：1的情况留下的数组

    for (var i = 0; i < tableResult.trendWiseDTO.length; i++) {

        estimatedValueArray.push(tableResult.trendWiseDTO[i].backForecast[backKey]);

        var estimatedString = '';
        if (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') {
            estimatedString = '推测 <strong>' + backKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong>为: ' + Number(tableResult.trendWiseDTO[i].backForecast[backKey]).toFixed(numReservation);
        } else {
            estimatedString = 'The predictive value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + backKey + '</strong> is: ' + Number(tableResult.trendWiseDTO[i].backForecast[backKey]).toFixed(numReservation);
        }
        $(container_6).append(
            $(block).clone().append(
                $(span).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'backward_' + (i + 1) + '_steps')
            ).append(
                $(span).clone().text(': ')
            ).append(
                $(span).clone().html(estimatedString)
            )
        );
    }

    var backwardAverageWeightValue = 0;
    estimatedValueArray.map(function (val) {
        backwardAverageWeightValue += val;
    });
    backwardAverageWeightValue /= 3;

    var trendEstimatedString_1 = '';
    var trendEstimatedString_2 = '';

    if (localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE') == 'zh-cn') {
        trendEstimatedString_1 = '前移趋势回归按5:3:2的权重得到 <strong>' + backKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> 为' + Number(tableResult.backForecast[backKey]).toFixed(numReservation);
        trendEstimatedString_2 = '前移趋势回归按1:1:1的权重得到 <strong>' + backKey + '</strong> 的 <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> 为' + Number(backwardAverageWeightValue).toFixed(numReservation);
    } else {
        trendEstimatedString_1 = 'Using the Weight ratio of 5:3:2, the forward trend regression returns the value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + backKey + '</strong> is: ' + Number(tableResult.backForecast[backKey]).toFixed(numReservation);
        trendEstimatedString_2 = 'Using the Weight ratio of 1:1:1, the forward trend regression returns the value of <strong>' + TSRConfig.dependentVariable[0].varietyName + '</strong> in <strong>' + backKey + '</strong> is: ' + Number(backwardAverageWeightValue).toFixed(numReservation);
    }

    $(container_6).append(
        $('<br>').clone()
    ).append(
        $(block).clone().html(trendEstimatedString_1)
    ).append(
        $(block).clone().html(trendEstimatedString_2)
    );

    $(presentArea).append(container_6);
};

var handleOptimalSegmentation = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

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

    $(container).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'modal_title_optimal_segmentation'));

    var SEGMENT_TABLES = tableResult.optRes;

    //有几个表格打印几个
    SEGMENT_TABLES.map(function (object, index) {
        var resultTable = $(table).clone();

        var titleRow = $(row).clone();
        $(titleRow).append($(headerCell).clone().attr('colspan', '4').css('text-align', 'center').text('最优 ' + object.segNum + ' 段分割结果[Result]'));

        var titleSecRow = $(row).clone();
        $(titleSecRow).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'segment_number')
        ).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'from')
        ).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'to')
        ).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'variance_of_the_segment')
        );
        $(resultTable).append(titleRow)
            .append(titleSecRow);

        object.segDataList.map(function (data, index) {
            var dataRow = $(row).clone();
            $(dataRow).append(
                $(cell).clone().text(index + 1)
            ).append(
                $(cell).clone().text(data.from)
            ).append(
                $(cell).clone().text(data.to)
            ).append(
                $(cell).clone().text(Number(data.sswg).toFixed(numReservation))
            );
            $(resultTable).append(dataRow);
        });

        var footerRow = $(row).clone();
        $(footerRow).append(
            $(cell).clone().attr('colspan', '4').append(
                $(span).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'segment')
            ).append(
                $(span).clone().text(': ')
            ).append(
                $(span).clone().text(Number(object.sst).toFixed(numReservation))
            )
        );
        $(resultTable).append(footerRow);

        $(container).append(resultTable);
    });

    //打印第二张表：分割表
    var OS = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_OPTIMAL_SEGMENTATION'));

    $(container).append($(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_segment_table'));
    var divideTable = $(table).clone();
    var titleRow = $(row).clone();
    for (var i = 0; i < OS.segNum[0]; i++) {
        if (i == 0) {
            $(titleRow).append($(headerCell).clone().css('text-align', 'center').attr('data-i18n-type', 'table').attr('data-i18n-tag', 'sign_segment'))
        } else {
            $(titleRow).append($(headerCell).clone().css('text-align', 'center').text(i + 1));
        }
    }
    $(divideTable).append(titleRow);

    var OS_COL = Number(sessionStorage.getItem('PRIVATE_OPT_SEG_COL'));
    for (var j = 0; j < OS.local_var.length; j++) {
        var dataRow = $(row).clone();

        $(dataRow).append($(cell).clone().css('text-align', 'center').text(OS.local_var[j]));

        for (var k = 1; k < OS.segNum[0]; k++) {
            $(dataRow).append($(cell).clone().attr('id', 'target_' + (k + 1) + '_' + (j + 1)));
        }
        $(divideTable).append(dataRow);
    }

    $(container).append(divideTable);

    $(presentArea).append(container);

    SEGMENT_TABLES.map(function (object, index) {

        object.segDataList.map(function (segData, i) {
            var target = '#target_' + object.segNum + '_';

            if (segData.to !== (OS_COL - 1)) {
                target += segData.to;
                $(target).css('border-bottom', '1px solid black');
            }
        });
    });

    ['fisher_line', 'fisher'].map(function (graphName, i) {
        var graphArea = $('<div>');
        $(graphArea).css({
            'height': 800
        }).addClass('col-md-12').attr('id', 'graph_' + i);

        $(presentArea).append(graphArea);

        var GN = _String['graph'][lang][graphName];

        $('#graph_' + i).charts({
            title: GN,
            type: [graphName],
            data: tableResult
        });

    });
};

var handleClusterAnalysis = function (tableResult) {

    //声明打印区域
    var presentArea = $('.result-table');

    //保留小数配置
    var numReservation = Number(localStorage.getItem('MULTIINFO_CONFIG_RESERVATION'));

    //声明DOM 元素
    var container = $('<div>');
    $(container).addClass('frequency-table');

    var tableHeader = $('<h1>');

    var table = $('<table>');
    $(table).addClass('table table-striped table-bordered table-condensed');

    var row = $('<tr>');
    var headerCell = $('<th>');
    var cell = $('<td>');

    var block = $('<div>');
    var span = $('<span>');

    //获取返回值中的最大data
    var max = 0;
    tableResult.stepList.map(function (step, index) {
        step.data > max ? max = step.data : null;
    });

    var container_1 = $(container).clone();
    $(container_1).append(
        $(tableHeader).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_cal_res_of_clustering_step_by_step')
    );

    var table_1 = $(table).clone();
    $(table_1).append(
        $(row).clone().append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'times')
        ).append(
            $(headerCell).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', 'label_statistics_for_clustering')
        ).append(
            $(headerCell).clone().attr('data-i18n-type', 'table').attr('data-i18n-tag', 'result')
        )
    );

    var CONFIG = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CLUSTER_ANALYSIS'));
    var statisticsMethodList = ['label_distance_coefficient', 'label_angle_cosine', 'label_correlation_coefficients'];

    tableResult.stepList.map(function (step, index) {
        var dataRow = $(row).clone();

        $(dataRow).append(
            $(cell).clone().text(index + 1).css('text-align', 'center')
        );

        $(dataRow).append(
            $(cell).clone().append(
                $(span).clone().text('[' + step.rowIndex + ']--[' + step.colIndex + '] ')
            ).append(
                $(span).clone().attr('data-i18n-type', 'page').attr('data-i18n-tag', statisticsMethodList[CONFIG.statisticsMethod[0]])
            )
        );

        $(dataRow).append(
            $(cell).clone().text(Number(step.data).toFixed(numReservation))
        );

        $(table_1).append(dataRow);
    });

    $(container_1).append(table_1);
    $(presentArea).append(container_1);

    //制图
    var graphArea = $('<div>');
    $(graphArea).css({
        'height': 800
    }).addClass('col-md-12').attr('id', 'graph_1');

    $(presentArea).append(graphArea);

    var graphName = _String['graph'][lang]['cluster_analysis'];

    $('#graph_1').charts({
        title: graphName,
        type: ['clustering'],
        data: JSON.parse(sessionStorage.getItem('PRIVATE_DATA_GRID')),
        clusterConfig: CONFIG,
        clusterDataMax: max
    });
};