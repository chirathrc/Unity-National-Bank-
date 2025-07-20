package lk.codebridge.app.web.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lk.codebridge.app.core.provider.MaliServiceProvider;

@WebListener
public class Context_Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context_Listener");
        MaliServiceProvider.getInstance().start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MaliServiceProvider.getInstance().shutdown();
    }
}
