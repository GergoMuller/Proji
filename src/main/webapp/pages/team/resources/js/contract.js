$(document).ready(function() {
    $("#div_amount").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl/cmd+A
            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+C
            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+X
            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
});

var showMoadal = function() {
	$('#contractModal').modal('show');
}

function hiedModal() {
	$('#contractModal').modal('toggle');
}

function invalidEmail() {
	$.growl.error({
		message : "There is no acount that matches this eamil address"
	});
}

function validateContract() {
	this.fail = false;
	if (document.getElementById('contractForm:contract-to').value.length == 0
			|| !(/^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/
					.test(document.getElementById('contractForm:contract-to').value))) {
		$.growl.error({
			message : "Wrong email format."
		});
		fail = true;
	}else {
		checkEmail();
	}

	if (document.getElementById('contractForm:amount').value.length == 0) {
		$.growl.error({
			message : "Payment cannot be null."
		});
		fail = true;
	} 
	if (document.getElementById('contractForm:contractContent').value.length == 0) {
		$.growl.error({
			message : "Content of the contract cannot be null."
		});
		fail = true;
	}
	if (document.getElementById('contractForm:valid').value.length == 0) {
		$.growl.error({
			message : "Validation date cannot be null."
		});
		fail = true;
	} 
}

function finishContract() {
	if (!fail) {
		sendContract();
		hiedModal();
		clearModal();
		$.growl.notice({
			message : "Contract sent"
		});
	}
}

function clearModal() {
	$('#contractModal').on('hidden.bs.modal', function() {
		$('#div_contract-to').find("input").val('').end();
		$('#div_amount').find("input").val('').end();
		$('#div_valid').find("input").val('').end();
		$('#div_contractContent').find("input").val('').end();
	});

}
// clear the modal
