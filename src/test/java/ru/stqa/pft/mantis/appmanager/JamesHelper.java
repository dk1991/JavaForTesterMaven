package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

    private ApplicationManager app;

    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    private Session mailSession;
    private Store store;
    private String mailServer;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        telnet = new TelnetClient(); // создание телнет клиента
        mailSession = Session.getDefaultInstance(System.getProperties()); // почтовая сессия
    }

    public boolean doesUserExist(String name) {
        initTelnetSession();
        write("verify " + name);
        String result = readUntil("exist");
        closeTelnetSession();
        return result.trim().equals("User " + name + " exist");
    }

    public void createUser(String name, String password) {
        initTelnetSession(); // установить соединение по протоколу telnet
        write("adduser " + name + " " + password); // команда на создание юзера (можно посмотреть в telnet)
        String result = readUntil("User " + name + " added"); // ждем, пока на консоли не появится надпись
        closeTelnetSession(); // закрыть соединение
    }

    public void deleteUser(String name) {
        initTelnetSession(); // установить соединение по протоколу telnet
        write("deluser " + name); // команда на удаление юзера
        String result = readUntil("User " + name + " deleted"); // ждем, пока на консоли не появится надпись
        closeTelnetSession(); // закрыть соединение
    }

    private void initTelnetSession() {
        mailServer = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");

        try {
            telnet.connect(mailServer, port); // установить соединение
            in = telnet.getInputStream(); // берем входной поток соединения для чтения
            out = new PrintStream(telnet.getOutputStream()); // выходной поток для записи в телнет
        } catch (Exception e) {
            e.printStackTrace();
        }

        // с первой попытки логин не работает в телнет
        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        // вторая попытка с нашими данными
        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    // по символьно читаем то, что выдает сервер и сравниваем с шаблоном
    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ввести в консоль telnet данные
    private void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTelnetSession() {
        write("quit");
    }

    // удалить все письма полученные каким-то юзером
    public void drainEmail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
        for (Message message : inbox.getMessages()) {
            message.setFlag(Flags.Flag.DELETED, true); // пометить флагом к удалению
        }
        closeFolder(inbox);
    }

    private void closeFolder(Folder folder) throws MessagingException {
        folder.close(true); // true - удалить все письма, помеченные к удалению
        store.close();
    }

    private Folder openInbox(String username, String password) throws MessagingException {
        store = mailSession.getStore("pop3");
        store.connect(mailServer, username, password);
        Folder folder = store.getDefaultFolder().getFolder("INBOX"); // по pop3 есть доступ ТОЛЬКО к этой папке
        folder.open(Folder.READ_WRITE);
        return folder;
    }

    public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
        long now = System.currentTimeMillis();
        while (System.currentTimeMillis() < now + timeout) {
            List<MailMessage> allMail = getAllMail(username, password);
            if (allMail.size() > 0) {
                return allMail;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail :(");
    }

    public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password); // для pop3 надо открыть сессию и закрыть
        List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream()
                .map((m) -> toModelMail(m)).collect(Collectors.toList());
        closeFolder(inbox); // для pop3 надо открыть сессию и закрыть
        return messages;
    }

    public static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
