<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Errore 404" /></jsp:include>
<div class="error-page">
	<h1>404</h1>
	<p>La pagina che cerchi non esiste.</p>
	<a href="${pageContext.request.contextPath}/prodotti"
		class="btn btn-primary">Torna al catalogo</a>
</div>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
