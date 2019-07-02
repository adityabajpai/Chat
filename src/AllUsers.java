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

public class AllUsers extends JFrame {
    JButton btn1,btn2;
    ArrayList<String> al  = new ArrayList<>();
    ArrayList<String> al_requestList  = new ArrayList<>();
    JScrollPane jScrollPane;
    JTable jTable;
    AllUsers(){
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ALL USERS");
        btn1 = new JButton("REQUEST");
        btn2 = new JButton("CHAT LIST");
        add(btn1);
        add(btn2);
        btn1.setBounds(100,30,100,30);
        btn2.setBounds(220,30,100,30);
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement("select email from user");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pswd = rs.getString("email");
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
                JOptionPane.showMessageDialog(null,"Name in the clicked Cell is: "+data[row][0]);
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

    AllUsers(String email){
        System.out.println("ALL USERS with Constructor");
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ALL USERS");
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
                FriendUser friendUser = new FriendUser(email);
                friendUser.setVisible(true);
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
            PreparedStatement ps = con.prepareStatement("select email from user where email!=?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pswd = rs.getString("email");
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
//                JOptionPane.showMessageDialog(null,"Name in the clicked Cell is: "+data[row][0]);
//                int dialogButton = JOptionPane.YES_NO_OPTION;
//                JOptionPane.showConfirmDialog (null, "Would You Like to Send Request to "+data[row][0]+" for Chat?","Warning",dialogButton);
//                if (dialogButton == JOptionPane.YES_OPTION){
//                    JOptionPane.showMessageDialog(null,"Clicked Yes");
//                }else {
//                    JOptionPane.showMessageDialog(null,"Clicked No");
//                }
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Send Request to "+data[row][0]+" for Chat?", "Title on Box", dialogButton);
                if(dialogResult == 0) {
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
                        System.out.println(email);
                        PreparedStatement ps = con.prepareStatement("select reciever from request_list where sender=?");
                        ps.setString(1,email);
                        ResultSet rs = ps.executeQuery();
                        System.out.println(rs+"");
                        while(rs.next()){
                            System.out.println("Hello");
                            String pswd = rs.getString("reciever");
                            al_requestList.add(pswd);
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                    System.out.println(al_requestList);
                    if(al_requestList.contains(data[row][0])){
                        JOptionPane.showMessageDialog(null,"You have already send the request");
                    }else{
                        try{
                            int x = 0;
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
                            PreparedStatement ps = con.prepareStatement("insert into request_list (sender,reciever) values(?,?)");
                            ps.setString(1, email);
                            ps.setString(2, data[row][0]);
                            int rs = ps.executeUpdate();
                            x++;
                            if (x > 0){
                                JOptionPane.showMessageDialog(null,"You have send the request");
                            }
                        }catch (Exception e1){

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"You are not willing to send request");
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
        new AllUsers();
    }
}
