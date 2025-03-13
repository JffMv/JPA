async function register() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    });

    if (response.ok) {
        alert("Registration successful! Redirecting to login...");
        window.location.href = "login.html";
    } else {
        alert("Registration failed. Try again.");
    }
}

// Redirigir a la página de login
function goToLogin() {
    window.location.href = "login.html";
}
