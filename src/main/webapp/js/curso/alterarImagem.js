function atualizarFotoDoUsuario(destino, origem){
	let preview = document.getElementById(destino);
	let arquivo = document.getElementById(origem).files[0];
	let reader = new FileReader();
	
	reader.onloadend = function(){
		preview.src = reader.result;
	};
	
	if(arquivo){
		reader.readAsDataURL(arquivo);
	}else{
		preview.src='';
	}
}