import java.util.*;
import java.util.Properties;
import java.io.*;

public class ReadConfigDetails
{
	public static String LoadUIDBName =null;
	public static String WaptproDBName  =null;
	public static String DBconnectionpath  =null;
	public static String DBusername  =null;
	public static String DBpassword  =null;
	public static String SMTPServer  =null;
	public static String SMTPPort  =null;
	public static String SMTPusername  =null;
	public static String SMTPpassword  =null;
	public static String FromMailAddress = null;
	public static String FinalResultsPath  =null;
	
	//Properties configFile;
	   public ReadConfigDetails()
	   {
		//configFile = new java.util.Properties();
		try 
		{	
			
			Properties prop = new Properties();
		    String fileName = "C:\\PT-Framework-files\\PTproperties.config";
		    InputStream is = new FileInputStream(fileName);

		    prop.load(is);
		    FinalResultsPath = prop.getProperty("FinalResultsPath");
		    LoadUIDBName = prop.getProperty("LoadUIDBName");
		    WaptproDBName = prop.getProperty("WaptproDBName");
		    DBconnectionpath = prop.getProperty("DBconnectionpath");
		    DBusername = prop.getProperty("DBusername");
		    DBpassword = prop.getProperty("DBpassword");
		    SMTPServer = prop.getProperty("SMTPServer");
		    SMTPPort = prop.getProperty("SMTPPort");
		    SMTPusername = prop.getProperty("SMTPusername");
		    SMTPpassword = prop.getProperty("SMTPpassword");
		    FromMailAddress = prop.getProperty("FromMailAddress");
		    

		 // configFile.load(this.getClass().getClassLoader().getResourceAsStream("C:\\PT-Framework-files\\PTproperties.config"));	
		 //LoadUIDBName = configFile.getProperty("LoadUIDBName");
		 
		}catch(Exception eta){
		    eta.printStackTrace();
		}
	   }
	 
//	   public String getProperty(String key)
//	   {
//		String value = this.configFile.getProperty(key);		
//		return value;
//	   }
	   public static void main(String args[])
	   {
		   new ReadConfigDetails();
	   }

}
