document.addEventListener('DOMContentLoaded', function() {
    // Password toggle functionality
    const passwordToggles = document.querySelectorAll('.profileUI-password-toggle');

    passwordToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const input = this.parentElement.querySelector('input');
            const icon = this.querySelector('i');

            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('bi-eye');
                icon.classList.add('bi-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('bi-eye-slash');
                icon.classList.add('bi-eye');
            }
        });
    });

    // Password strength indicator
    const newPasswordInput = document.getElementById('newPassword');
    const strengthMeter = document.querySelector('.profileUI-strength-meter');
    const strengthText = document.querySelector('.profileUI-strength-text span');

    newPasswordInput.addEventListener('input', function() {
        const password = this.value;
        let strength = 0;

        // Length check
        if (password.length >= 8) strength += 1;
        if (password.length >= 12) strength += 1;

        // Character type checks
        if (/[A-Z]/.test(password)) strength += 1;
        if (/[0-9]/.test(password)) strength += 1;
        if (/[^A-Za-z0-9]/.test(password)) strength += 1;

        // Update meter
        const width = strength * 20;
        strengthMeter.style.width = width + '%';

        // Update colors and text
        if (strength <= 2) {
            strengthMeter.style.backgroundColor = '#e74c3c';
            strengthText.textContent = 'Weak';
            strengthText.style.color = '#e74c3c';
        } else if (strength <= 4) {
            strengthMeter.style.backgroundColor = '#f39c12';
            strengthText.textContent = 'Medium';
            strengthText.style.color = '#f39c12';
        } else {
            strengthMeter.style.backgroundColor = '#2ecc71';
            strengthText.textContent = 'Strong';
            strengthText.style.color = '#2ecc71';
        }
    });

    // Password update form submission
    const passwordForm = document.getElementById('passwordUpdateForm');
    const successModal = new bootstrap.Modal(document.getElementById('successModal'));

    passwordForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        // Simple validation
        if (newPassword !== confirmPassword) {
            alert('New passwords do not match');
            return;
        }

        if (newPassword.length < 8) {
            alert('Password must be at least 8 characters long');
            return;
        }

        console.log(newPassword);
        console.log(currentPassword);

        const object = {
            oldPassword: currentPassword,
            newPassword: newPassword
        };

        try {
            const response = await fetch("http://localhost:8080/unity_national_bank/user/updatePassword", {
                method: "POST", // Assuming it's a POST request
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(object)
            });

            if (response.ok) {
                const data = await response.json();

                if (data.success) {
                    // Show success modal
                    successModal.show();
                    this.reset();
                } else {
                    alert(data.message);
                }
            } else {
                const errorText = await response.text();
                alert(`Server error (${response.status}): ${errorText || "Something went wrong."}`);
            }
        } catch (error) {
            console.error("Fetch error:", error);
            alert("An unexpected error occurred. Please try again later.");
        }



    });
});