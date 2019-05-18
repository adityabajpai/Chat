import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUser extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JTextField tf1, tf2, tf5, tf6, tf7;
    JButton btn1, btn2;
    JPasswordField p1, p2;
    LoginUser()
    {
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        l1 = new JLabel("Login Form in Windows Form:");
        l2 = new JLabel("E-mail");
        l3 = new JLabel("Password");
        l4 = new JLabel("Not a member! Register");
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 120, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 120, 200, 30);
        btn1.setBounds(80,170,100,30);
        btn2.setBounds(200,170,100,30);
        l4.setBounds(180,200,200,30);
        add(l1);
        add(l2);
        add(l3);
        add(tf1);
        add(p1);
        add(btn1);
        add(btn2);
        add(l4);
        l4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                RegisterUser registerUser = new RegisterUser();
                registerUser.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        btn1.addActionListener(e -> {
            int x = 0;
            String s1 = tf1.getText();
            char[] s3 = p1.getPassword();
            String s8 = new String(s3);
            if(s1.equals("")||s8.equals(""))
            {
                JOptionPane.showMessageDialog(btn1, "All fields must be filled");
            }
            else
            {
                try
                {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
                    PreparedStatement ps = con.prepareStatement("select pswd from user where email = ?");
                    ps.setString(1, s1);
                    ResultSet rs = ps.executeQuery();
                    x++;
//                    if (x > 0)
//                    {
//                        String pswd = rs.getString("pswd");
//                        JOptionPane.showMessageDialog(btn1, pswd);
////                        JOptionPane.showMessageDialog(btn1, pswd);
//                    }
                    if(rs.next()){
                        String pswd = rs.getString(1);
                        if(pswd.equals(s8)) {
                            JOptionPane.showMessageDialog(btn1, "Successfully Login");
                            dispose();
                            FriendUser friendUser = new FriendUser();
                            friendUser.setVisible(true);
                        }
                        else{
                            JOptionPane.showMessageDialog(btn1, "Password doesn't match");
                        }
                    }
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(btn1, "You are not registered");
                    System.out.println(ex);
                }
            }
    });
}

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new LoginUser();
    }
}
