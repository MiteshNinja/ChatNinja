package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    public static Boolean isServer = false;
    public static Boolean isClient = false;
    public static ChatServer server;
    public static ChatClient client;
    public Main mainApp;

//    public static void getController(){

    @FXML
    TextField inputMessage, serverAdd;

    @FXML
    Button buttonConnectServer, buttonSendMessage, buttonStartServer;
    @FXML
    Label outputMessage;
    public void startServer(ActionEvent event){
        System.out.println("startServer clicked.");
        if(isClient || isServer) {
            return;
        }
        isServer = true;
//        server = new ChatServer();
//        server.start();
//        System.out.println("Server Started.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server Starting..");
                server = new ChatServer();
                server.start();
                System.out.println("Server Started.");
            }
        }).start();
        outputMessage.setText("Server started.");
    }

    public void connectServer(ActionEvent event){
        if(isClient || isServer)
            return;
        System.out.println("connectServer Clicked");
        isClient = true;
        client = new ChatClient();
        client.connect(serverAdd.getText());
        outputMessage.setText("Connected to: " + inputMessage.getText());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                client = new ChatClient();
//                client.connect(serverAdd.getText());
//            }
//        });
    }

    public void sendMessage(ActionEvent event){
        System.out.println("sendMessage Clicked");
        client.sendMessage(inputMessage.getText());
        outputMessage.setText(inputMessage.getText());
    }

    public void updateMessageWindow(String message){
        outputMessage.setText(message);
    }

    //    }
//        return Controller;
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        outputMessage.setText(mainApp.getMessage());
    }
}
