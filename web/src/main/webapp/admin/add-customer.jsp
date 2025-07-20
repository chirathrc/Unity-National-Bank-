<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Customer | Bank Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/adminDash.css">
    <link rel="stylesheet" href="../css/add-customer.css">
</head>
<body>
<div class="dashboard-container">

    <%@ include file="../components/sidebar.jsp" %>

    <main class="main-content">
        <header class="main-header">
            <div class="header-left">
                <h1>Register New Customer</h1>
                <div class="breadcrumbs">
                    <span>Home</span> / <span>Customers</span> / <span>Add New</span>
                </div>
            </div>
            <div class="header-right">
                <!-- ... -->
            </div>
        </header>

        <div class="form-container">
            <form id="customerRegistrationForm">
                <div class="form-section">
                    <h3><i class="fas fa-user-circle"></i> Personal Information</h3>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="fullName">Full Name *</label>
                            <input type="text" id="fullName" required>
                        </div>

                        <div class="form-group">
                            <label for="firstName">First Name *</label>
                            <input type="text" id="firstName" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name *</label>
                            <input type="text" id="lastName" required>
                        </div>


                        <div class="form-group">
                            <label for="dob">Date of Birth *</label>
                            <input type="date" id="dob" required>
                        </div>

                        <div class="form-group">
                            <label for="gender">Gender *</label>
                            <select id="gender" required>
                                <option value="">Select</option>
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="nic">NIC Number *</label>
                            <input type="text" id="nic" pattern="[0-9]{9}[VvXx]|[0-9]{12}" required>
                            <small class="hint">Format: 123456789V or 123456789012</small>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h3><i class="fas fa-map-marker-alt"></i> Address Information</h3>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="addressLine1">Address Line 1 *</label>
                            <input type="text" id="addressLine1" required>
                        </div>
                        <div class="form-group">
                            <label for="addressLine2">Address Line 2</label>
                            <input type="text" id="addressLine2">
                        </div>
                        <div class="form-group">
                            <label for="city">City *</label>
                            <input type="text" id="city" required>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label for="district">District *</label>--%>
<%--                            <select id="district" required>--%>
<%--                                <option value="">Select District</option>--%>
<%--                                <option value="colombo">Colombo</option>--%>
<%--                                <!-- Add all SL districts -->--%>
<%--                            </select>--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label for="postalCode">Postal Code *</label>
                            <input type="text" id="postalCode" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h3><i class="fas fa-phone-alt"></i> Contact Information</h3>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="mobile">Mobile Number *</label>
                            <div class="input-with-prefix">
                                <span>+94</span>
                                <input type="tel" id="mobile" pattern="[0-9]{9}" required>
                            </div>
                        </div>
<%--                        <div class="form-group">--%>
<%--                            <label for="homePhone">Home Phone</label>--%>
<%--                            <input type="tel" id="homePhone">--%>
<%--                        </div>--%>
                        <div class="form-group">
                            <label for="email">Email Address *</label>
                            <input type="email" id="email" required>
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="button" onclick="(function() {
                            window.location.href = '<%= request.getContextPath()+"/admin/" %>';
                            })();"class="btn btn-outline">Back
                    </button>
                    <button class="btn btn-primary" type="button" onclick="registerCustomer()">
                        <i class="fas fa-save"></i> Register Customer
                    </button>
                </div>
            </form>
        </div>
    </main>
</div>

<script src="../js/adminDash.js"></script>
<script src="../js/add-customer.js"></script>
</body>
</html>