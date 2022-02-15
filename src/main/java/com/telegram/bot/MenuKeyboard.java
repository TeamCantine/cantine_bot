package com.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;



public class MenuKeyboard {
    private static final String MY_TASKS = "I miei Task";

    public static SendMessage SendMainKeyboardMenu(Update update){


        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields

        System.out.println("User id: " + update.getMessage().getChatId().toString() + " " + update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName());


        message.setChatId(update.getMessage().getChatId().toString());

        message.setText( "Seleziona un cammando dalla tastiera");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add(MY_TASKS);

        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

}
