package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

import static org.example.Dictionary.chooseWord;
import static org.example.Dictionary.isWordInDic;


public class Bot extends TelegramLongPollingBot {
    private final String Bot_Username = "WtoWannabot";
    private final String Bot_Token = "6876567457:AAER-zz2SgZnDskgTfuCKUm5N0NmLjYP3Rg";
    private HashMap<Long, UserData> users;

    public Bot() {
        this.users = new HashMap<>();
    }

    @Override
    public String getBotUsername() {
        return Bot_Username;
    }

    @Override
    public String getBotToken() {
        return Bot_Token;
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText().toLowerCase();
        long userId = message.getFrom().getId();

        if (text.equals("/start")) {
            users.put(userId, new UserData());
            sendText(userId, "Привет! Я умею играть в слова. Начнем? Напиши слово");
        } else {
            UserData userData = users.get(userId);
            if (isWordInDic(text)) {

                if (userData.getFirstSignNextWord() == '1') {
                    userData.getAllWordsList().add(text);
                    userData.setFirstSignNextWord();
                    String botText = chooseWord(userData.getFirstSignNextWord(), userData);
                    userData.getAllWordsList().add(botText);
                    sendText(userId, "Отлично. Мое слово " + botText.toUpperCase());
                    userData.setFirstSignNextWord();
                } else {
                    if (text.charAt(0) == userData.getFirstSignNextWord()) {
                        if (userData.isCognateWord(text) == false) {
                            userData.getAllWordsList().add(text);
                            userData.setFirstSignNextWord();
                            String botText = chooseWord(userData.getFirstSignNextWord(), userData);
                            //word = "Не могу придумать слово, ты выйграл!";
                            //word.length()=36
                            if (botText.length() == 36) {
                                sendText(userId, botText);
                            } else {
                                userData.getAllWordsList().add(botText);
                                sendText(userId, "Отлично. Мое слово " + botText.toUpperCase());
                                userData.setFirstSignNextWord();
                            }

                        } else if (userData.isCognateWord(text) == true) {
                            sendText(userId, "Такое слово уже было. Однокоренные слова тоже не принимаю");
                        }

                    } else if (text.charAt(0) != userData.getFirstSignNextWord()) {
                        sendText(userId, "Тебе слово на =" + userData.getFirstSignNextWord() + "=, попробуй еще раз");
                    }
                }
            } else {
                sendText(userId, "Я не знаю такого слова, попробуй еще раз");
            }
        }
    }
}



