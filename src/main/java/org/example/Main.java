package org.example;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.example.Dictionary.getDictionary;

public class Main {
    public static void main(String[] args) throws TelegramApiException {

        Dictionary dic = new Dictionary();
        dic.buildDictionary("dic");
        List<String> allWord = new ArrayList<>();
        for (String d : getDictionary()) {
            char fest = d.charAt(0);
            char last = d.charAt(d.length() - 1);

            if (fest == 'а' && last == 'й') {
                allWord.add(d);
                System.out.println(d);
            }

        }

    }
}