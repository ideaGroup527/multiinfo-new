$(function () {
    var CONFIG = {};
    $('[data-config-group]').each(function () {
        var groupName = $(this).attr('data-group-name');
        var checkedFlag = $(this).attr('data-config-val-flag');

        var selector = '[name=' + groupName + ']';
        if (checkedFlag) {
            selector += checkedFlag;
        }

        var configsList = [];
        $(selector).each(function () {
            configsList.push($(this).val());
        });
        CONFIG[groupName] = configsList;
    });
    alert('van');
    sessionStorage.setItem('PRIVATE_CONFIG_DING_CHART', JSON.stringify(CONFIG));
});