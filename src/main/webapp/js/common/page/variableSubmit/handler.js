var handleSubmitType = function (type, data) {

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

    //4.配置发送数据包
    var dataPackage = {
        data: data,
        variableList: variablesLists[0]
    };
    // dataPackage.variableList = (variablesLists.length==1)?variablesLists[0]:   //TODO 等有多组参数列表再说

    switch (type) {
        case 'Descriptive_Statistics_Descriptive':
        case 'Descriptive_Statistics_Frequency':

            //描述统计 - 描述

            var handleURL = (type == 'Descriptive_Statistics_Descriptive') ?
                'statistics/descriptives.do?method=descriptives' :
                'statistics/descriptives.do?method=frequency';

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
                        console.log(tableResult);
                    }
                }, error: function (err) {
                    console.log('err', err)
                }
            });


            break;
        case 'Correlation_Bivariate':

            var variableGroups = $('.variabel-zone .zone');
            var variableGroupList = [];
            variableGroups.map(function (i, elem) {
                var group = {};

                var variables = $(elem).find('.active');
                var varList = [];
                variables.map(function (i, variable) {
                    varList.push($(variable).attr('data-variable-val'));
                });

                group[$(elem).attr('data-variable-group')] = varList;
                variableGroupList.push(group);
            });

            console.log(variableGroupList);

            sessionStorage.setItem('PRIVATE_RESULT_TYPE', 'Correlation_Bivariate');
            //相关分析 - 双变量
            // 算法部分 - 配置
            algorithmConfigList = $('#Algorithm_Config input[type=checkbox]:checked');
            algorithmConfigs = [];
            algorithmConfigList.map(function (i, config) {
                console.log(i, config);
                algorithmConfigs.push($(config).val());
            });
            sessionStorage.setItem('PRIVATE_ALGORITHM_CONFIG', algorithmConfigs);

            var variableChosed = $('#correlation_bivariate_area .active');

            var variableList = [];
            variableChosed.map(function (i, variable) {
                var params = JSON.parse($(variable).attr('data-config'));
                variableList.push(params);
            });

            var send = {
                'variableList': variableList,
                'dataGrid': data
            };
            console.log('send', send);

            $.ajax({
                url: 'statistics/correlation.do?method=bivariate',
                data: JSON.stringify(send),
                async: false,
                contentType: 'application/json',
                type: 'POST',
                dataType: 'JSON',
                success: function (tableResult) {
                    console.log(tableResult);
                    sessionStorage.setItem('PRIVATE_TABLE_RESULT', JSON.stringify(tableResult));
                }, error: function (err) {
                    console.log('err', err)
                }
            });

            // window.location.href = 'data.html';

            break;
    }
};