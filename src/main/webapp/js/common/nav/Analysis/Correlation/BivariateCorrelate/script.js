var descriptiveStatisticsVariableChoose = function (variableList) {
    var def = $.Deferred();

    var chooseArea = $('#correlation_bivariate_area');

    variableList.map(function (variable, i) {
        var variableWrapper = $('<span>');
        $(variableWrapper).addClass('variable-wrapper')
            .text(variable.varietyName);

        $(variableWrapper).attr('data-config', JSON.stringify(variable));
        $(chooseArea).append(variableWrapper);

    });

    return def.resolve().promise();
};