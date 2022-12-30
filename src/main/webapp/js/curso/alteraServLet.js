/**
 * 
 */
let contentPath = document.getElementById("path").value;
function AtualizaPorOutraServlet() {
	document.getElementById("formularioDeCadastro").action = contentPath + '/ServletAtualizarCadastroUsuario';
	document.getElementById("formularioDeCadastro").submit();
}
function deletarUsuarioPorGet(){
	
}