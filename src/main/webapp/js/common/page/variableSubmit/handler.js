var handleSubmitType = function (type, data) {
    var algorithmConfigList, algorithmConfigs,
        graphConfigList, graphConfigs;
    switch (type) {
        case 'Descriptive_Statistics_Descriptive':
        case 'Descriptive_Statistics_Frequency':

            sessionStorage.setItem('PRIVATE_RESULT_TYPE', (type == 'Descriptive_Statistics_Descriptive') ?
                'Descriptive_Statistics_Descriptive' :
                'Descriptive_Statistics_Frequency');

            //描述统计 - 描述
            //算法部分 - 配置
            algorithmConfigList = $('#Descriptive_Algorithm_Config input[type=checkbox]:checked');
            algorithmConfigs = [];
            algorithmConfigList.map(function (i, config) {
                console.log(i, config);
                algorithmConfigs.push($(config).val());
            });
            sessionStorage.setItem('PRIVATE_ALGORITHM_CONFIG', algorithmConfigs);

            //制图部分 - 配置
            graphConfigList = $('#Descriptive_Graph_Config input[type=checkbox]:checked');
            graphConfigs = [];
            graphConfigList.map(function (i, config) {
                console.log(i, config);
                graphConfigs.push($(config).val());
            });
            sessionStorage.setItem('PRIVATE_GRAPH_CONFIG', graphConfigs);

            var variableChosed = $('#descriptive_statistics_variables_area .active');

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

            var handleURL = (type == 'Descriptive_Statistics_Descriptive') ?
                'statistics/descriptives.do?method=descriptives' :
                'statistics/descriptives.do?method=frequency';

            $.ajax({
                url: handleURL,
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

            window.location.href = 'result.html';
            break;
        case 'Correlation_Bivariate':
            //描述统计 - 描述
            //算法部分 - 配置
            // algorithmConfigList = $('#Descriptive_Algorithm_Config input[type=checkbox]:checked');
            // algorithmConfigs = [];
            // algorithmConfigList.map(function (i, config) {
            //     console.log(i, config);
            //     algorithmConfigs.push($(config).val());
            // });
            // sessionStorage.setItem('PRIVATE_ALGORITHM_CONFIG', algorithmConfigs);

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

            break;
    }
};