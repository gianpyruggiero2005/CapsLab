<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Registrazione" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="form-page">
	<h1>Registrati</h1>
	<c:if test="${not empty errore}">
		<div class="alert alert-error">${errore}</div>
	</c:if>
	<form id="registrationForm"
		action="${pageContext.request.contextPath}/auth/registrazione"
		method="post" novalidate>
		<div class="form-group">
			<label for="nome">Nome</label> <input type="text" id="nome"
				name="nome" placeholder="Inserisci il tuo nome" required> <span
				class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="cognome">Cognome</label> <input type="text" id="cognome"
				name="cognome" placeholder="Inserisci il tuo cognome" required>
			<span class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" id="email"
				name="email" placeholder="Inserisci la tua email" required>
			<span class="error-msg" id="emailError"></span>
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				id="password" name="password"
				placeholder="Minimo 8 caratteri, 1 maiuscola, 1 numero" required>
			<span class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="confermaPassword">Conferma Password</label> <input
				type="password" id="confermaPassword"
				placeholder="Ripeti la password" required> <span
				class="error-msg"></span>
		</div>
		<button type="submit" class="btn btn-primary btn-block">Registrati</button>
	</form>
	<p>
		Hai già un account? <a
			href="${pageContext.request.contextPath}/auth/login">Accedi</a>
	</p>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
