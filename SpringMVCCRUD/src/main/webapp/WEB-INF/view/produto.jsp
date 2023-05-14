<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value="./resources/css/styles.css" />'>
<title>Produto</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
		<br />
	</div>
	<div>
		<form action="produto" method="post">
			<table>
				<tr>
					<td colspan="3"><input type="number" id="codigo" name="codigo"
						placeholder="Código" value='${produto.codigo }'></td>
					<td><input type="submit" value="Consultar" id="button"
						name="button"></td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="nome" name="nome"
						placeholder="Nome" value='${produto.nome }'></td>
				</tr>
				<tr>
					<td colspan="4"><input type="number" step="0.01" id="valor"
						name="valor" placeholder="Valor" value='${produto.valorUnitario }'></td>
				</tr>
				<tr>
					<td colspan="4"><input type="number" id="estoque"
						name="estoque" placeholder="Quantidade em Estoque"
						value='${produto.qtdEstoque }'></td>
				</tr>
				<tr>
					<td><input type="submit" value="Cadastrar" id="button"
						name="button"></td>
					<td><input type="submit" value="Atualizar" id="button"
						name="button"></td>
					<td><input type="submit" value="Excluir" id="button"
						name="button"></td>
					<td><input type="submit" value="Listar" id="button"
						name="button"></td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<c:if test="${not empty saida }">
			<p>
				<c:out value="${saida }" />
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
			<table border=1>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Valor Unitário</th>
						<th>Quantidade em Estoque</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${listaProdutos }">
						<tr>
							<td>${p.codigo }</td>
							<td>${p.nome }</td>
							<td>${p.valorUnitario }</td>
							<td>${p.qtdEstoque }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>