var singleVariableChoose = function (variableList) {

    var chooseArea = $('#correlation_bivariate_area');

    variableList.map(function (variable, i) {
        var variableWrapper = $('<span>');
        $(variableWrapper).addClass('variable-wrapper')
            .text(variable.varietyName);

        $(variableWrapper).attr('data-config', JSON.stringify(variable));
        $(chooseArea).append(variableWrapper);

    });
};