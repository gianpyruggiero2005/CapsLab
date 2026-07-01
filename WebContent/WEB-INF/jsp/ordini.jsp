<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="I miei ordini" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<h1>I miei Ordini</h1>
<c:choose>
	<c:when test="${empty ordini}">
		<p>Non hai ancora effettuato ordini.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="ordine" items="${ordini}">
			<div class="order-card">
				<div class="order-header">
					<span>Ordine #${ordine.id}</span> <span><fmt:formatDate
							value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" /></span> <span
						class="order-total">&euro; <fmt:formatNumber value="${ordine.totale}" pattern="0.00" /></span>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>Prodotto</th>
							<th>Prezzo</th>
							<th>IVA</th>
							<th>Prezzo con IVA</th>
							<th>Qtà</th>
							<th>Subtotale</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="r" items="${ordine.righe}">
							<tr>
								<td data-label="Prodotto">${r.nomeProdotto}</td>
								<td data-label="Prezzo">&euro; <fmt:formatNumber value="${r.prezzoUnitario}" pattern="0.00" /></td>
								<td data-label="IVA">${r.iva}%</td>
								<td data-label="Prezzo con IVA">&euro; <fmt:formatNumber value="${r.prezzoUnitario * (1 + r.iva / 100)}" pattern="0.00" /></td>
								<td data-label="Qtà">${r.quantita}</td>
								<td data-label="Subtotale">&euro; <fmt:formatNumber value="${r.subtotale}" pattern="0.00" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:forEach>
	</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
