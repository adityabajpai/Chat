import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AllUsers extends JFrame {
    JButton btn1,btn2;
    ArrayList<String> al  = new ArrayList<>();
    JScrollPane jScrollPane;
    JTable jTable;
    AllUsers(){
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Let's Start");
        btn1 = new JButton("REQUEST");
        btn2 = new JButton("CHAT LIST");
        add(btn1);
        add(btn2);
        btn1.setBounds(100,30,100,30);
        btn2.setBounds(220,30,100,30);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FriendUser friendUser = new FriendUser();
                friendUser.setVisible(true);
            }
        });
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement("select name from user");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pswd = rs.getString("name");
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
    }

    public static void main(String[] args) {
        new AllUsers();
    }
}
