package com.telegram.bot;


import com.telegram.api.*;
import com.telegram.qrcode.ReadQRCodeFromFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


import java.net.*;
import java.io.*;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.common.net.HttpHeaders.USER_AGENT;

import java.net.*;
import java.io.*;
public class MyAmazingBot extends TelegramLongPollingBot {

private static final String botToken = "5174941088:AAEFuzWWNKPwyyJQ_M53WlxRhoWrKVgsPXM";
// non mio
private static final String botToken1 = "5122590653:AAHxT90EEDOOQoNupWdhGPmRPQ9WYNC7Zj4";

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
        else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            System.out.println("Arrivato foto");
            performPhotoMessage(update);
        }
        else if(update.hasMessage() && update.getMessage().hasVoice()){
            System.out.println("Arrivato voce");
            performVoiceMessage(update);
        }

    }

    private void performVoiceMessage(Update update) {

        String chat_id = update.getMessage().getChatId().toString();
        Voice voice = update.getMessage().getVoice();
        String f_id = voice.getFileId();
        SendMessage mms = new SendMessage();
        File file = null;
        //GetFile getFile = new GetFile().setFileId(fileId);
        try {
            String filePath = execute(new GetFile(f_id)).getFileUrl(botToken);
            System.out.println(filePath);

         String finalUrl = "http://10.100.0.30:5000/api?urlTel="+filePath;

            mms.setChatId(chat_id);
            mms.setText("Attendere... Stiamo elaborando la vostra richiesta.....");
            execute(mms);

/*
            URL yahoo = new URL(finalUrl);

            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
*/

            URL obj = new URL(finalUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                String finale = response.toString().trim().toUpperCase(Locale.ROOT) ;
                System.out.println( finale);

                String task = "";

                String[] bits = finale.split("TASK");
                task = (bits[bits.length - 1]).trim();

                String smile = "\n Non ci credi? Vai a vedere! \uD83D\uDE09";

                if(finale.contains("COMPLETA")){
                   if(!task.matches("-?\\d+(\\.\\d+)?")) {
                       mms.setText("Non ho capito bene, prova a dire ad esempio: \n\n"
                               +"1) Completa task 100 \n2) Annulla task 100 \n"+
                               "3) Inizia task 100 \n4) Dettaglio task 100");
                   }
                       else {

                           if(Taskhelper.getMyStartedTask(task) == null){
                               mms.setText("Task " + task + " non esistente nei tuoi task TODO. Prova con un altro");
                           }
                           else {

                               mms.setText("Ok " + update.getMessage().getChat().getFirstName() + " ho assegnato il task " + task + " come completato." + smile);
                               Taskhelper.setTaskByIdCompleted(task);
                           }
                   }
                }
                else if(finale.contains("ANNULLA")){
                    if(!task.matches("-?\\d+(\\.\\d+)?")) {
                        mms.setText("Non ho capito bene, prova a dire ad esempio: \n\n"
                                +"1) Completa task 100 \n2) Annulla task 100 \n"+
                                "3) Inizia task 100 \n4) Dettaglio task 100");
                    }
                    else {
                      Task taskObj=  Taskhelper.getMyStartedTask(task);
                        if(taskObj != null && !taskObj.getStatus().equals("C")){
                            mms.setText("Task " + task + " non esistente nei tuoi task COMPLETATI. Prova con un altro");
                        }
                        else {
                            mms.setText("Ok " + update.getMessage().getChat().getFirstName() + " ho assegnato il task " + task + " come ANNULLATO." + smile);
                            Taskhelper.setTaskByIdNotCompleted(task);
                        }
                    }
                }
                else if(finale.contains("INIZIA")){
                    if(!task.matches("-?\\d+(\\.\\d+)?")) {
                        mms.setText("Non ho capito bene, prova a dire ad esempio: \n\n"
                                +"1) Completa task 100 \n2) Annulla task 100 \n"+
                                "3) Inizia task 100 \n4) Dettaglio task 100");
                    }
                    else {
                        Task taskObj=  Taskhelper.getMyStartedTask(task);
                        if(taskObj != null && !taskObj.getStatus().trim().isEmpty()){
                            mms.setText("Task " + task + " non esistente nei tuoi task COMPLETATI.Prova con un altro");
                        }
                        else {
                            mms.setText("Ok " + update.getMessage().getChat().getFirstName() + " ho INIZIATO il task " + task + "." + smile );
                            Taskhelper.setTaskByIdStartWorking(task);
                        }
                    }
                }
                else if(finale.contains("DETTAGLI")){
                    if(!task.matches("-?\\d+(\\.\\d+)?")) {
                        mms.setText("Non ho capito bene, prova a dire ad esempio: \n\n"
                                +"1) Completa task 100 \n2) Annulla task 100 \n"+
                                "3) Inizia task 100 \n4) Dettaglio task 100");
                    }
                    else {
                        ArrayList<String> taskArray= Taskhelper.getMyTaskDetail(task);
                        if(taskArray.isEmpty()){
                            mms.setText("Task " + task + " non ha nessun dettaglio");
                        }
                        else {
                            mms.setText("Ok " + update.getMessage().getChat().getFirstName() +
                                    " questi sono i dettagli del task " + task + "\n" + taskArray);

                        }
                    }
                }
                else
                    mms.setText("Non ho capito bene, prova a dire ad esempio: \n\n"
                            +"1) Completa task 100 \n2) Annulla task 100 \n"+
                            "3) Inizia task 100 \n4) Dettaglio task 100");




                mms.setChatId(chat_id);
                execute(mms);
                return;



               // System.out.println(finale);
            } else {
                System.out.println("GET request not worked");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        //String filePath = execute(getFile).getFilePath();
        //File file = downloadFile(filePath, outputFile);


    }


    private void performPhotoMessage(Update update)  {


        String chat_id = update.getMessage().getChatId().toString();
        List<PhotoSize> photos  = update.getMessage().getPhoto();

        String f_id = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();


        try {
            String filePath =  execute(new GetFile(f_id)).getFileUrl(botToken);
            System.out.println(filePath);
            String qrcode = ReadQRCodeFromFile.getQrcode(filePath);
            System.out.println(qrcode);



            Pending pendingTask = PendingHelper.getPendingTask(update.getMessage().getChatId().toString());

            String taskId = pendingTask.getTaskId();
            String vasoOld = pendingTask.getOldValue();
            String vasoNew = qrcode;
            SendMessage message = new SendMessage();
            message.setChatId(chat_id);


            if(vasoNew == null){
                try {
                    message.setText("Non sono riuscito a leggere bene l'immagine. \n Riprova a mandare un'altra... \n");
                    execute(message);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if(ModifyTaskHelper.updateVaso(taskId,vasoOld,vasoNew)){
                message.setText("Ok "+ update.getMessage().getChat().getFirstName()+ " ho modificato il vaso con successo! " );
                PendingHelper.deletePendingTask(chat_id);
                try {
                    execute(message);
                    SendMessage mms = MyTaskKeyboard.getSingleTask(chat_id,vasoNew,taskId);
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





        } catch (Exception e) {
            e.printStackTrace();
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
            message = MyTaskKeyboard.getUncompletedTaskKeyboard(update.getMessage().getChatId().toString());
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
        else if (message_text.contains("/riattiva_Task")){
            Taskhelper.setTaskByIdNotCompleted(message_text.replace("/riattiva_Task", ""));
            try {
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText("Ok "+ update.getMessage().getChat().getFirstName()+ " riporto il task nella lista 'To Do");
                execute(message);
                message = MyTaskKeyboard.getUncompletedTaskKeyboard(chat_id);
                execute(message);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
        if (call_data.contains("\ud83d\udccb")) {
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

            try {
                execute(message);
                message = MyTaskKeyboard.getUncompletedTaskKeyboard(chat_id);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        // Inizio il task
        else if(call_data.contains("\u25b6\ufe0f")){
            message.setChatId(chat_id);
            message.setText("Ok "+ update.getCallbackQuery().getMessage().getChat().getFirstName() + " assegno il task come iniziato");
            Taskhelper.setTaskByIdStartWorking(call_data.replace("\u25b6\ufe0f", ""));
            try {
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

            message = MyTaskKeyboard.getUncompletedTaskKeyboard(chat_id);

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
            message.setText("Digitare il codice del vaso di destinazione, \n o in alternativa mandami una foto del QRCODE \n");

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
        return botToken;
    }

}