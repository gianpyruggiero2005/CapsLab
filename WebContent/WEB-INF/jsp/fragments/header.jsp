<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${param.titolo}- CapsLab</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<header class="site-header">
		<div class="container header-content">
			<a href="${pageContext.request.contextPath}/prodotti" class="logo">CapsLab</a>
			<nav class="nav-main">
				<form class="search-form"
					action="${pageContext.request.contextPath}/prodotti" method="get">
					<div class="search-wrapper">
						<input type="text" id="searchInput" name="q"
							placeholder="Cerca prodotti..." autocomplete="off">
						<div id="searchResults" class="search-results"></div>
					</div>
				</form>
				<ul class="nav-links">
					<li><a href="${pageContext.request.contextPath}/prodotti">Catalogo</a></li>
					<li><a href="${pageContext.request.contextPath}/carrello">
							Carrello <c:if
								test="${not empty sessionScope.carrello and not sessionScope.carrello.isEmpty()}">
								<span class="badge">${sessionScope.carrello.numeroArticoli}</span>
							</c:if>
					</a></li>
					<c:choose>
						<c:when test="${not empty sessionScope.utente}">
							<li><a href="${pageContext.request.contextPath}/ordini">I
									miei ordini</a></li>
							<c:if test="${sessionScope.utente.admin}">
								<li><a
									href="${pageContext.request.contextPath}/admin/prodotti"
									class="btn-admin">Admin</a></li>
							</c:if>
							<li><a href="${pageContext.request.contextPath}/auth/logout">Logout
									(${sessionScope.utente.nome})</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/auth/login">Login</a></li>
							<li><a
								href="${pageContext.request.contextPath}/auth/registrazione">Registrati</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
				<button class="menu-toggle" aria-label="Menu">&#9776;</button>
			</nav>
		</div>
	</header>
	<c:if test="${not empty sessionScope.messaggio}">
		<div class="alert alert-success">
			${sessionScope.messaggio}
			<c:remove var="messaggio" scope="session" />
		</div>
	</c:if>
	<main class="container main-content">
