$.fn.navGenerator = function (config) {

    //多语言配置
    var lang = (localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE")) ?
        localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE") :
        "zh-cn";

    var that = this;

    var defer = $.Deferred();

    $.getJSON('js/common/nav/nav.json', function (packageJSON) {

        packageJSON.map(function (level_1_menu, i) {
            config.map(function (navName, i) {
                if (level_1_menu.term.toUpperCase() == navName.toUpperCase()) {

                    var level_1_item = $('<li>');
                    $(level_1_item).addClass('dropdown');

                    var level_1_item_name = $('<a>');
                    $(level_1_item_name).addClass('dropdown-toggle')
                        .attr('data-toggle', 'dropdown')
                        .attr('role', 'button')
                        .attr('aria-haspopup', true)
                        .attr('aria-expanded', true)
                        .html(level_1_menu.name[lang]);

                    var caret = $('<span>');
                    $(caret).addClass('caret');

                    $(level_1_item_name).append(caret);

                    $(level_1_item).append(level_1_item_name);

                    if (level_1_menu.subMenu) {

                        var level_2_menu_list = $('<ul>');
                        $(level_2_menu_list).addClass('dropdown-menu');

                        level_1_menu.subMenu.map(function (level_2_menu, i) {
                            $(level_2_menu_list).addClass('dropdown-menu');

                            var level_2_item = $('<li>');

                            var level_2_item_name = $('<a>');
                            $(level_2_item_name).html(level_2_menu.name[lang])
                                .attr('href', 'javascript:');


                            if (!level_2_menu.subMenu) {
                                $(level_2_item).addClass('dropdown');

                                $(level_2_item_name).addClass('js-menu-click')
                                    .attr('data-script', 'js/common/nav/' + level_1_menu.term + '/' + level_2_menu.term + '/' + level_2_menu.script);
                                $(level_2_item).append(level_2_item_name);

                            } else {
                                $(level_2_item).addClass('dropdown-submenu');
                                $(level_2_item).append(level_2_item_name);

                                $(level_1_item_name).attr('data-submenu', '');
                                var level_3_menu_list = $('<ul>');
                                $(level_3_menu_list).addClass('dropdown-menu');

                                level_2_menu.subMenu.map(function (level_3_menu) {
                                    var level_3_item = $('<li>');

                                    var level_3_item_name = $('<a>');
                                    $(level_3_item_name).addClass('js-menu-click')
                                        .html(level_3_menu.name[lang])
                                        .attr('data-modal-id', level_3_menu.modalId)
                                        .attr('href', 'javascript:')
                                        .attr('data-modal', 'js/common/nav/' + level_1_menu.term + '/' + level_2_menu.term + '/' + level_3_menu.term + '/' + level_3_menu.modal)
                                        .attr('data-script', 'js/common/nav/' + level_1_menu.term + '/' + level_2_menu.term + '/' + level_3_menu.term + '/' + level_3_menu.script);

                                    $(level_3_item).append(level_3_item_name);

                                    $(level_3_menu_list).append(level_3_item);
                                });

                                $(level_2_item).append(level_3_menu_list);

                            }
                            $(level_2_menu_list).append(level_2_item);
                        });

                        $(level_1_item).append(level_2_menu_list);
                    }

                    $(that).append(level_1_item);
                    defer.resolve();
                }
            });
        });
    });

    return defer.promise();
};