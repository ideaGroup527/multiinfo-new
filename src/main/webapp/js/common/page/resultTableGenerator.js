var resultTableGenerator = function () {

    var def = $.Deferred();

    //语言配置
    var lang = localStorage.getItem('MULTIINFO_CONFIG_LANGUAGE');
    var String = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_LANGUAGE_STRINGS'));

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
        case 'Optimal_Segmentation':
            //最优分割
            handleOptimalSegmentation(tableResult);
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

            var graphName = String['graph'][lang][graph];

            switch (graph) {
                case 'boxplot':
                    //箱线图
                    new Boxplot({
                        data: tableResult,
                        opt: "",
                        container: 'graph_' + i,
                        title: graphName
                    }).render();
                    break;
                case 'piegraph':
                    //饼图
                    new Pie({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        container: 'graph_' + i,//图表容器的id
                        title: graphName
                    }).render();
                    break;
                case 'histogram':
                    //直方图
                    new Bar({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        container: 'graph_' + i,//图表容器的id
                        title: graphName
                    }).render();
                    break;
                case 'linechart':
                    //折线图
                    new Line({
                        data: tableResult,//数据json,
                        opt: "",//配置json
                        container: 'graph_' + i,//图表容器的id
                        title: graphName
                    }).render();
                    break;
                case 'scatterdiagram':
                    //散点图
                    new Scatter({
                        data: tableResult,//数据json,
                        container: 'graph_' + i,//图表容器的id
                        title: graphName
                    }).render();
                    break;
                case 'dingchart':
                    //丁氏图
                    var dingConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DING_CHART'));
                    new DingChart({
                        data: tableResult,
                        container: 'graph_' + i,//图表容器的id
                        calculateMethod: dingConfig.calculateMethod[0]//0行，1列，2全
                    }).render();
                    break;
                case 'basicline':
                    //碎石图
                    new BasicLine({
                        data: tableResult.data.eigTotalInit,
                        container: 'graph_' + i,
                        title: graphName
                    }).render();
                    break;
            }
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

    //打印碎石图

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
    var modelSummaryList = ['r', 'rsquared', 'adjustedRSquared', 'regressionStandardError'];
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
                .append(Number(value).toFixed(numReservation));
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
            } else {
                equationString += ' - ' + Math.abs(Number(value).toFixed(numReservation)) + ' × ' + independentVariable[index - 1].varietyName;
            }
        }
    });

    $(container).append(
        $(block).clone().html('<strong>' + config.dependentVariable[0].varietyName + '</strong>')
            .append($(span).clone().text(' = ' + equationString))
    );

    $(presentArea).append(container);
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
    for (var j = 0; j < OS_COL - 1; j++) {
        var dataRow = $(row).clone();

        $(dataRow).append($(cell).clone().css('text-align', 'center').text(OS.col[j].varietyName));

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
                $(target).css('text-align', 'center').html('<strong>━</strong>');
            }
        });
    });
};