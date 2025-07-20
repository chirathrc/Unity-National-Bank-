document.addEventListener('DOMContentLoaded', async function () {

    await loadData();

    // Mobile menu toggle
    const mobileMenuToggle = document.createElement('div');
    mobileMenuToggle.className = 'mobile-menu-toggle d-lg-none';
    mobileMenuToggle.innerHTML = '<i class="bi bi-list"></i>';
    document.querySelector('.top-nav').prepend(mobileMenuToggle);

    mobileMenuToggle.addEventListener('click', function () {
        document.querySelector('.sidebar').classList.toggle('show');
    });

    // Simulate loading data
    simulateDataLoading();

    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Simulate account balance updates
    setInterval(updateBalances, 5000);
});

function simulateDataLoading() {
    // Show loading state
    const cards = document.querySelectorAll('.card');
    cards.forEach(card => {
        card.classList.add('loading');
    });

    // Remove loading state after 1.5 seconds (simulating API call)
    setTimeout(() => {
        cards.forEach(card => {
            card.classList.remove('loading');
        });
    }, 1500);
}

function updateBalances() {
    // Simulate small balance changes
    // const balanceElements = document.querySelectorAll('.card-amount');
    // balanceElements.forEach(el => {
    //     const currentAmount = parseFloat(el.textContent.replace(/[^0-9.-]+/g,""));
    //     const change = (Math.random() * 10 - 5).toFixed(2);
    //     const newAmount = (currentAmount + parseFloat(change)).toFixed(2);
    //
    //     // Only update if positive balance
    //     if (newAmount > 0) {
    //         el.textContent = '$' + newAmount;
    //
    //         // Add animation
    //         el.classList.add('updated');
    //         setTimeout(() => {
    //             el.classList.remove('updated');
    //         }, 1000);
    //     }
    // });

    // Update interest earned
    // const interestElement = document.querySelector('.interest-card .card-amount');
    // if (interestElement) {
    //     const currentInterest = parseFloat(interestElement.textContent.replace(/[^0-9.-]+/g,""));
    //     const interestChange = (Math.random() * 2).toFixed(2);
    //     const newInterest = (currentInterest + parseFloat(interestChange)).toFixed(2);
    //     interestElement.textContent = '$' + newInterest;
    //
    //     // Add animation
    //     interestElement.classList.add('updated');
    //     setTimeout(() => {
    //         interestElement.classList.remove('updated');
    //     }, 1000);
    // }
}

// Add event listeners for quick action buttons
document.querySelectorAll('.btn-action').forEach(button => {
    button.addEventListener('click', function () {
        const action = this.querySelector('span').textContent;
        console.log(`${action} button clicked`);
        // You would typically redirect to the appropriate page here
    });
});

// Add click handler for transaction rows
document.querySelectorAll('.table tbody tr').forEach(row => {
    row.addEventListener('click', function () {
        // Get transaction details
        const date = this.cells[0].textContent;
        const description = this.cells[1].textContent;
        const amount = this.cells[2].textContent;

        // In a real app, you would show a modal with more details
        console.log('Transaction selected:', {date, description, amount});
    });
});


async function loadData() {

    const cardClasses = ["balance-card", "savings-card", "interest-card"];
    const btnColors = ["primary", "success", "warning"];
    const container = document.getElementById("accountSummaryRow");


    const response = await fetch("http://localhost:8080/unity_national_bank/user/getDetails");

    if (response.ok) {

        const resData = await response.json();
        console.log(resData)

        if (resData.success) {

            const account = resData.message.accounts;

            account.forEach((acc, i) => {
                const cardClass = cardClasses[i % 3];   // Rotate card style
                const btnColor = btnColors[i % 3];      // Rotate button color
                const maskedNumber = acc.accountNumber.slice(-4).padStart(acc.accountNumber.length, '*');

                const box = document.createElement("div");
                box.className = "col-md-4";
                box.innerHTML = `
      <div class="card ${cardClass}">
        <div class="card-body">
          <h5 class="card-title">${acc.accountType}</h5>
          <h2 class="card-amount">LKR. ${acc.balance.toLocaleString()}</h2>
          <p class="card-detail">Available Balance</p>
          <div class="account-number">${maskedNumber}</div>
          <a href="../user/getInterest?id=${acc.id}" class="btn btn-sm btn-outline-${btnColor} mt-2">View Details</a>
        </div>
      </div>
    `;
                container.appendChild(box);
            });


        }
    }
}