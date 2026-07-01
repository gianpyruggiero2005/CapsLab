<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Admin - Ordini" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<div class="admin-nav">
	<a href="${pageContext.request.contextPath}/admin/prodotti" class="btn btn-admin">Prodotti</a>
	<a href="${pageContext.request.contextPath}/admin/ordini" class="btn btn-admin active">Ordini</a>
</div>

<h1>Gestione Ordini</h1>

<div class="filters">
	<form action="${pageContext.request.contextPath}/admin/ordini"
		method="get" class="filter-form">
		<div class="form-row">
			<div class="form-group">
				<label for="da">Da:</label> <input type="date" id="da" name="da"
					value="${param.da}">
			</div>
			<div class="form-group">
				<label for="a">A:</label> <input type="date" id="a" name="a"
					value="${param.a}">
			</div>
			<button type="submit" class="btn btn-primary">Filtra per
				data</button>
		</div>
	</form>
	<form action="${pageContext.request.contextPath}/admin/ordini"
		method="get" class="filter-form">
		<div class="form-row">
			<div class="form-group">
				<label for="cliente">Cliente:</label> <input type="text"
					id="cliente" name="cliente" value="${param.cliente}"
					placeholder="Nome cliente">
			</div>
			<button type="submit" class="btn btn-primary">Filtra per
				cliente</button>
		</div>
	</form>
	<a href="${pageContext.request.contextPath}/admin/ordini" class="btn">Mostra
		tutti</a>
</div>

<c:choose>
	<c:when test="${empty ordini}">
		<p>Nessun ordine trovato.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="ordine" items="${ordini}">
			<div class="order-card">
				<div class="order-header">
					<span>Ordine #${ordine.id}</span> <span>Cliente:
						${ordine.nomeCliente}</span> <span><fmt:formatDate
							value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" /></span> <span
						class="order-total">&euro; ${ordine.totale}</span>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>Prodotto</th>
							<th>Prezzo</th>
							<th>IVA</th>
							<th>Qtà</th>
							<th>Subtotale</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="r" items="${ordine.righe}">
							<tr>
								<td data-label="Prodotto">${r.nomeProdotto}</td>
								<td data-label="Prezzo">&euro; ${r.prezzoUnitario}</td>
								<td data-label="IVA">${r.iva}%</td>
								<td data-label="Qtà">${r.quantita}</td>
								<td data-label="Subtotale">&euro; ${r.subtotale}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:forEach>
	</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
