import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;



public class DatabaseMethods {
	
	//Class.forName("com.mysql.jdbc.Driver");
	Connection conn = null;//DriverManager.getConnection("jdbc:mysql:\\localhost:3306\\\ericsson_database","root","root");
	public DatabaseMethods(String dbname)// throws InstantiationException, IllegalAccessException, ClassNotFoundException 

	{
		new ReadConfigDetails();
//		try 
//		{
//			Class.forName("com.mysql.jdbc.Driver");
//			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ericsson_loadui_db","root","root");
//			new ReadConfigDetails();
//			String connectionstring =  ReadConfigDetails.LoadUIDBName.trim() +"," + ReadConfigDetails.DBusername.trim()+","+ReadConfigDetails.DBpassword.trim();
//			//connectionstring = "jdbc:mysql://localhost:3306/ericsson_loadui_db," + "root"+ "," +"root";
//			
//			System.out.println(connectionstring);
//			conn = DriverManager.getConnection(connectionstring);
//			
//		} 
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try { 
			
			Properties p = new Properties(); 

			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			p.put("user",ReadConfigDetails.DBusername); 
			p.put("password",ReadConfigDetails.DBpassword); // put a password in if a password exists for user
			if(dbname.equalsIgnoreCase("waptdb"))
				conn = DriverManager.getConnection(ReadConfigDetails.WaptproDBName,p); 
			else
				conn = DriverManager.getConnection(ReadConfigDetails.LoadUIDBName,p);
			
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
	}
	public void UpdateInsertToTable(String sqlcommand) 

	{
		Statement stmt;
		try 
		{
			stmt=(Statement) conn.createStatement();
			stmt.executeUpdate(sqlcommand);
			//stmt.executeQuery(sqlcommand);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet SelectFromTable(String sqlcommand)
	{
		Statement stmt;
		ResultSet rs =null;
		try 
		{
			stmt=(Statement) conn.createStatement();			
			rs=stmt.executeQuery(sqlcommand);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void main( String args[])
	{
		new DatabaseMethods("db");
//		String sql,tcaseid,tcaseName;
//		Statement stmt;
//		PreparedStatement st;
//		
//		sql = "select * from tbltestcasemstr";
//		st=conn.prepareStatement(sql);
//		rs=st.executeQuery();
//		While(rs.next()){
//		tcaseid = rs.getInt("tCaseId");
//		tcaseid = rs.getString("tCaseName");
//		}
//
//
//		sql="INSERT INTO tbltestresults(`TCaseID`,`TCaseTitle`,`TCaseResult`,`TCaseErrorReason`,`TcaseExecutionTime`,`TCaseExecutionLog`) VALUES(" + (Integer.parseInt(result.get(0)))+ ",'" + result.get(1)+"','"+ result.get(2)+"','"+ result.get(3)+"','"+ result.get(4)+ "',"+ (Integer.parseInt(result.get(5)))+ ")";
//					
//					stmt=(Statement) conn.createStatement();
//					stmt.executeUpdate(sql);
	}
}

