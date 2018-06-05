$("#join-form button[type=submit]").click(function(event){
	join_ajax_submit(event);
 });

function join_ajax_submit(e) {
    console.log("클릭클릭")
    e.preventDefault();

    var join = {};

    join["accountId"] = $("#accountId").val();
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
            for (var i=0; i < fieldErrors.length; i++) {
                console.log(fieldErrors[i].field);
                console.log(fieldErrors[i].message);
            }
        }
    });
}

$("#uploadForm button[type=submit]").click(function(event){
	test_upload(event);
 });

function test_upload(e) {
	console.log("test 클릭클릭")
	e.preventDefault();

	var form = $('form')[0];
	var formData = new FormData(form);
	$.ajax({
		url : '/api/companies/test',
		contentType: false,
		processData: false,
		data: formData,
		enctype: 'multipart/form-data',
		type : 'POST',
		success : function(result) {
			alert("업로드 성공!!");
		},
		error : function(error) {
			alert("파일 업로드 실패");
			console.log(error);
		}
	});

};