package lk.codebridge.app.core.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lk.codebridge.app.core.provider.MaliServiceProvider;
import lk.codebridge.app.core.util.Env;

public abstract class Mailable implements Runnable {

    private MaliServiceProvider provider;

    public Mailable() {
        provider = MaliServiceProvider.getInstance();
    }

    @Override
    public void run() {
        Session session = Session.getInstance(provider.getProperties(), provider.getAuthenticator());
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(Env.get("app.email")));
            build(message);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void build(Message message) throws MessagingException;
}
