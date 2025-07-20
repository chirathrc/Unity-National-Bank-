document.addEventListener('DOMContentLoaded', async function() {

    await loadCustomerData();

    // Search functionality
    const searchInput = document.getElementById('customerSearch');
    const customersTable = document.getElementById('customersTable');
    const rows = customersTable.getElementsByTagName('tr');

    searchInput.addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();

        for (let i = 1; i < rows.length; i++) { // Skip header row
            const row = rows[i];
            const cells = row.getElementsByTagName('td');
            let found = false;

            for (let j = 0; j < cells.length; j++) {
                const cellText = cells[j].textContent.toLowerCase();
                if (cellText.includes(searchTerm)) {
                    found = true;
                    break;
                }
            }

            row.style.display = found ? '' : 'none';
        }
    });



    // Populate sample accounts (in real app would be dynamic)
    // const accountsList = document.querySelector('.accounts-list');
    //
    // sampleAccounts.forEach(account => {
    //     const accountEl = document.createElement('div');
    //     accountEl.className = 'account-item';
    //     accountEl.innerHTML = `
    //         <div class="account-number">${account.number}</div>
    //         <div class="account-type">${account.type}</div>
    //         <div class="account-balance">LKR ${account.balance}</div>
    //         <div class="account-status ${account.status.toLowerCase()}">${account.status}</div>
    //     `;
    //     accountsList.appendChild(accountEl);
    // });
});


async function loadCustomerData(){


    const response = await fetch("http://localhost:8080/unity_national_bank/admin/allCustomerManagement");

    if (response.ok){

        const data = await response.json();
        console.log(data);
        addToTable(data.message.users);
        document.getElementById("totalCustomers").textContent = data.message.count;
        document.getElementById("newCustomers").textContent = data.message.thisMonthRegisteredUsers;
        document.getElementById("todaysCustomers").textContent = data.message.todayRegisteredUsers;


    }else {

    }

}


function addToTable(customers){


    const tbody = document.getElementById("customersTableBody");
    tbody.innerHTML = ""; // Clear existing rows

    customers.forEach(user => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.fullName}</td>
            <td>${user.nic}</td>
            <td>+94 ${user.mobileNumber}</td>
            <td>${user.email}</td>
            <td>1</td>
            <td><span class="status-badge ${user.status.toLowerCase()}">${user.status}</span></td>
            <td>${user.regTime.year}/${user.regTime.month}/${user.regTime.day}</td>
            <td>
             <a href="../admin/single-customer-management.jsp?nic=${user.nic}" class="btn-action view" title="View" style="text-decoration: none;">
                   <i class="fas fa-eye"></i>
             </a>


<!--                <button class="btn-action edit" title="Edit">-->
<!--                    <i class="fas fa-edit"></i>-->
<!--                </button>-->
<!--                <button class="btn-action deactivate" title="Deactivate">-->
<!--                    <i class="fas fa-user-slash"></i>-->
<!--                </button>-->
            </td>
        `;

        tbody.appendChild(row);
    });
}