<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Unity National Bank | Secure Banking Solutions</title>
    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rajdhani:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link href="css/landing.css" rel="stylesheet">
    <!-- AOS Animation -->
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
</head>
<body>
<!-- Animated Gradient Background -->
<div class="gradient-bg"></div>

<!-- Floating Particles -->
<div class="particles" id="particles-js"></div>

<!-- Main Container -->
<div class="modern-container">
    <!-- Glassmorphic Navigation -->
    <nav class="glass-nav">
        <div class="nav-container">
            <div class="nav-logo">
                <label class="nav-title">Unity National Bank</label>
            </div>
            <div class="nav-links">
                <a  href="${pageContext.request.contextPath}/login.jsp"  class="nav-link">Sign In</a>
            </div>
            <div class="mobile-menu-btn">
                <i class="bi bi-list"></i>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content" data-aos="fade-up" data-aos-duration="800">
            <h1 class="hero-title">
                <span class="gradient-text">Unity</span> National <br> Bank
            </h1>
            <p class="hero-subtitle">
                Trusted financial services with the security you deserve.
            </p>
            <div class="hero-cta">
                <a href="${pageContext.request.contextPath}/login.jsp" class="cta-btn primary">
                    <span>Sign In</span>
                    <i class="bi bi-arrow-right"></i>
                </a>
            </div>
        </div>
        <div class="hero-image" data-aos="fade-left" data-aos-duration="1000">
            <img src="assets/contactless.png" alt="Banking Services" class="floating">
        </div>
    </section>

    <!-- Features Grid -->
    <section class="features-section">
        <div class="section-header" data-aos="fade-up">
            <h2>Our Banking Services</h2>
            <p>Reliable solutions for your financial needs</p>
        </div>
        <div class="features-grid">
            <div class="feature-card" data-aos="fade-up" data-aos-delay="100">
                <div class="feature-icon">
                    <i class="bi bi-lightning-charge"></i>
                </div>
                <h3>Instant Transfers</h3>
                <p>Move money quickly between accounts with immediate processing</p>
            </div>
            <div class="feature-card" data-aos="fade-up" data-aos-delay="200">
                <div class="feature-icon">
                    <i class="bi bi-shield-lock"></i>
                </div>
                <h3>Advanced Security</h3>
                <p>Multi-layer protection for your accounts and transactions</p>
            </div>
            <div class="feature-card" data-aos="fade-up" data-aos-delay="300">
                <div class="feature-icon">
                    <i class="bi bi-piggy-bank"></i>
                </div>
                <h3>Savings Accounts</h3>
                <p>Competitive interest rates to help your money grow</p>
            </div>
        </div>
    </section>
</div>

<!-- Footer -->
<footer class="modern-footer">
    <div class="footer-container">
        <div class="footer-logo">
            <img src="assets/unity-bank-logo-white.png" alt="Unity National Bank">
            <p>Your trusted banking partner</p>
        </div>
        <div class="footer-links">
            <div class="link-group">
                <h4>Quick Links</h4>
                <a href="about.html">About Us</a>
                <a href="login.html">Sign In</a>
                <a href="register.html">Open Account</a>
            </div>
            <div class="link-group">
                <h4>Support</h4>
                <a href="#">Contact Us</a>
                <a href="#">Help Center</a>
                <a href="#">Security</a>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <p>© 2023 Unity National Bank. All rights reserved.</p>
        <div class="legal-links">
            <a href="#">Privacy Policy</a>
            <a href="#">Terms of Service</a>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/particles.js@2.0.0/particles.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="js/landing.js"></script>
</body>
</html>