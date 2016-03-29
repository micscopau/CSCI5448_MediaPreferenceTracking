package databaseConnection;

import java.sql.*;


public class DBConnect {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/"; // 3306 port not needed
	static final String Database = "mpt";
	
	static final String uName = "mediahog";
	static final String uPass = "p@ssw0rd";
	
	public static void main(String[] args){
		Connection conn = null;
		//PreparedStatement pstmt = null;
		String SQL;
		Statement stmt = null;
		
		try{
			//Register JDBC Driver
			Class.forName(JDBC_DRIVER);
			
			//Open connection
			System.out.println("Connecting to DB...");
			
			conn = DriverManager.getConnection(DB_URL+Database,uName,uPass);
			
			//Execute a query 
			System.out.println("Creating statement...");
			
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//stmt = conn.createStatement(); 
			//pstmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			SQL = "SELECT * FROM userinfotable";
						
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next()){
				int uID = rs.getInt("uID");
				String fName = rs.getString("fName");
				String lName = rs.getString("lName");
				String email = rs.getString("email");
				String uName = rs.getString("username");
				String uPass = rs.getString("password");
				
				System.out.print("uID: " + uID);
				System.out.print(", fName: " + fName);
				System.out.print(", lName: " + lName);
				System.out.print(", email: " + email);
				System.out.print(", uName: " + uName);
				System.out.println(", uPass: " + uPass);
			}
					
			//Clean Up !
			System.out.println("closing up shop");
			rs.close();
			stmt.close();
			conn.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for class.forname
			e.printStackTrace();
		}
		finally {
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
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
}//end class

