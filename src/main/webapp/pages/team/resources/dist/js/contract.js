var showMoadal = function(){
	$('#contractModal').modal('show');
}

function hiedModal(){
	$('#contractModal').modal('toggle');
}

function invalidEmail(){
	$.growl.error({message: "There is no acount that matches this eamil address"});
}