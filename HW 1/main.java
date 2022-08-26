import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class main {
    public JPanel main;
    private JButton signIntoSunLabButton;
    private JButton accessPanelButton;
    private JLabel uname;
    private JLabel status;
    private JLabel time;
    private JLabel sign;

    public main(Statement stmt, String passthru) throws Exception{
        //initialize labels
        ResultSet rset = stmt.executeQuery(passthru);
        ResultSetMetaData rsmd = rset.getMetaData();

        if(rset.next()) {
            //username
            uname.setText("Hello: " + rset.getString(2));
            //Status: Suspended/Active
            int suspended = 0;
            if (Objects.equals(rset.getString(4), "1")) {
                status.setText("Status: Active");
                suspended = 1;
            }
            else {
                status.setText("Status: Suspended");
            }
            //Disable admin panel if not staff
            if (!rset.getString(3).equals("STA")) {
                accessPanelButton.setEnabled(false);
            }

        }

        signIntoSunLabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get timestamp data
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String full = format.format(date);
                sign.setText("Signed in at:");
                time.setText(full);

                ResultSet rset = null;
                try {
                    rset = stmt.executeQuery(passthru);
                    ResultSetMetaData rsmd = rset.getMetaData();
                    if (rset.next()) {
                        int id = rset.getInt(1);
                        String addString = ("insert into access_times values (" + id + ", timestamp '" + full + "')");
                        System.out.println(addString);

                        int check = stmt.executeUpdate(addString);
                        if (check > 0) {System.out.println("Successfully inserted");}
                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
        accessPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open admin panel
                open(stmt);
            }
        });
    }
    public static void open(Statement stmt){
        JFrame frame = new JFrame("Access Panel");
        try {
            frame.setContentPane(new adminpanel(stmt).panel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
