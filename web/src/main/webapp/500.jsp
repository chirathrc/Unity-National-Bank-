<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500 Internal Server Error | MyBank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        /* shared styles same as your original */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            color: #343a40;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .error-container {
            width: 100%;
            max-width: 600px;
            padding: 2rem;
            text-align: center;
        }
        .error-content {
            background-color: white;
            border-radius: 12px;
            padding: 3rem 2rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }
        .error-icon {
            font-size: 5rem;
            color: #dc3545;
            margin-bottom: 1.5rem;
        }
        .error-title {
            font-size: 4rem;
            font-weight: 700;
            color: #212529;
        }
        .error-subtitle {
            font-size: 1.75rem;
            font-weight: 500;
            color: #495057;
        }
        .error-message {
            font-size: 1.1rem;
            color: #6c757d;
            margin: 1.5rem 0 2rem;
        }
        .error-actions {
            display: flex;
            justify-content: center;
        }
        .btn {
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-weight: 500;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-content">
        <div class="error-icon"><i class="bi bi-bug"></i></div>
        <h1 class="error-title">500</h1>
        <h2 class="error-subtitle">Internal Server Error</h2>
        <p class="error-message">Something went wrong on our end. Please try again later.</p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <i class="bi bi-house-door me-2"></i>Return Home
            </a>
        </div>
    </div>
</div>
</body>
</html>
