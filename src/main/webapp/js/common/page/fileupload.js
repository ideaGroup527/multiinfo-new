$(function () {
    var fileNameSuffix = '';
    var xlsReg = /(xls|xlsx)/i;
    var txtReg = /(dat|txt)/i;

    //在选择文件之前弹出的提示
    $("#data_file").tooltip().change(function () {
        var filename = $(this)[0].files[0].name;
        if (filename) {
            fileNameSuffix = filename.split('.').reverse()[0];
            if (fileNameSuffix.match(xlsReg) || fileNameSuffix.match(txtReg)) {
                $('button.btn').attr('disabled', false);
            } else {
                alert('选择的文件格式有误，请重新选择。');
                $('button.btn').attr('disabled', true);
            }
        }
    });

    var select = $('#sheet-select');

    $('#file_upload_btn').click(function () {
        sessionStorage.clear();

        $('#file_upload_btn').text((localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE") == 'zh-cn') ? '正在上传...' : 'Uploading...');

        var fileExpected = $("#data_file")[0].files[0];

        var isFirstRowVarString = '';
        switch (localStorage.getItem("MULTIINFO_CONFIG_LANGUAGE")) {
            case 'zh-cn':
                isFirstRowVarString = '所传文件的第一行是否为变量？';
                break;
            case 'en-us':
                isFirstRowVarString = 'Is the first row of this file is variable?'
                break;
        }
        var isFirstRowVar = confirm(isFirstRowVarString);
        sessionStorage.setItem('isFirstRowVar',isFirstRowVar);

        var file = new FormData();
        file.append('data_file', fileExpected);
        file.append('isFirstRowVar', isFirstRowVar);

        var fileUploadUrl = '';

        if (fileNameSuffix.match(xlsReg)) {
            fileUploadUrl = 'upload/excel.do';
        } else if (fileNameSuffix.match(txtReg)) {
            fileUploadUrl = 'upload/text.do';
        }

        $.ajax({
            url: fileUploadUrl,
            type: 'post',
            data: file,
            async: false,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {

                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('isMultiSheet', data.isMultiSheet);
                sessionStorage.setItem('sheetNameList', JSON.stringify(data.sheetNameList));

                if (data.isMultiSheet) {

                    data.sheetNameList.map(function (sheet, i) {
                        var option = $('<option>');
                        $(option).text(sheet).val(i);
                        $(select).append(option);
                    });

                    $('#multi-sheet-select').modal({
                        'show': true,
                        'backdrop': 'static',
                        'keyboard': false
                    });
                } else {
                    window.location.href = 'data.html';
                }
            }
        })
    });

    $(select).change(function () {
        sessionStorage.setItem('sheetNo', $(this).val());
        window.location.href = 'data.html';
    });

});