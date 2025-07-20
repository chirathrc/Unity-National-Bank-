// Authentication functions
document.addEventListener('DOMContentLoaded', function () {
    // Toggle password visibility
    const togglePassword = document.querySelectorAll('.toggle-password');
    togglePassword.forEach(button => {
        button.addEventListener('click', function () {
            const passwordInput = this.previousElementSibling;
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            // Toggle icon
            const icon = this.querySelector('i');
            if (type === 'password') {
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            } else {
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            }
        });
    });

    // Login form submission
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            // Get form values
            const username = document.getElementById('accountNumber').value;
            const password = document.getElementById('password').value;
            // const rememberMe = document.getElementById('rememberMe').checked;


            const data = {
                username: username,
                password: password
            };

            try {
                const response = await fetch("http://localhost:8080/unity_national_bank/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    const responseData = await response.json();
                    console.log(responseData);

                    if (responseData.success) {
                        // Redirect to the URL sent by the server
                        window.location.href = responseData.message;
                    } else {
                        alert("Invalid Details");
                    }
                } else {
                    alert("Invalid Details");
                }
            } catch (err) {
                console.error("Error during login:", err);
                alert("An error occurred while connecting to the server.");
            }



            // Here you would typically send this to your backend
            // console.log('Login attempt:', { accountNumber, password, rememberMe });

            // Simulate successful login
            // setTimeout(() => {
            //     alert('Login successful! Redirecting to dashboard...');
            //     // window.location.href = 'dashboard.html';
            // }, 1000);
        });
    }

});