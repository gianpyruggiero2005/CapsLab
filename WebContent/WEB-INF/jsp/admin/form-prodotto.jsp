<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"><jsp:param
		name="titolo" value="Admin - Prodotto" /></jsp:include>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h1>${not empty prodotto ? 'Modifica' : 'Nuovo'}Prodotto</h1>

<form id="prodottoForm"
	action="${pageContext.request.contextPath}/admin/prodotti/salva"
	method="post" novalidate>
	<c:if test="${not empty prodotto}">
		<input type="hidden" name="id" value="${prodotto.id}">
	</c:if>
	<div class="form-group">
		<label for="nome">Nome</label> <input type="text" id="nome"
			name="nome" value="${prodotto.nome}" placeholder="Nome del prodotto"
			required> <span class="error-msg"></span>
	</div>
	<div class="form-group">
		<label for="descrizione">Descrizione</label>
		<textarea id="descrizione" name="descrizione"
			placeholder="Descrizione del prodotto" rows="4">${prodotto.descrizione}</textarea>
		<span class="error-msg"></span>
	</div>
	<div class="form-row">
		<div class="form-group">
			<label for="prezzo">Prezzo (&euro;)</label> <input type="number"
				id="prezzo" name="prezzo" value="${prodotto.prezzo}" step="0.01"
				min="0" placeholder="0.00" required> <span class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="iva">IVA (%)</label> <input type="number" id="iva"
				name="iva" value="${not empty prodotto ? prodotto.iva : '22'}"
				step="0.01" min="0" max="100" placeholder="22" required> <span
				class="error-msg"></span>
		</div>
	</div>
	<div class="form-row">
		<div class="form-group">
			<label for="quantita">Quantità disponibile</label> <input
				type="number" id="quantita" name="quantita"
				value="${prodotto.quantitaDisponibile}" min="0" placeholder="0"
				required> <span class="error-msg"></span>
		</div>
		<div class="form-group">
			<label for="immagine">Nome file immagine</label> <input type="text"
				id="immagine" name="immagine" value="${prodotto.immagine}"
				placeholder="immagine.jpg"> <span class="error-msg"></span>
		</div>
	</div>
	<button type="submit" class="btn btn-primary">Salva Prodotto</button>
	<a href="${pageContext.request.contextPath}/admin/prodotti" class="btn">Annulla</a>
</form>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
