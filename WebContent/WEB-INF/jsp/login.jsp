<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Login" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="form-page">
	<h1>Accedi</h1>
	<c:if test="${not empty errore}">
		<div class="alert alert-error">${errore}</div>
	</c:if>
	<form id="loginForm"
		action="${pageContext.request.contextPath}/auth/login" method="post"
		novalidate>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" id="email"
				name="email" placeholder="Inserisci la tua email" required>
			<span class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				id="password" name="password"
				placeholder="Inserisci la tua password" required> <span
				class="error-msg"></span>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Accedi</button>
	</form>
	<p>
		Non hai un account? <a
			href="${pageContext.request.contextPath}/auth/registrazione">Registrati</a>
	</p>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
