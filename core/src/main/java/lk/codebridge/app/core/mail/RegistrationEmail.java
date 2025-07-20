package lk.codebridge.app.core.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;

public class RegistrationEmail extends Mailable {

    private final String to;
    private final String full_name;
    private final String username;
    private final String password;

    // Constructor or setters (recommended) to inject values
    public RegistrationEmail(String to, String full_name, String username, String password) {
        this.to = to;
        this.full_name = full_name;
        this.username = username;
        this.password = password;
    }

    @Override
    public void build(Message message) throws MessagingException {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Welcome to National Bank - Registration Successful");

        String html = "<!DOCTYPE html>" +
                "<html><body>" +
                "<h2 style='color:#4361ee;'>Welcome, " + full_name + "!</h2>" +
                "<p>Thank you for registering with <strong>MyBank</strong>.</p>" +
                "<p>Your account has been successfully created. Below are your login credentials:</p>" +
                "<table style='border-collapse: collapse;'>" +
                "<tr><td style='padding: 8px; font-weight: bold;'>Username:</td><td style='padding: 8px;'>" + username + "</td></tr>" +
                "<tr><td style='padding: 8px; font-weight: bold;'>Password:</td><td style='padding: 8px;'>" + password + "</td></tr>" +
                "</table>" +
                "<p style='margin-top: 20px;'>Please keep this information safe and do not share it with anyone.</p>" +
                "<br>" +
                "<p>Regards,<br/>National Bank Team</p>" +
                "</body></html>";

        message.setContent(html, "text/html; charset=utf-8");
    }
}
