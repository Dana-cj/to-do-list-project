package ro.exampledana.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ro.exampledana.service.EmailScheduler;
import ro.exampledana.service.GmailService;

@WebListener
public class AppContextListener implements ServletContextListener {
    private EmailScheduler scheduler;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Initialize Gmail service
            GmailService gmailService = new GmailService();

//            try {
//                Context  initialContext = new InitialContext();
//                Context envContext = (Context) initialContext.lookup("java:/comp/env");
//                DataSource ds = (DataSource) envContext.lookup("jdbc/MyDB");
//                Connection dbConnection = ds.getConnection();
//            } catch (NamingException | SQLException e) {
//                throw new RuntimeException(e);
//            }
           // TaskService taskService= new TaskService(dbConnection);



            // Start email scheduler
            scheduler = new EmailScheduler(gmailService);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup resources if necessary
        scheduler.stop();
    }

}

