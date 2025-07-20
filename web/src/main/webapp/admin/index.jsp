<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Admin Dashboard</title>
    <!-- Chart.js for analytics -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- CSS Files -->
    <link rel="stylesheet" href="../css/adminDash.css">
</head>
<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../components/sidebar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <header class="main-header">
            <div class="header-left">
                <h1>Admin Dashboard</h1>
                <div class="breadcrumbs">
                    <span>Home</span> / <span>Dashboard</span>
                </div>
            </div>
        </header>

        <!-- Dashboard Stats -->
        <section class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon bg-primary">
                    <i class="fas fa-users"></i>
                </div>
                <div class="stat-info">
                    <h3>Total Customers</h3>
                    <h2 id="totalCustomers">0</h2>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon bg-success">
                    <i class="fas fa-wallet"></i>
                </div>
                <div class="stat-info">
                    <h3>Active Accounts</h3>
                    <h2 id="activeAccounts">0</h2>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon bg-warning">
                    <i class="fas fa-piggy-bank"></i>
                </div>
                <div class="stat-info">
                    <h3>FD Accounts</h3>
                    <h2 id="fdAccounts">0</h2>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon bg-danger">
                    <i class="fas fa-money-bill-wave"></i>
                </div>
                <div class="stat-info">
                    <h3>Total Assets</h3>
                    <h2 id="totalAssets">LKR 0.00</h2>
                </div>
            </div>
        </section>

        <!-- Quick Actions -->
        <section class="quick-actions">
            <h2>Quick Actions</h2>
            <div class="actions-grid">
                <button class="action-btn" id="registerCustomerBtn"
                        onclick="window.location.href='${pageContext.request.contextPath}/admin/add-customer.jsp';">
                    <i class="fas fa-user-plus"></i>
                    <span>Register New Customer</span>
                </button>

                <button class="action-btn" id="createAccountBtn"
                        onclick="window.location.href='${pageContext.request.contextPath}/admin/create-account.jsp';">
                    <i class="fas fa-plus-circle"></i>
                    <span>Create Bank Account</span>
                </button>

                <button class="action-btn" id="generateReportBtn"
                        onclick="window.location.href='${pageContext.request.contextPath}/admin/generate-report';">
                    <i class="fas fa-file-alt"></i>
                    <span>Generate Report (All Accounts)</span>
                </button>
            </div>
        </section>
    </main>

    <!-- Modals -->
    <div class="modal" >
        <div class="modal-content">
            <div class="modal-header">
                <h3>Register New Customer</h3>
                <button class="close-modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="customerRegistrationForm">
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input type="text" id="fullName" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="tel" id="phone" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="dob">Date of Birth</label>
                            <input type="date" id="dob" required>
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender</label>
                            <select id="gender" required>
                                <option value="">Select</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <textarea id="address" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="idProof">ID Proof</label>
                        <select id="idProof" required>
                            <option value="">Select ID Proof</option>
                            <option value="passport">Passport</option>
                            <option value="driver_license">Driver's License</option>
                            <option value="national_id">National ID</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="idNumber">ID Number</label>
                        <input type="text" id="idNumber" required>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn btn-secondary close-modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Register Customer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Create Bank Account</h3>
                <button class="close-modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="accountCreationForm">
                    <div class="form-group">
                        <label for="customerSelect">Customer</label>
                        <select id="customerSelect" required>
                            <option value="">Select Customer</option>
                            <option value="1">John Doe (CUST-1001)</option>
                            <option value="2">Jane Smith (CUST-1002)</option>
                            <option value="3">Robert Johnson (CUST-1003)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="accountType">Account Type</label>
                        <select id="accountType" required>
                            <option value="">Select Account Type</option>
                            <option value="savings">Savings Account</option>
                            <option value="current">Current Account</option>
                            <option value="salary">Salary Account</option>
                            <option value="student">Student Account</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="initialDeposit">Initial Deposit ($)</label>
                        <input type="number" id="initialDeposit" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="accountCurrency">Currency</label>
                        <select id="accountCurrency" required>
                            <option value="USD">USD</option>
                            <option value="EUR">EUR</option>
                            <option value="GBP">GBP</option>
                            <option value="JPY">JPY</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="branch">Branch</label>
                        <select id="branch" required>
                            <option value="">Select Branch</option>
                            <option value="main">Main Branch</option>
                            <option value="north">North Branch</option>
                            <option value="south">South Branch</option>
                            <option value="east">East Branch</option>
                        </select>
                    </div>
                    <div class="form-actions">
                        <button type="button" class="btn btn-secondary close-modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Account</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript Files -->
<script src="../js/adminDash.js"></script>
</body>
</html>