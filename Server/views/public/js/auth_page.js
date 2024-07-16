const container = document.getElementById("container");
const movetoRegister = document.getElementById("register");
const movetoLogin = document.getElementById("login");

const registerBtn = document.getElementById("registerBtn");
const loginBtn = document.getElementById("loginBtn");

// Toggle form visibility
movetoRegister.addEventListener('click', () => {
    container.classList.add("active");
});

movetoLogin.addEventListener('click', () => {
    container.classList.remove("active");
});

// Handle login
loginBtn.addEventListener('click', async (event) => {
    event.preventDefault();

    const email = document.querySelector('.sign-in input[type="email"]').value;
    const password = document.querySelector('.sign-in input[type="password"]').value;

    if(!email || !password) {
        alert('Please input all the empty field');
        return;
    }

    const response = await fetch('/admin/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    });

    console.log("response", response.ok);

    if (response.ok) {
        const data = await response.json();
        // Handle successful login
        alert('Login successful!');
        localStorage.setItem('username', email);        
        localStorage.setItem('token', data.token);
        window.location.href = '/home';
    } else {
        // Handle error
        alert('Login failed!');
    }
});

// Handle registration (similar logic can be applied for registration if needed)
registerBtn.addEventListener('click', async (event) => {
    event.preventDefault();

    const email = document.querySelector('.sign-up input[type="email"]').value;
    const name = document.querySelector('.sign-up input[type="text"]').value;
    const password = document.querySelector('.sign-up input[type="password"]').value;

    if(!email || !name || !password) {
        alert('Please input all the empty field');
        return;
    }

    const response = await fetch('/admin/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, name, password })
    });

    if (response.ok) {
        const data = await response.json();
        // Handle successful registration
        alert('Registration successful!');
        container.classList.remove("active");        
        // Redirect to another page or perform other actions
    } else {
        // Handle error
        alert('Registration failed!');
    }
});
