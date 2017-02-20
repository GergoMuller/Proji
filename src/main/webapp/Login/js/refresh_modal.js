$('#myModal').on('hidden.bs.modal', function () {
    $(this).find("input").val('').end();
});

