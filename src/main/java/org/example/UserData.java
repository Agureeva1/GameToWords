package org.example;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private char firstSignNextWord;
    private List<String> allWordsList;

    public UserData() {
        this.allWordsList = new ArrayList<>();
        this.firstSignNextWord = '1';
    }

    public char getFirstSignNextWord() {
        return firstSignNextWord;
    }

    public void setFirstSignNextWord() {
        this.firstSignNextWord = getLastSign();
    }

    public List<String> getAllWordsList() {
        return allWordsList;
    }

    public Character getLastSign() {
        String word = allWordsList.get(allWordsList.size() - 1).toString();
        char lastSign = word.charAt(word.length() - 1);
        char beforLastSign = word.charAt(word.length() - 2);
        switch (lastSign) {
            case 'ъ':
                lastSign = beforLastSign;
            case 'ы':
                lastSign = beforLastSign;
            case 'ь':
                lastSign = beforLastSign;
        }

        return lastSign;
    }

    public boolean isCognateWord(String word) {
        boolean result = false;
        if (word.length() >= 6) {
            String firstPartWord = word.substring(0, 6);
            for (String every : this.allWordsList) {
                if (every.contains(firstPartWord))
                {
                    result = true;
                    break;
                }
            }
        }
        if (word.length() < 6) {
            for (String every : this.allWordsList) {
                if (every.equals(word)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
