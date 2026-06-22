<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Carrello" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>Il tuo Carrello</h1>
<c:choose>
	<c:when
		test="${empty sessionScope.carrello || sessionScope.carrello.isEmpty()}">
		<p>Il carrello è vuoto.</p>
		<a href="${pageContext.request.contextPath}/prodotti"
			class="btn btn-primary">Vai al catalogo</a>
	</c:when>
	<c:otherwise>
		<table class="table cart-table">
			<thead>
				<tr>
					<th>Prodotto</th>
					<th>Prezzo</th>
					<th>Quantità</th>
					<th>Subtotale</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${sessionScope.carrello.items}">
					<c:set var="item" value="${entry.value}" />
					<tr>
						<td data-label="Prodotto">${item.prodotto.nome}</td>
						<td data-label="Prezzo">&euro; ${item.prodotto.prezzo}</td>
						<td data-label="Quantità">
							<form
								action="${pageContext.request.contextPath}/carrello/aggiorna"
								method="post" class="inline-form">
								<input type="hidden" name="prodottoId"
									value="${item.prodotto.id}"> <input type="number"
									name="quantita" value="${item.quantita}" min="1"
									class="qty-input">
								<button type="submit" class="btn btn-sm">Aggiorna</button>
							</form>
						</td>
						<td data-label="Subtotale">&euro; ${item.prodotto.prezzo * item.quantita}</td>
						<td>
							<form
								action="${pageContext.request.contextPath}/carrello/rimuovi"
								method="post">
								<input type="hidden" name="prodottoId"
									value="${item.prodotto.id}">
								<button type="submit" class="btn btn-danger btn-sm">Rimuovi</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cart-footer">
			<p class="cart-total">Totale: &euro;
				${sessionScope.carrello.totale}</p>
			<form action="${pageContext.request.contextPath}/carrello/checkout"
				method="post">
				<button type="submit" class="btn btn-primary btn-lg">Conferma
					Ordine</button>
			</form>
		</div>
	</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
