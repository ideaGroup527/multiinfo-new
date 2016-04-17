var handleSubmitType = function (type, data) {
    switch (type) {

        case 'Descriptive_Statistics':
            //描述统计 - 描述
            var configList = $('#Descriptive_Statistics_Config input[type=checkbox]:checked');
            var configs = [];
            configList.map(function (i, config) {
                console.log(i, config);
                configs.push($(config).val());
            });

            console.log(configs);
            sessionStorage.setItem('PRIVATE_CONFIG', configs);

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

            window.location.href = 'result-display.html';

            break;
    }
};