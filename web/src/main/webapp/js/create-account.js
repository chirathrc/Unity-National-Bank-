document.addEventListener('DOMContentLoaded', function () {
    const verifyCustomerBtn = document.getElementById('verifyCustomerBtn');
    const customerNIC = document.getElementById('customerNIC');
    const verificationResult = document.getElementById('verificationResult');
    const accountDetailsSection = document.getElementById('accountDetailsSection');
    const createAccountBtn = document.getElementById('createAccountBtn');


    verifyCustomerBtn.addEventListener('click', function () {
        const nicValue = customerNIC.value.trim();

        if (!nicValue) {
            showNotification('error', 'Please enter a NIC number');
            return;
        }

        // Validate NIC format
        const nicRegex = /^([0-9]{9}[VvXx]|[0-9]{12})$/;
        if (!nicRegex.test(nicValue)) {
            showNotification('error', 'Please enter a valid NIC number (Format: 123456789V or 123456789012)');
            return;
        }

        // Simulate API call
        verifyCustomerBtn.disabled = true;
        verifyCustomerBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Verifying...';

        setTimeout(async () => {
            verifyCustomerBtn.disabled = false;
            verifyCustomerBtn.innerHTML = '<i class="fas fa-search"></i> Verify';


            const response = await fetch("http://localhost:8080/unity_national_bank/admin/getUser?nic=" + nicValue);

            if (response.ok) {

                const resData = await response.json();
                console.log(resData)

                if (resData.success) {

                    const customer = resData.message;
                    verificationResult.innerHTML = `
                    <div class="customer-details active">
                        <div class="customer-info">
                            <div class="customer-avatar">
                                <i class="fas fa-user"></i>
                            </div>
                            <div class="customer-text">
                                <h4>${customer.fullName}</h4>
                                <p>${customer.username}</p>
                                <div class="customer-meta">
                                    <span><i class="fas fa-id-card"></i> ${customer.nic}</span>
                                    <span><i class="fas fa-phone"></i>0${customer.mobileNumber}</span>
                                </div>
                            </div>
                        </div>
                    </div>
               <div class="customer-accounts">
            <p><strong>Existing Accounts:</strong> 
                ${customer.accounts.map(acc => `${acc.accountType} (${acc.accountNumber})`).join(', ')}
            </p>
        </div>
  
                `;
                    accountDetailsSection.style.display = 'block';
                    createAccountBtn.disabled = false;
                    showNotification('success', 'Customer verified successfully!');

                } else {

                    verificationResult.innerHTML = `
                    <div class="error-message">
                        <i class="fas fa-exclamation-triangle"></i>
                        <span>Customer not found. Please register the customer first.</span>
                    </div>
                `;
                    accountDetailsSection.style.display = 'none';
                    createAccountBtn.disabled = true;
                    showNotification('error', 'Customer not found in our system');

                }

            }

        }, 1500);
    });


    // // Form submission
    // document.getElementById('accountCreationForm').addEventListener('submit', function (e) {
    //     e.preventDefault();
    //
    //     showNotification('error', 'Please verify customer NIC first');
    //
    //     if (customerNIC.value == null){
    //         showNotification('error', 'Please verify customer NIC first');
    //     }
    //
    //     if (!customerNIC.value) {
    //         showNotification('error', 'Please verify customer NIC first');
    //         return;
    //     }
    //
    //     const accountType = document.getElementById('accountType').value;
    //     if (!accountType) {
    //         showNotification('error', 'Please select an account type');
    //         return;
    //     }
    //
    //     const initialDeposit = parseFloat(document.getElementById('initialDeposit').value);
    //     if (isNaN(initialDeposit)) {
    //         showNotification('error', 'Please enter a valid initial deposit amount');
    //         return;
    //     }
    //
    //     // Prepare form data
    //     const formData = {
    //         customerNIC: customerNIC.value,
    //         accountType: accountType,
    //         accountPlan: document.getElementById('accountPlan').value,
    //         branch: document.getElementById('branch').value,
    //         currency: document.getElementById('currency').value,
    //         initialDeposit: initialDeposit,
    //         features: Array.from(document.querySelectorAll('input[name="features"]:checked')).map(el => el.value)
    //     };
    //
    //     // Simulate API call
    //     createAccountBtn.disabled = true;
    //     createAccountBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
    //
    //     setTimeout(() => {
    //         createAccountBtn.disabled = false;
    //         createAccountBtn.innerHTML = '<i class="fas fa-plus-circle"></i> Create Account';
    //
    //         // Generate account number
    //         const accountNumber = document.getElementById('accountNumberPreview').textContent;
    //
    //         showNotification('success', `Account ${accountNumber} created successfully!`);
    //
    //         // Reset form
    //         this.reset();
    //         verificationResult.innerHTML = `
    //             <div class="default-message">
    //                 <i class="fas fa-info-circle"></i>
    //                 <span>Enter customer NIC and click verify to load details</span>
    //             </div>
    //         `;
    //         accountDetailsSection.style.display = 'none';
    //
    //         // Clear account number preview
    //         document.getElementById('accountNumberPreview').textContent = '';
    //     }, 2000);
    // });
});


async function registerBankAccount() {

    const createAccountBtn = document.getElementById('createAccountBtn');
    const verificationResult = document.getElementById('verificationResult');
    const accountDetailsSection = document.getElementById('accountDetailsSection');
    const form = document.getElementById('accountCreationForm');

    const customerNIC = document.getElementById('customerNIC');
    if (!customerNIC.value) {
        showNotification('error', 'Please verify customer NIC first');
        return;
    }

    const accountType = document.getElementById('accountType').value;
    if (!accountType) {
        showNotification('error', 'Please select an account type');
        return;
    }

    const branch = document.getElementById('branch').value;
    if (!branch) {
        showNotification('error', 'Please select a Branch for Account');
        return;
    }

    const initialDeposit = parseFloat(document.getElementById('initialDeposit').value);
    if (isNaN(initialDeposit)) {
        showNotification('error', 'Please enter a valid initial deposit amount');
        return;
    }

    if (initialDeposit < 5000) {
        showNotification('error', 'Minimum initial deposit is Rs.5000');
        return;
    }


    const data = {
        customerNIC: customerNIC.value,
        accountType: accountType,
        branch: document.getElementById('branch').value,
        initialDeposit: initialDeposit,
    };

    // Simulate API call
    createAccountBtn.disabled = true;
    createAccountBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';

    let account;

    const response = await fetch("http://localhost:8080/unity_national_bank/admin/account_create", {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        const resData = await response.json();

        console.log(resData);

        if (resData.success) {

            account = resData.message;

            setTimeout(() => {
                createAccountBtn.disabled = false;
                createAccountBtn.innerHTML = '<i class="fas fa-plus-circle"></i> Create Account';

                showNotification('success', `Account ${account} created successfully!`);

                form.reset();
                verificationResult.innerHTML = `
            <div class="default-message">
                <i class="fas fa-info-circle"></i>
                <span>Enter customer NIC and click verify to load details</span>
            </div>
        `;
                accountDetailsSection.style.display = 'none';
                document.getElementById('accountNumberPreview').textContent = '';

            }, 2000);

        } else {

            showNotification("error", resData.message);
            createAccountBtn.disabled = false;
            createAccountBtn.innerHTML = '<i class="fas fa-plus-circle"></i> Create Account';

        }

    } else {

        showNotification("error", "Something went wrong in our System");
        createAccountBtn.disabled = false;
        createAccountBtn.innerHTML = '<i class="fas fa-plus-circle"></i> Create Account';
        accountDetailsSection.style.display = 'none';
        document.getElementById('accountNumberPreview').textContent = '';
    }


}
