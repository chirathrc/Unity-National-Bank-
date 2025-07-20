<%@ page import="javax.naming.InitialContext" %>
<%@ page import="lk.codebridge.app.core.service.BankAccountService" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account | Bank Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="../css/adminDash.css">
    <link rel="stylesheet" href="../css/create-account.css">
</head>
<body>
<div class="dashboard-container">

    <!-- Sidebar Navigation -->
    <%@ include file="../components/sidebar.jsp" %>


    <main class="main-content">
        <header class="main-header">
            <div class="header-left">
                <h1>Create New Bank Account</h1>
                <div class="breadcrumbs">
                    <span>Home</span> / <span>Accounts</span> / <span>Create New</span>
                </div>
            </div>
        </header>

        <div class="form-container">
            <form id="accountCreationForm">
                <div class="form-section">
                    <h3><i class="fas fa-user-check"></i> Customer Verification</h3>
                    <div class="verification-box">
                        <div class="input-with-button">
                            <input type="text" id="customerNIC" placeholder="Enter Customer NIC"
                                   pattern="[0-9]{9}[VvXx]|[0-9]{12}">
                            <button type="button" id="verifyCustomerBtn" class="btn btn-primary">
                                <i class="fas fa-search"></i> Verify
                            </button>
                        </div>
                        <div class="verification-result" id="verificationResult">
                            <div class="default-message">
                                <i class="fas fa-info-circle"></i>
                                <span>Enter customer NIC and click verify to load details</span>
                            </div>
                        </div>
                    </div>
                </div>

                <%

                    List<String> accountList;
                    List<String> branchesList;

                    InitialContext initialContext = new InitialContext();
                    try {
                        BankAccountService bankAccountService = (BankAccountService) initialContext.lookup("java:global/unity_national_bank_ear/accounts-module/BankAccountSessionBean!lk.codebridge.app.core.service.BankAccountService");

                        accountList = bankAccountService.getAccountTypes();
                        branchesList = bankAccountService.getBranches();
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }
                %>

                <div class="form-section" id="accountDetailsSection" style="display: none;">
                    <h3><i class="fas fa-wallet"></i> Account Details</h3>
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="accountType">Account Type *</label>
                            <select id="accountType" required>
                                <option value="">Select Account Type</option>

                                <%
                                    for (String type : accountList) {

                                %>
                                <option value="<%= type %>"><%= type %>
                                </option>
                                <%
                                    }
                                %>

                            </select>
                        </div>


                        <div class="form-group">
                            <label for="branch">Branch *</label>
                            <select id="branch" required>
                                <option value="">Select Branch</option>

                                <%
                                    for (String branch : branchesList) {

                                %>
                                <option value="<%= branch %>"><%= branch %>
                                </option>
                                <%
                                    }
                                %>

                            </select>
                        </div>

                        <div class="form-group">
                            <label for="initialDeposit">Initial Deposit (LKR) *</label>
                            <div class="input-with-symbol">
                                <span>Rs.</span>
                                <input type="number" id="initialDeposit" min="0" step="100" required>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="form-actions">
                    <button type="button" class="btn btn-outline">Cancel</button>
                    <button id="createAccountBtn" type="button" class="btn btn-primary" onclick="registerBankAccount()" disabled>
                        <i class="fas fa-plus-circle"></i> Create Account
                    </button>
                </div>
            </form>
        </div>
    </main>
</div>

<script src="../js/adminDash.js"></script>
<script src="../js/create-account.js"></script>
</body>
</html>