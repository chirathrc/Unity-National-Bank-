document.addEventListener('DOMContentLoaded', async function () {


    await loadData();

    // Initialize modal
    const transactionModal = new bootstrap.Modal(document.getElementById('transactionModal'));

    // Export as PDF functionality
    document.getElementById('exportPdfBtn').addEventListener('click', function () {
        alert('Your full transaction history PDF will be downloaded shortly.');
        // Simulate PDF download
        const link = document.createElement('a');
        link.href = '#';
        link.download = 'MyBank-Transaction-History.pdf';
        link.click();
    });

    // View transaction details
    document.querySelectorAll('.transaction-row').forEach(row => {
        const viewBtn = row.querySelector('.btn-view');
        viewBtn.addEventListener('click', function () {
            const transaction = {
                date: row.cells[0].textContent,
                title: row.querySelector('h6').textContent,
                amount: row.cells[2].textContent,
                status: row.cells[3].textContent.trim(),
                account: row.cells[1].textContent.includes('Primary') ? 'Primary (•••• 4567)' : 'Savings (•••• 8910)',
                type: row.classList.contains('scheduled') ? 'scheduled' :
                    row.querySelector('i').classList.contains('bi-house-door') ? 'payment' :
                        row.querySelector('i').classList.contains('bi-arrow-down-circle') ? 'deposit' :
                            row.querySelector('i').classList.contains('bi-lightning') ? 'bill' : 'transfer',
                reference: row.querySelector('small').textContent,
                id: 'TX-' + Math.random().toString(36).substr(2, 9).toUpperCase()
            };

            // Set modal content
            document.getElementById('modalTransactionTitle').textContent = transaction.title;
            document.getElementById('modalTransactionAmount').textContent = transaction.amount;
            document.getElementById('modalTransactionDate').textContent = transaction.date;
            document.getElementById('modalTransactionAccount').textContent = transaction.account;
            document.getElementById('modalTransactionStatus').innerHTML = `<span class="badge ${transaction.status === 'Completed' ? 'bg-success-bg text-success' : 'bg-warning-bg text-warning'}">${transaction.status}</span>`;
            document.getElementById('modalTransactionReference').textContent = transaction.reference;
            document.getElementById('modalTransactionId').textContent = transaction.id;

            // Set category based on type
            const categoryMap = {
                'transfer': 'Transfer',
                'payment': 'Payment',
                'deposit': 'Deposit',
                'bill': 'Utility Bill',
                'scheduled': 'Scheduled Payment'
            };
            document.getElementById('modalTransactionCategory').textContent = categoryMap[transaction.type];

            // Set appropriate icon and color
            const modalIcon = document.getElementById('modalTransactionIcon');
            modalIcon.className = 'transaction-icon-lg';
            modalIcon.classList.add(`transaction-type-${transaction.type}`);

            const iconClass = {
                'transfer': 'bi-arrow-left-right',
                'payment': 'bi-house-door',
                'deposit': 'bi-arrow-down-circle',
                'bill': 'bi-lightning',
                'scheduled': 'bi-calendar-check'
            }[transaction.type];

            modalIcon.innerHTML = `<i class="bi ${iconClass} fs-4"></i>`;

            // Set download button handler
            document.getElementById('downloadReceiptBtn').onclick = function () {
                alert(`Receipt for ${transaction.title} (${transaction.date}) will be downloaded as PDF.`);
                const link = document.createElement('a');
                link.href = '#';
                link.download = `Receipt-${transaction.date}-${transaction.title.replace(/\s+/g, '-')}.pdf`;
                link.click();
            };

            // Show modal
            transactionModal.show();
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const transactionModal = new bootstrap.Modal('#transactionModal');

    // Handle View button clicks
    document.querySelectorAll('.btn-view').forEach(btn => {
        btn.addEventListener('click', function () {
            const row = this.closest('.transaction-row');
            const transaction = {
                date: row.cells[0].textContent,
                title: row.querySelector('h6').textContent,
                description: row.querySelector('small').textContent,
                amount: row.cells[2].textContent,
                status: row.cells[3].textContent.trim(),
                type: row.dataset.type || 'transfer',
                account: 'Primary (•••• 4567)' // Default account
            };

            // Set modal content
            document.getElementById('modalTransactionTitle').textContent = transaction.title;
            document.getElementById('modalTransactionAmount').textContent = transaction.amount;
            document.getElementById('modalTransactionDate').textContent = transaction.date;
            document.getElementById('modalTransactionAccount').textContent = transaction.account;

            // Set status badge
            const statusBadge = document.getElementById('modalTransactionStatus');
            statusBadge.innerHTML = row.cells[3].innerHTML;

            // Set reference and ID
            document.getElementById('modalTransactionReference').textContent = transaction.description;
            document.getElementById('modalTransactionId').textContent = 'TX-' + Math.random().toString(36).substr(2, 9).toUpperCase();

            // Set category based on type
            const categories = {
                payment: 'Payment',
                transfer: 'Transfer',
                deposit: 'Deposit',
                bill: 'Utility Bill'
            };
            document.getElementById('modalTransactionCategory').textContent = categories[transaction.type] || 'Transaction';

            // Set icon and color
            const icon = document.getElementById('modalTransactionIcon');
            icon.className = 'transaction-icon-lg';
            icon.classList.add(`transaction-type-${transaction.type}`);

            const icons = {
                payment: 'bi-house-door',
                transfer: 'bi-arrow-left-right',
                deposit: 'bi-arrow-down-circle',
                bill: 'bi-lightning'
            };
            icon.innerHTML = `<i class="bi ${icons[transaction.type] || 'bi-cash'} fs-4"></i>`;

            // Show modal
            transactionModal.show();
        });
    });

    // Handle PDF download buttons
    document.querySelectorAll('.btn-download-pdf').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const row = this.closest('.transaction-row');
            const date = row.cells[0].textContent;
            const title = row.querySelector('h6').textContent;

            alert(`Downloading receipt for ${title} (${date}) as PDF`);
            // In a real app, this would generate/download a PDF
            const link = document.createElement('a');
            link.href = '#';
            link.download = `Receipt_${date.replace(/ /g, '_')}_${title.replace(/ /g, '_')}.pdf`;
            link.click();
        });
    });

    // Handle modal PDF download button
    document.getElementById('downloadReceiptBtn').addEventListener('click', function () {
        const title = document.getElementById('modalTransactionTitle').textContent;
        const date = document.getElementById('modalTransactionDate').textContent;

        alert(`Downloading receipt for ${title} (${date}) as PDF`);
        // In a real app, this would generate/download a PDF
        const link = document.createElement('a');
        link.href = '#';
        link.download = `Receipt_${date.replace(/ /g, '_')}_${title.replace(/ /g, '_')}.pdf`;
        link.click();
    });
});


async function loadData() {

    try {
        const response = await fetch("http://localhost:8080/unity_national_bank/user/getHistory");

        if (!response.ok) {
            alert("Server error while loading account.");
            return;
        }

        const data = await response.json();
        console.log(data);

        if (data.success && data.message) {

            loopData(data.message);

        } else {
            alert("Account data not found.");
        }

    } catch (err) {
        console.error(err);
        alert("Error loading account info. Check connection.");
    }
}


function loopData(transfer) {
    const tbody = document.getElementById("transactionTableBody");
    tbody.innerHTML = ''; // Clear previous content

    transfer.forEach(transfer => {
        const row = document.createElement("tr");
        row.classList.add("transaction-row");

        // Get date + time string
        const jsDate = new Date(
            transfer.dateTime.date.year,
            transfer.dateTime.date.month - 1,
            transfer.dateTime.date.day,
            transfer.dateTime.time.hour,
            transfer.dateTime.time.minute
        );
        const displayDate = jsDate.toLocaleString("en-US", {
            year: "numeric",
            month: "short",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            hour12: true
        });

        // Format amount with sign and color
        const isIncoming = transfer.isFromTransfer !== true;
        const amountFormatted = `${isIncoming ? "+" : "-"}LKR. ${transfer.amount.toFixed(2)}`;
        const amountClass = isIncoming ? "text-success" : "text-danger";

        // Determine status, icons, styles
        let statusLabel = "";
        let badgeClass = "";
        let iconColor = "";
        let icon = "bi-arrow-" + (isIncoming ? "down-left" : "up-right");

        if (transfer.status === "PENDING") {
            statusLabel = "Pending";
            badgeClass = "bg-warning-bg text-warning";
            iconColor = "text-warning";
        } else if (transfer.status === "COMPLETED") {
            statusLabel = "Completed";
            badgeClass = "bg-success-bg text-success";
            iconColor = isIncoming ? "text-success" : "text-primary";
        } else if (transfer.status === "FAILED") {
            statusLabel = "Failed";
            badgeClass = "bg-danger-bg text-danger";
            iconColor = "text-danger";
        }

        // Description
        let description = "";
        if (transfer.type === "SCHEDULED") {
            description = isIncoming ? "Scheduled Incoming Transfer" : "Scheduled Outgoing Transfer";
        } else {
            description = isIncoming ? "Received Money" : "Sent Money";
        }

        // Subtext
        const subtext = transfer.status === "COMPLETED" ? "Completed" :
            transfer.status === "FAILED" ? "Failed on " + displayDate :
                "Scheduled for " + convertToDateTimeString(transfer.registerScheduleTime);

        // Receipt Button
        const receiptDisabled = transfer.status !== "COMPLETED";
        const buttonClass = receiptDisabled ? "btn-outline-secondary" : "btn-outline-primary";

        row.innerHTML = `
            <td>${displayDate}</td>
            <td>
                <div class="d-flex align-items-center">
                    <div class="transaction-icon ${badgeClass}">
                        <i class="bi ${icon} ${iconColor}"></i>
                    </div>
                    <div>
                        <h6 class="mb-0">${description}</h6>
                        <small class="text-muted">${subtext}</small>
                    </div>
                </div>
            </td>
            <td class="${amountClass}">${amountFormatted}</td>
            <td><span class="badge ${badgeClass}">${statusLabel}</span></td>
            <td>
    <a href="http://localhost:8080/unity_national_bank/user/modernReceipt?id=${transfer.id}" 
       class="btn btn-sm ${buttonClass} ${receiptDisabled ? 'disabled' : ''}" 
       role="button" 
       ${receiptDisabled ? 'tabindex="-1" aria-disabled="true"' : ''} 
       target="_blank">
        <i class="bi bi-file-earmark-pdf"></i>
    </a>
</td>

        `;

        tbody.appendChild(row);
    });
}



function convertToDateTimeString(dateTimeObj) {

    const jsDate = new Date(
        dateTimeObj.date.year,
        dateTimeObj.date.month - 1,
        dateTimeObj.date.day,
        dateTimeObj.time.hour,
        dateTimeObj.time.minute
    );

    return jsDate.toLocaleString("en-US", {
        year: "numeric",
        month: "short",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        hour12: true
    });
}
