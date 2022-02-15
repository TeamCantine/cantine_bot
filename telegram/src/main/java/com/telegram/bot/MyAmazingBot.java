package com.telegram.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.ArrayList;
import java.util.List;



public class MyAmazingBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();

            if(message_text.equals("I miei Task")){

                MyTask.getTaskKeyboard();


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


                try {
                    execute(message); // Sending our message object to user
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields

            System.out.println("User id: " + update.getMessage().getChatId().toString());


            message.setChatId(update.getMessage().getChatId().toString());

            message.setText( " Hola");

            // Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("I miei Task");

            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row

            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            message.setReplyMarkup(keyboardMarkup);

            try {
                execute(message); // Sending our message object to user
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

        else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            String message_id = update.getCallbackQuery().getMessage().getMessageId().toString();
            String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();

            if (call_data.equals("update_msg_text")) {
                String answer = "Updated message text";
                try {
                    SendMessage mes =   new SendMessage();
                    mes.setText("Ciao");


                 //   execut();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }




    }

    @Override
    public String getBotUsername() {
        // TODO
        return "myamazingbot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5122590653:AAHxT90EEDOOQoNupWdhGPmRPQ9WYNC7Zj4";
    }




}