/**
 * 
 */
function deletarPorGet(){
	let usuario = document.getElementById("loginDoUsuario").value;
	if(confirm("Deseja realmente excluir os dados do Usuario:  " + usuario+"?")){
		
		document.getElementById("formularioDeCadastro").method = 'GET';
		document.getElementById("formularioDeCadastro").action = contentPath + '/ServLetDeletarUsuarioPorGet'
		document.getElementById("acao").value = 'deletar';
		document.getElementById("formularioDeCadastro").submit();
	}
}

function deletarPorAjax(){
	debugger;
	let usuario = document.getElementById("loginDoUsuario").value;
	if(confirm("Deseja realmente excluir os dados do Usuario:  " + usuario+"?")){
		let urlAction = document.getElementById("formularioDeCadastro").action = contentPath + '/ServLetDeletarUsuarioPorGet';
		let IdUsuario = document.getElementById("idDoUsuario") .value;
		$.ajax({
			method: "get",
			url: urlAction,
			data: "idDoUsuario="+ IdUsuario + "&acao=AJAX",
			success: function (response){
				alert(response);
				document.getElementById("msg").textContent = response;
			}
		}).fail(function (xhr, status, errorThrown){
			alert("Erro ao deletar "+usuario+" por Ajax id: " + xhr.responseText());
		});		
	}
	
}