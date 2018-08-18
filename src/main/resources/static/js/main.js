var companyTable;
var contactNumb;

$('#selectSalesPerson').multiselect({
    includeSelectAllOption: true,
    buttonWidth: '200px',
    nonSelectedText: '영업사원 선택',
    maxHeight: 300
});

$('#selectType').multiselect({
    includeSelectAllOption: true,
    buttonWidth: '200px',
    nonSelectedText: '업종 선택',
    maxHeight: 300

});

$('#selectMeatCut').multiselect({
    includeSelectAllOption: true,
    buttonWidth: '200px',
    nonSelectedText: '품목 선택',
    maxHeight: 300
});

$('#updateBtn').click(function() {
    alert($('#selectSalesPerson').val());
    alert($('#selectType').val());
    alert($('#selectMeatCut').val());
});

$("#companyUpdateForm button[type=submit]").click(function (event) {
    company_update(event);
});

function company_update(e) {
    console.log('company_update()')
    e.preventDefault();

    var form = $("#companyUpdateForm")[0];
    var formData = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/api/companies/upload",
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (companyData) {
            $("#result").text(companyData);
            console.log("SUCCESS : ", companyData);
            $("#btnSubmit").prop("disabled", false);

            companyTable = $('#companyTable').DataTable({
                "data": companyData,
                // "select": {
                //     style: 'multi'
                // },
                "columns": [
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"}
                ]
            });
        },
        error: function (e) {
            $("#result").text(e.responseJSON);
            $("#btnSubmit").prop("disabled", false);

        }
    });

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/api/companies/update",
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

        },
        error: function (e) {
            $("#result").text(e.responseJSON);
            $("#btnSubmit").prop("disabled", false);

        }
    });
};

$("#uploadForm button[type=submit]").click(function (event) {
    excel_upload(event);
});

function excel_upload(e) {
    console.log('excel_upload()')
    e.preventDefault();

    var form = $("#uploadForm")[0];
    var formData = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/api/companies/upload",
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (companyData) {

            $("#result").text(companyData);
            console.log("SUCCESS : ", companyData);
            $("#btnSubmit").prop("disabled", false);

            companyTable = $('#companyTable').DataTable({
                "data": companyData,
                // "select": {
                //     style: 'multi'
                // },
                "columns": [
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"}
                ]
            });
        },
        error: function (e) {
            $("#result").text(e.responseJSON);
            $("#btnSubmit").prop("disabled", false);
        }
    });
};

$("#send-message button[type=submit]").click(function (event) {
    send_message_ajax_submit(event);
});

function send_message_ajax_submit(e) {
    console.log("send_message_ajax_submit()")
    e.preventDefault();

    var contactNumb;
    console.log(contactNumb);

    if ($("input[type='radio'][name='selectMode']:checked").val() == 'Y') {
        contactNumb = companyTable.columns(4).data().join();
    } else {
        contactNumb = companyTable.rows({selected: true}).data().map(v => v.contactNumb).join();
    }

    // TODO contactNumb이 없으면 어럴트 띄우기

    var send = {};
    send["receiver"] = contactNumb;
    send["title"] = $("#title").val();
    send["msg"] = $("#textarea").val();
    send["testmode_yn"] = $("input[type='radio'][name='sendMode']:checked").val();

    $("#btn-submit").prop("disabled", false);

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/api/companies/send",
        data: JSON.stringify(send),
        dataType: 'json',
        success: function (data) {
            var result = '<tr>'
                + '<td>' + data.message + '</td>'
                + '<td>' + data.msg_id + '</td>'
                + '<td>' + data.success_cnt + '</td>'
                + '<td>' + data.error_cnt + '</td>'
                + '<td>' + data.msg_type + '</td>'
                + '</tr>';
            $("#response-data").append(result);
            // $("#btnSubmit").prop("disabled", false);
            // TODO 막아 드리는게 좋을까?
        },
        error: function (e) {
            var result;
            for (var i = 0; i < e.responseJSON.errors.length; i++) {
                result += '<tr>'
                    + '<td>' + e.responseJSON.errors[i].defaultMessage + '</td>'
                    + '<td></td>'
                    + '<td></td>'
                    + '<td></td>'
                    + '<td></td>'
                    + '</tr>';
            }
            $("#response-data").append(result);
            // $("#btnSubmit").prop("disabled", false);
        }
    });
}

var calByte = {
    getByteLength: function (s) {

        if (s == null || s.length == 0) {
            return 0;
        }
        var size = 0;

        for (var i = 0; i < s.length; i++) {
            size += this.charByteSize(s.charAt(i));
        }

        return size;
    },

    charByteSize: function (ch) {
        if (ch == null || ch.length == 0) {
            return 0;
        }

        var charCode = ch.charCodeAt(0);

        if (charCode <= 0x00007F) {
            return 1;
        } else if (charCode <= 0x0007FF) {
            return 2;
        } else if (charCode <= 0x00FFFF) {
            return 2;
        } else {
            return 2;
        }
    }
};

$('#title').on('keyup', function () {
    var numChar = calByte.getByteLength($(this).val());
    $('#title-length > em').text(numChar);
    if (numChar > 44) {
        $('span > em').addClass('warning');
        $('#btn-submit').prop('disabled', true);
    } else {
        $('span > em').removeClass('warning');
        $('#btn-submit').prop('disabled', false);
    }
});

$('textarea').on('keyup', function () {
    var numChar = calByte.getByteLength($(this).val());
    $('#textarea-length > em').text(numChar);
    if (numChar > 2000) {
        $('span > em').addClass('warning');
        $('#btn-submit').prop('disabled', true);
    } else {
        $('span > em').removeClass('warning');
        $('#btn-submit').prop('disabled', false);
    }
});