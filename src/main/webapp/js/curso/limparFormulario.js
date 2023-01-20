function limparFormulario() {
	debugger;
	let elementos = document.getElementById("formularioDeCadastro").elements;
	for (const element of elementos) {
		element.value = '';
	}
	$("#emailDoUsuarioFeminino").attr("value", "feminino");
	$("#sexoDoUsuarioMasculino").attr("value", "masculino");
	$("#linkTelefoneModal").remove();
	$("#linkTelefonePagina").remove();
	$("#fotoDoUsuario").attr("src","https://img.freepik.com/vetores-gratis/sala-de-estar-vazia-em-apartamento-moderno_529539-69.jpg?w=1480&t=st=1673132321~exp=1673132921~hmac=9c0543809d23fc114c7a2aba8bb6ba3fc4e3ddd5810d5bca1072e41caea9af25");
}