<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyBank - Transaction History</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link href="../css/history.css" rel="stylesheet">
</head>

<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../components/CustomerSideBar.jsp" %>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Top Navigation -->
        <%@ include file="../components/customerHeader.jsp" %>

        <!-- Transaction History Content -->
        <div class="content-area">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1><i class="bi bi-clock-history me-2"></i>Transaction History</h1>
<%--                <button class="btn btn-outline-primary" id="exportPdfBtn">--%>
<%--                    <i class="bi bi-file-earmark-pdf me-1"></i> Export as PDF--%>
<%--                </button>--%>
            </div>

            <!-- Transaction List -->
            <div class="card transaction-card">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Description</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Receipt</th>
                            </tr>
                            </thead>
                            <tbody id="transactionTableBody">

                            <!-- Scheduled Transfer -->
                            <tr class="transaction-row scheduled">
                                <td>May 28, 2023</td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="transaction-icon bg-warning-bg">
                                            <i class="bi bi-arrow-up-right text-warning"></i>
                                        </div>
                                        <div>
                                            <h6 class="mb-0">Transfer to Savings</h6>
                                            <small class="text-muted">Scheduled for May 28</small>
                                        </div>
                                    </div>
                                </td>
                                <td class="text-danger">-$500.00</td>
                                <td><span class="badge bg-warning-bg text-warning">Pending</span></td>
                                <td>
                                    <button class="btn btn-sm btn-outline-secondary" disabled>
                                        <i class="bi bi-file-earmark-pdf"></i>
                                    </button>
                                </td>
                            </tr>

                            <!-- Completed Transfer -->
                            <tr class="transaction-row completed">
                                <td>May 15, 2023</td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="transaction-icon bg-primary-bg">
                                            <i class="bi bi-arrow-up-right text-primary"></i>
                                        </div>
                                        <div>
                                            <h6 class="mb-0">Transfer to Savings</h6>
                                            <small class="text-muted">Completed</small>
                                        </div>
                                    </div>
                                </td>
                                <td class="text-danger">-$500.00</td>
                                <td><span class="badge bg-success-bg text-success">Completed</span></td>
                                <td>
                                    <button class="btn btn-sm btn-outline-primary">
                                        <i class="bi bi-file-earmark-pdf"></i>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script src="../js/history.js"></script>
</body>

</html>


<!-- Add this modal at the bottom of your history.html file -->
<div class="modal fade" id="transactionModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title d-flex align-items-center">
                    <i class="bi bi-receipt me-2"></i>
                    <span>Transaction Details</span>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="transaction-header text-center mb-4">
                    <div class="transaction-icon-lg mb-3" id="modalTransactionIcon">
                        <i class="bi bi-arrow-up-right fs-4"></i>
                    </div>
                    <h4 id="modalTransactionTitle">Transfer to Savings</h4>
                    <div class="transaction-amount-lg" id="modalTransactionAmount">-$500.00</div>
                </div>

                <div class="transaction-details">
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-calendar me-2"></i>Date</span>
                        <span class="detail-value" id="modalTransactionDate">May 15, 2023</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-credit-card me-2"></i>Account</span>
                        <span class="detail-value" id="modalTransactionAccount">Primary (•••• 4567)</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-tag me-2"></i>Category</span>
                        <span class="detail-value" id="modalTransactionCategory">Transfer</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-info-circle me-2"></i>Status</span>
                        <span class="detail-value" id="modalTransactionStatus">
                            <span class="badge bg-success-bg text-success">Completed</span>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-chat-left-text me-2"></i>Reference</span>
                        <span class="detail-value" id="modalTransactionReference">Monthly savings transfer</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label"><i class="bi bi-123 me-2"></i>Transaction ID</span>
                        <span class="detail-value" id="modalTransactionId">TX-7894A2B5C1</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer bg-light">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-lg me-1"></i> Close
                </button>
                <button type="button" class="btn btn-primary" id="downloadReceiptBtn">
                    <i class="bi bi-file-earmark-pdf me-1"></i> Download Receipt
                </button>
            </div>
        </div>
    </div>
</div>