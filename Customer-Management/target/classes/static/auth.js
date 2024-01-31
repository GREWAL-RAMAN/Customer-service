function submitLoginForm() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    console.log('Email:', email);
    console.log('Password:', password);

    fetch('http://localhost:8080/login/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(response => {
                return response.json();
        })
        .then(data => {
            // Save token to localStorage or session storage for subsequent requests
            localStorage.setItem('jwtToken', data.jwtToken);
            localStorage.setItem('username', data.username);
            console.log(localStorage.getItem('jwtToken'))
            window.location.href="/web/home";
        })
        .catch(error => {
            document.getElementById('error-message').textContent = 'Invalid credentials HONGE';
        });
}

