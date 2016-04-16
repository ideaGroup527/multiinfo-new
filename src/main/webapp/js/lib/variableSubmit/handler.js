var handleSubmitType = function (type, data) {
    switch (type) {

        case 'Descriptive_Statistics':
            //描述统计 - 描述
            var variableChosed = $('#descriptive_statistics_variables_area .active');

            var variableList = [];
            variableChosed.map(function (i, variable) {
                var params = JSON.parse($(variable).attr('data-config'));
                variableList.push(params);
            });
            console.log(variableList);
            var da = {
                    'variableList': variableList,
                    'dataGrid': data
                }
            console.log(da);

            $.ajax({
                url: 'statistics/descriptives.do?method=descriptives',
                //url: 'statistics/descriptives.do?method=frequency',
                data: JSON.stringify(da),
                //async: false,
                //processData: false,
                contentType: 'application/json',
                type: 'POST',
                dataType: 'JSON',
                success: function (data) {
                    console.log(data);
                }
            });

            break;
    }
};