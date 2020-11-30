$(function () {
    const val_arr = ["Alabama", "Alaska"];
    //$("#id_mixed_application").val(val_arr).trigger("change");

    $("#id_mixed_application").on('input', function (event) {
        alert($("#id_mixed_application").val());
    });
});