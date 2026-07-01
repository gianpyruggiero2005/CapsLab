<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Catalogo" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<h1>Catalogo Prodotti</h1>
<div class="products-grid">
	<c:forEach var="p" items="${prodotti}">
		<div class="product-card">
			<div class="product-img">
				<img src="${pageContext.request.contextPath}/images/${p.immagine}"
					alt="${p.nome}">
			</div>
			<div class="product-info">
				<h3>
					<a
						href="${pageContext.request.contextPath}/prodotti/dettaglio?id=${p.id}">${p.nome}</a>
				</h3>
				<p class="price-small">&euro; <fmt:formatNumber value="${p.prezzo}" pattern="0.00" /></p>
				<p class="iva-small">IVA ${p.iva}%</p>
				<p class="price-total">Totale: &euro; <fmt:formatNumber value="${p.getPrezzoConIva()}" pattern="0.00" /></p>
				<form action="${pageContext.request.contextPath}/carrello/aggiungi"
					method="post">
					<input type="hidden" name="prodottoId" value="${p.id}"> <input
						type="hidden" name="quantita" value="1">
					<button type="submit" class="btn btn-primary">Aggiungi al
						carrello</button>
				</form>
			</div>
		</div>
	</c:forEach>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
