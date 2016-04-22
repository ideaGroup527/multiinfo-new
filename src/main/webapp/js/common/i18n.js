$(function () {
    i18n();
});

var i18n = function () {
    //多语言配置
    var lang = (localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE")) ?
        localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE") :
        "zh-cn";

    var CONFIG_LANG_STRING = sessionStorage.getItem('PRIVATE_CONFIG_LANGUAGE_STRINGS');

    if (CONFIG_LANG_STRING) {
        $('[data-i18n-tag]').each(function () {
            var String = JSON.parse(CONFIG_LANG_STRING);

            var that = this;
            $(that).text(String[$(that).attr('data-i18n-type')][lang][$(that).attr('data-i18n-tag')]);
        });
    } else {
        $.getJSON('js/config/String.json', function (String) {
            sessionStorage.setItem('PRIVATE_CONFIG_LANGUAGE_STRINGS', JSON.stringify(String));
            $('[data-i18n-tag]').each(function () {
                var that = this;
                $(that).text(String[$(that).attr('data-i18n-type')][lang][$(that).attr('data-i18n-tag')]);
            });
        });
    }
};