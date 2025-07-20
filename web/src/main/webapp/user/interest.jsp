<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyBank - Interest Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="../css/interest.css">
    <link rel="stylesheet" href="../css/dashboard.css">
</head>

<body>
<div class="dashboard-container">

    <%@ include file="../components/CustomerSideBar.jsp" %>

    <main class="main-content">

        <%@ include file="../components/customerHeader.jsp" %>

        <div class="interestUI-container">
            <div class="interestUI-header">
                <h1 class="interestUI-title"><i class="bi bi-percent me-2"></i>Interest Details</h1>
                <div class="interestUI-account-badge">
                    <i class="bi bi-piggy-bank me-2"></i>
                    <span class="interestUI-account-name">${account.accountType}</span>
                </div>
            </div>

            <div class="interestUI-grid">
                <!-- Account Summary Card -->
                <div class="interestUI-card">
                    <div class="interestUI-card-header">
                        <h5><i class="bi bi-info-circle me-2"></i>Account Summary</h5>
                    </div>
                    <div class="interestUI-card-body">
                        <div class="interestUI-details">
                            <div class="interestUI-detail-item">
                                <i class="bi bi-credit-card interestUI-detail-icon"></i>
                                <div>
                                    <span class="interestUI-detail-label">Account Number</span>
                                    <strong class="interestUI-detail-value">${account.accountNumber}</strong>
                                </div>
                            </div>
                            <div class="interestUI-detail-item">
                                <i class="bi bi-cash-stack interestUI-detail-icon"></i>
                                <div>
                                    <span class="interestUI-detail-label">Current Balance</span>
                                    <strong class="interestUI-detail-value">LKR. ${account.balance}</strong>
                                </div>
                            </div>
                            <div class="interestUI-detail-item highlight">
                                <i class="bi bi-percent interestUI-detail-icon"></i>
                                <div>
                                    <span class="interestUI-detail-label">Interest Rate</span>
                                    <strong class="interestUI-detail-value">${rate}% APY</strong>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Interest Overview Card -->
                <div class="interestUI-card">
                    <div class="interestUI-card-header">
                        <h5><i class="bi bi-graph-up me-2"></i>Interest Overview</h5>
                    </div>
                    <div class="interestUI-card-body">
                        <div class="interestUI-metrics">
                            <div class="interestUI-metric-item">
                                <div class="interestUI-metric-icon bg-primary">
                                    <i class="bi bi-stack"></i>
                                </div>
                                <div>
                                    <div class="interestUI-metric-value">LKR. ${totalInterest}</div>
                                    <div class="interestUI-metric-label">Total Interest Earned</div>
                                </div>
                            </div>
                            <div class="interestUI-metric-item">
                                <div class="interestUI-metric-icon bg-success">
                                    <i class="bi bi-calendar-month"></i>
                                </div>
                                <div>
                                    <div class="interestUI-metric-value">LKR. ${lastMonthInterest}</div>
                                    <div class="interestUI-metric-label">Last Month</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Rate Details Card -->
                <div class="interestUI-card">
                    <div class="interestUI-card-header">
                        <h5><i class="bi bi-percent me-2"></i>Rate Details</h5>
                    </div>
                    <div class="interestUI-card-body">
                        <div class="interestUI-rate-display">
                            <div class="interestUI-rate-value">${rate}%</div>
                            <div class="interestUI-rate-type">Annual Percentage Yield</div>
                        </div>
                        <ul class="interestUI-rate-features">
                            <li>
                                <i class="bi bi-check-circle-fill text-success"></i>
                                <span>Compounded daily</span>
                            </li>
                            <li>
                                <i class="bi bi-check-circle-fill text-success"></i>
                                <span>Paid monthly on 1st</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>


            <!-- Interest History Section -->
            <div class="interestUI-history-card">
                <div class="interestUI-history-header">
                    <h5><i class="bi bi-clock-history me-2"></i>Interest History</h5>
                    <div class="interestUI-period-filter">
                    </div>
                </div>
                <div class="interestUI-history-body">
                    <div class="interestUI-table-responsive">
                        <table class="interestUI-history-table">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Description</th>
                                <th>Amount</th>
                                <th>Rate</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="record" items="${interestHistory}">
                                <tr>
                                    <td><fmt:formatDate value="${record.formattedDate}" pattern="MMM d, yyyy"/></td>
                                    <td>
                                        Monthly Interest Payment
                                    </td>
                                    <td class="interestUI-amount-positive">
                                        LKR <fmt:formatNumber value="${record.amount}" maxFractionDigits="2"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${record.rate}" maxFractionDigits="2"/>%
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- <div class="interestUI-load-more">
                        <button class="interestUI-load-btn">
                            <i class="bi bi-arrow-down-circle me-2"></i>Load More
                        </button>
                    </div> -->
                </div>
            </div>
        </div>
    </main>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="../js/dashboard.js"></script>
</body>

</html>