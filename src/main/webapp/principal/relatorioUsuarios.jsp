<%@page import="model.ModelLogin"%>
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
				<div class="row">
					<div class="col-lg-12">
						<div class="card-block">
							<h4 class="sub-title text-center">Relatório de Usuários:</h4>
						</div>
						<form class="user"
							action="<%=request.getContextPath()%>/ServletRelatorioUsuario"
							method="get" id="formRelatorioUsuario">
							<div class="form-row align-items-center">
								<div class="col-sm-3 my-1">
									<label class="sr-only" for="dataInicial">Data Inicial:</label>
									<div class="input-group">
										<div class="input-group-text">Data Inicial-</div>
										<input type="date" class="form-control" id="dataInicial"
											placeholder="Data Inicial" name="dataInicial">
									</div>
								</div>
								<div class="col-sm-3 my-1">
									<label class="sr-only" for="dataFinal">Data Final:</label>
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">Data final-</div>
											<input type="date" class="form-control" id="dataFinal"
												placeholder="Data Final" name="dataFinal">
										</div>
									</div>
								</div>
								<div class="col-auto my-2">
									<input type="hidden" name="acao" id="acao">
									<button type="submit"
										class="btn btn-primary btn-user btn-block">Imprimir
										Relatório</button>
									<button type="button" onclick="imprimirPdf();"
										class="btn btn-primary btn-user btn-block">Imprimir
										PDF</button>
									<button type="button" onclick="imprimirECXEL();"
										class="btn btn-primary btn-user btn-block">Imprimir
										ECXEL</button>
									<button type="button" onclick="gerarGrafico();"
										class="btn btn-primary btn-user btn-block">Gerar Grafico</button>
								</div>
							</div>
						</form>
					</div>
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
									<th>Nome</th>
									<th>E-mail</th>
									<th>Telefones</th>
									<th>Selecionar</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>ID</th>
									<th>Login</th>
									<th>Nome</th>
									<th>E-mail</th>
									<th>Telefones</th>
									<th>Selecionar</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${listaDeUsuarios}" var="usuario">
									<tr>
										<td><c:out value="${usuario.id }"></c:out></td>
										<td><c:out value="${usuario.login }"></c:out></td>
										<td><c:out value="${usuario.nome }"></c:out></td>
										<td><c:out value="${usuario.email }"></c:out></td>
										<td>
											<table>
												<c:forEach items="${usuario.listaDeTelefones }"
													var="telefone">
													<tr>
														<td><c:out value="${telefone.numero}"></c:out></td>
													</tr>
												</c:forEach>
											</table>
										</td>
										<td><a class="btn btn-secondary bg-gradient-warning"
											href="<%=request.getContextPath() %>/ServLetPerquisaUsuario?acao=selecionadoDoModal&id=${usuario.id }">Selecionar</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<%-- 	<div>
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
											</div> --%>
					<hr>
					<!-- Area Chart -->
                            <div class="card shadow mb-6">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Salários</h6>
                                </div>
                                <div class="card-body">
                                    <div class="chart-area">
                                        <canvas id="myChart"></canvas>
                                    </div>
                                    <hr>                                    
                                </div>
                            </div>
					<span id="totalDeRegistrosDaTabela"></span> <span>${msg }</span>
					<hr>
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
	<script src="<%=request.getContextPath()%>/js/curso/grafico.js"></script>
	
	<script type="text/javascript">
		function imprimirPdf() {

			document.getElementById("acao").value = "imprimirPDF";
			document.getElementById("formRelatorioUsuario").submit();
		}
		function imprimirECXEL() {

			document.getElementById("acao").value = "imprimirECXEL";
			document.getElementById("formRelatorioUsuario").submit();
		}
	</script>
</body>
</html>