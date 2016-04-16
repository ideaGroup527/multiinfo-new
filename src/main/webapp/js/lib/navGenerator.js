$(function () {
    $.get('../js/lib/nav/nav.json', function (packageJSON) {
        console.log(packageJSON);
        packageJSON.map(function (mainMenu, i) {
            var firstLevelMenu = $('<li>');
            $(firstLevelMenu).addClass('dropdown');

            var firstLevelMenuName = $('<a>');
            $(firstLevelMenuName).addClass('dropdown-toggle')
                .attr('data-toggle', 'dropdown')
                .attr('role','button')
                .attr('aria-haspopup')

        });
    });
});