import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login {
    private JPanel panel1;
    private JButton loginButton;
    private JPasswordField passwordField1;
    private JTextField textField1;


    public login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Connecting to the database...");

                // Open an OracleDataSource and get a connection
                OracleDataSource ods = null;
                try {
                    ods = new OracleDataSource();
                    ods.setURL("jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu");
                    String username = getUsername();
                    ods.setUser(username);
                    String password = getPassword();
                    ods.setPassword(password);

                    // Open connection
                    Connection conn = ods.getConnection();
                    System.out.println("\nconnected.");

                    Statement stmt = conn.createStatement();

                    String u = textField1.getText();
                    String p = String.valueOf(passwordField1.getPassword());

                    //Searches database for matching ID and password combination
                    String passthru = ("Select * from login where ID = '" + u + "' and PASSWORD = '" + p + "'");
                    ResultSet rset = stmt.executeQuery(passthru);

                    System.out.print("\n");
                    if (rset.next()) {
                        System.out.println("LOGIN SUCCESSFUL");
                        openMain(stmt, passthru);
                    }
                    else {
                        System.out.println("[display as popup] LOGIN UNSUCCESSFUL");
                    }

                } catch (SQLException ex) {
                    System.out.println("Connection to database failed, check the db login again?");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });


    }
    public static void openMain(Statement stmt, String passthru){
        JFrame frame = new JFrame("Main Menu");
        try {
            frame.setContentPane(new main(stmt, passthru).main);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static String getUsername() {
        //stuff
        return "ghl5043";
    }
    public static String getPassword() {
        //stuff
        return "Rumblefighter123!!"; //I'm hiding my password
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("gui");
        frame.setContentPane(new login().panel1);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
