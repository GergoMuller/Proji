
var user_type = "user_player";

function start_validation() {
    this.fail = false;
    if(user_type === "user_player"){
	    if (document.getElementById('registerForm:firstName').value.length <= 0) {
	        $.growl.error({message: "Enter your first name."});
	        fail = true;
	    }
	    if (document.getElementById('registerForm:lastName').value.length <= 0) {
	        $.growl.error({message: "Enter your last name."});
	        fail = true;
	    } else if (document.getElementById('registerForm:firstName').value.length < 2) {
	        $.growl.error({message: "Your first name has to be at least 2 caracters long."});
	        fail = true;
	    } else if (document.getElementById('registerForm:lastName').value.length < 2) {
	        $.growl.error({message: "Your last name has to be at least 2 caracters long."});
	        fail = true;
	    }
    }
    else if(user_type === "user_team"){
    	if(document.getElementById('registerForm:teamCity').value == "no"){
    		$.growl.error({message: "You must select a City"});
    	}
    }
    

    if (document.getElementById('registerForm:email').value.length <= 0) {
        $.growl.error({message: "Please enter your email address."});
        fail = true;
        continue_validation();
    } else if (!(/^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/.test(document.getElementById('registerForm:email').value))) {
        $.growl.error({message: "Wrong email format."});
        fail = true;
        continue_validation();
    } else {
        check_mail();
    }
}

function continue_validation() {
    if (document.getElementById('registerForm:password').value.length <= 0) {
        $.growl.error({message: "Please enter your password."});
        fail = true;
    } else if (document.getElementById('registerForm:password').value.length < 6) {
        $.growl.error({message: "Your password has to be at least 6 caracters long."});
        fail = true;
    } else if (document.getElementById('registerForm:password').value !== document.getElementById('registerForm:confirmPassword').value) {
        $.growl.error({message: "Passwords don't match"});
        fail = true;
    }
    if (!fail) {
        reg();
    }
}

function wrong_email() {
    $.growl.error({message: "This email is already in use"});
    fail = true;
}

function invalid_login(){
	$(document.getElementById('loginForm:loginemail')).val('');
	$(document.getElementById('loginForm:loginpw')).val('');
	$.growl.error({message: "Invalid login name or password"});
}


function reg_success() {
    $.growl.notice({title: "Your account has been successfully created", message: "Welcome! :)"});
    $('#myModal').modal('hide');
    document.getElementById('registerForm:firstName').value = "";
    document.getElementById('registerForm:lastName').value = "";
    document.getElementById('registerForm:email').value = "";
    document.getElementById('registerForm:password').value = "";
    document.getElementById('registerForm:confirmPassword').value = "";
        location.reload();
}

function defaultModal(){
	$('#label_teamName').hide();
	$(document.getElementById('div_teamName')).hide();
	$(document.getElementById('div_teamCity')).hide();
}

function roleChanged(type) {
    if (type == 'user_team') {
    	user_type = 'user_team';
    	$('#label_firstName').hide();
    	$('#label_lastName').hide();
    	$(document.getElementById('div_firstName')).hide();
    	$(document.getElementById('div_lastName')).hide();
    	$('#label_teamName').show();
    	$(document.getElementById('div_teamName')).show();
    	$(document.getElementById('div_teamCity')).show();
    	
    }
    else 
    {
    	user_type = 'user_player';
    	$('#label_firstName').show();
    	$('#label_lastName').show();
    	$(document.getElementById('div_firstName')).show();
    	$(document.getElementById('div_lastName')).show();
    	$('#label_teamName').hide();
    	$(document.getElementById('div_teamName')).hide();
    	$(document.getElementById('div_teamCity')).hide();
    }
}

