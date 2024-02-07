package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dictionary {
   private static List<String> dictionary;

    public static void buildDictionary (String fileName){
     try {
       dictionary = new ArrayList<>(Files.readAllLines(Paths.get(fileName)));
    }
 catch (IOException e) {

    }}

    public static boolean isWordInDic (String word) {
        boolean isWord = false;
        for (String dic: dictionary) {
            if(dic.equals(word)) {
               isWord = true;
                break;}
        } return isWord;
    }

    public static String chooseWord (char ch,UserData userData) {
        List <String> allOptional = new ArrayList<>();
        for (String dic:dictionary) {
            if (dic.charAt(0)==ch) {
              if (userData.isCognateWord(dic)==false)  allOptional.add(dic);
            }
        }
        ThreadLocalRandom r = ThreadLocalRandom.current();
        String word;
        if (allOptional.size()>1) {
            word = allOptional.get(r.nextInt(0, allOptional.size() - 1));
        }
        else {
            word = "Не могу придумать слово, ты выйграл!";
        }

            return word;
        }

    public static List<String> getDictionary() {
        return dictionary;
    }

}
