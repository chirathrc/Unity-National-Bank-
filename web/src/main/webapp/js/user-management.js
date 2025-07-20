document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const customerNIC = urlParams.get('nic');
    console.log("Customer NIC:", customerNIC);

    if (customerNIC) {
        await loadCustomer(customerNIC);
    } else {
        console.error("NIC not provided in URL.");
        return;
    }

    // Form & Button elements
    const editPersonalBtn = document.getElementById('editPersonalBtn');
    const editContactBtn = document.getElementById('editContactBtn');
    const personalForm = document.getElementById('personalDetailsForm');
    const contactForm = document.getElementById('contactDetailsForm');

    // Toggle personal details editing
    editPersonalBtn?.addEventListener('click', function () {
        const inputs = personalForm.querySelectorAll('input, select');
        const isEditing = inputs[0]?.disabled;

        inputs.forEach(input => input.disabled = !isEditing);
        personalForm.querySelector('.form-actions').style.display = isEditing ? 'flex' : 'none';
        editPersonalBtn.textContent = isEditing ? 'Cancel' : 'Edit';
    });

    // Toggle contact details editing
    editContactBtn?.addEventListener('click', function () {
        const inputs = contactForm.querySelectorAll('input, select');
        const isEditing = inputs[0]?.disabled;

        inputs.forEach(input => input.disabled = !isEditing);
        contactForm.querySelector('.form-actions').style.display = isEditing ? 'flex' : 'none';
        editContactBtn.textContent = isEditing ? 'Cancel' : 'Edit';
    });

    // Cancel buttons
    personalForm.querySelector('.cancel-edit')?.addEventListener('click', function () {
        personalForm.querySelectorAll('input, select').forEach(input => input.disabled = true);
        personalForm.querySelector('.form-actions').style.display = 'none';
        editPersonalBtn.textContent = 'Edit';
    });

    contactForm.querySelector('.cancel-edit')?.addEventListener('click', function () {
        contactForm.querySelectorAll('input, select').forEach(input => input.disabled = true);
        contactForm.querySelector('.form-actions').style.display = 'none';
        editContactBtn.textContent = 'Edit';
    });

    // Submit handlers
    personalForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        await savePersonalDetails();
        personalForm.querySelector('.form-actions').style.display = 'none';
        editPersonalBtn.textContent = 'Edit';
        personalForm.querySelectorAll('input, select').forEach(input => input.disabled = true);
    });

    contactForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        await saveContactDetails();
        contactForm.querySelector('.form-actions').style.display = 'none';
        editContactBtn.textContent = 'Edit';
        contactForm.querySelectorAll('input, select').forEach(input => input.disabled = true);
    });

    const userStatus = document.getElementById('userStatus');
    const statusBadge = document.getElementById('userStatusShow'); // ✅ update to specific badge ID

    userStatus?.addEventListener('change', async function () {
        const newStatus = this.value.toUpperCase(); // Ensures it matches enum

        // Remove previous status classes
        statusBadge?.classList.remove('active', 'inactive');

        // Add new status class (lowercase for CSS class)
        statusBadge?.classList.add(newStatus.toLowerCase());

        // Update text
        statusBadge.textContent = capitalize(newStatus);

        const nic = document.getElementById("userNic").textContent;

        console.log(nic + userStatus.value);

        const response = await fetch(`http://localhost:8080/unity_national_bank/admin/updateUser?nic=${nic}&status=${userStatus.value}`);

        if (response.ok) {

            const resData = await response.json();

            if (resData.success) {
                alert(`User status changed to ${newStatus}`);
            } else {

            }
        } else {

        }
    });


    // Suspend user
    const suspendUserBtn = document.getElementById('suspendUserBtn');
    suspendUserBtn?.addEventListener('click', function () {
        if (confirm('Are you sure you want to suspend this user?')) {
            userStatus.value = 'suspended';
            statusBadge?.classList.remove('active', 'pending');
            statusBadge?.classList.add('suspended');
            statusBadge.textContent = 'Suspended';
            alert('User has been suspended');
        }
    });
});

async function loadCustomer(nic) {
    try {
        const response = await fetch(`http://localhost:8080/unity_national_bank/admin/getUser?nic=${nic}`);

        if (!response.ok) {
            throw new Error("Failed to fetch user data");
        }

        const resData = await response.json();
        console.log("Response:", resData);

        if (!resData.success) {
            console.warn("User not found or server returned failure.");
            return;
        }

        const user = resData.message;

        // Format date
        const day = String(user.dateOfBirth.day).padStart(2, '0');
        const month = String(user.dateOfBirth.month).padStart(2, '0');
        const year = user.dateOfBirth.year;
        const formattedDate = `${year}-${month}-${day}`;

        // Fill fields
        document.getElementById("userFullName").textContent = user.fullName;
        document.getElementById("userNic").textContent = user.nic;
        document.getElementById("userPhone").textContent = "+94 " + user.mobileNumber;
        document.getElementById("userEmail").textContent = user.email;
        document.getElementById("userStatus").value = user.status;
        const statusBadge = document.getElementById("userStatusShow");
        statusBadge.classList.remove("active", "inactive");
        statusBadge.classList.add(user.status.toLowerCase());
        statusBadge.textContent = user.status.charAt(0).toUpperCase() + user.status.slice(1).toLowerCase();
        document.getElementById("userHeader").textContent = user.fullName;


        document.getElementById("firstName").value = user.firstName;
        document.getElementById("lastName").value = user.lastName;
        document.getElementById("dob").value = formattedDate;
        document.getElementById("gender").value = user.gender.toLowerCase();
        document.getElementById("fullName").value = user.fullName;

        document.getElementById("mobile").value = user.mobileNumber;
        document.getElementById("email").value = user.email;
        document.getElementById("address1").value = user.addressLineOne;
        document.getElementById("address2").value = user.addressLineTwo;
        document.getElementById("city").value = user.City; // case-sensitive!
        document.getElementById("postal").value = user.postalCode;


        // Render accounts
        const accountsList = document.getElementById("accounts-list");
        if (!accountsList) {
            console.warn("Element with ID 'accounts-list' not found.");
            return;
        }

        accountsList.innerHTML = ''; // clear existing

        user.accounts?.forEach(account => {
            const accountItem = document.createElement("div");
            accountItem.className = "account-item";

            accountItem.innerHTML = `
                <div class="account-info">
                    <span class="account-number">${account.accountNumber}</span>
                    <span class="account-type">${account.accountType}</span>
                    <span class="account-balance">LKR ${Number(account.balance).toLocaleString('en-LK', {minimumFractionDigits: 2})}</span>
                </div>
                <div class="account-status">
                    <span class="status-badge ${account.status.toLowerCase()}">${capitalize(account.status)}</span>
                </div>
            `;
            accountsList.appendChild(accountItem);
        });

    } catch (error) {
        console.error("Error loading customer:", error);
        alert("Error loading customer data.");
    }
}

function capitalize(str) {
    return str ? str.charAt(0).toUpperCase() + str.slice(1).toLowerCase() : '';
}

async function savePersonalDetails() {
    const user = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        dateOfBirth: document.getElementById("dob").value,
        gender: document.getElementById("gender").value,
        fullName: document.getElementById("fullName").value,
        nic: document.getElementById("userNic").textContent.trim(),
        detailsCategory: 1
    };

    console.log(JSON.stringify(user));

    try {
        const response = await fetch("http://localhost:8080/unity_national_bank/admin/updateUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            const resData = await response.json();

            if (resData.success) {
                alert("User updated successfully.");
            } else {
                alert("Update failed.");
            }
        } else {
            alert("Update failed.");
            window.location.reload();
        }
    } catch (error) {
        console.error("Error while updating user:", error);
        alert("An unexpected error occurred.");
    }
}


async function saveContactDetails() {
    const contactDetails = {
        mobileNumber: document.getElementById("mobile").value.trim(),
        email: document.getElementById("email").value.trim(),
        addressLine1: document.getElementById("address1").value.trim(),
        addressLine2: document.getElementById("address2").value.trim(),
        city: document.getElementById("city").value.trim(),
        postalCode: document.getElementById("postal").value.trim(),
        nic: document.getElementById("userNic").textContent.trim(),
        detailsCategory: 2
    };


    try {
        const response = await fetch("http://localhost:8080/unity_national_bank/admin/updateUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(contactDetails)
        });

        if (response.ok) {
            const resData = await response.json();

            if (resData.success) {
                alert("User Contact details updated successfully.");
            } else {
                alert("Update failed.");
            }
        } else {
            alert("Update failed.");
            window.location.reload();
        }
    } catch (error) {
        console.error("Error while updating user:", error);
        alert("An unexpected error occurred.");
    }
}