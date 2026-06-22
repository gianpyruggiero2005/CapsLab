<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Errore 403" /></jsp:include>
<div class="error-page">
	<h1>403</h1>
	<p>Non hai i permessi per accedere a questa risorsa.</p>
	<a href="${pageContext.request.contextPath}/prodotti"
		class="btn btn-primary">Torna al catalogo</a>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
