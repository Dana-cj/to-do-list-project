package ro.exampledana.entity;

import ro.exampledana.servlet.TasksServlet;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

public class Task extends Doable{
    private int id;
    private String description;
    private GregorianCalendar initialDate;
    private GregorianCalendar dueDate;
    private Priority priority;
    private Status status;
    private String project;
    private List<File> files;
    private List<Contact> contacts;

    public Task(int id, String description, GregorianCalendar initialDate, GregorianCalendar dueDate, Priority priority,
                Status status, String project, List<File> files, List<Contact> contacts) {

        super(id, description, status);
        this.initialDate = initialDate;
        this.dueDate = dueDate;
        this.priority=priority;
        this.project=project;
        this.files= new ArrayList<>();
        this.files.addAll(files);
        this.contacts= new ArrayList<>();
        this.contacts.addAll(contacts);
    }
    public Task(int id, String description, GregorianCalendar initialDate, GregorianCalendar dueDate, Priority priority,
                Status status, String project) {
        super(id, description, status);
        this.initialDate = initialDate;
        this.dueDate = dueDate;
        this.priority=priority;
        this.project= project;
        this.files= new ArrayList<>();
        this.contacts=new ArrayList<>();
    }
    public Task(int id, String description, GregorianCalendar dueDate, Priority priority, String project) {
        this(id, description,(GregorianCalendar) GregorianCalendar.getInstance(), dueDate, priority, Status.TO_DO, project);
    }


    public void addFiles(File file){
        files.add(new File(file.getId(), file.getTaskId(), file.getName(),file.getPath()));
    }
    public void addContacts(Contact contact){
        contacts.add(new Contact(contact.getId(), contact.getTaskId(), contact.getEmailAddress()));
    }
    public GregorianCalendar getInitialDate() {
        return initialDate;
    }

    public String getFormattedInitialDate(){
        String browserLanguage= TasksServlet.browserLanguage;
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale(browserLanguage));
        String formattedDate = df.format(new java.util.Date(initialDate.getTimeInMillis()));
        return formattedDate;
    }

    public GregorianCalendar getDueDate() {
        return dueDate;
    }

    public String getFormattedDueDate(){
        String browserLanguage= TasksServlet.browserLanguage;
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale(browserLanguage));
        String formattedDate = df.format(new java.util.Date(dueDate.getTimeInMillis()));
        return formattedDate;
    }

    public LocalDate getLocalDueDate(){
        return new Date(dueDate.getTimeInMillis()).toLocalDate();
    }

    public String getPriority() {
        return priority.toString().replace("_", " ");
    }
    public int getPriorityValue(){
        return priority.getValue();
    }

    public List<File> getFiles() {
        List<File> copy= new ArrayList<>();
        copy.addAll(files);
        return copy;
    }
    public List<Contact> getContacts() {
        List<Contact> copy= new ArrayList<>();
        copy.addAll(contacts);
        return copy;
    }

    public String getProject() {
        return project;
    }

    public boolean getIsOverdue(){
        GregorianCalendar today= new GregorianCalendar();
        return (dueDate.before(today)||dueDate.equals(today));
    }

    public boolean isItDueTomorrow(){
        GregorianCalendar today= new GregorianCalendar();

        int dueDateDay= dueDate.get(Calendar.DAY_OF_MONTH);
        int dueDateMonth= dueDate.get(Calendar.MONTH);
        int dueDateYear= dueDate.get(Calendar.YEAR);

        int todayDay= today.get(Calendar.DAY_OF_MONTH);
        int todayMonth= today.get(Calendar.MONTH);
        int todayYear= today.get(Calendar.YEAR);
        return (dueDateDay==(todayDay+1)&&dueDateMonth==todayMonth&&dueDateYear==todayYear);
    }
}
