// TODO STEP1 변수명 메소드명 정리하기

$("#join-form button[type=submit]").click(function (event) {
    join_ajax_submit(event);
});

function join_ajax_submit(e) {
    console.log("클릭클릭")
    e.preventDefault();

    var join = {};

    join["accId"] = $("#accId").val();
    join["password"] = $("#password").val();
    join["cpassword"] = $("#cpassword").val();
    join["name"] = $("#name").val();
    join["phoneNumb"] = $("#phoneNumb").val();
    join["aligoId"] = $("#aligoId").val();
    join["aligoKey"] = $("#aligoKey").val();

    var token = $("#csrf").val();

    $("#btn-submit").prop("disabled", true);

    $.ajax({
        type: "POST",
        url: "/api/accounts/join",
        data: JSON.stringify(join),
        dataType: 'json',
        beforeSend : function(xhr){
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        contentType: "application/json",
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

$("#uploadForm button[type=submit]").click(function(event){
    	test_upload(event);
});

function test_upload(e) {
    console.log("test upload 클릭클릭")
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
// TODO 현재 몇글자 적었는지 보여주기(잘 만든거 가져다 쓰자)
$("#send-message button[type=submit]").click(function (event) {
    send_message_ajax_submit(event);
});

function send_message_ajax_submit(e) {
    console.log("send_message_ajax_submit() 들어왔음")
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
            // TODO result_code가 1, -101 if문으로 분기해서 tr td 뿌리기
            // bootstrap tables 적용
            // {
            //     "result_code": 1
            //     "message": ""
            //     "msg_id": 123456789
            //     "success_cnt": 2
            //     "error_cnt": 0
            //     "msg_type": "SMS"
            // }

            // {
            //     "result_code": -101
            //     "message": "인증오류입니다."
            // }

            $("#response-data").append(JSON.stringify(data));
        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });
}