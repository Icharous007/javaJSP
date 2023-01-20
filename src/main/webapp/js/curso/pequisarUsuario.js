
function pesquisarUsuarioPorNome(pagina) {
	
	let nomeDoUsuario = document.getElementById("nomeParaPesquisar").value;
	if (nomeDoUsuario != null && nomeDoUsuario != "" && nomeDoUsuario.trim() != "") {
		let urlAction = document.getElementById("formularioDeCadastro").action = contentPath + '/ServLetPerquisaUsuario';

		$.ajax({
			method: "get",
			url: urlAction,
			data: "nomeDoUsuario=" + nomeDoUsuario + "&acao=AJAX" +"&pagina="+pagina,
			success: function(response, textStatus, xhr) {
				let json = JSON.parse(response);
				let botao = '<button onclick="selecionarCadastroDeUsuario();" class="btn btn-secondary bg-gradient-warning" type="button" >Seletc</button>';

				$('#tabelaDeUsuariosCadastradosModal > tbody > tr').remove();

				for (var k in json) {

					$('#tabelaDeUsuariosCadastradosModal > tbody').append('<tr><td>' + json[k].id + '</td>'
						+ '<td>' + json[k].nome + '</td>'
						+ '<td>' + json[k].email + '</td>'
						+ '<td>' + '<button onclick="selecionarCadastroDeUsuario(' + json[k].id + ');" class="btn btn-secondary bg-gradient-warning" type="button" >Seletc</button>' + '</td></tr>'
					);
				}
				let totalDePaginas=xhr.getResponseHeader("totalDePaginas")
				debugger;
				let ul = document.getElementById("ULpaginacaoAjax");
					if(ul.parentNode){
						ul.innerHTML ='';
					}
				
				for(var i =0 ;i<totalDePaginas;i++){
					let url =  urlAction+'?nomeDoUsuario='+nomeDoUsuario+'&acao=AJAX&pagina='+(i*3);
					//url = urlAction+'?'+'nomeDoUsuario='+nomeDoUsuario+'&acao=AJAX&pagina='+(i*3);
					//url='"'+url+'"';
					nomeDoUsuario = '"'+nomeDoUsuario+'"';
					
					$("#ULpaginacaoAjax")	.append('<li class="page-item"><a class="page-link" onclick="pesquisarUsuarioPorNome('+(i*3)+');" href="#" >'+(i+1)+'</a></li>');
				}
				
				document.getElementById("totalDeRegistros").textContent = "Total de registros encontrados: " + json.length;
				
			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao deletar " + usuario + " por Ajax id: " + xhr.responseText());
		});
	}
}

function buscaPaginaNoModalAJAX(url, nomeDoUsuario){
	debugger;
	$.ajax({
			method: "get",
			url: url,
			//data: "nomeDoUsuario=" + nomeDoUsuario + "&acao=AJAX" +"&pagina=0",
			success: function(response, textStatus, xhr) {
				let json = JSON.parse(response);
				let botao = '<button onclick="selecionarCadastroDeUsuario();" class="btn btn-secondary bg-gradient-warning" type="button" >Seletc</button>';

				$('#tabelaDeUsuariosCadastradosModal > tbody > tr').remove();

				for (var k in json) {

					$('#tabelaDeUsuariosCadastradosModal > tbody').append('<tr><td>' + json[k].id + '</td>'
						+ '<td>' + json[k].nome + '</td>'
						+ '<td>' + json[k].email + '</td>'
						+ '<td>' + '<button onclick="selecionarCadastroDeUsuario(' + json[k].id + ');" class="btn btn-secondary bg-gradient-warning" type="button" >Seletc</button>' + '</td></tr>'
					);
				}
				let totalDePaginas=xhr.getResponseHeader("totalDePaginas")
				
				for(var i =0 ;i<totalDePaginas;i++){
					let url = urlAction + "?nomeDoUsuario="+nomeDoUsuario+"&acao=AJAX&pagina="+(i*3);
					$("#ULpaginacaoAjax")	.append('<li class="page-item"><a class="page-link" onclick="buscaPaginaNoModalAJAX("'+url+'","'+nomeDoUsuario+'" );" >'+(i+1)+'</a></li>');
				}
				
				document.getElementById("totalDeRegistros").textContent = "Total de registros encontrados: " + json.length;
				
			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao deletar " + usuario + " por Ajax id: " + xhr.responseText());
		});
}

function selecionarCadastroDeUsuario(id) {
	let urlAction = document.getElementById("formularioDeCadastro").action = contentPath + '/ServLetPerquisaUsuario';
	window.location.href = urlAction + '?acao=selecionadoDoModal&id=' + id;
}

function pesquisaCepUsuario() {
	debugger;
	let cep = $("#CEPDoUsuario").val();
	$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function(dados) {
		if (!("erro" in dados)) {
			//Atualiza os campos com os valores da consulta.
			$("#CEPDoUsuario").val(dados.cep);
			$("#cidadeDoUsuario").val(dados.localidade);
			$("#enderecoDoUsuario").val(dados.logradouro + ' ' + dados.bairro + ' ' + dados.uf + '/' + dados.localidade);
			//$("#bairro").val(dados.bairro);
			//$("#uf").val(dados.uf);
			//$("#ibge").val(dados.ibge);
		} //end if.
		else {
			//CEP pesquisado não foi encontrado.
			alert("CEP não encontrado.");
		}
	});
}

