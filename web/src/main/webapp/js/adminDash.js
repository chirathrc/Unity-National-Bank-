document.addEventListener('DOMContentLoaded', async function () {

    await loadData();

    // Modal functionality
    const registerCustomerBtn = document.getElementById('registerCustomerBtn');
    const createAccountBtn = document.getElementById('createAccountBtn');
    const registerCustomerModal = document.getElementById('registerCustomerModal');
    const createAccountModal = document.getElementById('createAccountModal');
    const closeModalButtons = document.querySelectorAll('.close-modal');

    // Open modals
    registerCustomerBtn.addEventListener('click', () => {
        registerCustomerModal.classList.add('active');
    });

    createAccountBtn.addEventListener('click', () => {
        createAccountModal.classList.add('active');
    });

    // Close modals
    closeModalButtons.forEach(button => {
        button.addEventListener('click', () => {
            registerCustomerModal.classList.remove('active');
            createAccountModal.classList.remove('active');
        });
    });

    // Close modal when clicking outside
    window.addEventListener('click', (e) => {
        if (e.target === registerCustomerModal) {
            registerCustomerModal.classList.remove('active');
        }
        if (e.target === createAccountModal) {
            createAccountModal.classList.remove('active');
        }
    });

    // Form submissions
    const customerRegistrationForm = document.getElementById('customerRegistrationForm');
    const accountCreationForm = document.getElementById('accountCreationForm');

    customerRegistrationForm.addEventListener('submit', function (e) {
        e.preventDefault();
        // In a real app, you would send this data to your backend
        alert('Customer registration submitted successfully!');
        registerCustomerModal.classList.remove('active');
        this.reset();
    });

    accountCreationForm.addEventListener('submit', function (e) {
        e.preventDefault();
        // In a real app, you would send this data to your backend
        alert('Bank account created successfully!');
        createAccountModal.classList.remove('active');
        this.reset();
    });

    // Initialize charts
    initializeCharts();

    // Simulate loading data
    setTimeout(() => {
        document.querySelectorAll('.stat-card').forEach(card => {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        });
    }, 300);
});

// Add animation to stat cards on page load
// document.addEventListener('DOMContentLoaded', function () {
//     const statCards = document.querySelectorAll('.stat-card');
//
//     statCards.forEach((card, index) => {
//         card.style.opacity = '0';
//         card.style.transform = 'translateY(20px)';
//         card.style.transition = 'all 0.5s ease ' + (index * 0.1) + 's';
//     });
// });

// Notification function
function showNotification(type, message) {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <div class="notification-content">
            <i class="fas fa-${type === 'success' ? 'check-circle' : 'exclamation-circle'}"></i>
            <span>${message}</span>
        </div>
        <button class="notification-close">&times;</button>
    `;

    document.body.appendChild(notification);

    // Show notification
    setTimeout(() => {
        notification.classList.add('show');
    }, 100);

    // Close button event
    notification.querySelector('.notification-close').addEventListener('click', () => {
        notification.classList.remove('show');
        setTimeout(() => {
            notification.remove();
        }, 300);
    });

    // Auto-remove after 5 seconds
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 5000);
}


async function loadData() {
    try {
        const response = await fetch("http://localhost:8080/unity_national_bank/admin/statics");

        if (!response.ok) {
            // If HTTP status is not OK (e.g., 401, 403, 500), redirect
            window.location.reload();
            return;
        }

        const data = await response.json();

        if (data.success) {
            // Correct properties: adjust based on actual JSON
            const stats = data.message; // This is a JsonObject from the server

            document.getElementById("totalCustomers").innerText = stats.totalCustomers.toLocaleString();
            document.getElementById("activeAccounts").innerText = stats.activeAccounts.toLocaleString();
            document.getElementById("fdAccounts").innerText = stats.fdAccounts.toLocaleString();
            document.getElementById("totalAssets").innerText = new Intl.NumberFormat('en-LK', {
                style: 'currency',
                currency: 'LKR'
            }).format(stats.totalAssets);

        } else {
            // If success: false
            window.location.reload();
        }

    } catch (error) {
        // Catch fetch/network/JSON parse errors
        console.error("Error loading data:", error);
    }
}
