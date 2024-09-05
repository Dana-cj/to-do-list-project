package ro.exampledana.entity;

public class Contact {

    private int id;
    private int taskId;

    private String emailAddress;


    public Contact(int id, int taskId, String emailAddress) {
        this.id = id;
        this.taskId = taskId;
        this.emailAddress=emailAddress;
    }

    public int getId() {
        return id;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

}
