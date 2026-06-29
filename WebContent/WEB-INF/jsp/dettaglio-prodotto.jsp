<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="${prodotto.nome}" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="product-detail">
	<div class="product-detail-img">
		<img
			src="${pageContext.request.contextPath}/images/${prodotto.immagine}"
			alt="${prodotto.nome}">
	</div>
	<div class="product-detail-info">
		<h1>${prodotto.nome}</h1>
		<p class="description">${prodotto.descrizione}</p>
		<p class="price">&euro; ${prodotto.prezzo}</p>
		<p class="iva">IVA: ${prodotto.iva}%</p>
		<p class="price-with-iva"><strong>Totale: &euro; ${prodotto.getPrezzoConIva()}</strong></p>
		<p class="stock">Disponibilità: ${prodotto.quantitaDisponibile}
			pezzi</p>
		<form action="${pageContext.request.contextPath}/carrello/aggiungi"
			method="post">
			<input type="hidden" name="prodottoId" value="${prodotto.id}">
			<label for="quantita">Quantità:</label> <input type="number"
				id="quantita" name="quantita" value="1" min="1"
				max="${prodotto.quantitaDisponibile}">
			<button type="submit" class="btn btn-primary">Aggiungi al
				carrello</button>
		</form>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
