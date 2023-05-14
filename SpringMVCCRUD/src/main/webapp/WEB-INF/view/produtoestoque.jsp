<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/styles.css" />'>
<title>Estoque</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
		<br />
	</div>
	<div>
		<form action="produtoEstoque" method="post">
			<table>
				<tr>
					<td colspan="2"><input type="number" id="qtdMinima"
						name="qtdMinima" placeholder="Mínimo" value='${qtdMinima }'></td>
				</tr>
				<tr>
					<td><input type="submit" value="Qtd" id="button" name="button"></td>
					<td><input type="submit" value="Lista" id="button"
						name="button"></td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<c:if test="${not empty totalMinimo }">
			<p>
				Quantidade de Produtos com estoque abaixo:
				<c:out value="${totalMinimo }" />
			</p>
		</c:if>
		<c:if test="${not empty erro }">
			<H2 style="color: red">
				<c:out value="${erro }" />
			</H2>
		</c:if>
	</div>
	<div>
		<br />
		<c:if test="${not empty listaProdutos }">
			<p>Produtos com estoque abaixo:</p>
			<table border=1>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Quantidade em Estoque</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${listaProdutos }">
						<tr>
							<td>${p.codigo }</td>
							<td>${p.nome }</td>
							<td>${p.qtdEstoque }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>