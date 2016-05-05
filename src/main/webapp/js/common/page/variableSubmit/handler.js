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
        dataGrid: data
    };

    var handleURL = '';
    switch (type) {
        case 'Descriptive_Statistics_Descriptive':
        case 'Descriptive_Statistics_Frequency':
            //描述统计 - 描述
            //描述统计 - 频率
            handleURL = (type == 'Descriptive_Statistics_Descriptive') ? 'statistics/descriptives.do?method=descriptives' : 'statistics/descriptives.do?method=frequency';
            var descriptive = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_DESCRIPTIVE'));
            dataPackage.variableList = descriptive.variableList;
            break;
        case 'Correlation_Bivariate':
            //相关分析 - 双变量
            handleURL = 'statistics/correlation.do?method=bivariate';
            var bivariate = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRELATION_BIVARIATE'));
            dataPackage.variableList = bivariate.variableList;
            break;
        case 'Correlation_Distance':
            //相关分析 - 距离
            handleURL = 'statistics/correlation.do?method=distance';
            var distanceConfig = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_CORRELATION_DISTANCE'));
            dataPackage.variableList = distanceConfig.variableList;
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
            break;
        case 'Oneway_ANOVA':
            //单因素方差分析
            handleURL = 'statistics/comparemean.do?method=onewayanova';
            var ANOVA = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_ONEWAY_ANOVA'));
            dataPackage.factorVariable = ANOVA.factorVariable[0];
            dataPackage.dependentVariable = ANOVA.dependentVariable;
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
            sessionStorage.setItem('PRIVATE_GRAPH_CONFIG', 'basicline');
            var PCA = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_PRINCIPAL_COMPONENT'));
            dataPackage.variableList = PCA.variableList;
            dataPackage.extractMethod = PCA.extractMethod[0];
            dataPackage.eigExtraNum = PCA.eigExtraNum[0];
            dataPackage.factorExtraNum = PCA.factorExtraNum[0];
            break;
        case 'Gray_Correlation':
            //灰色关联度
            handleURL = 'statistics/prediction.do?method=greydegree';
            var GC = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GRAY_CORRELATION'));
            dataPackage.sonSeqArr = GC.sonSeqArr;
            dataPackage.motherSeq = GC.motherSeq[0];
            dataPackage.resolutionRatio = GC.resolutionRatio[0];
            break;
        case 'Related_Variable':
            //灰色预测 - 关联变量
            handleURL = 'statistics/prediction.do?method=grey';
            var RV = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GRAY_PREDICTION_RELATED_VAR'));
            dataPackage.associationType = 1;
            dataPackage.factorVarVariable = JSON.parse(RV.factorVarVariable[0]);
            dataPackage.independentVariable = RV.independentVariable;
            dataPackage.formCoefficient = RV.formCoefficient[0];
            break;
        case 'Independent_Variable':
            //灰色预测 - 独立变量
            handleURL = 'statistics/prediction.do?method=grey';
            var IV = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GRAY_PREDICTION_INDEPENDENT_VAR'));
            dataPackage.associationType = 0;
            dataPackage.factorVarVariable = JSON.parse(IV.factorVarVariable[0]);
            dataPackage.independentVariable = IV.independentVariable;
            dataPackage.formCoefficient = IV.formCoefficient[0];
            break;
        case 'Simple_Linear_Regression':
            //一元线性回归
            handleURL = 'statistics/regression.do?method=singleLinear';
            var SLR = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_SINGLE_LINEAR_REGRESSION'));
            dataPackage.independentVariable = SLR.independentVariable;
            dataPackage.dependentVariable = SLR.dependentVariable[0];
            break;
        case 'Multi_Linear_Regression':
            //多远线性回归
            handleURL = 'statistics/regression.do?method=multipleLinear';
            var MLR = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_MULTI_LINEAR_REGRESSION'));
            dataPackage.independentVariable = MLR.independentVariable;
            dataPackage.dependentVariable = MLR.dependentVariable[0];
            break;
        case 'General_Stepwise_Regression':
            //回归 - 逐步回归 - 一般逐步回归
            var GSR = JSON.parse(sessionStorage.getItem('PRIVATE_CONFIG_GENERAL_STEPWISE_REGRESSION'));
            dataPackage.independentVariable = GSR.independentVariable;
            dataPackage.dependentVariable = GSR.dependentVariable[0];
            dataPackage.entryF = GSR.entryF[0];
            dataPackage.delF = GSR.delF[0];
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