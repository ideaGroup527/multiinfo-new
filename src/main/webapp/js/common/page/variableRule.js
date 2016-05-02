var variableRule = function () {

    //变量选择规则 - 普通型
    $('[data-variable-select-rule="normal"]').each(function () {
        var that = this;
        $(this).find('.variable-wrapper').on('click', function (e) {
            $(this).toggleClass('active');
            e.stopPropagation();
        });
    });

    //变量选择规则 - 连续选择型
    $('[data-variable-select-rule="consequent"]').each(function () {

        var variableList = {};
        $(this).find('.variable-wrapper').map(function (i, elem) {
            variableList[$(elem).index()] = $(elem);
        });
        var variablesIndex = Object.keys(variableList);
        var _variablesIndex = Object.keys(variableList).reverse();

        $(this).find('.variable-wrapper').on('click', function () {
            //选择该变量之前的所有变量
            for (var i = variablesIndex[0]; i < $(this).index(); i++) {
                $(variableList[i]).addClass('active');
            }
            //取消该变量之后的所有变量
            for (var j = _variablesIndex[0]; j > $(this).index(); j--) {
                $(variableList[j]).removeClass('active');
            }

        });
    });

    //变量选择规则 - 单选变量型
    $('[data-variable-select-rule="single"]').each(function () {

        var that = this;
        $(this).find('.variable-wrapper').on('click', function (e) {
            //取消之前选择的
            $(that).find('.variable-wrapper').removeClass('active');
            $(this).toggleClass('active');
        });

        //组合型情况
        //TODO 现在的情况只有两个分组
        if ($(this).attr("data-variable-select-group")) {
            var groupList = $(this).attr('data-variable-select-group').split('.');

            groupList.map(function (group, index) {

                $('[data-group-name=' + groupList[1 - index] + ']').on('click', '.variable-wrapper', function (e) {
                    if (!$(e.target).hasClass('active') && !$(e.target).attr('disabled')) {
                        $('[data-group-name=' + groupList[index] + ']').find('[data-toggle-select=' + $(e.target).attr('data-toggle-select') + ']')
                            .attr('disabled', true);
                    } else {
                        //从选中到无
                        $('[data-group-name=' + groupList[index] + ']').find('[data-toggle-select=' + $(e.target).attr('data-toggle-select') + ']')
                            .attr('disabled', false);
                    }
                });

            });
        }

    });

    //变量选择规则 - 组合型
};