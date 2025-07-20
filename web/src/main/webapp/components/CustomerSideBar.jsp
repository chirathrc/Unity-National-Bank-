<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="currentPath" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<aside class="sidebar">
    <div class="sidebar-header">
        <img src="${contextPath}/assets/credit-card.png" alt="MyBank Logo" class="logo">
        <h3>MyBank</h3>
    </div>
    <nav class="sidebar-nav">
        <ul class="nav flex-column">
            <li class="nav-item ${fn:contains(currentPath, '/index.jsp') ? 'active' : ''}">
                <a class="nav-link" href="${contextPath}/user/index.jsp">
                    <i class="bi bi-house-door"></i> <span>Dashboard</span>
                </a>
            </li>
            <li class="nav-item ${fn:contains(currentPath, '/transfer.jsp') ? 'active' : ''}">
                <a class="nav-link" href="${contextPath}/user/getSavings">
                    <i class="bi bi-arrow-left-right"></i> <span>Transfers</span>
                </a>
            </li>
            <li class="nav-item ${fn:contains(currentPath, '/transaction_history.jsp') ? 'active' : ''}">
                <a class="nav-link" href="${contextPath}/user/transaction_history.jsp">
                    <i class="bi bi-clock-history"></i> <span>Transaction History</span>
                </a>
            </li>
            <li class="nav-item ${fn:contains(currentPath, '/loadProfile') ? 'active' : ''}">
                <a class="nav-link" href="${contextPath}/user/loadProfile">
                    <i class="bi bi-person"></i> <span>Profile</span>
                </a>
            </li>
        </ul>
    </nav>
    <div class="sidebar-footer">
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">
            <i class="bi bi-box-arrow-right"></i> <span>Sign Out</span>
        </a>
    </div>
</aside>
