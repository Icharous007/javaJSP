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
				<!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- 404 Error Text -->
                    <div class="text-center">
                        <div class="error mx-auto" data-text="404">404</div>
                        <p class="lead text-gray-800 mb-5">Page Not Found</p>
                        <p class="text-gray-500 mb-0">Mensagem de Erro, entre em contato com a equipe de suporte
					do sistema.</p>
						<p class="text-lg text-gray-900 mb-0"></p>
                        <a href="index.jsp">&larr; Back to Dashboard</a>
                    </div>

                </div>
				<h1></h1>
				<%
				out.print(request.getAttribute("msg"));
				%>
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
</body>
</html>
