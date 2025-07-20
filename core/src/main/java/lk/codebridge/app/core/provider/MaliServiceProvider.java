package lk.codebridge.app.core.provider;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import lk.codebridge.app.core.mail.Mailable;
import lk.codebridge.app.core.util.Env;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MaliServiceProvider {
    private Properties properties = new Properties();
    private Authenticator authenticator;
    private static MaliServiceProvider instance;
    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

    private MaliServiceProvider() {
        properties.put("mail.smtp.host", Env.get("mailtrap.host"));
        properties.put("mail.smtp.port", Env.get("mailtrap.port"));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", Env.get("mailtrap.host"));
    }

    public static MaliServiceProvider getInstance() {
        if (instance == null) {
            instance = new MaliServiceProvider();
        }
        return instance;
    }

    public void start() {
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Env.get("mailtrap.username"), Env.get("mailtrap.password"));
            }
        };

        executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, blockingQueue,
                new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();

        System.out.println("MailServiceProvider: Running...");
    }

    public void sendMail(Mailable mailable) {

        blockingQueue.offer(mailable);
    }

    public Properties getProperties() {

        return properties;
    }

    public Authenticator getAuthenticator() {

        return authenticator;
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

}