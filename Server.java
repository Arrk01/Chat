
import java.io.*;
import java.net.*;

class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready");
            System.out.println("Waiting.....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader Started...");

            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("Exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Client : " + msg);

                }
            } catch (Exception e) {
                System.out.println("Connection Closed..");
            }

        };

        new Thread(r1).start();

    }

    public void startWriting() {
        System.out.println("Writer Started..");
        Runnable r2 = () -> {

            try {

                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("Exit")) {
                        socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("Connection Closed..");
            }

        };

        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("Server started...");
        new Server();
    }
}
