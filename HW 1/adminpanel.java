import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Vector;

public class adminpanel {
    public JPanel panel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton searchButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public adminpanel(Statement stmt) {

        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String search = "Select * from access_times ";
                //Query string generator
                if(!textField1.getText().isBlank()) {
                    search = search.concat("where ID = " + textField1.getText() + " ");
                    //check time frame
                    if (!textField2.getText().isBlank()) {
                        search = search.concat("and signin < timestamp '" + textField2.getText() + "' ");
                    }
                    if (!textField3.getText().isBlank()) {
                        search = search.concat("and signin > timestamp '" + textField3.getText() + "' ");
                    }
                }
                else {
                    if (!textField2.getText().isBlank()) {
                        search = search.concat("where signin < timestamp '" + textField2.getText() + "' ");

                        if (!textField3.getText().isBlank()) {
                            search = search.concat("and signin > timestamp '" + textField3.getText() + "' ");
                        }
                    }
                    else {
                        if (!textField3.getText().isBlank()) {
                            search = search.concat("where signin > timestamp '" + textField3.getText() + "' ");
                        }
                    }

                }
                //Order by
                if(!(comboBox1.getSelectedIndex() == 0)) {
                    search = search.concat("order by " + comboBox1.getSelectedItem() + " " + comboBox2.getSelectedItem());
                }
                //System.out.println("Search query:");
                //System.out.println(search);
                ResultSet rset = null;
                try {
                    rset = stmt.executeQuery(search);
                    JTable data = new JTable(buildTableModel(rset));
                    JOptionPane.showMessageDialog(null, new JScrollPane(data));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}
