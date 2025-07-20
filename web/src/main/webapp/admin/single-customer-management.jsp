<%@ page import="java.util.List" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.codebridge.app.core.service.BankAccountService" %>
<%@ page import="javax.naming.NamingException" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management | Bank Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/adminDash.css">
    <link rel="stylesheet" href="../css/user-management.css">
</head>

<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../components/sidebar.jsp" %>

    <main class="main-content">
        <header class="main-header">
            <div class="header-left">
                <h1>User Management</h1>
                <div class="breadcrumbs">
                    <a href="manage-customers.html">Customers</a> / <span id="userHeader">John Doe</span>
                </div>
            </div>
            <div class="header-right">
                <button class="sidebar-toggle mobile-only">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
        </header>

        <div class="user-management-container">
            <div class="user-profile-header">
                <div class="profile-photo">
                    <img src="../assets/boy.png" alt="User Photo" id="userAvatar">
                </div>
                <div class="profile-info">
                    <h1 id="userFullName">John Doe</h1>
                    <div class="user-meta">
                        <div class="meta-item">
                            <span id="userNic">901234567V</span>
                        </div>
                        <div class="meta-item">
                            <span id="userPhone">+94 77 123 4567</span>
                        </div>
                        <div class="meta-item">
                            <span id="userEmail">john.doe@example.com</span>
                        </div>
                    </div>


                    <%

                        List<String> status;

                        InitialContext initialContext = new InitialContext();
                        try {
                            BankAccountService bankAccountService = (BankAccountService) initialContext.lookup("java:global/unity_national_bank_ear/accounts-module/BankAccountSessionBean!lk.codebridge.app.core.service.BankAccountService");

                            status = bankAccountService.getStatus();

                        } catch (NamingException e) {
                            throw new RuntimeException(e);
                        }
                    %>

                    <div class="status-container">
                        <span class="status-badge" id="userStatusShow"></span>
                        <select id="userStatus" class="status-select">
                            <%--              <option value="active">Active</option>--%>
                            <%--              <option value="suspended" selected>Suspended</option>--%>
                            <%--              <option value="pending">Pending</option>--%>
                            <%
                                for (String statusCustomer : status) {

                            %>
                            <option value="<%= statusCustomer %>"><%= statusCustomer %>
                            </option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                </div>
            </div>
            <div class="user-details-grid">
                <!-- Personal Details Section -->
                <div class="details-section">
                    <div class="section-header">
                        <h3><i class="fas fa-user-circle"></i> Personal Details</h3>
                        <button class="btn-edit" id="editPersonalBtn">Edit</button>
                    </div>
                    <form id="personalDetailsForm">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" id="fullName" value="John Doe" disabled>
                        </div>
                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" id="firstName" value="John" disabled>
                        </div>
                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" id="lastName" value="Doe" disabled>
                        </div>
                        <div class="form-group">
                            <label>Date of Birth</label>
                            <input type="date" id="dob" value="1985-05-15" disabled>
                        </div>
                        <div class="form-group">
                            <label>Gender</label>
                            <select id="gender" disabled>
                                <option value="male" selected>Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                        <div class="form-actions" style="display:none;">
                            <button type="button" class="btn btn-outline cancel-edit">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>

                <!-- Contact Details Section -->
                <div class="details-section">
                    <div class="section-header">
                        <h3><i class="fas fa-address-book"></i> Contact Details</h3>
                        <button class="btn-edit" id="editContactBtn">Edit</button>
                    </div>
                    <form id="contactDetailsForm">
                        <div class="form-group">
                            <label>Mobile Number</label>
                            <div class="input-with-prefix">
                                <span>+94</span>
                                <input type="tel" id="mobile" value="77 123 4567" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Email Address</label>
                            <input type="email" id="email" value="john.doe@example.com" disabled>
                        </div>
                        <div class="form-group">
                            <label>Address Line 1</label>
                            <input type="text" id="address1" value="123 Main Street" disabled>
                        </div>
                        <div class="form-group">
                            <label>Address Line 2</label>
                            <input type="text" id="address2" value="Colombo 05" disabled>
                        </div>
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" id="city" value="Colombo" disabled>
                        </div>
                        <div class="form-group">
                            <label>Postal Code</label>
                            <input type="text" id="postal" value="123456" disabled>
                        </div>
                        <div class="form-actions" style="display:none;">
                            <button type="button" class="btn btn-outline cancel-edit">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>

                <!-- NIC Verification Section -->

                <!-- Bank Accounts Section -->
                <div class="details-section">
                    <div class="section-header">
                        <h3><i class="fas fa-wallet"></i> Bank Accounts</h3>
                    </div>
                    <div class="accounts-list" id="accounts-list">
                        <%--            <div class="account-item">--%>
                        <%--              <div class="account-info">--%>
                        <%--                <span class="account-number">AC10024578</span>--%>
                        <%--                <span class="account-type">Savings Account</span>--%>
                        <%--                <span class="account-balance">LKR 125,450.00</span>--%>
                        <%--              </div>--%>
                        <%--              <div class="account-status">--%>
                        <%--                <span class="status-badge active">Active</span>--%>
                        <%--                <a href="account-details.html?account=AC10024578" class="btn-view">View</a>--%>
                        <%--              </div>--%>
                        <%--            </div>--%>
                        <%--            <div class="account-item">--%>
                        <%--              <div class="account-info">--%>
                        <%--                <span class="account-number">FD10034567</span>--%>
                        <%--                <span class="account-type">Fixed Deposit</span>--%>
                        <%--                <span class="account-balance">LKR 500,000.00</span>--%>
                        <%--              </div>--%>
                        <%--              <div class="account-status">--%>
                        <%--                <span class="status-badge active">Active</span>--%>
                        <%--                <a href="account-details.html?account=FD10034567" class="btn-view">View</a>--%>
                        <%--              </div>--%>
                        <%--            </div>--%>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script src="../js/adminDash.js"></script>
<script src="../js/user-management.js"></script>
</body>

</html>