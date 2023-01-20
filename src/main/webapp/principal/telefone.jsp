<%@page import="model.ModelLogin"%>
<%@page import="model.ModelTelefone"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<div class="card-block">
					<h4 class="sub-title text-center">Cadasto Telefone de
						${modelLogin.nome }.</h4>
					<form class="form-group"
						action="<%=request.getContextPath()%>/ServeletTelefoneController"
						method="post" id="fomularioTelefone">
						<div class="input-group mb-1 col-sm-4">
							<span class="input-group-text bg-gradient-info text-gray-100"
								id="basic-addon1">ID:</span> <input type="text"
								name="idDoUsuarioTelefone"
								class="form-control form-control-user" id="idDoUsuarioTelefone"
								placeholder="ID." readonly="readonly" value="${modelLogin.id}">
						</div>
						<div class="input-group mb-1 col-sm-4">
							<span class="input-group-text bg-gradient-info text-gray-100"
								id="basic-addon1">Nome:</span> <input type="text"
								name="nomeUsuario" class="form-control form-control-user"
								id="nomeUsuario" placeholder="numero"
								value="${modelLogin.nome }">
						</div>
						<div class="input-group mb-1 col-sm-4">
							<span class="input-group-text bg-gradient-info text-gray-100"
								id="basic-addon1">Numero:</span> <input type="tel"
								name="numeroTelefoneUsuario"
								class="form-control form-control-user"
								id="NumeroTelefoneUsuario" required="required"
								value="${modelTelefone.numero }">
						</div>
						<div class="input-group mb-1 col-sm-4">
							<button type="submit"
								class="btn bg-gradient-success border-left-dark text-gray-100 border-bottom-dark ">Cadastrar</button>
						</div>
					</form>
				</div>
				<div class="card-body">
					<div class="table-responsive"
						style="overflow: scroll; height: 300px;">
						<table class="table table-bordered"
							id="tabelaDeUsuariosCadastrados">
							<thead>
								<tr>
									<th>ID</th>
									<th>Nome</th>
									<th>Numero</th>
									<th>Excluir</th>

								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>ID</th>
									<th>Nome</th>
									<th>Numero</th>
									<th>Excluir</th>

								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${modelTelefones}" var="telefone">
									<tr>
										<td><c:out value="${telefone.id }"></c:out></td>
										<td><c:out value="${modelLogin.nome }"></c:out></td>
										<td><c:out value="${telefone.numero}"></c:out></td>
										<td><a class="btn btn-secondary bg-gradient-warning"
											href="<%=request.getContextPath() %>/ServeletTelefoneController?acao=deletarTelefone&idDoTelefone=${telefone.id }&idDoUsuario=${modelLogin.id}">Excluir</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
		<script src="<%=request.getContextPath()%>/js/curso/formatacaoDeCampos.js"></script>
</body>
</html>