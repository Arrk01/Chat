
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ServerG implements ActionListener {

    JTextField text;
    JPanel a1;

    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    static JFrame f = new JFrame();

    ServerG() {

        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);

        f.add(p1);

        ImageIcon iback = new ImageIcon(ClassLoader.getSystemResource("Back.png"));
        Image ib2 = iback.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon ib3 = new ImageIcon(ib2);
        JLabel back = new JLabel(ib3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        ImageIcon ipic = new ImageIcon(ClassLoader.getSystemResource("Pic.png"));
        Image ip2 = ipic.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon ip3 = new ImageIcon(ip2);
        JLabel pic = new JLabel(ip3);
        pic.setBounds(30, 10, 50, 50);
        p1.add(pic);

        ImageIcon icall = new ImageIcon(ClassLoader.getSystemResource("call.png"));
        Image ic2 = icall.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon ic3 = new ImageIcon(ic2);
        JLabel call = new JLabel(ic3);
        call.setBounds(350, 20, 30, 30);
        p1.add(call);

        ImageIcon ivcall = new ImageIcon(ClassLoader.getSystemResource("vcall.png"));
        Image ivc2 = ivcall.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon ivc3 = new ImageIcon(ivc2);
        JLabel vcall = new JLabel(ivc3);
        vcall.setBounds(300, 20, 30, 30);
        p1.add(vcall);

        ImageIcon idot = new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        Image id2 = idot.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon id3 = new ImageIcon(id2);
        JLabel dot = new JLabel(id3);
        dot.setBounds(400, 20, 30, 30);
        p1.add(dot);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        JLabel name = new JLabel("Abhishek");
        name.setBounds(90, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(90, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 10));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5, 75, 425, 530);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 612, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(315, 612, 115, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        f.add(send);

        f.setSize(450, 695);
        f.setLocation(200, 50);
        // setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (IOException ae) {
            {
            }
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");

        output.setFont(new Font("Tahome", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {

        new ServerG();

        try {
            ServerSocket skt = new ServerSocket(7778);

            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (IOException e) {
        }
    }

}
