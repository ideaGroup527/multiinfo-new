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
        var fileExpected = $("#data_file")[0].files[0];

        var file = new FormData();
        file.append('data_file', fileExpected);

        var fileUploadUrl = '';

        if (fileNameSuffix.match(xlsReg)) {
            fileUploadUrl = 'upload.do?method=excel';
        } else if (fileNameSuffix.match(txtReg)) {
            fileUploadUrl = 'upload.do?method=text';
        }

        $.ajax({
            url: fileUploadUrl + '&isFirstRowVar=true',
            type: 'post',
            data: file,
            async: false,
            processData: false,
            contentType: false,
            dataType: 'json',
            beforeSend: function () {
                $('#file_upload_btn').attr('data-i18n-tag', 'btn_uploading');
                i18n();
            }, success: function (data) {

                sessionStorage.clear();

                sessionStorage.setItem('token', data.token);
                sessionStorage.setItem('isMultiSheet', data.isMultiSheet);

                if (data.isMultiSheet) {

                    data.sheetNameList.map(function (sheet, i) {
                        var option = $('<option>');
                        $(option).text(sheet).val(i);
                        $(select).append(option);
                    });

                    $('#multi-sheet-select').modal({
                        'show': true,
                        'backdrop': 'static'
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