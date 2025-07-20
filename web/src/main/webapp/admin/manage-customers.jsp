<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Customers | Bank Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/adminDash.css">
    <link rel="stylesheet" href="../css/manage-customers.css">
</head>

<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../components/sidebar.jsp" %>

    <main class="main-content">
        <header class="main-header">
            <div class="header-left">
                <h1>Manage Customers</h1>
                <div class="breadcrumbs">
                    <span>Home</span> / <span>Customers</span> / <span>Manage</span>
                </div>
            </div>
            <div class="header-right">
                <div class="search-box">
                    <input type="text" id="customerSearch" placeholder="Search customers...">
                    <i class="fas fa-search"></i>
                </div>

                <!--responsive sidebar toggle button-->
                <button class="sidebar-toggle mobile-only">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
        </header>

        <div class="customers-container">
            <div class="customers-header">
                <div class="customers-summary">
                    <div class="summary-card">
                        <i class="fas fa-users"></i>
                        <div>
                            <h3>Total Customers</h3>
                            <span id="totalCustomers">856</span>
                        </div>
                    </div>
                    <div class="summary-card">
                        <i class="fas fa-user-plus"></i>
                        <div>
                            <h3>New This Month</h3>
                            <span id="newCustomers">42</span>
                        </div>
                    </div>
                    <div class="summary-card">
                        <i class="fas fa-calendar-day"></i>
                        <div>
                            <h3>Today's Registrations</h3>
                            <span id="todaysCustomers">5</span>
                        </div>
                    </div>
                </div>
                <div class="customers-actions">
                    <a href="add-customer.jsp" class="btn btn-primary">
                        <i class="fas fa-user-plus"></i> Add New Customer
                    </a>
                </div>
            </div>

            <div class="customers-table-container">
                <table id="customersTable">
                    <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>Full Name</th>
                        <th>NIC</th>
                        <th>Mobile</th>
                        <th>Email</th>
                        <th>Accounts</th>
                        <th>Status</th>
                        <th>Registered Date</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody id="customersTableBody">
                    <!-- Sample data - in real app this would come from backend -->
                    <tr>
                        <td>CUS10001</td>
                        <td>John Doe</td>
                        <td>901234567V</td>
                        <td>+94 77 123 4567</td>
                        <td>john.doe@example.com</td>
                        <td>3</td>
                        <td><span class="status-badge active">Active</span></td>
                        <td>2023-05-15</td>
                        <td>
                            <button class="btn-action view" title="View">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-action edit" title="Edit">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-action deactivate" title="Deactivate">
                                <i class="fas fa-user-slash"></i>
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>CUS10002</td>
                        <td>Jane Smith</td>
                        <td>901234568V</td>
                        <td>+94 76 234 5678</td>
                        <td>jane.smith@example.com</td>
                        <td>2</td>
                        <td><span class="status-badge active">Active</span></td>
                        <td>2023-06-20</td>
                        <td>
                            <button class="btn-action view" title="View">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-action edit" title="Edit">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-action deactivate" title="Deactivate">
                                <i class="fas fa-user-slash"></i>
                            </button>
                        </td>
                    </tr>
                    <!-- More rows would be here -->
                    </tbody>
                </table>
            </div>

        </div>
    </main>
</div>

<script src="../js/adminDash.js"></script>
<script src="../js/manage-customers.js"></script>
</body>

</html>