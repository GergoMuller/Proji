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
	} else {
		checkEmail();
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
	});

}
// clear the modal
