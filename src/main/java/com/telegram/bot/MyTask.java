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
        InlineKeyboardButton c = new InlineKeyboardButton();
        c.setText("Compito 1");
        c.setCallbackData("compito1");
        rowInline.add(c);

        InlineKeyboardButton d = new InlineKeyboardButton();
        d.setText("Compito 2");
        d.setCallbackData("compito2");

        rowInline.add(d);


        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        return message;
    }

}
