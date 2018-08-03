$("#join-form button[type=submit]").click(function (event) {
    join_ajax_submit(event);
});

function join_ajax_submit(e) {
    e.preventDefault();

    var join = {};

    join["accId"] = $("#accId").val();
    join["password"] = $("#password").val();
    join["cpassword"] = $("#cpassword").val();
    join["name"] = $("#name").val();
    join["phoneNumb"] = $("#phoneNumb").val();
    join["aligoId"] = $("#aligoId").val();
    join["aligoKey"] = $("#aligoKey").val();

    $("#btn-submit").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/accounts/join",
        data: JSON.stringify(join),
        dataType: 'json',
        success: function (response) {
            console.log(response.responseJSON);
        },
        error: function (response) {
            console.log(response.responseJSON);
            console.log(response.responseJSON.code);
            console.log(response.responseJSON.message);
            console.log(response.responseJSON.fieldErrors);
            var fieldErrors = response.responseJSON.fieldErrors;
            for (var i = 0; i < fieldErrors.length; i++) {
                console.log(fieldErrors[i].field);
                console.log(fieldErrors[i].message);
            }
        }
    });
}

var companyTable;
var rows_selected;
var contactNumb;

$("#companyUpdateForm button[type=submit]").click(function(event){
    update_company(event);
});

function update_company(e) {
    e.preventDefault();

    var form = $('form')[0];
    var formData = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: '/api/companies/update',
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);


            companyTable = $('#companyTable').DataTable({

                select: {
                    style: 'multi'
                },

                order: [[1, 'asc']],

                destroy: true,

                data: data,

                columns: [
                    {
                        data: null,
                        defaultContent: '',
                        className: 'select-checkbox',
                        orderable: false
                    },
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"}
                ],

                columnDefs: [
                    {
                        targets: 0,
                        checkboxes: {
                            selectRow: true
                        }
                    }
                ]

            });

            rows_selected = companyTable.column(0).checkboxes.selected().data();

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });
};

$("#uploadForm button[type=submit]").click(function(event){
    	upload_company(event);
});

function test_upload(e) {
    e.preventDefault();

    var form = $('form')[0];
    var formData = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: '/api/companies/upload',
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);


            companyTable = $('#companyTable').DataTable({

                select: {
                    style: 'multi'
                },

                order: [[1, 'asc']],

                destroy: true,

                data: data,

                columns: [
                    {
                        data: null,
                        defaultContent: '',
                        className: 'select-checkbox',
                        orderable: false
                    },
                    {data: "companyName"},
                    {data: "type"},
                    {data: "personIncharge"},
                    {data: "position"},
                    {data: "contactNumb"}
                ],

                columnDefs: [
                    {
                        targets: 0,
                        checkboxes: {
                            selectRow: true
                        }
                    }
                ]

            });

            rows_selected = companyTable.column(0).checkboxes.selected().data();

        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });
};

$("#send-message button[type=submit]").click(function (event) {
    send_message_ajax_submit(event);
});

function send_message_ajax_submit(e) {
    e.preventDefault();

    console.log(rows_selected);
    contactNumb = [];

    for (var i = 0; i < rows_selected.length; i++) {
        contactNumb.push(rows_selected[i].contactNumb);
    }

    contactNumb = contactNumb.toString()
    console.log(contactNumb);

    var send = {};

    send["title"] = $("#title").val();
    send["msg"] = $("#msg").val();
    send["contactNumb"] = contactNumb;

    $("#btn-submit").prop("disabled", false);

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/api/companies/send",
        data: JSON.stringify(send),
        dataType: 'json',
        success: function (data) {
            $("#response-data").append(JSON.stringify(data));
        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });
}