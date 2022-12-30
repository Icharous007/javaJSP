/**
 * 
 */
function deletarPorGet(){
	document.getElementById("formularioDeCadastro").method = 'GET';
	document.getElementById("formularioDeCadastro").action = contentPath+'/ServLetDeletarUsuarioPorGet'
	document.getElementById("acao").value='deletar';
	document.getElementById("formularioDeCadastro").submit();
}