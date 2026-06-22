<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Admin - Prodotti" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>Gestione Prodotti</h1>
<a href="${pageContext.request.contextPath}/admin/prodotti/nuovo"
	class="btn btn-primary">Nuovo Prodotto</a>

<table class="table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>Prezzo</th>
			<th>IVA</th>
			<th>Qtà</th>
			<th>Attivo</th>
			<th>Azioni</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="p" items="${prodotti}">
			<tr>
				<td data-label="ID">${p.id}</td>
				<td data-label="Nome">${p.nome}</td>
				<td data-label="Prezzo">&euro; ${p.prezzo}</td>
				<td data-label="IVA">${p.iva}%</td>
				<td data-label="Qtà">${p.quantitaDisponibile}</td>
				<td data-label="Attivo">${p.attivo ? 'Sì' : 'No'}</td>
				<td data-label="Azioni"><a
					href="${pageContext.request.contextPath}/admin/prodotti/modifica?id=${p.id}"
					class="btn btn-sm">Modifica</a>
					<form
						action="${pageContext.request.contextPath}/admin/prodotti/elimina"
						method="post" class="inline-form"
						onsubmit="return confirm('Sei sicuro di voler eliminare questo prodotto?');">
						<input type="hidden" name="id" value="${p.id}">
						<button type="submit" class="btn btn-danger btn-sm">Elimina</button>
					</form></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
