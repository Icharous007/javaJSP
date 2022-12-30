<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
							<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
							<div class="col-lg-7">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">Cadastro de Usuário...</h1>
									</div>
									<input type="hidden"name="path" id="path" value="<%= request.getContextPath()%>">
									<form class="user" action="<%= request.getContextPath()%>/ServLetUsuarioController" method="post" id="formularioDeCadastro">
									<input type="hidden"name="acao" id="acao">
										<div class="form-group row">
											<div class="input-group mb-1 col-sm-2">
												<span class="input-group-text bg-gradient-info text-gray-100" id="basic-addon1">ID:</span>
												<input type="text" name="idDoUsuario"class="form-control form-control-user" id="idDoUsuario" placeholder="ID." readonly="readonly" value="${modelLogin.id}" >
											</div>											
											<div class="input-group mb-1 col-sm-5">
												<span class="input-group-text bg-gradient-primary text-gray-100" id="basic-addon1">Nome:</span>
												<input type="text" name="nomeDoUsuario"class="form-control form-control-user" id="nomeDoUsuario" placeholder="Nome do usuario." required="required" value="${modelLogin.nome }" >
											</div>
											<div class="input-group mb-1 col-sm-3">
												<span class="input-group-text bg-gradient-secondary text-gray-100" id="basic-addon1">Login:</span>
												<input type="text" name="loginDoUsuario" class="form-control form-control-user" id="loginDoUsuario" placeholder="Login do usuario." required="required" value="${modelLogin.login }" >
											</div>											
										</div>
										<div class="form-group">
											<div class="input-group col-sm-8">
												<span class="input-group-text bg-gradient-info text-gray-100" id="basic-addon1">E-mail:</span>
												<input type="email" name="emailDoUsuario"  class="form-control form-control-user" id="emailDoUsuario" placeholder="E-mail..." required="required" value="${modelLogin.email} ">
											</div>
										</div>
										<div class="form-group ">
											<div class="input-group col-sm-4">
												<span class="input-group-text bg-gradient-warning text-gray-100" id="basic-addon1">Senha:</span>
												<input type="password" name="senhaDoUsuario" class="form-control form-control-user" id="senhaDoUsuario" placeholder="Senha..." required="required" value="${modelLogin.senha }">
											</div>
										</div>
										<div class = "form-group row col-sm-8">
											<button type="submit" class="btn bg-gradient-success border-left-dark text-gray-100 border-bottom-dark ">Cadastrar</button>
											<button type="button" class="btn bg-gradient-primary border-left-dark text-gray-100 border-bottom-dark" onclick="AtualizaPorOutraServlet();">Atualizar</button>
											
											<button type="button" class="btn bg-gradient-danger border-left-dark text-gray-100 border-bottom-dark"onclick="deletarPorGet();">Excluir</button>
											<button type="button" class="btn bg-gradient-light border-left-dark text-gray-900 border-bottom-dark" onclick="limparFormulario();">Limpar</button>
										</div>															
										<hr>
									</form>
									<span class="bg-gradient-warning text-gray-100"> ${msg }</span>
									<hr>
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
	<jsp:include page="javascripts.jsp"></jsp:include>
	<script src="<%=request.getContextPath() %>/js/curso/limparFormulario.js"></script>
	<script src="<%=request.getContextPath() %>/js/curso/deletar.js"></script>
	<script src="<%=request.getContextPath() %>/js/curso/alteraServLet.js"></script>
</body>
</html>