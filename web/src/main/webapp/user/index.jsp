<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyBank - Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link href="../css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../components/CustomerSideBar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Top Navigation -->

        <%@ include file="../components/customerHeader.jsp" %>
        <%--        <header class="top-nav">--%>
        <%--            <div class="user-profile">--%>
        <%--                <img src="../assets/boy.png" alt="User" class="avatar">--%>
        <%--                <span class="username">${sessionScope.name}</span>--%>
        <%--            </div>--%>
        <%--&lt;%&ndash;            <div class="notifications">&ndash;%&gt;--%>
        <%--&lt;%&ndash;                <i class="bi bi-bell"></i>&ndash;%&gt;--%>
        <%--&lt;%&ndash;                <span class="badge">3</span>&ndash;%&gt;--%>
        <%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
        <%--        </header>--%>

        <!-- Dashboard Content -->
        <div class="content-area">
            <!-- Welcome Banner -->
            <div class="welcome-banner">
                <h1>Welcome back, ${sessionScope.name}!</h1>
                <p>Here's what's happening with your accounts today.</p>
            </div>

            <!-- Account Summary Cards -->
            <div class="row summary-cards" id="accountSummaryRow">
                <%--                <div class="col-md-4">--%>
                <%--                    <div class="card balance-card">--%>
                <%--                        <div class="card-body">--%>
                <%--                            <h5 class="card-title">Primary Account</h5>--%>
                <%--                            <h2 class="card-amount">$12,456.78</h2>--%>
                <%--                            <p class="card-detail">Available Balance</p>--%>
                <%--                            <div class="account-number">•••• 4567</div>--%>
                <%--                            <a href="#" class="btn btn-sm btn-outline-primary mt-2">View Details</a>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <%--                <div class="col-md-4">--%>
                <%--                    <div class="card savings-card">--%>
                <%--                        <div class="card-body">--%>
                <%--                            <h5 class="card-title">Savings Account</h5>--%>
                <%--                            <h2 class="card-amount">$8,245.32</h2>--%>
                <%--                            <p class="card-detail">Available Balance</p>--%>
                <%--                            <div class="account-number">•••• 8910</div>--%>
                <%--                            <a href="#" class="btn btn-sm btn-outline-success mt-2">View Details</a>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <%--                <div class="col-md-4">--%>
                <%--                    <div class="card interest-card">--%>
                <%--                        <div class="card-body">--%>
                <%--                            <h5 class="card-title">Interest Earned</h5>--%>
                <%--                            <h2 class="card-amount">$45.67</h2>--%>
                <%--                            <p class="card-detail">This Month</p>--%>
                <%--                            <div class="interest-rate">1.2% APY</div>--%>
                <%--                            <a href="history.html" class="btn btn-sm btn-outline-warning mt-2">View History</a>--%>
                <%--                        </div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
            </div>

            <!-- Quick Actions -->
            <div class="quick-actions">
                <a href="${pageContext.request.contextPath}/user/getSavings" class="btn btn-action">
                    <i class="bi bi-arrow-up-circle"></i>
                    <span>Send Money</span>
                </a>
            </div>

            <!-- Recent Transactions -->
            <%--            <div class="card transaction-card">--%>
            <%--                <div class="card-header">--%>
            <%--                    <h5>Recent Transactions</h5>--%>
            <%--                    <a href="history.html" class="view-all">View All</a>--%>
            <%--                </div>--%>
            <%--                <div class="card-body">--%>
            <%--                    <div class="table-responsive">--%>
            <%--                        <table class="table">--%>
            <%--                            <thead>--%>
            <%--                            <tr>--%>
            <%--                                <th>Date</th>--%>
            <%--                                <th>Description</th>--%>
            <%--                                <th>Amount</th>--%>
            <%--                                <th>Status</th>--%>
            <%--                            </tr>--%>
            <%--                            </thead>--%>
            <%--                            <tbody>--%>
            <%--                            <tr>--%>
            <%--                                <td>Jun 12, 2023</td>--%>
            <%--                                <td>--%>
            <%--                                    <i class="bi bi-cart3"></i>--%>
            <%--                                    Amazon Purchase--%>
            <%--                                </td>--%>
            <%--                                <td class="debit">-$45.67</td>--%>
            <%--                                <td><span class="badge completed">Completed</span></td>--%>
            <%--                            </tr>--%>
            <%--                            <tr>--%>
            <%--                                <td>Jun 10, 2023</td>--%>
            <%--                                <td>--%>
            <%--                                    <i class="bi bi-arrow-down-circle"></i>--%>
            <%--                                    Deposit from ABC Corp--%>
            <%--                                </td>--%>
            <%--                                <td class="credit">+$1,200.00</td>--%>
            <%--                                <td><span class="badge completed">Completed</span></td>--%>
            <%--                            </tr>--%>
            <%--                            <tr>--%>
            <%--                                <td>Jun 8, 2023</td>--%>
            <%--                                <td>--%>
            <%--                                    <i class="bi bi-lightning"></i>--%>
            <%--                                    Utility Bill Payment--%>
            <%--                                </td>--%>
            <%--                                <td class="debit">-$78.90</td>--%>
            <%--                                <td><span class="badge completed">Completed</span></td>--%>
            <%--                            </tr>--%>
            <%--                            <tr>--%>
            <%--                                <td>Jun 5, 2023</td>--%>
            <%--                                <td>--%>
            <%--                                    <i class="bi bi-arrow-up-circle"></i>--%>
            <%--                                    Transfer to Jane Doe--%>
            <%--                                </td>--%>
            <%--                                <td class="debit">-$200.00</td>--%>
            <%--                                <td><span class="badge completed">Completed</span></td>--%>
            <%--                            </tr>--%>
            <%--                            <tr>--%>
            <%--                                <td>Jun 1, 2023</td>--%>
            <%--                                <td>--%>
            <%--                                    <i class="bi bi-bank"></i>--%>
            <%--                                    Interest Credit--%>
            <%--                                </td>--%>
            <%--                                <td class="credit">+$12.34</td>--%>
            <%--                                <td><span class="badge completed">Completed</span></td>--%>
            <%--                            </tr>--%>
            <%--                            </tbody>--%>
            <%--                        </table>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--            </div>--%>
        </div>
    </main>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script src="../js/dashboard.js"></script>
<%--<script src="../js/navigation.js"></script>--%>
</body>
</html>