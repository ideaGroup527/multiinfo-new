var handleSubmitType = function (type, data) {
    switch (type) {

        case 'Descriptive_Statistics_Descriptive':
            //描述统计 - 描述
            //算法部分 - 配置
            var algorithmConfigList = $('#Descriptive_Algorithm_Config input[type=checkbox]:checked');
            var algorithmConfigs = [];
            algorithmConfigList.map(function (i, config) {
                console.log(i, config);
                algorithmConfigs.push($(config).val());
            });
            sessionStorage.setItem('PRIVATE_ALGORITHM_CONFIG', algorithmConfigs);

            //制图部分 - 配置
            var graphConfigList = $('#Descriptive_Graph_Config input[type=checkbox]:checked');
            var graphConfigs = [];
            graphConfigList.map(function (i, config) {
                console.log(i, config);
                graphConfigs.push($(config).val());
            });
            sessionStorage.setItem('PRIVATE_GRAPH_CONFIG', graphConfigs);

            var variableChosed = $('#descriptive_statistics_descriptive_variables_area .active');

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
                url: 'statistics/descriptives.do?method=descriptives',
                //url: 'statistics/descriptives.do?method=frequency',
                //url: 'js/testJSON/result-table.json',
                data: JSON.stringify(send),
                async: false,
                //processData: false,
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
    }
};