import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatFrame extends JFrame {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jLabel1;
    private javax.swing.JButton jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private ArrayList<String> messageArrayList;


    ChatFrame(String email, String reciepient){




        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
//                    PreparedStatement ps = con.prepareStatement("insert into user (name,email,pswd,country,state,mobile) values(?,?,?,?,?,?)");
            PreparedStatement ps = con.prepareStatement("select * from messages where id=?");
            ps.setString(1,email+"_"+reciepient);
            ResultSet rs = ps.executeQuery();
            String name = "";
            String msg = "";
            Message message;
            messageArrayList = new ArrayList<>();
            System.out.println("Retrieving message");
            while(rs.next()){
                String nameArray[] = rs.getString("id").split("_");
                name = nameArray[0];
                System.out.println("name "+name);
                msg = rs.getString("msg");
                System.out.println("name "+msg);
                String finalmsg = name+":  "+msg;
                messageArrayList.add(finalmsg);
            }
//            strings = new String[messageArrayList.size()];
//            for(int i=0;i<messageArrayList.size();i++)
//            {
//                strings[i] = messageArrayList.get(i);
//            }
            System.out.println(messageArrayList+"");
//            System.out.println("Reciepient id " +reciepientId);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        jLabel1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BACK");
        jLabel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FriendUser friendUser = new FriendUser(email);
                friendUser.setVisible(true);
            }
        });

        jLabel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FriendUser friendUser = new FriendUser(email);
                friendUser.setVisible(true);
            }
        });


        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CHAT LIST");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("SENDER NAME");
        jLabel3.setText(reciepient);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jList1.setModel(new javax.swing.AbstractListModel() {
//            String[] strings = {"item1", "item2", "item3", "item4"};
            public int getSize() { return messageArrayList.size(); }
            public Object getElementAt(int i) { return messageArrayList.get(i); }
        });
        jScrollPane2.setViewportView(jList1);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("SEND MESSAGE");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senderId = "";
                String reciepientId = "";
                String msg = jTextArea1.getText();
                int x = 0;
                if(msg.equals(""))
                {
                    JOptionPane.showMessageDialog(jButton1,"No message to send");
                }
                else
                {
                    String finalID = email+"_"+reciepient;
                    System.out.println("finalID "+finalID);


                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","root");
                        PreparedStatement ps = con.prepareStatement("insert into messages (id, msg) values(?,?)");
                        ps.setString(1, finalID);
                        ps.setString(2, msg);
                        int rs = ps.executeUpdate();
                        x++;
                        if (x > 0)
                        {
                            System.out.println("Message send Successfully");
                        }
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
                dispose();
                new ChatFrame(email,reciepient).setVisible(true);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(414, 414, 414)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel2))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(241, 241, 241)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane2)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(162, 162, 162))
        );

        pack();
    }

    ChatFrame(){
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }

}
