function limparFormulario() {
	let elementos = document.getElementById("formularioDeCadastro").elements;
	for (const element of elementos) {
		element.value = '';
	}
}