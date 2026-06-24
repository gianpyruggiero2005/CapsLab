document.addEventListener('DOMContentLoaded', function() {
    // === AJAX SEARCH BAR ===
    const searchInput = document.getElementById('searchInput');
    const searchResults = document.getElementById('searchResults');
    let searchTimeout;
    if (searchInput && searchResults) {
        searchInput.addEventListener('input', function() {
            clearTimeout(searchTimeout);
            const q = this.value.trim();
            if (q.length < 2) { searchResults.style.display = 'none'; return; }
            searchTimeout = setTimeout(() => {
                fetch(contextPath + '/prodotti/search?q=' + encodeURIComponent(q))
                    .then(r => r.json())
                    .then(data => {
                        if (data.length === 0) { searchResults.style.display = 'none'; return; }
                        searchResults.innerHTML = data.map(p =>
                            `<a href="${contextPath}/prodotti/dettaglio?id=${p.id}">${p.nome} - &euro;${p.prezzo}</a>`
                        ).join('');
                        searchResults.style.display = 'block';
                    });
            }, 300);
        });
        document.addEventListener('click', e => {
            if (!searchInput.contains(e.target) && !searchResults.contains(e.target))
                searchResults.style.display = 'none';
        });
    }

    // === FORM VALIDATION ===
    const regForm = document.getElementById('registrationForm');
    if (regForm) {
        const patterns = {
            nome: /^[A-Za-zÀ-ÿ\s]{2,50}$/,
            cognome: /^[A-Za-zÀ-ÿ\s]{2,50}$/,
            email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            password: /^(?=.*[A-Z])(?=.*\d).{8,}$/
        };
        const messages = {
            nome: 'Nome: almeno 2 caratteri, solo lettere',
            cognome: 'Cognome: almeno 2 caratteri, solo lettere',
            email: 'Inserisci un indirizzo email valido',
            password: 'Minimo 8 caratteri, 1 maiuscola, 1 numero'
        };

        // Focus styling
        regForm.querySelectorAll('input').forEach(input => {
            input.addEventListener('focus', () => input.parentElement.classList.add('focused'));
            input.addEventListener('blur', () => input.parentElement.classList.remove('focused'));
        });

        // Real-time email check via AJAX
        const emailField = document.getElementById('email');
        const emailError = document.getElementById('emailError');
        let emailTimeout;
        if (emailField && emailError) {
            emailField.addEventListener('input', function() {
                clearTimeout(emailTimeout);
                const email = this.value.trim();
                if (!patterns.email.test(email)) return;
                emailTimeout = setTimeout(() => {
                    fetch(contextPath + '/auth/check-email?email=' + encodeURIComponent(email))
                        .then(r => r.json())
                        .then(data => {
                            emailError.textContent = data.exists ? 'Questa email è già registrata' : '';
                        });
                }, 500);
            });
        }

        regForm.addEventListener('submit', function(e) {
            let valid = true;
            Object.keys(patterns).forEach(name => {
                const input = regForm.querySelector(`[name="${name}"], #${name}`);
                if (!input) return;
                const errorSpan = input.parentElement.querySelector('.error-msg');
                if (!patterns[name].test(input.value.trim())) {
                    errorSpan.textContent = messages[name];
                    valid = false;
                } else {
                    errorSpan.textContent = '';
                }
            });
            // Conferma password
            const pwd = document.getElementById('password');
            const conf = document.getElementById('confermaPassword');
            if (conf && pwd && conf.value !== pwd.value) {
                conf.parentElement.querySelector('.error-msg').textContent = 'Le password non coincidono';
                valid = false;
            }
            if (emailError && emailError.textContent) valid = false;
            if (!valid) e.preventDefault();
        });
    }

    // Login form validation
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            let valid = true;
            const email = loginForm.querySelector('#email');
            const password = loginForm.querySelector('#password');
            if (!email.value.trim()) {
                email.parentElement.querySelector('.error-msg').textContent = 'Inserisci l\'email';
                valid = false;
            } else { email.parentElement.querySelector('.error-msg').textContent = ''; }
            if (!password.value.trim()) {
                password.parentElement.querySelector('.error-msg').textContent = 'Inserisci la password';
                valid = false;
            } else { password.parentElement.querySelector('.error-msg').textContent = ''; }
            if (!valid) e.preventDefault();
        });
    }

    // Product form validation
    const prodForm = document.getElementById('prodottoForm');
    if (prodForm) {
        prodForm.addEventListener('submit', function(e) {
            let valid = true;
            const nome = prodForm.querySelector('#nome');
            const prezzo = prodForm.querySelector('#prezzo');
            const iva = prodForm.querySelector('#iva');
            const quantita = prodForm.querySelector('#quantita');
            [[nome, 'Inserisci il nome'], [prezzo, 'Inserisci un prezzo valido'], [iva, 'Inserisci IVA valida'], [quantita, 'Inserisci quantità']].forEach(([el, msg]) => {
                const err = el.parentElement.querySelector('.error-msg');
                if (!el.value.trim()) { err.textContent = msg; valid = false; }
                else { err.textContent = ''; }
            });
            if (!valid) e.preventDefault();
        });
    }
});

// Context path from JSP - set in header
var ctxMeta = document.querySelector('meta[name="ctx"]');
var ctxLogo = document.querySelector('.logo');
var contextPath = (ctxMeta && ctxMeta.content) ||
    (ctxLogo && ctxLogo.getAttribute('href').replace('/prodotti', '')) || '';
