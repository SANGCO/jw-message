// TODO STEP1 변수명 메소드명 정리하기

var companyTable;
var rows_selected;
var contactNumb;

// TODO CSRF 적용
$("#uploadForm button[type=submit]").click(function(event){
    	excel_upload(event);
});

function excel_upload(e) {
    console.log('test upload 클릭클릭')
    e.preventDefault();

    var form = $("#uploadForm")[0];
    var formData = new FormData(form);
    var token = $("#csrf").val();

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/api/companies/upload",
        data: formData,
        beforeSend : function(xhr){
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
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
    var token = $("#csrf").val();

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
        beforeSend : function(xhr){
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
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

var calByte = {
    getByteLength : function(s) {

        if (s == null || s.length == 0) {
            return 0;
        }
        var size = 0;

        for ( var i = 0; i < s.length; i++) {
            size += this.charByteSize(s.charAt(i));
        }

        return size;
    },

    charByteSize : function(ch) {

        // if(encodeURIComponent(ch).length > 4)
        // {
        //     return 2;
        // }
        // else
        // {
        //     return 1;
        // }

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

        // if (charCode <= 0x00007F) {
        //     return 1;
        // } else if (charCode <= 0x0007FF) {
        //     return 2;
        // } else if (charCode <= 0x00FFFF) {
        //     return 3;
        // } else {
        //     return 4;
        // }
    }
};

$("textarea").on("keyup", function(){

    var numChar = calByte.getByteLength($(this).val());
    $("span > em").text(numChar);
    if(numChar > 2000){
        $("span > em").addClass("warning");
        $("#btn-submit").prop("disabled", true);
    } else {
        $("span > em").removeClass("warning");
        $("#btn-submit").prop("disabled", false);
    }
});