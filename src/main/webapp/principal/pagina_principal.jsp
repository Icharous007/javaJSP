<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="head.jsp"></jsp:include>

<body id="page-top">
<div id="wrapper">
	<jsp:include page="sidebar.jsp"></jsp:include>
	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">
		<!-- Main Content -->
		<div id="content">
			<jsp:include page="topbar.jsp"></jsp:include>
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
	<jsp:include page= "javascripts.jsp"></jsp:include>
</body>
</html>