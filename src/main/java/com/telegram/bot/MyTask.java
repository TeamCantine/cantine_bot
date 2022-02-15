package com.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MyTask {

    public static SendMessage getTaskKeyboard(Update update){
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        message.setText( "Lista dei task:  1) ... 2) ...");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();


        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        // Lista di compiti
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("Compito 1");
        b1.setCallbackData("compito1");
        rowInline.add(b1);

        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("Compito 2");
        b2.setCallbackData("compito2");
        rowInline.add(b2);


       // Second line
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        // Lista di compiti
        InlineKeyboardButton b3 = new InlineKeyboardButton();
        b3.setText("Compito 3");
        b3.setCallbackData("compito3");
        rowInline2.add(b3);

        InlineKeyboardButton b4 = new InlineKeyboardButton();
        b4.setText("Compito 4");
        b4.setCallbackData("compito4");

        rowInline2.add(b4);


        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        return message;
    }

}
