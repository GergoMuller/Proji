function openProfileModal() {
	$('#profileModal').modal('show');
}

function hideProfileModal() {
	$('#profileModal').modal('hide');
	refreshModal();
}

function update() {
	this.fail = false;
	if (document.getElementById('editForm:email').value.length > 0
			&& !(/^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/
					.test(document.getElementById('editForm:email').value))) {
		$.growl.error({
			message : "Wrong email format."
		});
		fail = true;

		if (!fail) {
			checkEmail();
		}
	}

	checkOldPassword();

}

function checkOldPassword() {
	if (document.getElementById('editForm:old_password').value.length > 0
			&& document.getElementById('editForm:new_password').value.length > 0
			&& document.getElementById('editForm:confirmPassword').value.length > 0) {
		checkPassword();
	}
	else if (document.getElementById('editForm:old_password').value.length > 0
			|| document.getElementById('editForm:new_password').value.length > 0
			|| document.getElementById('editForm:confirmPassword').value.length > 0) {
		$.growl.error({
			message : "you didn't fill every field to change password"
		});
		fail = true;
	}

	if (document.getElementById('editForm:old_password').value.length == 0
			&& document.getElementById('editForm:new_password').value.length == 0
			&& document.getElementById('editForm:confirmPassword').value.length == 0
			&& !fail) {
		finishUpdate();
	}

}

function validatePassword() {
	console.log("validate pw");
	if (document.getElementById('editForm:new_password').value.length < 6
			&& document.getElementById('editForm:new_password').value.length > 0) {
		$.growl.error({
			message : "Password minimum 6 characters."
		});
		fail = true;
	}

	if (document.getElementById('editForm:new_password').value !== document
			.getElementById('editForm:confirmPassword').value) {
		$.growl.error({
			message : "Passwords don't match"
		});
		fail = true;
	}

	if (!fail) {
		finishUpdate();
	}
}

function finishUpdate() {
	$.growl.notice({
		message : "Succesfull update."
	});
	updateEntity();
	$('#profileModal').modal('toggle');
	refreshModal();
}

function invalidPassword() {
	$.growl.error({
		message : "Your old password is not correct"
	});
}

function invalidEmail() {
	$.growl.error({
		message : "This email address is already in use"
	});
}

function refreshModal() {
	$('#profileModal')
			.on(
					'hidden.bs.modal',
					function() {
						document
								.getElementById("editForm:toggleable_picture_content").style.display = "none"
						document
								.getElementById("editForm:toggleable_email_content").style.display = "none"
						document
								.getElementById("editForm:toggleable_password_content").style.display = "none"
						$('#div_picture').find("input").val('').end();
						$('#div_password').find("input").val('').end();
						$('#div_playerName').find("input").val('').end();
						$('#div_calendar').find("input").val('').end();
					});
}