<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyBank - Transfers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
<%--    <link rel="stylesheet" href="../../css/transfers.css">--%>
<%--    <link rel="stylesheet" href="../../css/dashboard.css">--%>

    <!-- Link CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/transfers.css">
    <!-- Link CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>

<body>
<div class="dashboard-container">
    <!-- Sidebar Navigation -->
    <%@ include file="../../components/CustomerSideBar.jsp" %>

    <main class="main-content">
        <%@ include file="../../components/customerHeader.jsp" %>

        <div class="content-area">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1><i class="bi bi-arrow-left-right me-2"></i>Transfer Funds</h1>
            </div>

            <div class="row">
                <div class="col-lg-8">
                    <div class="card transfer-card">
                        <div class="card-body">
                            <form id="transferForm">
                                <!-- From Account (Auto-selected) -->
                                <div class="mb-4" id="fromAccountSection">
                                    <label class="form-label fw-semibold">From Account</label>
                                    <div class="account-selected d-flex align-items-center p-3">
                                        <div class="account-icon bg-primary bg-opacity-10 text-primary rounded-circle d-flex align-items-center justify-content-center"
                                             style="width: 48px; height: 48px;">
                                            <i class="bi bi-wallet2 fs-5"></i>
                                        </div>
                                        <div class="ms-3">
                                            <h6 class="mb-0 fw-semibold" id="accountType">${acc.accountType}</h6>
                                            <small class="text-muted" id="accountInfo">Balance: LKR ${acc.balance} ****
                                                ${last4}</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Recipient Selection -->
                                <div class="mb-4">
                                    <label class="form-label fw-semibold">To Account <span
                                            class="badge bg-primary bg-opacity-10 text-primary ms-2">MyBank
                                                Only</span></label>

                                    <!-- Account Verification Section -->
                                    <div class="account-verification mb-3">
                                        <div class="input-group">
                                            <span class="input-group-text bg-transparent"><i
                                                    class="bi bi-credit-card"></i></span>
                                            <input type="text" class="form-control" id="accountNumber"
                                                   placeholder="Enter account number" maxlength="12">
                                            <button class="btn btn-primary" type="button" id="verifyAccountBtn">
                                                <span id="verifyBtnText">Verify</span>
                                                <span class="spinner-border spinner-border-sm d-none"
                                                      id="verifySpinner"></span>
                                            </button>
                                        </div>
                                        <div class="form-text text-end"><i class="bi bi-lock-fill me-1"></i> Secure
                                            verification
                                        </div>
                                    </div>

                                    <!-- Verified Account Display -->
                                    <div class="verified-account d-none" id="verifiedAccount">
                                        <div class="account-selected d-flex align-items-center p-3 bg-success bg-opacity-10">
                                            <div class="account-icon bg-success bg-opacity-10 text-success rounded-circle d-flex align-items-center justify-content-center"
                                                 style="width: 48px; height: 48px;">
                                                <i class="bi bi-check-circle fs-5"></i>
                                            </div>
                                            <div class="ms-3">
                                                <h6 class="mb-0 fw-semibold" id="verifiedAccountName">-</h6>
                                                <small class="text-muted" id="verifiedAccountNumber">•••• ••••
                                                    ••••</small>
                                            </div>
                                            <button type="button" class="btn btn-sm btn-outline-secondary ms-auto"
                                                    id="changeAccountBtn">
                                                Change
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <!-- Amount Field -->
                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Amount</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-transparent">LKR</span>
                                        <input type="number" class="form-control" id="transferAmount"
                                               placeholder="0.00" min="1" step="0.01" required disabled>
                                        <span class="input-group-text bg-transparent">
                                          <i class="bi bi-cash"></i>
                                        </span>
                                    </div>
                                    <div class="d-flex justify-content-between mt-1">
                                        <small class="text-muted" id="availableBalance">Available: LKR ${acc.balance} </small>
                                        <small class="text-danger fw-semibold">Min: LKR 1,000</small>
                                    </div>
                                </div>


                                <!-- Transfer Timing -->
                                <div class="mb-4">
                                    <label class="form-label fw-semibold">Transfer Timing</label>
                                    <div class="transfer-timing-options d-flex gap-3">
                                        <div class="form-check-card flex-grow-1">
                                            <input class="form-check-input" type="radio" name="transferTiming"
                                                   id="transferNow" value="now" checked hidden>
                                            <label
                                                    class="form-check-label d-flex flex-column align-items-center p-3 border rounded-12"
                                                    for="transferNow">
                                                <i class="bi bi-lightning-fill text-primary fs-3 mb-2"></i>
                                                <span>Instant Transfer</span>
                                                <small class="text-muted text-center">Processed immediately</small>
                                            </label>
                                        </div>
                                        <div class="form-check-card flex-grow-1">
                                            <input class="form-check-input" type="radio" name="transferTiming"
                                                   id="scheduleTransfer" value="schedule" hidden>
                                            <label
                                                    class="form-check-label d-flex flex-column align-items-center p-3 border rounded-12"
                                                    for="scheduleTransfer">
                                                <i class="bi bi-calendar-check text-warning fs-3 mb-2"></i>
                                                <span>Schedule</span>
                                                <small class="text-muted text-center">Set future date</small>
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <!-- Schedule Options (Hidden by default) -->
                                <div class="schedule-options mb-4" style="display: none;">
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Transfer Date</label>
                                            <input type="date" class="form-control" id="transferDate" min="">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Time</label>
                                            <div class="input-group timepicker">
                                                <span class="input-group-text bg-transparent"><i
                                                        class="bi bi-clock"></i></span>
                                                <input type="time" class="form-control" id="transferTime" value="09:00">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Reference Note -->
                                <div class="mb-4">
                                    <label class="form-label">Reference (Optional)</label>
                                    <input type="text" class="form-control" id="transferReference"
                                           placeholder="e.g. Rent payment">
                                </div>

                                <!-- Submit Button -->
                                <div class="d-grid mt-4">
                                    <button type="submit" class="btn btn-primary btn-lg" id="submitTransferBtn"
                                            disabled>
                                        <i class="bi bi-send-check me-2"></i>
                                        Confirm Transfer
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4">
                    <!-- Transfer Summary -->
                    <div class="card summary-card">
                        <div class="card-header">
                            <h5>Transfer Summary</h5>
                        </div>
                        <div class="card-body">
                            <div class="transfer-summary">
                                <div class="summary-item">
                                    <span>From:</span>
                                    <strong> ${acc.accountType} (***** ${last4})</strong>
                                </div>
                                <div class="summary-item">
                                    <span>To:</span>
                                    <strong id="summaryRecipient">Not selected</strong>
                                </div>
                                <div class="summary-item">
                                    <span>Amount:</span>
                                    <strong id="summaryAmount">LKR. 0.00</strong>
                                </div>
                                <div class="summary-item">
                                    <span>When:</span>
                                    <strong id="summaryWhen">Now (Instant)</strong>
                                </div>
                                <hr>
                                <div class="summary-item total">
                                    <span>Total:</span>
                                    <strong id="summaryTotal">LKR. 0.00</strong>
                                </div>
                            </div>

                            <div class="transfer-limits mt-4">
                                <h6>Transfer Limits</h6>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        Minimum Transaction
                                        <span class="badge bg-primary">LKR. 1,000</span>
                                    </li>
<%--                                    <li class="list-group-item d-flex justify-content-between align-items-center">--%>
<%--                                        Daily--%>
<%--                                        <span class="badge bg-primary">$10,000</span>--%>
<%--                                    </li>--%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<!-- Transfer Confirmation Modal -->
<div class="modal fade" id="confirmationModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Transfer</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="text-center mb-4">
                    <div class="checkmark-circle">
                        <i class="bi bi-check-circle-fill text-success"></i>
                    </div>
                    <h4 class="mt-3">Transfer Details</h4>
                </div>

                <div class="confirmation-details">
                    <div class="detail-item">
                        <span>From:</span>
                        <strong id="confirmFrom">Primary Account (•••• 4567)</strong>
                    </div>
                    <div class="detail-item">
                        <span>To:</span>
                        <strong id="confirmTo">-</strong>
                    </div>
                    <div class="detail-item">
                        <span>Amount:</span>
                        <strong id="confirmAmount">LKR. 0.00</strong>
                    </div>
                    <div class="detail-item">
                        <span>When:</span>
                        <strong id="confirmWhen">Now (Instant)</strong>
                    </div>
                    <div class="detail-item">
                        <span>Reference:</span>
                        <strong id="confirmReference">-</strong>
                    </div>
                </div>

                <div class="alert alert-warning mt-4">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    Transfers cannot be cancelled once submitted
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="confirmTransferBtn">
                    <i class="bi bi-check-circle me-1"></i> Confirm Transfer
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<%--<script src="../../js/transfers.js"></script>--%>
<!-- Link JS -->
<script src="${pageContext.request.contextPath}/js/transfers.js"></script>
</body>

</html>