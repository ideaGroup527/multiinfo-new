$(function () {
    $.get('../js/lib/nav/nav.json', function (packageJSON) {
        console.log(packageJSON);
        packageJSON.map(function (mainMenu, i) {
            //一级菜单
            var mainMenuItem = $('<li>');
            $(mainMenuItem).addClass('dropdown');

            var name = $('<a>');
            $(name).addClass('dropdown-toggle')
                .attr('data-toggle', 'dropdown')
                .attr('role', 'button')
                .attr('aria-haspopup', true)
                .attr('aria-expanded', true)
                .text(mainMenu.name);

            var caret = $('<span>');
            $(caret).addClass('caret');

            $(name).append(caret);
            $(mainMenuItem).append(name);


            //二级菜单
            if (mainMenu.subMenu) {
                var subMenu = $('<ul>');
                $(subMenu).addClass('dropdown-menu');

                var subMenuList = mainMenu.subMenu;
                subMenuList.map(function (secondMenu, i) {
                    var subItem = $('<li>');
                    var subName = $('<a>');
                    $(subName).attr('href', 'javascirpt:')
                        .text(secondMenu.name);
                    $(subItem).append(subName);
                    $(subMenu).append(subItem);
                });
            }
        });
    });
});