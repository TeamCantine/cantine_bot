package com.telegram.bot;

import com.telegram.api.Change;
import com.telegram.api.ModifyTaskHelper;
import com.telegram.api.Task;
import com.telegram.api.Taskhelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MyTaskKeyboard {

    public static SendMessage getUncompletedTaskKeyboard(Update update){
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        message.setText( "Lista dei task non completati: \u274c");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        ArrayList<Task> myTask = Taskhelper.getMyUncompletedTask(update.getMessage().getChatId().toString());
        List<InlineKeyboardButton> rowInline = null;

         for (Task x : myTask){
             rowInline = new ArrayList<>();
             // Lista di compiti
             InlineKeyboardButton b1 = new InlineKeyboardButton();
             if(x.getStatus().equals("S"))
             b1.setText("\u270f\ufe0f"+ " " +x.getMsg());
             else
                 b1.setText("\u274c"+ " " +x.getMsg());

             b1.setCallbackData(x.getMsg() +"\u274c");
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

            b1.setText("\u2705"+ " " +x );

            b1.setCallbackData(x +"\u2705");
            rowInline.add(b1);
            rowsInline.add(rowInline);
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;

    }


    public static SendMessage getSingleTask( String chat_id, String dataMsg, String taskId){
       // String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        System.out.println("#####chatId: "+chat_id);
        System.out.println("#####DATA: "+dataMsg);
        String data = dataMsg;//update.getCallbackQuery().getData();
        String backupTaskId = data.substring(0, 1);
        if(!taskId.equals("")){
            backupTaskId = taskId;
        }
        ArrayList<String> myTaskDetail = Taskhelper.getMyTaskDetail(backupTaskId);
        SendMessage message = new SendMessage();
        message.setParseMode("HTML");
        message.setChatId(chat_id);
        String ms = "<b>Descrizione del task:</b> " + backupTaskId + "\n\n";
        for(String x : myTaskDetail){
            ms += x + "\n";
        }

        // Aggiungere anche le modifiche apportate
        List<Change> changes = ModifyTaskHelper.getChange(backupTaskId);
        if(!changes.isEmpty()){
            ms += "\n<b> Modifiche apportate:</b> \n\n";
            int i = 1;
           for(Change ch : changes){
               String msg = "<b>"+ i + ")</b> CAMPO= "+ ch.getFieldCode() + "  VECCHIO= " + ch.getOldValue() + "  NUOVO= "+ ch.getNewValue() + "\n";
               ms += msg;
               i++;
           }
        }

        // if doesnt exsists row of a task
        if(ms.equals("Descrizione del task \n\n")){
            ms += " Non esistono task!";
            message.setText(ms);
            return message;
        }
        // Sono nei completati non devo mostrare i 2 bottoni di conferma
        if(data.contains("\u2705")){
             ms += "\n <b>**</b> Vuoi modificare questo task come 'Da fare'? Clica su: /mettidafare"  + backupTaskId + "<b>**</b>" ;
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
        String callback = "CompletatoC" + backupTaskId;
        b1.setCallbackData(callback);
        rowInline.add(b1);

        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("\u2611\ufe0f Modifica");
        b2.setCallbackData("\u2611\ufe0f"+ backupTaskId);
        rowInline.add(b2);

        Task myStartedTask = Taskhelper.getMyStartedTask(backupTaskId);
        if(myStartedTask != null && !myStartedTask.getStatus().equals("S")){
            InlineKeyboardButton b3 = new InlineKeyboardButton();
            b3.setText("\u25b6\ufe0f Inizio");
            b3.setCallbackData("\u25b6\ufe0f"+ backupTaskId);
            rowInline.add(b3);
        }


        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        return message;
    }


    public static SendMessage getFieldsListKeyboard(Update update, String taskId) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setText( "Quale modifica vuoi apportare per il task: " +taskId + " ?" );

        //System.out.println(message);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<String> fieldsList = Taskhelper.getFieldsToModify();
        List<InlineKeyboardButton> rowInline = null;
       // System.out.println(fieldsList);
        int index = 0;
        String vaso = "\ud83c\udfee";
        String prodotto = "\ud83c\udf77";
        String operazione = "\u2699\ufe0f";
        for (String x : fieldsList){
            rowInline = new ArrayList<>();
            String icon = "";

            if(index==0)
                icon = vaso;
            else if(index == 1)
                icon = prodotto;
            else if (index == 2)
                icon = operazione;

            // Lista di compiti
            InlineKeyboardButton b1 = new InlineKeyboardButton();
            x=x.trim();
            b1.setText(icon+ " " +x);
            b1.setCallbackData(taskId + icon  );
            rowInline.add(b1);
            rowsInline.add(rowInline);
            index++;
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;

    }


    public static SendMessage getVasiVinarioKeyboard(Update update, String taskId) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setText( "Qual'è  il vaso vinaro da modificare per il task: " + taskId + "?" );

        //System.out.println(message);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<String> vasiList = ModifyTaskHelper.getVasoFromTask(taskId);
        List<InlineKeyboardButton> rowInline = null;

        for (String vasoOld : vasiList){
            rowInline = new ArrayList<>();
            // Lista di compiti
            String vasoIcon = "\ud83d\udcd9";
            InlineKeyboardButton b1 = new InlineKeyboardButton();
            vasoOld=vasoOld.trim();
            b1.setText(vasoIcon+ " " +vasoOld);

            b1.setCallbackData(vasoIcon +taskId+ " " +vasoOld);
            rowInline.add(b1);
            rowsInline.add(rowInline);
        }
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;

    }



}
