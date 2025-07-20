// Form validation
document.addEventListener('DOMContentLoaded', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');

            // Custom validation for password match
            if (form.id === 'registrationForm') {
                const password = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');

                if (password.value !== confirmPassword.value) {
                    confirmPassword.setCustomValidity('Passwords must match');
                    confirmPassword.classList.add('is-invalid');
                } else {
                    confirmPassword.setCustomValidity('');
                    confirmPassword.classList.remove('is-invalid');
                }
            }
        }, false);
    });

    // Real-time validation for password match
    const registrationForm = document.getElementById('registrationForm');
    if (registrationForm) {
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        password.addEventListener('input', function() {
            if (password.value !== confirmPassword.value && confirmPassword.value) {
                confirmPassword.setCustomValidity('Passwords must match');
                confirmPassword.classList.add('is-invalid');
            } else {
                confirmPassword.setCustomValidity('');
                confirmPassword.classList.remove('is-invalid');
            }
        });

        confirmPassword.addEventListener('input', function() {
            if (password.value !== this.value) {
                this.setCustomValidity('Passwords must match');
                this.classList.add('is-invalid');
            } else {
                this.setCustomValidity('');
                this.classList.remove('is-invalid');
            }
        });
    }

    // NIC validation
    const nicInput = document.getElementById('nic');
    if (nicInput) {
        nicInput.addEventListener('input', function() {
            // Basic NIC format validation (adjust according to your country's NIC format)
            const nicRegex = /^[0-9]{9}[vVxX]?$/;
            if (!nicRegex.test(this.value)) {
                this.setCustomValidity('Please enter a valid NIC number');
                this.classList.add('is-invalid');
            } else {
                this.setCustomValidity('');
                this.classList.remove('is-invalid');
            }
        });
    }

    // File upload validation
    const fileInputs = document.querySelectorAll('input[type="file"]');
    fileInputs.forEach(input => {
        input.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                // Check file size (max 5MB)
                if (file.size > 5 * 1024 * 1024) {
                    this.setCustomValidity('File size must be less than 5MB');
                    this.classList.add('is-invalid');
                } else {
                    this.setCustomValidity('');
                    this.classList.remove('is-invalid');
                }

                // Check file type
                const validTypes = ['image/jpeg', 'image/png', 'application/pdf'];
                if (!validTypes.includes(file.type)) {
                    this.setCustomValidity('Only JPG, PNG, or PDF files are allowed');
                    this.classList.add('is-invalid');
                } else {
                    this.setCustomValidity('');
                    this.classList.remove('is-invalid');
                }
            }
        });
    });
});