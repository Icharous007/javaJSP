<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bem Vindo ao curso de JSP.</title>
</head>
<body>
<%
	out.print("testando");
	
%>
<h1>Bem vindo ao curso de JSP</h1>  


<form action="ServletLogin" method="post">

<input name="Login" type="text">
<input name="Senha" type="password" >

<input type="submit" value="Enviar">
</form>
</body>
</html>