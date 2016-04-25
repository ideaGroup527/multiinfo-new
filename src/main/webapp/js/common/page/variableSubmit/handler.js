var handleSubmit = function (config) {

    console.log(config);

    var type = config.submitType;
    var data = config.gridData;

    //0.存储分析的类型，以便后续操作
    sessionStorage.setItem('PRIVATE_RESULT_TYPE', type);

    //1.获取选择的变量
    //全部变量存储的数组
    var variablesLists = [];

    //获取全部分区
    var varZoneField = $('.variable-zone .zone');

    //当前分区存储变量的数组
    var variableList = [];
    varZoneField.map(function (i, varZone) {
        var varSelected = $(varZone).find('.active');
        varSelected.map(function (i, variable) {
            var params = JSON.parse($(variable).attr('data-config'));
            variableList.push(params);
        });
        variablesLists.push(variableList);
    });

    console.log(variablesLists[0]);

    //2.获取算法配置项
    //获取算法配置分区
    var algorithmConfigZone = $('.algorithm-config-zone');
    var algorithmConfigList = $(algorithmConfigZone).find('input[type=checkbox]:checked');

    //全部算法配置存储的数组
    var algorithmConfigs = [];
    algorithmConfigList.map(function (i, config) {
        algorithmConfigs.push($(config).val());
    });

    //将配置存储在本地，以便跳转后读取
    sessionStorage.setItem('PRIVATE_ALGORITHM_CONFIG', algorithmConfigs);

    //3.获取制图配置项
    //获取制图配置分区
    var graphConfigZone = $('.graph-config-zone');

    //不是所有的分析都有制图选项
    if (graphConfigZone) {
        var graphConfigList = $(graphConfigZone).find('input[type=checkbox]:checked');

        //全部制图配置存储的数组
        var graphConfigs = [];
        graphConfigList.map(function (i, config) {
            graphConfigs.push($(config).val());
        });

        //将配置存储在本地，以便跳转后读取
        sessionStorage.setItem('PRIVATE_GRAPH_CONFIG', graphConfigs);
    }

    //5.配置发送数据包
    var dataPackage = {
        dataGrid: data,
        variableList: variablesLists[0]
    };
    // dataPackage.variableList = (variablesLists.length==1)?variablesLists[0]:   //TODO 等有多组参数列表再说
    var handleURL = '';
    switch (type) {
        case 'Descriptive_Statistics_Descriptive':
            //描述统计 - 描述
            handleURL = 'statistics/descriptives.do?method=descriptives';
            break;
        case 'Descriptive_Statistics_Frequency':
            //描述统计 - 频率
            handleURL = 'statistics/descriptives.do?method=frequency';
            break;
        case 'Correlation_Bivariate':
            //相关分析 - 双变量
            handleURL = 'statistics/correlation.do?method=bivariate';
            break;
        case 'Correlation_Distance':
            //相关分析 - 距离
            handleURL = 'statistics/correlation.do?method=distance';
            var distanceConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRELATION_DISTANCE'));
            dataPackage.minkowskiP = distanceConfig.minkowskiP[0];
            dataPackage.minkowskiQ = distanceConfig.minkowskiQ[0];
            break;
        case 'Ding_Chart':
            //丁氏图
            handleURL = 'statistics/chart.do?method=ding';
            sessionStorage.setItem('PRIVATE_GRAPH_CONFIG', 'dingchart');
            var dingChartConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DING_CHART'));
            dataPackage.calculateMethod = dingChartConfig.calculateMethod[0];
            dataPackage.rowVarList = dingChartConfig.rowVarList;
            dataPackage.colVarList = dingChartConfig.colVarList;
            dataPackage.variableList = null;
            break;
        case 'Oneway_ANOVA':
            //单因素方差分析
            handleURL = 'statistics/comparemean.do?method=onewayanova';
            var ANOVA = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_ONEWAY_ANOVA'));
            dataPackage.factorVariable = ANOVA.factorVariable[0];
            dataPackage.dependentVariable = ANOVA.dependentVariable;
            dataPackage.variableList = null;
            break;
        case 'Means':
            //均值
            handleURL = 'statistics/comparemean.do?method=mean';
            var means = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_MEANS'));
            dataPackage.independentVariable = means.independentVariable[0];
            dataPackage.dependentVariable = means.dependentVariable;
            break;
        case 'Principal_Component_Analysis':
            //主成分分析
            handleURL = 'statistics/analysis.do?method=principalComponent';
            var PCA = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_PRINCIPAL_COMPONENT'));
            dataPackage.variableList = PCA.variableList;
            dataPackage.extractMethod = PCA.extractMethod[0];
            dataPackage.eigExtraNum = PCA.eigExtraNum[0];
            dataPackage.factorExtraNum = PCA.factorExtraNum[0];
            break;
        case 'Related_Variable':
            //灰色预测 - 关联变量
            handleURL = 'statistics/prediction.do?method=grey';
            var RV = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GRAY_PREDICTION_RELATED_VAR'));
            dataPackage.associationType = 1;
            dataPackage.factorVarVariable = JSON.parse(RV.factorVarVariable[0]);
            dataPackage.independentVariable = RV.independentVariable;
            dataPackage.formCoefficient = RV.formCoefficient[0];
            console.log(dataPackage);
            break;
        case 'Independent_Variable':
            //灰色预测 - 独立变量
            dataPackage.associationType = 0;
            break;
    }

    $.ajax({
        url: handleURL,
        data: JSON.stringify(dataPackage),
        async: false,
        contentType: 'application/json',
        type: 'POST',
        dataType: 'JSON',
        success: function (tableResult) {
            if (tableResult.ret_code != '-1') {
                console.log(tableResult);
                sessionStorage.setItem('PRIVATE_TABLE_RESULT', JSON.stringify(tableResult));

                window.location.href = 'result.html';
            } else {
                alert('数据错误，请检查控制台');
                console.log(tableResult);
            }
        }, error: function (err) {
            console.log('err', err)
        }
    });

};