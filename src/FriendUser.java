import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendUser extends JFrame {
    JButton btn1,btn2;
    FriendUser() {
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Let's Start");
        btn1 = new JButton("REQUEST");
        btn2 = new JButton("ALL USERS");
        add(btn1);
        add(btn2);
        btn1.setBounds(100,30,100,30);
        btn2.setBounds(220,30,100,30);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AllUsers allUsers = new AllUsers();
                allUsers.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new FriendUser();
    }
}
