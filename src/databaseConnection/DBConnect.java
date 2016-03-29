package databaseConnection;

import java.sql.*;


public class DBConnect {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//URL Format "jdbc:mysql://hostname/databaseName
	static final String DB_URL = "jdbc:mysql://localhost/"; // "localhost/3306/" port not needed
	static final String databaseName = "mpt";
	
	static final String uName = "mediahog";
	static final String uPass = "p@ssw0rd";
	
	public static void main(String[] args){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL;
		//Statement stmt = null;
		
		try{
			//Register JDBC Driver
			Class.forName(JDBC_DRIVER);
			
			//Open connection
			System.out.println("Connecting to DB...");
			
			conn = DriverManager.getConnection(DB_URL+databaseName,uName,uPass);
			
			//Execute a query 
			System.out.println("Creating statement...");
			
			SQL = "SELECT * FROM userinfotable";
			
			//stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			//pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			//ResultSet rs = stmt.executeQuery(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);
			
			int maxID =0;
			
			rs.beforeFirst();
			
			while(rs.next()){
				int uID = rs.getInt("uID"); //rs.getInt(1) ; //column index starts at 1
				String fName = rs.getString("fName");
				String lName = rs.getString("lName");
				String email = rs.getString("email");
				String uName = rs.getString("username");
				String uPass = rs.getString("password");
				//rs.get java.net.URL should probably be used for IMDB link
				
				System.out.print("uID: " + uID);
				System.out.print(", fName: " + fName);
				System.out.print(", lName: " + lName);
				System.out.print(", email: " + email);
				System.out.print(", uName: " + uName);
				System.out.println(", uPass: " + uPass);
				
				if (uID > maxID)
					maxID = uID;
			}
				
			//methods to integrate
			//rs.moveToInsertRow(); //create new row
			//rs.moveToCurrentRow(); //return to existing location
			//rs.updateString("uName", "newUser"); //update existing row
			//Above just update columns in ResultSet object, not database
			//rs.updateRow(); //updates current row in DB
			//rs.deleteRow(); //deletes current row in DB
			//rs.refreshRow(); //refreshes result set
			//rs.cancelRowUpdates(); //cancels any updates made on current row
			//rs.insertRow(); //inserts row into DB, only invokable when cursor is @ moveToInsertRow()
			
			System.out.println("Inserting new record...");
			rs.moveToInsertRow();
			rs.updateInt("uID", maxID+1);
			rs.updateString("fName", "arthur");
			rs.updateString("lName", "dent");
			rs.updateString("email", "adent@aol.com");
			rs.updateString("username", "panic");
			rs.updateString("password", "1234");
			
			//commit row
			rs.insertRow();
			
			System.out.println("Result Set:");
			printRs(rs);
			
			//Clean Up !
			System.out.println("closing up shop");
			rs.close();
			//stmt.close();
			pstmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}
		//catch(ClassNotFoundException ex){ //replace generic exception below? specify certain exceptions? 
		//	System.out.println("Error:Unable to load driver class.");
		//  ex.printStackTrace();
		//}
		catch(Exception e){
			//Handle errors for class.forname
			e.printStackTrace();
		}
		finally {
			//finally block used to close resources
			try{
				//if(stmt!=null)
				if(pstmt!=null)
					//stmt.close();
					pstmt.close();
			}catch(SQLException se2){
			}//nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}// end finally try
		System.out.println("Goodbye...");
	}//end main
	
public static void printRs(ResultSet rs) throws SQLException{
	rs.beforeFirst();
    while(rs.next()){
    	//Retrieve by column name
    	int uID = rs.getInt("uID"); //rs.getInt(1) ; //column index starts at 1
		String fName = rs.getString("fName");
		String lName = rs.getString("lName");
		String email = rs.getString("email");
		String uName = rs.getString("username");
		String uPass = rs.getString("password");
		//rs.get java.net.URL should probably be used for IMDB link
		
		System.out.print("uID: " + uID);
		System.out.print(", fName: " + fName);
		System.out.print(", lName: " + lName);
		System.out.print(", email: " + email);
		System.out.print(", uName: " + uName);
		System.out.println(", uPass: " + uPass);
     }
     System.out.println();
   }//end printRs()
	
}//end class


