package sql;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Christopher on 2016-03-29.
 *
 * Currently configured for a localhost using applications like MAMP/LAMP/WAMP.
 * Looking into setting up a remote host.
 */
public abstract class Database {

    //Database login credentials
    static final String localHost = "jdbc:mysql://127.0.0.1:3306/mysql",
            userName = "root",
            password = "root";

    /**
     * This method is to be used for UPDATE, INSERT and DROP commands.
     * @param stmt - SQL Statement to be processed.
     * @return Boolean - True if statement executed, False is not.
     */
//    public static boolean Update(String stmt) {
//        try {
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());							//Create instance of Driver
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(localHost,userName,password);	//Create connection, with credentials
//            Statement stmtMade = connection.createStatement();										//Create a Statement Object
//            stmtMade.executeUpdate(stmt);
//            stmtMade.close();
//            connection.close();
//            return true;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }

    /**
     * This method is to be used for SELECT statements only.
     * @param stmt - Statement to be processed
     * @return String[][] in the format of String[Entry 1,2,3...n ][Nth Entry Col #1, Col #2...]
     */
//    public static String[][] Query(String stmt) {
//        try {
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());							//Create instance of Driver
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(localHost,userName,password);	//Create connection, with credentials
//            Statement stmtMade = connection.createStatement();									//Create a Statement Object
//            ResultSet tuplesIn = stmtMade.executeQuery(stmt);									//Retrieve Tuples from Query
//            ResultSetMetaData rsmd = tuplesIn.getMetaData();									//Some Meta Data regarding the Tuples returned
//            stmtMade.close();																	//Close connections...
//            connection.close();
//            ArrayList<String[]> tuplesOut = new ArrayList<String[]>();							//Dynamically sized array to convert Results to
//            String[] tempAdditions = new String[rsmd.getColumnCount()];
//            do {
//                for(int i = 0; i > tempAdditions.length; i++) {
//                    tempAdditions[i] = tuplesIn.getString(i);									//Loop through tuple,
//                }																				//converting each returned column into a String
//                tuplesOut.add(tempAdditions);													//Add Temp Made Array to ArrayList
//                Arrays.fill(tempAdditions, "N/A");												//Reset Temp values
//            } while(tuplesIn.next());
//            return (String[][]) tuplesOut.toArray();											//Convert to Array, and return.
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }

}

