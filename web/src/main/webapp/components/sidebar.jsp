<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="currentPath" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<aside class="sidebar">
    <div class="sidebar-header">
        <img src="${pageContext.request.contextPath}/assets/credit-card.png" alt="Bank Logo" class="logo">
        <h2>Bank Admin</h2>
    </div>
    <nav class="sidebar-nav">
        <ul>
            <li class="${fn:contains(currentPath, '/index.jsp') ? 'active' : ''}">
                <a href="${contextPath}/admin/index.jsp">
                    <i class="fas fa-tachometer-alt"></i>
                    <span>Dashboard</span>
                </a>
            </li>
            <li class="${fn:contains(currentPath, '/add-customer.jsp') ? 'active' : ''}">
                <a href="${contextPath}/admin/add-customer.jsp">
                    <i class="fas fa-users"></i>
                    <span>Customers</span>
                </a>
            </li>

            <li class="${fn:contains(currentPath, '/create-account.jsp') ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/admin/create-account.jsp">
                    <i class="fas fa-wallet"></i>
                    <span>Create Account</span>
                </a>
            </li>

            <li class="${fn:contains(currentPath, '/manage-customers.jsp') ? 'active' : (fn:contains(currentPath, '/single-customer-management.jsp') ? 'active' : '')}">
                <a href="${pageContext.request.contextPath}/admin/manage-customers.jsp">
                    <i class="fas fa-users-cog"></i>
                    <span>Manage Users</span>
                </a>
            </li>
        </ul>
    </nav>
    <div class="sidebar-footer">
        <div class="user-profile">
            <img src="../assets/user.png" alt="Admin Avatar">
            <div class="user-info">
                <h4>${pageContext.request.userPrincipal.name}</h4>
                <p>${sessionScope.role}</p>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i>
            <span>Logout</span>
        </a>
    </div>
</aside>