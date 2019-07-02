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

public class RequestList extends JFrame {

    JButton btn1,btn2;
    ArrayList<String> al  = new ArrayList<>();
    ArrayList<String> al_requestList  = new ArrayList<>();
    JScrollPane jScrollPane;
    JTable jTable;


    RequestList(String email){
        System.out.println("REQUEST LIST with Constructor");
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("REQUEST LIST");
        btn1 = new JButton("ALL USERS");
        btn2 = new JButton("CHAT LIST");
        add(btn1);
        add(btn2);
        btn1.setBounds(100,30,100,30);
        btn2.setBounds(220,30,100,30);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FriendUser(email).setVisible(true);
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AllUsers(email).setVisible(true);
            }
        });
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement("select sender from request_list where reciever=?");
            System.out.println(email);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pswd = rs.getString("sender");
                al.add(pswd);
            }
            System.out.println(al);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        if(al.size()>0)
        {
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
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Accept Request of "+data[row][0]+" for Chat?", "Accept Request.", dialogButton);
                    if(dialogResult == 0) {
                        try{
                            int x = 0;
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
                            PreparedStatement ps = con.prepareStatement("insert into friends (sender,reciever) values(?,?)");
                            ps.setString(1, email);
                            ps.setString(2, data[row][0]);
                            PreparedStatement ps1 = con.prepareStatement("delete from request_list where sender=? and reciever=?");
//                            JOptionPane.showMessageDialog(null,"You have accepted the request");
                            ps1.setString(1, data[row][0]);
                            ps1.setString(2, email);
                            int rs = ps.executeUpdate();
                            int rs1 = ps1.executeUpdate();
                            x++;
                            if (x > 0){
                                x++;
                                System.out.println("Record deleted from request_list");
                            }
                        }catch (Exception e1){

                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"You are not sure to accept the request");
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
        else{
            JOptionPane.showMessageDialog(null,"No Request");
        }
    }

    RequestList() {

    }

    public static void main(String[] args) {
        new RequestList();
    }

}
