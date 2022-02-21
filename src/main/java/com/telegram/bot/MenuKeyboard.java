package com.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;



public class MenuKeyboard {
    private static final String MY_UNCOMPLETED_TASKS = "\ud83d\uddc2\ufe0f To Do Task";
    private static final String MY_COMPLETED_TASKS = "\u2705 Task completati";
    private static final String MY_CHANGES = "\u267b\ufe0f Le mie modifiche";

    public static SendMessage SendMainKeyboardMenu(Update update){


        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        System.out.println("User id: " + update.getMessage().getChatId().toString() + " " + update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName());
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText( "Seleziona un commando dalla tastiera");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow keyboard1 = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        keyboard1.add(MY_UNCOMPLETED_TASKS);
        keyboard1.add(MY_COMPLETED_TASKS);
        // Add the first row to the keyboard
        keyboard.add(keyboard1);
        // Create another keyboard row



        KeyboardRow keyboardRow2 = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        keyboardRow2.add(MY_CHANGES);
        keyboard.add(keyboardRow2);



        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

}
