import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

public class run {
    public static String getUsername() {
        //stuff
        return "ghl5043";
    }
    public static String getPassword() {
        //stuff
        return "Rumblefighter123!!"; //I'm hiding my password
    }

    public static void main(String[] args) throws Exception {
        System.out.print("Connecting to the database...");

        // Open an OracleDataSource and get a connection
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu");

        String username = getUsername();
        ods.setUser(username);
        String password = getPassword();
        ods.setPassword(password);

        // Open connection
        Connection conn = ods.getConnection();
        System.out.println("\nconnected.");

        Scanner sc=new Scanner(System.in);
        System.out.println("Enter username: ");
        String u = sc.nextLine();
        System.out.println("Enter password: ");
        String p = sc.nextLine();

        Statement stmt = conn.createStatement();
        //ResultSet rset = stmt.executeQuery("Select * from login");
        ResultSet rset = stmt.executeQuery("Select * from login where ID = '" + u + "' and PASSWORD = '" + p + "'");
        ResultSetMetaData rsmd = rset.getMetaData();

        System.out.print("\n");
        if (rset.next()) {
            System.out.println("LOGIN SUCCESSFUL");
        }
        else {
            System.out.println("LOGIN UNSUCCESSFUL");
        }
    }


}
