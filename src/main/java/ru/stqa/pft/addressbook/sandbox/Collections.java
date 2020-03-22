package ru.stqa.pft.addressbook.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
    public static void main(String[] args) {
        /*String[] langs = new String[4];
        langs[0] = "Java";
        langs[1] = "C#";
        langs[2] = "Python";
        langs[3] = "PHP";*/
        String[] langs = {"Java", "C#", "Python", "PHP"};
        /*for (int i = 0; i < langs.length; i++) {
            System.out.println("I want to learn " + langs[i]);
        }*/

        /*for (String l : langs) {
            System.out.println("I wanna learn " + l);
        }*/

        List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP"/*langs*/);// new ArrayList<String>();
        /*languages.add("Java");
        languages.add("C#");
        languages.add("Python");
        languages.add("PHP");*/

        for (String l : languages) {
            System.out.println("I wanna learn " + l);
        }

        /*for (int i = 0; i < languages.size(); i++) {
            System.out.println("I want to learn " + languages.get(i));
        }*/
    }
}
