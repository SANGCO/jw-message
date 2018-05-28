$("#join-form").click(join_ajax_submit);

function join_ajax_submit(e) {
    console.log("클릭클릭")
    e.preventDefault();

    var join = {};

    join["accountId"] = $("#accountId").val();
    join["password"] = $("#password").val();
    join["cPassword"] = $("#cPassword").val();
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