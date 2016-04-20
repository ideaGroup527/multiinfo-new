var variableChoose = function (variableList) {

    var variableZone = $('.variable-zone');

    var group = [''];

    group.map(function (elem) {
        var zone = $('<div>');
        $(zone).addClass('zone').attr('data-variable-group', elem);

        var variableName = $('<h4>');
        $(variableName).text(elem);
        $(zone).append(variableName);

        variableList.map(function (variable, i) {
            var variableWrapper = $('<span>');
            $(variableWrapper).addClass('variable-wrapper')
                .text(variable.varietyName);

            $(variableWrapper).attr('data-config', JSON.stringify(variable));
            $(zone).append(variableWrapper);
        });

        $(variableZone).append(zone);
    });
};