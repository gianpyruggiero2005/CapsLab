<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Errore 500" /></jsp:include>
<div class="error-page">
	<h1>500</h1>
	<p>Si è verificato un errore interno del server.</p>
	<a href="${pageContext.request.contextPath}/prodotti"
		class="btn btn-primary">Torna al catalogo</a>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
