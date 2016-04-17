$.fn.navGenerator = function () {

    var that = this;

    var packageJSON = [
        {
            "term": "Analysis",
            "name": "分析",
            "subMenu": [
                {
                    "term": "DescriptiveStatistics",
                    "name": "描述统计",
                    "subMenu": [
                        {
                            "term": "Descriptive",
                            "name": "描述",
                            "modalId": "#Descriptive_Statistics_Descriptive",
                            "modal": "modal.html",
                            "script": "script.js"
                        },
                        {
                            "term": "Frequency",
                            "modalId": "Descriptive_Statistics_Frequency",
                            "modal": "modal.html",
                            "script": "script.js",
                            "name": "频率"
                        }
                    ]
                }
            ]
        }
    ];

    //$.getJSON('../js/lib/nav/nav.json', function (packageJSON) {
    console.log(packageJSON);

    packageJSON.map(function (level_1_menu, i) {
        var level_1_item = $('<li>');
        $(level_1_item).addClass('dropdown');

        var level_1_item_name = $('<a>');
        $(level_1_item_name).addClass('dropdown-toggle')
            .attr('data-toggle', 'dropdown')
            .attr('role', 'button')
            .attr('aria-haspopup', true)
            .attr('aria-expanded', true)
            .text(level_1_menu.name);

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
                $(level_2_item).addClass('dropdown-submenu');

                var level_2_item_name = $('<a>');
                $(level_2_item_name).text(level_2_menu.name);

                $(level_2_item).append(level_2_item_name);

                if (level_2_menu.subMenu) {
                    var level_3_menu_list = $('<ul>');
                    $(level_3_menu_list).addClass('dropdown-menu');

                    level_2_menu.subMenu.map(function (level_3_menu) {
                        var level_3_item = $('<li>');

                        var level_3_item_name = $('<a>');
                        $(level_3_item_name).text(level_3_menu.name)
                            .attr('data-modal-id', level_3_menu.modalId)
                            .attr('data-modal', level_1_menu.term + '/' + level_2_menu.term + '/' + level_3_menu.term + '/' + level_3_menu.modal)
                            .attr('data-script', level_1_menu.term + '/' + level_2_menu.term + '/' + level_3_menu.term + '/' + level_3_menu.script);

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
    });
};