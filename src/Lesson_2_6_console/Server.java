package Lesson_2_6_console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static Scanner sc = new Scanner(System.in);
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    private static String str = "";

    public static void main(String[] args) {
        socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8188)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread threadOut = new Thread(() -> {
                while(true){
                    serverMsg();
                }
            });
            threadOut.setDaemon(true);
            threadOut.start();

            while (true) {

                str = in.readUTF();

                System.out.println(str);
                if (str.equals("/end")) {
                    break;
                }
//             out.writeUTF("Эхо: " + str);
            }


        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public static void serverMsg() {
        try {
            String text = sc.next();
            out.writeUTF(socket.getLocalPort() + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


