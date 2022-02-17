package com.telegram.bot;


import com.telegram.api.Taskhelper;
import com.telegram.api.UserHelper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class MyAmazingBot extends TelegramLongPollingBot {



    private static final String MY_UNCOMPLETED_TASKS = "\ud83d\uddc2\ufe0f To Do Task";
    private static final String MY_COMPLETED_TASKS = "\u2705 Task completati";
    private static final String VASO = "\ud83c\udfee";
    private static final String PRODOTTO = "\ud83c\udf77";
    private static final String OPERAZIONE = "\u2699\ufe0f";
    private static final String VASO_ICON = "\ud83d\udcd9";


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

        // Uncompleted
        if (call_data.contains("\u274c")) {
            try {
                execute(MyTaskKeyboard.getSingleTask(update));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Completed
        else  if (call_data.contains("\u2705")) {
            try {
                execute(MyTaskKeyboard.getSingleTask(update));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(call_data.contains("CompletatoC")){
            try {
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come completato");
                Taskhelper.setTaskByIdCompleted(call_data.replace("CompletatoC", ""));
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Show field list
        else if(call_data.contains("\u2611\ufe0f")){
            try {
               String taskId = call_data.replace("\u2611\ufe0f","");
                SendMessage message = new SendMessage();
               // execute(message);
                message = MyTaskKeyboard.getFieldsListKeyboard(update, taskId);
               execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Modifica campo Vaso vanario
        else if (call_data.contains(VASO)){
            try {
                String taskId = call_data.replace(VASO,"");
                execute(MyTaskKeyboard.getVasiVinarioKeyboard(update, taskId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Modifica campo prodotto
        else if(call_data.contains(PRODOTTO)){
            try {
            SendMessage message = new SendMessage();
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText("Non è ancora gestito. Portate pazienza!");
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Modifica campo operazione
        else if(call_data.contains(OPERAZIONE)){
                try {
            SendMessage message = new SendMessage();
            message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            message.setText("Non è ancora gestito. Portate pazienza!");
                    execute(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        // Ho selezionato il vaso vecchio e chiedo il nuovo
        else if(call_data.contains(VASO_ICON)){

            try {
                String concat = call_data.replace(VASO_ICON,"");
                String[] currencies = concat.split(" ");
                String taskId = currencies[0];
                String valoOld = currencies[1];

                SendMessage message = new SendMessage();
                message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());

                String g = "";
                for (int i =0; i<50; i++ ){
                    g += "'/D_VAS_" + taskId+"_"+ valoOld +  i + "'  ";
                }

                message.setText("Scegli il vaso di destinazione: \n\n\n" +   g );
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
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
        return "5174941088:AAEFuzWWNKPwyyJQ_M53WlxRhoWrKVgsPXM";
    }

}