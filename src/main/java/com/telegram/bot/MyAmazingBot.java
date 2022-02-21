package com.telegram.bot;


import com.telegram.api.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class MyAmazingBot extends TelegramLongPollingBot {



    private static final String MY_UNCOMPLETED_TASKS = "\ud83d\uddc2\ufe0f To Do Task";
    private static final String MY_STARTED_TASKS = "\ud83d\uddc2\ufe0f";
    private static final String MY_COMPLETED_TASKS = "\u2705 Task completati";
    private static final String VASO = "\ud83c\udfee";
    private static final String PRODOTTO = "\ud83c\udf77";
    private static final String OPERAZIONE = "\u2699\ufe0f";
    private static final String VASO_ICON = "\ud83d\udcd9";
    private static final String MY_CHANGES = "\u267b\ufe0f Le mie modifiche";

    @Override
    public void onUpdateReceived(Update update) {
      //  System.out.println(update.getMessage());
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

           String id = update.getMessage().getChatId().toString();

           // se null user non esiste
            String user = UserHelper.getUser(id, update);
            if(user == null){
                String message_text = update.getMessage().getText();
                SendMessage message = new SendMessage();
                message.setChatId(id);
                message.setText("Utente non abilitato");
                try {
                    execute(message);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(user.equals("inserito")){
                SendMessage message = new SendMessage();
                message.setChatId(id);
                message.setText("Benvenuto " + update.getMessage().getChat().getFirstName() + "\n Adesso puoi usare il bot");
                try {
                    execute(message);
                //    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //authenticate user first
            performTextMessage(update);
    }
        else if (update.hasCallbackQuery()) {
            //authenticate user first
            performCallBackMessage(update);
        }

    }


    /**
     * Match text only
     * @param update
     */
    public void performTextMessage(Update update){
        String message_text = update.getMessage().getText();
        SendMessage message = new SendMessage();

        String chat_id = update.getMessage().getChatId().toString();
        Pending pendingTask = PendingHelper.getPendingTask(update.getMessage().getChatId().toString());

        // Se l'utente ha un task in pending, devo fine l'operazione
        if(pendingTask != null && !message_text.equals("/Annulla_Operazione")){

            message.setChatId(update.getMessage().getChatId().toString());
            // Se l'utente sta premendo qualcosa dalla keyboard principale
            if(message_text.equals(MY_UNCOMPLETED_TASKS) || message_text.equals(MY_COMPLETED_TASKS) || message_text.equals(MY_CHANGES)){
                String info = "Se vuoi annullare l'operazione del " + pendingTask.getOperation() + ", clicca su:  /Annulla_Operazione,  altrimenti scrivere il nome del vaso e inviare per completare l'operazione...";
                message.setText(info);
            }
            else
            {
                String taskId = pendingTask.getTaskId();
                String vasoOld = pendingTask.getOldValue();
                String vasoNew = message_text;

                    if(ModifyTaskHelper.updateVaso(taskId,vasoOld,vasoNew)){
                        message.setText("Ok "+ update.getMessage().getChat().getFirstName()+ " ho modificato il vaso con successo! " );
                        PendingHelper.deletePendingTask(chat_id);
                        try {
                            execute(message);
                            SendMessage mms = MyTaskKeyboard.getSingleTask(chat_id,message_text,taskId);
                            execute(mms);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        message.setText("Non è stato possibile completare la tua operazione!");
                    }

            }
            try {
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(message_text.equals(MY_UNCOMPLETED_TASKS)){
            message = MyTaskKeyboard.getUncompletedTaskKeyboard(update);
            try {
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(message_text.equals(MY_COMPLETED_TASKS)){
           // Taskhelper.getMyUncompletedTask( update.getMessage().getChatId().toString());
            message = MyTaskKeyboard.getCompetedTaskKeyboard(update);
            try {
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Devo annullare l'operazione
        else if(message_text.equals("/Annulla_Operazione")){
            PendingHelper.deletePendingTask(update.getMessage().getChatId().toString());
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Operazione annullata con successo!");
            try{
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (message_text.contains("/mettidafare")){
            Taskhelper.setTaskByIdNotCompleted(message_text.replace("/mettidafare", ""));
            try {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Ok "+ update.getMessage().getChat().getFirstName()+ " modifico il task come 'Da Fare");

                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        else if(message_text.startsWith("/_____")){

            String a = message_text.replace("/_____", "");
            String[] b = a.split("_____");
            String taskId = b[0];
            String vasoOld = b[1];
            String vasoNew = b[2];
            message.setChatId(update.getMessage().getChatId().toString());

            try {
            if(ModifyTaskHelper.updateVaso(taskId,vasoOld,vasoNew)){
                message.setText("Ok "+ update.getMessage().getChat().getFirstName()+ " ho modificato il tuo vaso con successo! [" +vasoOld+ " --->" +  vasoNew +"]" );
            }
            else
            {
                message.setText("Non è stato possibile completare la tua operazione!");
            }
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        */
        else if(message_text.equals(MY_CHANGES)) {
            message.setChatId(update.getMessage().getChatId().toString());
            List<String> changes = ModifyTaskHelper.showChanges(update.getMessage().getChatId().toString());
            String all = "";
            for (String i : changes) {
                all += i;
            }

            message.setText(" <b>Queste sono le tue modifiche finora:</b> \n\n" + all);
            try {

                message.setParseMode("html");
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


    /**
     * Match Callback only
     * @param update
     */
    public void performCallBackMessage(Update update){
        // Set variables
        String call_data = update.getCallbackQuery().getData();
        Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
        String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
        SendMessage message = new SendMessage();
        // Uncompleted
        if (call_data.contains("\u274c")) {
           message = MyTaskKeyboard.getSingleTask(chat_id,call_data, "");
        }
        // Completed
        else  if (call_data.contains("\u2705")) {
            message = MyTaskKeyboard.getSingleTask(chat_id,call_data,"");
        }
        else if(call_data.contains("CompletatoC")){
                message.setChatId(chat_id);
                message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come completato");
                Taskhelper.setTaskByIdCompleted(call_data.replace("CompletatoC", ""));
        }




        // Inizio il task
        else if(call_data.contains("\u25b6\ufe0f")){
            message.setChatId(chat_id);
            message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come iniziato");
            Taskhelper.setTaskByIdStartWorking(call_data.replace("\u25b6\ufe0f", ""));
        }




        // Show field list
        else if(call_data.contains("\u2611\ufe0f")){
               String taskId = call_data.replace("\u2611\ufe0f","");
                message = MyTaskKeyboard.getFieldsListKeyboard(update, taskId);
        }
        // Modifica campo Vaso vanario
        else if (call_data.contains(VASO)){
                String taskId = call_data.replace(VASO,"");
                message = MyTaskKeyboard.getVasiVinarioKeyboard(update, taskId);
        }
        // Modifica campo prodotto
        else if(call_data.contains(PRODOTTO)){
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText("Non è ancora gestito. Portate pazienza!");
        }
        // Modifica campo operazione
        else if(call_data.contains(OPERAZIONE)){
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText("Non è ancora gestito. Portate pazienza!");
        }
        // Ho selezionato il vaso vecchio e chiedo il nuovo

        else if(call_data.contains(VASO_ICON)){
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText("Scrivimi il vaso di destinazione: \n");

            String concat = call_data.replace(VASO_ICON,"");
            String[] currencies = concat.split(" ");
            String taskId = currencies[0];
            String valoOld = currencies[1];
            System.out.println("taskId " +taskId);
            System.out.println("valoOld " +valoOld);
            System.out.println("IDDDDD: "+ update.getCallbackQuery().getMessage().getChatId().toString());


                PendingHelper.deletePendingTask(update.getCallbackQuery().getMessage().getChatId().toString());
                PendingHelper.insertPending(update.getCallbackQuery().getMessage().getChatId().toString(),
                        taskId, "Vaso", valoOld );

        }


        // Execute only the message object
        try {
            System.out.println("Finale " +call_data);
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
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
        return "5174941088:AAEFuzWWNKPwyyJQ_M53WlxRhoWrKVgsPXM";
    }

}