package ro.exampledana.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import ro.exampledana.servlet.TasksServlet;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class GmailService {
    private final Gmail gmailService;
    private static final String APPLICATION_NAME = "to-do-list";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    protected static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final File DATA_STORE_DIR = new File(System.getProperty("catalina.base"), "/bin/tokens");

    public GmailService() throws Exception {
        this.gmailService = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build();
        //LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888)
                .setCallbackPath("/Callback")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }


    public void sendEmail(String to, String from, String subject, String bodyText, String icsContent, List<String> files) throws MessagingException, IOException {
        MimeMessage email = createEmail(to, "me", subject, bodyText, icsContent, files);
        sendMessage(gmailService, "me", email);
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText,String icsContent, List<String> files) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
       // email.setText(bodyText);
        // Create multipart email content
        Multipart multipart = new MimeMultipart();

        // Body part for the plain text content of the email
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(bodyText);
        multipart.addBodyPart(textPart);

        // Body part for the ICS file (calendar invite)
        MimeBodyPart icsPart = new MimeBodyPart();
        icsPart.setDataHandler(new DataHandler(new CalendarDataSource(icsContent)));
        icsPart.setFileName("invite.ics");  // Set file name for ICS file

        // Add ICS part to the multipart
        multipart.addBodyPart(icsPart);

        // Body part for another file attachment (e.g., a PDF or image)
        if(files!=null&&files.size()>0) {
            for (String fileName : files) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new FileDataSource(TasksServlet.UPLOAD_PATH + fileName);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(fileName);
                multipart.addBodyPart(attachmentPart);
            }
        }

        // Set the email content
        email.setContent(multipart);
        return email;
    }

    private void sendMessage(Gmail service, String userId, MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        service.users().messages().send("me", message).execute();
    }
}
