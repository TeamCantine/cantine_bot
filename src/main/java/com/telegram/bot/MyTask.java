package com.telegram.bot;

import com.telegram.api.Taskhelper;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MyTask {



    public static SendMessage getUncompletedTaskKeyboard(Update update){
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        message.setText( "Lista dei task non completati: \u274c");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        ArrayList<String> myTask = Taskhelper.getMyUncompletedTask(update.getMessage().getChatId().toString());
        List<InlineKeyboardButton> rowInline = null;

         for (String x : myTask){
             rowInline = new ArrayList<>();
             // Lista di compiti
             InlineKeyboardButton b1 = new InlineKeyboardButton();
             b1.setText("\u274c"+ " " +x);
             b1.setCallbackData(x +"\u274c");
             rowInline.add(b1);
             rowsInline.add(rowInline);
         }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    public static SendMessage getCompetedTaskKeyboard(Update update) {

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        message.setText( "Lista dei task completati: \u2705");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        ArrayList<String> myTask = Taskhelper.getMycompletedTask(update.getMessage().getChatId().toString());
        List<InlineKeyboardButton> rowInline = null;

        for (String x : myTask){
            rowInline = new ArrayList<>();
            // Lista di compiti
            InlineKeyboardButton b1 = new InlineKeyboardButton();
            b1.setText("\u2705"+ " " +x);
            b1.setCallbackData(x +"\u2705");
            rowInline.add(b1);
            rowsInline.add(rowInline);
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;


    }


    public static SendMessage getSingleTask( Update update){
        Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();

        String data = update.getCallbackQuery().getData();
        String backupData = data.substring(0, 1);
        ArrayList<String> myTaskDetail = Taskhelper.getMyTaskDetail(data.substring(0, 1));
        SendMessage message = new SendMessage();

      //  System.out.println(myTaskDetail);

        message.setChatId(chat_id);

        String ms = "Descrizione del task \n\n";

        for(String x : myTaskDetail){
            ms += x + "\n";
        }

        // if doesnt exsists row of a task
        if(ms.equals("Descrizione del task \n\n")){
            ms += " Non esistono task!";
            message.setText(ms);
            return message;
        }



// Sono nei completati non devo mostrare i 2 bottoni di conferma
        if(data.contains("\u2705")){

ms += "\n  Vuoi modificare questo task come 'Da fare'? Clica su: '/mettidafare"  +backupData + "'" ;
            message.setText(ms);
            return message;
        }

        message.setText(ms);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        // Lista di compiti
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("\u2714\ufe0f Completato");
        String callback = "CompletatoC" + backupData;
        System.out.println("My callback is: " + callback);
        b1.setCallbackData(callback);
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
