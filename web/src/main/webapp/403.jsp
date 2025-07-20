<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 Forbidden | MyBank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
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
            margin-bottom: 0.5rem;
            color: #212529;
        }

        .error-subtitle {
            font-size: 1.75rem;
            font-weight: 500;
            margin-bottom: 1rem;
            color: #495057;
        }

        .error-message {
            font-size: 1.1rem;
            color: #6c757d;
            margin-bottom: 2rem;
            line-height: 1.6;
        }

        .error-actions {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            transition: all 0.2s ease;
        }

        .btn-primary {
            background-color: #4361ee;
            border-color: #4361ee;
        }

        .btn-primary:hover {
            background-color: #3a56d4;
            border-color: #3a56d4;
        }

        @media (max-width: 576px) {
            .error-container {
                padding: 1rem;
            }

            .error-content {
                padding: 2rem 1.5rem;
            }

            .error-title {
                font-size: 3rem;
            }

            .error-subtitle {
                font-size: 1.5rem;
            }

            .error-actions {
                flex-direction: column;
                gap: 0.75rem;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-content">
        <div class="error-icon">
            <i class="bi bi-exclamation-octagon"></i>
        </div>
        <h1 class="error-title">403</h1>
        <h2 class="error-subtitle">Access Forbidden</h2>
        <p class="error-message">
            You don’t have permission to access this page or the resource has been restricted.
        </p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                <i class="bi bi-house-door me-2"></i>Return Home
            </a>
        </div>
    </div>
</div>
</body>
</html>
