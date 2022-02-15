package com.telegram.bot;


import com.ibm.as400.vaccess.VSystemPool;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.ArrayList;
import java.util.List;



public class MyAmazingBot extends TelegramLongPollingBot {

    private static final String MY_TASKS = "I miei Task";

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            SendMessage message = new SendMessage();

            if(message_text.equals(MY_TASKS)){

                 message = MyTask.getTaskKeyboard(update);
                try {
                    execute(message);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // here goes the condition for showing the right menu keyboard based on the user type
             message = MenuKeyboard.SendMainKeyboardMenu(update);

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