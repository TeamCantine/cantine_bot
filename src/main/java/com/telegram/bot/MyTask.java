package com.telegram.bot;

import org.jetbrains.annotations.NotNull;
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

        message.setText( "La tua lista dei task: ");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();


        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        // Lista di compiti
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("\ud83d\udcd7 Compito 1");
        b1.setCallbackData("\ud83d\udcd7");
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


    public static SendMessage getSingleTask( Update update){
        Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        SendMessage message = new SendMessage();

        message.setChatId(chat_id);

        message.setText( "Questa è una descrizione del task selezionato \nl - puo essere lungo un tot di caratteri \nl " +
                "  - , puo contener spazi ecc.." + "\n\n\n *In che stato vuoi mettere il tuo task??? ");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();


        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        // Lista di compiti
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("\u2714\ufe0f Completato");
        b1.setCallbackData("Completato");
        rowInline.add(b1);

        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("\u2611\ufe0f Completato con revisione");
        b2.setCallbackData("Completato con revisione");
        rowInline.add(b2);





        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        return message;
    }

}
