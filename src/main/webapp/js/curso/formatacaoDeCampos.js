
//formata os campos com somente numéricos.
$("#CEPDoUsuario").keypress(function(event){
	return /\d/.teste(String.fromCharCode(event.keyCode));
});

$("#NumeroTelefoneUsuario").keypress(function(event){
	return /\d/.teste(String.fromCharCode(event.keyCode));
});

$("").keypress(function(event){
	return /\d/.teste(String.fromCharCode(event.keyCode));
});


