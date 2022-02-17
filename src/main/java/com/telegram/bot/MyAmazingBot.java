package com.telegram.bot;


import com.telegram.api.Taskhelper;
import com.telegram.api.UserHelper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;


import static java.lang.Math.toIntExact;


public class MyAmazingBot extends TelegramLongPollingBot {



    private static final String MY_UNCOMPLETED_TASKS = "\ud83d\uddc2\ufe0f I miei Task";
    private static final String MY_COMPLETED_TASKS = "\u2705 Task completati";



    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

           String id = update.getMessage().getChatId().toString();
            System.out.println(id);
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
            message = MyTask.getUncompletedTaskKeyboard(update);
            try {
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(message_text.equals(MY_COMPLETED_TASKS)){
           // Taskhelper.getMyUncompletedTask( update.getMessage().getChatId().toString());
            message = MyTask.getCompetedTaskKeyboard(update);
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
                execute(MyTask.getSingleTask(update));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Completed
        else  if (call_data.contains("\u2705")) {
            try {
                execute(MyTask.getSingleTask(update));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(call_data.contains("CompletatoC")){
            try {
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come completato");
                System.out.println("Mi arriva: " + call_data.replace("CompletatoC", ""));
                Taskhelper.setTaskByIdCompleted(call_data.replace("CompletatoC", ""));
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(call_data.equals("Completato con revisione")){
            try {
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come Completato con revisione");

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