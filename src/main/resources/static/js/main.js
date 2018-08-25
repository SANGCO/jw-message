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

$("#companyUpdateForm button[type=submit]").click(function (event) {
    company_update(event);
});

function company_update(e) {
    console.log('company_update()')
    e.preventDefault();

    var form = $("#companyUpdateForm")[0];
    var formData = new FormData(form);

    $.ajax({
        type: "PUT",
        enctype: "multipart/form-data",
        url: "/api/companies",
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            console.log("success!" + data)
            location.reload();
        },
        beforeSend: function() {
            $('.wrap-loading').removeClass('display-none');
        },
        complete: function() {
            $('.wrap-loading').addClass('display-none');
        },
        error: function (e) {
            $("#result").text(e.responseJSON);
            $("#btnSubmit").prop("disabled", false);

        },
        timeout: 60000
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
        url: "/api/companies",
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
                "select": {
                    style: 'multi'
                },
                "columns": [
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"},
                    {data: "salesPerson"}
                ]
            });
        },
        error: function (e) {
            $("#result").text(e.responseJSON);
            $("#btnSubmit").prop("disabled", false);
        }
    });
};

$("#companyFromDatabase button[type=submit]").click(function (event) {
    company_from_database(event);
});

function company_from_database(e) {
    console.log('company_from_database()')
    e.preventDefault();

    var search = {};
    search['type'] = $('#selectType').val().join();
    search['salesPerson'] = $('#selectSalesPerson').val().join();
    search['meatCuts'] = $('#selectMeatCut').val().join();

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: '/api/companies/search',
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        success: function (companyData) {
            $("#result").text(companyData);
            console.log("SUCCESS : ", companyData);
            $("#btnSubmit").prop("disabled", false);

            companyTable = $('#companyTable').DataTable({
                'data': companyData,
                'destroy': true,
                "select": {
                    style: 'multi'
                },
                'columns': [
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"},
                    {data: "salesPerson"}
                ]
            });
        },
        beforeSend: function() {
            $('.wrap-loading').removeClass('display-none');
        },
        complete: function() {
            $('.wrap-loading').addClass('display-none');
        },
        error: function (e) {
            $('#result').text(e.responseJSON);
            $('#btnSubmit').prop('disabled', false);
        },
        timeout: 10000
    });
};

$("#send-message button[type=submit]").click(function (event) {
    send_message_ajax_submit(event);
});

function send_message_ajax_submit(e) {
    console.log("send_message_ajax_submit()")
    e.preventDefault();


    function ajax_submit(sender, receiver) {

        var send = {};
        send["sender"] = sender;
        send["receiver"] = receiver;
        send["title"] = $("#title").val();
        send["msg"] = $("#textarea").val();
        send["testmode_yn"] = $("input[type='radio'][name='sendMode']:checked").val();

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

    function defaultSender() {
        var pNumb = {};

        for (var i = 0; i < companyTable.context[0].aoData.length; i++) {
            pNumb[companyTable.row(i).data().salesPerson] = [];
        }

        for (var i = 0; i < companyTable.context[0].aoData.length; i++) {
            pNumb[companyTable.row(i).data().salesPerson].push(companyTable.row(i).data().contactNumb);
        }

        var sender = Object.keys(pNumb).map(function (k) {
            return k
        });

        var receiver = Object.keys(pNumb).map(function (k) {
            return pNumb[k]
        });

        Array.prototype.division = function (n) {
            var arr = this;
            var len = arr.length;
            var cnt = Math.floor(len / n) + (Math.floor(len % n) > 0 ? 1 : 0);
            var tmp = [];

            for (var i = 0; i < cnt; i++) {
                tmp.push(arr.splice(0, n));
            }

            return tmp;
        };

        var numbObj = {};

        // for (var i=0; i < sender.length; i++) {
        //     numbObj[sender[i]] = [];
        // }

        for (var i = 0; i < receiver.length; i++) {
            numbObj[sender[i]] = receiver[i].division(1000);
        }

        for (var i = 0; i < sender.length; i++) {
            for (var j = 0; j < numbObj[sender[i]].length; j++) {
                ajax_submit(sender[i], numbObj[sender[i]][j].join());
            }
        }
    }

    if ($("input[type='radio'][name='selectMode']:checked").val() == 'Y') {

        if ($('#numbDirect').val() === "") {
            defaultSender();
        } else {
            ajax_submit($('#numbDirect').val(), companyTable.columns(4).data().join());
        }

    } else {
        ajax_submit($('#numbDirect').val(), companyTable.rows({selected: true}).data().map(v => v.contactNumb).join());
    }
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