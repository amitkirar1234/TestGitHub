$(function() {
    // Select All click
    $("#selectall").click(function() {
        $('.case').prop('checked', this.checked);
    });

    // Individual checkbox click (fix applied)
    $(document).on("click", ".case", function() {
        $("#selectall").prop(
            "checked",
            $(".case").length === $(".case:checked").length
        );
    });
});
