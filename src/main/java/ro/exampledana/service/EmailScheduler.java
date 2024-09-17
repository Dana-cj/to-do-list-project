package ro.exampledana.service;

import ro.exampledana.entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EmailScheduler {

    private final GmailService gmailService;
    private final ScheduledExecutorService scheduler;
    private Connection dbConnection;
    private TaskService taskService;



    public EmailScheduler(GmailService gmailService) {
        this.gmailService = gmailService;
        this.scheduler = Executors.newScheduledThreadPool(5);
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/MyDB");
            dbConnection = ds.getConnection();
            taskService=new TaskService(dbConnection);
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void start() {
        // Schedule the email task to run once a day
        scheduler.scheduleAtFixedRate(
                () -> {sendDailyEmail();}
                , 0, 1, TimeUnit.DAYS);
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

    private void sendDailyEmail() {
        try {
            Map<Task, List<Contact>> taskContacts= findContactsToSendEmails();
            for (Map.Entry<Task, List<Contact>> entry: taskContacts.entrySet()) {
            Task task= entry.getKey();
            List<String> filesNames= new Vector<>();
                for (File file:task.getFiles()) {
                    filesNames.add(file.getName());
                }
            List<Contact> contacts=entry.getValue();
                for (Contact contact:contacts) {
                    gmailService.sendEmail(
                            ""+contact.getEmailAddress(),
                            "me",
                            "Reminder: "+task.getDescription(),
                            "Hello!\n"+"This is a friendly reminder that your task: \n"+task.getDescription()+", part of project: "+task.getProject().trim()+", is due tomorrow.",
                            CalendarDataSource.createICSFileContent("task: "+(task.getDescription()+", project: "+task.getProject()),
                                    task.getDueDate()),
                           filesNames
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Map<Task,List<Contact>> findContactsToSendEmails(){
        Map<Task,List<Contact>> taskContacts=new Hashtable<>();
        List<Task> tasks= findTasksDueTomorrow();
        for (Task task:tasks) {
            List<Contact> contacts= new Vector<>();
             contacts.addAll(taskService.findContactsByTaskId(task.getId()));
            taskContacts.put(task,contacts);
        }
        return taskContacts;
    }
    private List<Task> findTasksDueTomorrow() {
        List<Task> tasks= taskService.findAllTasks()
                .stream()
                .filter(task->!task.getStatus().equalsIgnoreCase("DONE"))
                .filter(Task::isItDueTomorrow)
                //.filter(task-> task.getProject().toUpperCase().matches((project==null||project.isBlank()||project.equalsIgnoreCase("all"))?".*":project.toUpperCase()))
                .sorted(Comparator.comparing(Task::getDueDate)
                        .thenComparing(Task::getPriorityValue)
                        .thenComparing(Task::getInitialDate))
                .collect(Collectors.toList());
        return tasks;
    }

}
