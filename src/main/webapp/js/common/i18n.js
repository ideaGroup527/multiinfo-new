$(function () {
    i18n();
});

var i18n = function () {

    //多语言配置
    var lang = (localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE")) ?
        localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE") :
        "zh-cn";

    $.getJSON('js/config/String.json', function (String) {
        $('[data-i18n-tag]').each(function () {
            var that = this;
            $(that).text(String[$(that).attr('data-i18n-type')][lang][$(that).attr('data-i18n-tag')]);
        });
    });
};