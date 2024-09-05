package ro.exampledana.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailScheduler {

    private final GmailService gmailService;
    private final ScheduledExecutorService scheduler;


    public EmailScheduler(GmailService gmailService) {
        this.gmailService = gmailService;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start(Runnable runnable) {
        // Schedule the email task to run once a day
        scheduler.scheduleAtFixedRate(
               // () -> {sendDailyEmail();}
                runnable, 0, 1, TimeUnit.DAYS);
    }

    public void stop() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

}
