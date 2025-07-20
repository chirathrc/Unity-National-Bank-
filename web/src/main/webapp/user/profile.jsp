<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>MyBank - User Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
  <link rel="stylesheet" href="../css/profile.css">
  <link rel="stylesheet" href="../css/dashboard.css">
</head>

<body>
<div class="dashboard-container">

  <%@ include file="../components/CustomerSideBar.jsp" %>

  <main class="main-content">
    <div class="profileUI-container">
      <div class="profileUI-header">
        <h1 class="profileUI-title"><i class="bi bi-person-circle me-2"></i>User Profile</h1>
      </div>

      <div class="profileUI-grid">
        <!-- Profile Card -->
        <div class="profileUI-card">
          <div class="profileUI-card-header">
            <h5><i class="bi bi-person-badge me-2"></i>Personal Information</h5>
          </div>
          <div class="profileUI-card-body">
            <div class="profileUI-avatar-container">
              <div class="profileUI-avatar">
                <i class="bi bi-person-fill"></i>
              </div>
              <div class="profileUI-avatar-info">
                <div class="profileUI-username">${user.fullName}</div>
                <div class="profileUI-status">Active <span class="profileUI-status-badge"></span></div>
              </div>
            </div>

            <div class="profileUI-details">
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Full Name</label>
                <div class="profileUI-detail-value">${user.fullName}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">First Name</label>
                <div class="profileUI-detail-value">${user.firstName}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Last Name</label>
                <div class="profileUI-detail-value">${user.lastName}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">NIC Number</label>
                <div class="profileUI-detail-value">${user.NIC}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Email</label>
                <div class="profileUI-detail-value">${user.email}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Birthday</label>
                <div class="profileUI-detail-value">${user.dateOfBirth}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Gender</label>
                <div class="profileUI-detail-value">${user.gender}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Address Card -->
        <div class="profileUI-card">
          <div class="profileUI-card-header">
            <h5><i class="bi bi-house-door me-2"></i>Address Information</h5>
          </div>
          <div class="profileUI-card-body">
            <div class="profileUI-details">
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Address Line 1</label>
                <div class="profileUI-detail-value">${user.addressLineOne}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Address Line 2</label>
                <div class="profileUI-detail-value">${user.addressLineTwo}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">City</label>
                <div class="profileUI-detail-value">${city}</div>
              </div>
              <div class="profileUI-detail-item">
                <label class="profileUI-detail-label">Postal Code</label>
                <div class="profileUI-detail-value">${user.postalCode}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Password Update Card -->
        <div class="profileUI-card">
          <div class="profileUI-card-header">
            <h5><i class="bi bi-shield-lock me-2"></i>Security Settings</h5>
          </div>
          <div class="profileUI-card-body">
            <form id="passwordUpdateForm">
              <div class="profileUI-form-group">
                <label for="currentPassword" class="profileUI-form-label">Current Password</label>
                <div class="profileUI-input-group">
                  <input type="password" class="profileUI-form-control" id="currentPassword" required>
                  <button type="button" class="profileUI-password-toggle">
                    <i class="bi bi-eye"></i>
                  </button>
                </div>
              </div>
              <div class="profileUI-form-group">
                <label for="newPassword" class="profileUI-form-label">New Password</label>
                <div class="profileUI-input-group">
                  <input type="password" class="profileUI-form-control" id="newPassword" required>
                  <button type="button" class="profileUI-password-toggle">
                    <i class="bi bi-eye"></i>
                  </button>
                </div>
                <div class="profileUI-password-strength">
                  <div class="profileUI-strength-meter"></div>
                  <small class="profileUI-strength-text">Password strength: <span>Weak</span></small>
                </div>
              </div>
              <div class="profileUI-form-group">
                <label for="confirmPassword" class="profileUI-form-label">Confirm New Password</label>
                <div class="profileUI-input-group">
                  <input type="password" class="profileUI-form-control" id="confirmPassword" required>
                  <button type="button" class="profileUI-password-toggle">
                    <i class="bi bi-eye"></i>
                  </button>
                </div>
              </div>
              <div class="profileUI-form-actions">
                <button type="submit" class="profileUI-btn-primary">
                  <i class="bi bi-key me-2"></i>Update Password
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- Update Request Notice -->
      <div class="profileUI-notice">
        <div class="profileUI-notice-icon">
          <i class="bi bi-info-circle"></i>
        </div>
        <div class="profileUI-notice-content">
          <h5>Need to update your personal information?</h5>
          <p>For security reasons, personal details cannot be changed online. Please send a request to
            <a href="mailto:support@mybank.com?subject=Personal%20Information%20Update%20Request" class="profileUI-notice-link">support@mybank.com</a>
            from your registered email address.</p>
        </div>
      </div>
    </div>
  </main>
</div>

<!-- Password Update Success Modal -->
<div class="modal fade" id="successModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="profileUI-modal-body">
        <div class="profileUI-modal-icon success">
          <i class="bi bi-check-circle"></i>
        </div>
        <h4>Password Updated Successfully</h4>
        <p>Your password has been changed. Please use your new password for your next login.</p>
        <button type="button" class="profileUI-btn-primary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script src="../js/profile.js"></script>
<script src="../js/dashboard.js"></script>
</body>

</html>