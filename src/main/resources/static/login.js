async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: username, password: password })
    });

    if (response.ok) {
        alert('Login successful!');
        window.location.href = "index.html";
    } else {
        alert('Invalid credentials');
    }
}

// Redirigir a la página de registro
function goToRegister() {
    window.location.href = "register.html";
}
