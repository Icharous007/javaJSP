<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="head.jsp"></jsp:include>

<body id="page-top">
	<div id="wrapper">
		<jsp:include page="sidebar.jsp"></jsp:include>
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
			<!-- Main Content -->
			<div id="content">
				<jsp:include page="topbar.jsp"></jsp:include>
				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-5 d-none d-lg-block" >
							<c:choose>
								<c:when test="${modelLogin.foto_do_usuario != '' && modelLogin.foto_do_usuario != null}">
									<a href="<%= request.getContextPath()%>/ServletDownloadFotoUsuario?id=${modelLogin.id}">
										<img id="fotoDoUsuario" alt="Imagem do usuario" src = "${modelLogin.foto_do_usuario}" width="700px" >
									</a>
								</c:when>
								<c:otherwise>
									<img id="fotoDoUsuario" alt="Imagem do usuario" src = "img/imagem_vazia.png" width="700px" >
								</c:otherwise>
							</c:choose>
							</div>
							<div class="col-lg-7">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">Cadastro de Usuário...</h1>
									</div>
									<input type="hidden" name="path" id="path"
										value="<%=request.getContextPath()%>">
									<form class="user" enctype="multipart/form-data"
										action="<%=request.getContextPath()%>/ServLetUsuarioController"
										method="post" id="formularioDeCadastro">
										<input type="hidden" name="acao" id="acao">
										<div class="form-group row">
											<div class="input-group mb-1 col-sm-2">
												<span
													class="input-group-text bg-gradient-info text-gray-100"
													id="basic-addon1">ID:</span> <input type="text"
													name="idDoUsuario" class="form-control form-control-user"
													id="idDoUsuario" placeholder="ID." readonly="readonly"
													value="${modelLogin.id}">
											</div>
											<div class="input-group mb-1 col-sm-5">
												<span
													class="input-group-text bg-gradient-primary text-gray-100"
													id="basic-addon1">Nome:</span> <input type="text"
													name="nomeDoUsuario" class="form-control form-control-user"
													id="nomeDoUsuario" placeholder="Nome do usuario."
													required="required" value="${modelLogin.nome }">
											</div>
											<div class="input-group mb-1 col-sm-3">
												<span
													class="input-group-text bg-gradient-secondary text-gray-100"
													id="basic-addon1">Login:</span> <input type="text"
													name="loginDoUsuario"
													class="form-control form-control-user" id="loginDoUsuario"
													placeholder="Login do usuario." required="required"
													value="${modelLogin.login }">
											</div>
										</div>
										<div class= "form-group">
											<div class = "input-group mb-3 col-sm-6">
												<input type="file" class = "form-control-file" accept="image/*" name="arquivoFotoDoUsuario"
												 onchange="atualizarFotoDoUsuario('fotoDoUsuario','arquivoFotoDoUsuario');" id="arquivoFotoDoUsuario">										
											</div>
											<div class="input-group mb-1 col-sm-3">
												<span
													class="input-group-text bg-gradient-info text-gray-100">Data de nascimento:</span> 
													<input  type="date" name="dataDeNascimentoUsuario" class="form-control form-control-user"
													id="dataDeNascimentoUsuario" value="${modelLogin.dataDeNascimentoString}">
											</div>
											<div class="input-group mb-1 col-sm-5">
												<span
													class="input-group-text bg-gradient-info text-gray-100">Renda Mensal:</span> 
													<input type="text" 
													id="rendaDoUsuario" onkeyup="formatarMoeda();" value="${modelLogin.renda}" name="rendaDoUsuario">
											</div>
										</div>
										<div class="form-group">
											<div class="input-group col-sm-8">
												<span
													class="input-group-text bg-gradient-info text-gray-100"
													id="basic-addon1">Perfil:</span> <select
													class="form-control " aria-label="Default select example"
													name="perfilDoUsuario">

													<option disabled="disabled">[Selecione o Perfil
														desejado.]</option>
													<option value="Administrador"
														<%ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
														if (modelLogin != null && modelLogin.getPerfil_usuario()!=null &&modelLogin.getPerfil_usuario().equalsIgnoreCase("Administrador")) {
															out.print(" ");
															out.print("selected=\"selected\"");
															out.print(" ");
														}%>>Administrador
														</option>
													<option value="Auxiliar"
														<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
														if (modelLogin != null && modelLogin.getPerfil_usuario()!=null&&modelLogin.getPerfil_usuario().equalsIgnoreCase("Auxiliar")) {
															out.print(" ");
															out.print("selected=\"selected\"");
															out.print(" ");
														}%>>Auxiliar
														</option>
													<option value="Secretario"
														<%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
														if (modelLogin != null && modelLogin.getPerfil_usuario()!=null &&modelLogin.getPerfil_usuario().equalsIgnoreCase("Secretario")) {
															out.print(" ");
															out.print("selected=\"selected\"");
															out.print(" ");
														}%>>Secretario
														</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<div class="input-group col-sm-4">
												<span
													class="input-group-text bg-gradient-info text-gray-100"	id="basic-addon1">Sexo:</span> 
													<input type="radio"	name="sexoDoUsuario" class="form-control form-control-user"
													id="emailDoUsuarioFeminino" required="required" value="feminino"
													<% modelLogin = (ModelLogin) request.getAttribute("modelLogin");
													if(modelLogin != null && modelLogin.getSexo()!=null && modelLogin.getSexo().equalsIgnoreCase("feminino")) 
													{
														out.print(" ");
														out.print("checked=\"checked\"");
														out.print(" ");
														
													}%>
													> Feminino.
													<input type="radio" name="sexoDoUsuario"	class="form-control form-control-user"
													 id="sexoDoUsuarioMasculino" required="required" value="masculino" 
													 <%modelLogin = (ModelLogin) request.getAttribute("modelLogin");
													 if (modelLogin != null && modelLogin.getSexo()!=null && modelLogin.getSexo().equalsIgnoreCase("masculino")) 
													{
														out.print(" ");
														out.print("checked=\"checked\"");
														out.print(" ");
														
													}%>
													 >Masculino.
											</div>
										</div>
										<div class="form-group">
											<div class="input-group col-sm-8">
												<span
													class="input-group-text bg-gradient-info text-gray-100"
													id="basic-addon1">E-mail:</span> <input type="email"
													name="emailDoUsuario"
													class="form-control form-control-user" id="emailDoUsuario"
													placeholder="E-mail..." required="required"
													value="${modelLogin.email} ">
											</div>
										</div>
										<div class="form-group row">
											<div class="input-group mb-1 col-sm-3">
												<span
													class="input-group-text bg-gradient-info text-gray-100">CEP:</span> 
													<input onblur="pesquisaCepUsuario();" type="text" name="CEPDoUsuario" class="form-control form-control-user"
													id="CEPDoUsuario" value="${modelLogin.CEP}">
											</div>
											<div class="input-group mb-1 col-sm-4">
												<span
													class="input-group-text bg-gradient-primary text-gray-100">Cidade:</span>
													 <input type="text" name="cidadeDoUsuario" class="form-control form-control-user"
													id="cidadeDoUsuario" required="required" value="${modelLogin.cidade }">
											</div>
											<div class="input-group mb-1 col-sm-8">
												<span
													class="input-group-text bg-gradient-secondary text-gray-100">Endereço:</span> 
													<input type="text" name="enderecoDoUsuario"	class="form-control form-control-user" id="enderecoDoUsuario"
													 required="required" value="${modelLogin.endereco}">
											</div>
										</div>
										<div class="form-group ">
											<div class="input-group col-sm-4">
												<span
													class="input-group-text bg-gradient-warning text-gray-100"
													id="basic-addon1">Senha:</span> <input type="password"
													name="senhaDoUsuario"
													class="form-control form-control-user" id="senhaDoUsuario"
													placeholder="Senha..." required="required"
													value="${modelLogin.senha }">
											</div>
										</div>
										<div class="form-group row col-sm-8">
											<button type="submit"
												class="btn bg-gradient-success border-left-dark text-gray-100 border-bottom-dark ">Cadastrar</button>
											<button type="button"
												class="btn bg-gradient-primary border-left-dark text-gray-100 border-bottom-dark"
												onclick="AtualizaPorOutraServlet();">Atualizar</button>
												<c:if test="${modelLogin != null }">
													<a type="button" id="linkTelefoneModal"
													class="btn bg-gradient-info border-left-dark text-gray-100 border-bottom-dark"
													data-toggle="modal"
													data-target="#modalDeUsuariosCadastrados">Telefone Modal</a>
												</c:if>
												<c:if test="${modelLogin != null }">
													<a id="linkTelefonePagina"
													href="<%=request.getContextPath() %>/ServeletTelefoneController?idDoUsuario=${modelLogin.id}"
													class="btn bg-gradient-info border-left-dark text-gray-100 border-bottom-dark"
													>Telefone Pagina</a>
												</c:if>

											<button type="button"
												class="btn bg-gradient-danger border-left-dark text-gray-100 border-bottom-dark"
												onclick="deletarPorGet();">Excluir</button>
											<button type="button"
												class="btn bg-gradient-danger border-left-warning text-gray-100 border-bottom-warning"
												onclick="deletarPorAjax();">ExcluirAJAX</button>
											<button type="button"
												class="btn bg-gradient-info text-gray-100 "
												data-toggle="modal"
												data-target="#modalDeUsuariosCadastrados">Usuarios
												Cadastrados</button>
											<button type="button"
												class="btn bg-gradient-light border-left-dark text-gray-900 border-bottom-dark"
												onclick="limparFormulario();">Limpar</button>
										</div>
										<hr>
									</form>
									<span class="bg-gradient-warning text-gray-100" id="msg">
										${msg }</span>
									<hr>
									<c:set var="tipoUsuario" scope="session" value="${isUserAdmin}" />
									<c:if test="${tipoUsuario == true}">
									
										<div class="card-body">
											<div class="table-responsive"
												style="overflow: scroll; height: 300px;">
												<table class="table table-bordered" id="tabelaDeUsuariosCadastrados" >
													<thead>
														<tr>
															<th>ID</th>
															<th>Nome</th>
															<th>Nome</th>
															<th>E-mail</th>
															<th>Selecionar</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th>ID</th>
															<th>Login</th>
															<th>Nome</th>
															<th>E-mail</th>
															<th>Selecionar</th>
														</tr>
													</tfoot>
													<tbody>
														<c:forEach items="${listaDeUsuariosCadastrados}"
															var="usuario">
															<tr>
																<td><c:out value="${usuario.id }"></c:out></td>
																<td><c:out value="${usuario.login }"></c:out></td>
																<td><c:out value="${usuario.nome }"></c:out></td>
																<td><c:out value="${usuario.email }"></c:out></td>
																<td><a
																	class="btn btn-secondary bg-gradient-warning"
																	href="<%=request.getContextPath() %>/ServLetPerquisaUsuario?acao=selecionadoDoModal&id=${usuario.id }">Selecionar</a></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
											<div>
												<nav aria-label="Page navigation example">
													  <ul class="pagination">
													  <%
													  	int totalDePaginas = (int)  request.getAttribute("totalDePaginas");
													    
													  	for(int i =0;i<totalDePaginas;i++){
													  		String url = request.getContextPath() + "/ServeLetPaginacao?acao=paginar&pagina=" +(i*3);
													  		out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\">"+(i+1)+"</a></li>");
													  	}
													  %>													   
													    
													  </ul>
												</nav>
											</div>
											<hr>
											<span id="totalDeRegistrosDaTabela"></span>
											<hr>
										</div>

									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="rodape.jsp"></jsp:include>
			<!-- Fim do Main Content -->
		</div>
		<!-- Fim do Content Wrapper-->
		<!-- Scroll to Top Button-->
	</div>
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>
	<jsp:include page="logoutModal.jsp"></jsp:include>
	<jsp:include page="modalDeUsuariosCadastrados.jsp"></jsp:include>
	<jsp:include page="modalDeCadastramentoEVisializacaoDeTelefones.jsp"></jsp:include>
	<jsp:include page="javascripts.jsp"></jsp:include>
	<script
		src="<%=request.getContextPath()%>/js/curso/limparFormulario.js"></script>
	<script src="<%=request.getContextPath()%>/js/curso/deletar.js"></script>
	<script src="<%=request.getContextPath()%>/js/curso/alteraServLet.js"></script>
	<script src="<%=request.getContextPath()%>/js/curso/pequisarUsuario.js"></script>
	<script src="<%=request.getContextPath()%>/js/curso/alterarImagem.js"></script>
	<script src="<%=request.getContextPath()%>/js/curso/formatacaoDeCampos.js"></script>
	<script src="<%=request.getContextPath()%>/jquery/jqueryMaskMoney.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">	
	function formatarMoeda() {
        var elemento = document.getElementById('rendaDoUsuario');
        var valor = elemento.value;

        valor = valor + '';
        valor = parseInt(valor.replace(/[\D]+/g, ''));
        valor = valor + '';
        valor = valor.replace(/([0-9]{2})$/g, ",$1");

        if (valor.length > 6) {
            valor = valor.replace(/([0-9]{3}),([0-9]{2}$)/g, ".$1,$2");
        }

        elemento.value = valor;
        if(valor == 'NaN') elemento.value = '';
    }
	</script>
</body>
</html>