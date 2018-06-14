// TODO STEP1 변수명 메소드명 정리하기

$("#join-form button[type=submit]").click(function(event){
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


var companyTable;

$("#frm-example button[type=submit]").click(function(event){
    test(event);
});

function test(e) {
    e.preventDefault();
    console.log(companyTable.column(0).checkboxes.selected());
}


$("#uploadForm button[type=submit]").click(function(event){
	test_upload(event);
 });

function test_upload(e) {
	console.log("test upload 클릭클릭")
	e.preventDefault();

	var form = $('form')[0];
	var formData = new FormData(form);
	$.ajax({
        type : "POST",
        enctype: 'multipart/form-data',
        url : '/api/companies/upload',
        data: formData,
        processData: false,
        contentType: false,
        cache : false,
        success: function (data) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);


            companyTable = $('#companyTable').dataTable({

                select : {
                    style: 'multi'
                },

                order : [[1, 'asc']],

                destroy : true,

                data : data,

                columns : [
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

                columnDefs : [
                    {
                        targets : 0,
                        checkboxes : {
                            selectRow : true
                        }
                    }
                ]

            });

            // $('#userTable').DataTable().rows('.selected').data();


            // Handle form submission event
            // $('#frm-example').on('submit', function(e){
            //     var form = this;
            //
            //     var rows_selected = companyTable.column(0).checkboxes.selected();
            //
            //     console.log(rows_selected.join(","))
            //     // Iterate over all selected checkboxes
            //     $.each(rows_selected, function(index, rowId){
            //         // Create a hidden element
            //         $(form).append(
            //             $('<input>')
            //                 .attr('type', 'hidden')
            //                 .attr('name', 'id[]')
            //                 .val(rowId)
            //         );
            //     });

                // FOR DEMONSTRATION ONLY
                // The code below is not needed in production

                // Output form data to a console
                // $('#example-console-rows').text(rows_selected.join(","));

                // Output form data to a console
                // $('#example-console-form').text($(form).serialize());

                // Remove added elements
                // $('input[name="id\[\]"]', form).remove();

                // Prevent actual form submission
                // e.preventDefault();



        },
        error: function (e) {

            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
	});
};