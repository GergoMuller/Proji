function openProfileModal(){
	$('#profileModal').modal('show');
}

function hideProfileModal(){
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
	}
	if (document.getElementById('editForm:new_password').value !== document.getElementById('editForm:confirmPassword').value) {
        $.growl.error({message: "Passwords don't match"});
        fail = true;
    }
	//CHECK OLD PASSWORD!!
	//CHECK NEW EMAIL

	if (!fail) {
		$.growl.notice({
			message : "Succesfull update."
		});
		updateEntity();
		$('#profileModal').modal('toggle');
		refreshModal();
	}

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
						$('#div_teamName').find("input").val('').end();
						$('#div_calendar').find("input").val('').end();
					});
}