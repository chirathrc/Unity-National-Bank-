let value = 0;
let accountUser;
let fromAccount;
let toAccount;
let dateTrans;

document.addEventListener('DOMContentLoaded', async function () {

    console.log("Calling loadAccounts...");
    // await loadAccounts();

    const transferForm = document.getElementById('transferForm');
    const accountNumberInput = document.getElementById('accountNumber');
    const verifyAccountBtn = document.getElementById('verifyAccountBtn');
    const verifyBtnText = document.getElementById('verifyBtnText');
    const verifySpinner = document.getElementById('verifySpinner');
    const verifiedAccount = document.getElementById('verifiedAccount');
    const verifiedAccountName = document.getElementById('verifiedAccountName');
    const verifiedAccountNumber = document.getElementById('verifiedAccountNumber');
    const changeAccountBtn = document.getElementById('changeAccountBtn');
    const submitTransferBtn = document.getElementById('submitTransferBtn');
    const transferAmountInput = document.getElementById('transferAmount');

    // Set minimum date for scheduling (tomorrow)
    // const today = new Date();
    // const tomorrow = new Date(today);
    // tomorrow.setDate(tomorrow.getDate() + 1);
    // const minDate = tomorrow.toISOString().split('T')[0];
    // document.getElementById('transferDate').min = minDate;

    const today = new Date();
    const minDate = today.toISOString().split('T')[0];
    document.getElementById('transferDate').min = minDate;


    // Toggle schedule options
    document.querySelectorAll('input[name="transferTiming"]').forEach(radio => {
        radio.addEventListener('change', function () {
            const scheduleOptions = document.querySelector('.schedule-options');
            scheduleOptions.style.display = this.value === 'schedule' ? 'block' : 'none';
            updateSummary();
        });
    });

    // Account verification handler
    verifyAccountBtn.addEventListener('click', function () {
        const accountNumber = accountNumberInput.value.trim();

        if (!accountNumber || accountNumber.length !== 12) {
            showAlert('Please enter a valid 12-digit account number', 'danger');
            return;
        }

        // Show loading state
        verifyBtnText.textContent = 'Verifying...';
        verifySpinner.classList.remove('d-none');
        verifyAccountBtn.disabled = true;

        // Simulate API call with timeout
        setTimeout(() => {
            verifyAccount(accountNumber);

            // Hide loading state
            verifyBtnText.textContent = 'Verified';
            verifySpinner.classList.add('d-none');
            verifyAccountBtn.disabled = false;
        }, 1500);
    });

    // Change account button
    changeAccountBtn.addEventListener('click', function () {
        verifiedAccount.classList.add('d-none');
        accountNumberInput.value = '';
        accountNumberInput.focus();
        verifyBtnText.textContent = 'Verify';
        submitTransferBtn.disabled = true;
        accountNumberInput.disabled = false;
        verifyAccountBtn.disabled = false;
        transferAmountInput.disabled = false;
        updateSummary();
    });

    // Form submission
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));

    transferForm.addEventListener('submit', function (e) {
        e.preventDefault();

        // Validate amount
        const amount = parseFloat(transferAmountInput.value);
        if (amount < 1000) {
            showAlert('Transfer amount exceeds maximum per transaction limit of LKR. 1,000', 'danger');
            return;
        }

        if (amount <= 0 || isNaN(amount)) {
            showAlert('Please enter a valid transfer amount', 'danger');
            return;
        }

        // if (amount > value) {
        //     showAlert('Please enter a valid transfer amount', 'danger');
        //     return;
        // }

        if (dateTrans === 2){
            showAlert('Please enter a valid date for Schedule', 'danger');
            return;
        }

        // Set confirmation details
        document.getElementById('confirmFrom').textContent = accountUser;
        document.getElementById('confirmTo').textContent = verifiedAccountName.textContent + ' (' + verifiedAccountNumber.textContent + ')';
        document.getElementById('confirmAmount').textContent = `LKR. ${amount.toFixed(2)}`;
        document.getElementById('confirmWhen').textContent = getTransferTiming();
        document.getElementById('confirmReference').textContent = document.getElementById('transferReference').value || '-';

        // Show confirmation modal
        confirmationModal.show();
    });

    // Confirm transfer button in modal
    document.getElementById('confirmTransferBtn').addEventListener('click', async function () {
        const amount = parseFloat(transferAmountInput.value);
        const recipient = verifiedAccountName.textContent;
        const timing = getTransferTiming();

        // console.log('Transfer submitted:', {
        //     from: 'Primary Account (•••• 4567)',
        //     to: recipient,
        //     amount: amount,
        //     timing: timing,
        //     reference: document.getElementById('transferReference').value
        // });

        const data = {
            // from: fromAccount,
            to: toAccount,
            amount: amount,
            timing : dateTrans,
            reference: document.getElementById('transferReference').value
        };

        console.log(data);

        const response  = await fetch("http://localhost:8080/unity_national_bank/user/transfer",{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok){

            // Show success message
            confirmationModal.hide();
            showAlert(`Success! Your transfer of $${amount.toFixed(2)} to ${recipient} has been submitted.`, 'success');

            // Reset form
            transferForm.reset();
            document.querySelector('.schedule-options').style.display = 'none';
            verifiedAccount.classList.add('d-none');
            submitTransferBtn.disabled = true;
            accountNumberInput.disabled = false;
            verifyAccountBtn.disabled = false;
            updateSummary();

        }else{

            showAlert('Transfer unsuccessful, Try Again Later', 'danger');

        }
    });

    // Real-time validation and summary updates
    transferAmountInput.addEventListener('input', function () {
        const amount = parseFloat(this.value) || 0;
        if (amount < 1000) {
            this.classList.add('is-invalid');
        }else {
            this.classList.remove('is-invalid');
        }

        // if (amount < 1000) {
        //     this.classList.add('is-invalid');
        // } else if (amount > value) {
        //     this.classList.add('is-invalid');
        //     showAlert('Please enter a valid transfer amount', 'danger');
        // } else {
        //     this.classList.remove('is-invalid');
        // }
        updateSummary();
    });

    document.getElementById('transferDate').addEventListener('change', updateSummary);
    document.getElementById('transferTime').addEventListener('change', updateSummary);

    // Helper functions
    async function verifyAccount(accountNumber) {
        try {
            const response = await fetch(`http://localhost:8080/unity_national_bank/user/getUserForTransfer?accountNo=${accountNumber}`);

            if (!response.ok) {
                showAlert("Server error while loading account.", "danger");
                return;
            }

            const resData = await response.json();

            if (resData.success && resData.message) {
                const UserName = resData.message;

                verifiedAccountName.textContent = UserName;
                verifiedAccountNumber.textContent = accountNumber;
                verifiedAccount.classList.remove('d-none');
                accountNumberInput.disabled = true;
                verifyAccountBtn.disabled = true;
                submitTransferBtn.disabled = false;
                transferAmountInput.disabled = false;
                updateSummary();
                toAccount = accountNumber;

            } else {
                showAlert('Account not found. Please check the account number and try again.', 'danger');
                accountNumberInput.focus();
            }

        } catch (err) {
            console.error(err);
            showAlert("Error loading account info. Check connection.", "danger");
        }


    }

    function updateSummary() {
        const amount = transferAmountInput.value || '0.00';
        const formattedAmount = parseFloat(amount).toFixed(2);

        document.getElementById('summaryRecipient').textContent =
            verifiedAccount.classList.contains('d-none') ?
                'Not selected' :
                `${verifiedAccountName.textContent} (${verifiedAccountNumber.textContent})`;

        document.getElementById('summaryAmount').textContent = `LKR. ${formattedAmount}`;
        document.getElementById('summaryWhen').textContent = getTransferTiming();
        document.getElementById('summaryTotal').textContent = `LKR. ${formattedAmount}`;
    }

    function getTransferTiming() {
        const timing = document.querySelector('input[name="transferTiming"]:checked').value;
        if (timing === 'now'){
            dateTrans = 1;
            return 'Now (Instant)';
        }

        const date = document.getElementById('transferDate').value;
        const time = document.getElementById('transferTime').value;
        if (!date){
            dateTrans = 2;
            return 'Scheduled (Date not set)';
        }

        const formattedDate = new Date(`${date}T${time}`).toLocaleString('en-US', {
            weekday: 'short',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
        dateTrans = formattedDate;
        return `Scheduled for ${formattedDate}`;
    }
});

function showAlert(message, type) {
    // Remove any existing alerts
    const existingAlert = document.querySelector('.alert-toast');
    if (existingAlert) existingAlert.remove();

    // Create alert element
    const alert = document.createElement('div');
    alert.className = `alert-toast alert alert-${type} fixed-top mx-auto mt-3`;
    alert.style.maxWidth = '500px';
    alert.style.zIndex = '1100';
    alert.style.width = '90%';
    alert.role = 'alert';
    alert.innerHTML = `
            <div class="d-flex align-items-center">
                <i class="bi ${type === 'danger' ? 'bi-exclamation-triangle-fill' : 'bi-check-circle-fill'} me-2"></i>
                <span>${message}</span>
                <button type="button" class="btn-close ms-auto" data-bs-dismiss="alert"></button>
            </div>
        `;

    // Add to DOM
    document.body.appendChild(alert);

    // Auto-dismiss after 5 seconds
    setTimeout(() => {
        alert.classList.add('fade');
        setTimeout(() => alert.remove(), 150);
    }, 5000);

    // Enable Bootstrap dismiss
    new bootstrap.Alert(alert);
}


// async function loadAccounts() {
//     console.log("loadAccounts() called");
//
//     try {
//         const response = await fetch("http://localhost:8080/unity_national_bank/user/getSavings");
//
//         if (!response.ok) {
//             showAlert("Server error while loading account.", "danger");
//             return;
//         }
//
//         const resData = await response.json();
//         console.log(resData);
//
//         if (resData.success && resData.message) {
//             const account = resData.message;
//
//             const last4 = account.accountNumber.slice(-4);
//
//             document.getElementById("accountType").textContent = account.accountType;
//             document.getElementById("accountInfo").textContent =
//                 `Balance: ${formatLKR(account.balance)} **** ${last4}`;
//
//             document.getElementById("availableBalance").textContent = "Available: " + formatLKR(account.balance);
//             value = account.balance;
//             accountUser = "**** " + last4;
//             fromAccount = account.accountNumber;
//         } else {
//             showAlert("Account data not found.", "warning");
//         }
//
//     } catch (err) {
//         console.error(err);
//         showAlert("Error loading account info. Check connection.", "danger");
//     }
// }


function formatLKR(amount) {
    return "LKR " + amount.toLocaleString("en-LK", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}