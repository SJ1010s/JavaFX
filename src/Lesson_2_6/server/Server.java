package Lesson_2_6.server;

import Lesson_2_6_console.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {

    private static int PORT = 8191;
    ServerSocket server = null;
    Socket socket = null;
    List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void broadCastMsg(ClientHandler sender, String msg) {
        String message = String.format("%s : %s", sender.getNickname(), msg);
        for (ClientHandler client : clients) {
            client.sendMsg(message + "\n");
        }
    }

    void personalBroadCastMsg(ClientHandler sender, String msg){
       // Boolean nickNameTrue = false;
        String[] msgFormat = msg.split("\\s", 3);
        String message = String.format("%s от %s : %s", "Лично", sender.getNickname(), msgFormat[2]);
        for (ClientHandler client : clients){
            if ((client.getNickname()).equals(msgFormat[1])){
                client.sendMsg(message + "\n");
                sender.sendMsg(message + "\n");
   //             nickNameTrue = true;
                break;
            }
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public AuthService getAuthService(){
        return authService;
    }

}
