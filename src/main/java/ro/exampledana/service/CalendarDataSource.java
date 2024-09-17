package ro.exampledana.service;
import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDataSource implements DataSource {
    private String icsContent;

    public CalendarDataSource(String icsContent) {
        this.icsContent = icsContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(icsContent.getBytes("UTF-8"));
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getContentType() {
        return "text/calendar; charset=UTF-8; method=REQUEST";
    }

    @Override
    public String getName() {
        return "calendar.ics";
    }


    // Create the ICS file content (iCalendar format)
    public static String createICSFileContent(String taskDescription, GregorianCalendar dueDate) {
        String day=""+dueDate.get(Calendar.DAY_OF_MONTH);
        day=(day.length()==1)?"0"+day:day;
        String month="0"+(dueDate.get(Calendar.MONTH)+1);
        month=(month.length()==1)?"0"+month:month;
        String year=""+dueDate.get(Calendar.YEAR);

        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "CALSCALE:GREGORIAN\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:"+year+month+day+"T090000Z\n" +
                "DTEND:"+year+month+day+"T100000Z\n" +
                "SUMMARY:"+taskDescription+"\n" +
                "DESCRIPTION:"+taskDescription+"\n" +
                "LOCATION: \n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";

    }

}