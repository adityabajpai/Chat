import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FriendUser extends JFrame {
    JButton btn1,btn2;
    String email;
    ArrayList<String> al = new ArrayList<>();
    JTable jTable;
    JScrollPane jScrollPane;
    FriendUser() {
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("FRIEND USER");
        btn1 = new JButton("REQUEST");
        btn2 = new JButton("ALL USERS");
        add(btn1);
        add(btn2);
        btn1.setBounds(100,30,100,30);
        btn2.setBounds(220,30,100,30);
    }
    FriendUser(String name) {
        System.out.println("Friend User with contructor");
        email = name;
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("FRIEND USER");
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
                AllUsers allUsers = new AllUsers(email);
                allUsers.setVisible(true);
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RequestList(email).setVisible(true);
            }
        });
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement("select reciever from friends where sender=?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pswd = rs.getString("reciever");
                al.add(pswd);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        String data[][] = new String[al.size()][1];
        for(int i=0;i<al.size();i++)
        {
            data[i][0] = al.get(i);
        }
        String column[] = {"name"};
        String temp = "";
        for(int i=0;i<al.size();i++)
        {
            temp = temp+data[i][0];
        }
        JOptionPane.showMessageDialog(btn1, temp+"");
        jTable = new JTable(data,column);
        jTable.setBounds(10,80,800,300);
        jScrollPane = new JScrollPane(jTable);
        add(jTable);
        jTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable.rowAtPoint(e.getPoint());
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Want to chat to "+data[row][0]+" for Chat?", "Title on Box", dialogButton);
                if(dialogResult == 0) {
                    dispose();
                    new ChatFrame(email,data[row][0]).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,"Sure don't want to chat");
                }
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
    }

    public static void main(String[] args) {
        new FriendUser();
    }
}
