
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

class ClientGUI {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    JFrame frame;
    JTextArea chatArea;
    JTextField messageInput;
    JButton sendButton;
    JLabel heading;

    public ClientGUI() {
        try {
            System.out.println("Sending request to server...");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection established.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            createGUI();
            handleEvents();

            startReading();

        } catch (Exception e) {
            System.out.println("Connection Closed");
        }
    }

    private void createGUI() {

        frame = new JFrame("Client Chat");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        heading = new JLabel("Client Chat Application");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(heading, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageInput = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageInput, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleEvents() {

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contentToSend = messageInput.getText();
                if (!contentToSend.isEmpty()) {
                    chatArea.append("Me: " + contentToSend + "\n");
                    out.println(contentToSend);
                    messageInput.setText("");

                    if (contentToSend.equalsIgnoreCase("Exit")) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void startReading() {

        Runnable r1 = () -> {
            System.out.println("Reader Started...");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("Exit")) {
                        System.out.println("Server terminated the chat");
                        chatArea.append("Server terminated the chat\n");
                        socket.close();
                        break;
                    }
                    chatArea.append("Server: " + msg + "\n");
                }
            } catch (Exception e) {
                System.out.println("Connection Closed..");
            }
        };

        new Thread(r1).start();
    }

    public static void main(String[] args) {
        System.out.println("Client starting...");
        new ClientGUI();
    }
}
