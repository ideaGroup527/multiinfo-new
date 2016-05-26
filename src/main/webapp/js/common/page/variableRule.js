var variableRule = function () {

    //变量选择规则 - 普通型
    $('[data-variable-select-rule="normal"]').each(function () {
        $(this).find('.variable-wrapper').on('click', function (e) {
            if (!$(this).attr('disabled')) {
                $(this).toggleClass('active');
            }
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
            for (var i = variablesIndex[0]; i <= $(this).index(); i++) {
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
        $(this).find('.variable-wrapper').on('click', function () {
            //取消之前选择的
            if (!$(this).attr('disabled')) {
                $(that).find('.variable-wrapper').removeClass('active');
                $(this).addClass('active');
            }
        });
    });

    //变量选择规则 - 组合型
    //TODO 现在只应对两个group的情况，待后期优化
    if ($('[data-variable-select-group]').length != 0) {
        var groupList = $('[data-variable-select-group]').attr('data-variable-select-group').split(',');

        $('[data-group-name=' + groupList[0] + ']').on('click', '.variable-wrapper', function (e) {
            e.stopPropagation();
            $('[data-group-name=' + groupList[0] + ']').find('.variable-wrapper').map(function (index, variable) {
                $('[data-group-name=' + groupList[1] + ']').find('[data-toggle-select=' + $(variable).attr('data-toggle-select') + ']')
                    .attr('disabled', ($(this).hasClass('active')) ? true : false);
            });
        });

        $('[data-group-name=' + groupList[1] + ']').on('click', '.variable-wrapper', function (e) {
            e.stopPropagation();
            $('[data-group-name=' + groupList[1] + ']').find('.variable-wrapper').map(function (index, variable) {
                $('[data-group-name=' + groupList[0] + ']').find('[data-toggle-select=' + $(variable).attr('data-toggle-select') + ']')
                    .attr('disabled', ($(this).hasClass('active')) ? true : false);
            });
        });
    }
};