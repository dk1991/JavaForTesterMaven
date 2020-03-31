package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format: " + format);
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstName(String.format("Robot%s", i)).withLastName(String.format("LastName%s", i))
                    .withMobilePhone(phoneGenerator()).withHomePhone(phoneGenerator()).withWorkPhone(phoneGenerator())
                    .withAddress(String.format("Moscow Kievskaya st. %s-%s", i, i)).withEmail(emailGenerator())
                    .withEmail2(emailGenerator()).withEmail3(emailGenerator()));
        }
        return contacts;
    }

    private String phoneGenerator() {
        final int idLength = 9;
        final char[] charArray = "0123456789".toCharArray();
        final Random generator = ThreadLocalRandom.current();
        final StringBuilder uniqueId = new StringBuilder(idLength);
        uniqueId.append(8).append(9);
        for (int i = 0; i < idLength; i++) {
            uniqueId.append(charArray[generator.nextInt(charArray.length)]);
        }
        return uniqueId.toString();
    }

    private String emailGenerator() {
        final int idLength = 7;
        final char[] charArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final Random generator = ThreadLocalRandom.current();
        final StringBuilder uniqueId = new StringBuilder(idLength);
        for (int i = 0; i < idLength; i++) {
            uniqueId.append(charArray[generator.nextInt(charArray.length)]);
        }
        uniqueId.append("@").append("google.com");
        return uniqueId.toString();
    }
}
