# Unity-National-Bank- 🏦

## 📘 Overview

**Unity National Bank** is a modular, enterprise-level banking system built using **Jakarta EE** technologies. It demonstrates secure and scalable enterprise application development with:

- **EJBs** for business logic
- **JSP** for the web interface
- **JPA** for persistence
- **Jakarta Security** (JAAS) for authentication & authorization
- **Jakarta Mail** for email notifications
- **CDI**, **Interceptors**, and **Timers** for cross-cutting concerns

---

## 🚀 Key Features

### 👤 Authentication & Roles
- Custom **IdentityStore** with JAAS and **Container-Managed Security**
- Role-based access: **Admin**, **Manager**, **Customer**
- Secure credential storage & validation

### 👨‍💼 Admin Features
- Create and manage customer accounts
- Auto-email new credentials on registration
- Search/filter customers by ID, name, or email

### 💳 Customer Features
- Secure login & dashboard
- View balances and account details
- One-time and scheduled fund transfers
- Transaction history view

### ⏰ Scheduled Transfers
- EJB `@Schedule`–driven timer runs every 1 minutes
- Automatically processes pending transfers

### 🧾 Transaction Management
- Container-Managed Transactions (CMT) by default
- Bean-Managed Transactions (BMT) for special cases
- Automatic rollback on failure

### 📧 Email Notifications
- Sending emails with using Mailtrap
- HTML-templated emails

---

## 🛠️ Technology Stack

| Technology            | Usage                          |
|-----------------------|--------------------------------|
| **Jakarta EE 10**     | Platform                      |
| **EJB**               | Business services & timers    |
| **JSP**               | Web front end                 |
| **JPA (EclipseLink)** | ORM / persistence             |
| **Jakarta Security**  | Authentication & authorization|
| **Mailtrap**          | Email delivery                |
| **CDI**               | Dependency Injection          |
| **Interceptors**      | Audit & logging               |
| **Maven**             | Build & dependency management |
| **Payara Server 6**   | Application server            |

---

## 💾 Local Setup & Deployment

1. **Clone** this repo:
   ```bash
   git clone https://github.com/YOUR-USERNAME/unity-national-bank.git
   cd unity-national-bank
