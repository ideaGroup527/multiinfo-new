var variableRule = function () {
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
};