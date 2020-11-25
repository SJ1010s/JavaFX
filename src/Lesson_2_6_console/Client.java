package Lesson_2_6_console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client {
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8188;

    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Client evgeniy = new Client();
        evgeniy.initialize();
    }

    public void initialize() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread threadOut = new Thread(() -> {
                            while(true) {
                                sendMsg();
                            }
                        });
                        threadOut.setDaemon(true);
                        threadOut.start();

                        while (true) {

                            String str = null;
                            str = in.readUTF();

                            if (str.equals("/end")) {
                                System.out.println("Клиент отключился");
                                break;
                            }

                            System.out.println("Клиент: " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Мы отключились от сервера");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsg() {
        try {
            String text = sc.next();
            out.writeUTF(socket.getLocalPort() + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

