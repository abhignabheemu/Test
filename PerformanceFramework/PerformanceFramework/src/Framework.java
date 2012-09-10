import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.io.*;
import java.text.*;
//import java.util.*;
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//import javax.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class Framework {
	
	public static String database = null;
	public static String recipents = "";
	public static int tclog =0;
	public static String ComFilenames = null;
	public ArrayList<Integer> TestCases = new ArrayList<Integer>();
	String testcaserange = null; 
	
	String filepath =null;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
	
	String Resultpath = null;
	
	String[] monthName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug", "Sep","Oct","Nov","Dec"}; 

	
	public Framework()
	{
//		String filename1 = "ServerVitals2012-04-04-04-15-52";
//		try {
//			new CSVReader("LoginLogout","172.20.160.132","C:\\LoadUIResults\\"+filename1+"1.csv",database);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		new ReadConfigDetails();
		Resultpath = ReadConfigDetails.FinalResultsPath + "\\" + "PerfReport" + df.format(new Date());
		database = "LoadUIDB";
		//System.out.println("Res path  " +Resultpath);
		String filepaths =null;
		String Folderpath = "C:\\PT-Framework-files\\Loaduiresults";
		ResultSet rs = null;
		String command =null;
		try
		{
			command = "select max(TCaseExecutionLog) as ExecutionLog from ltbltestresults";
			DatabaseMethods db = new DatabaseMethods(database);
			rs = db.SelectFromTable(command);
			rs.first();
			String tcexeclog = rs.getString("ExecutionLog");
			if (tcexeclog != null)
				tclog = Integer.parseInt(tcexeclog)+1;
			else
				tclog = 1;
			//System.out.println(tclog);sh ServerVital.sh
			
			TestcaseRange();
			
			for(int i=0;i<TestCases.size();i++)
			  {
				if(i==0)
					testcaserange = TestCases.get(i).toString()+ ",";
				else
//					if(i==TestCases.size()-1)
//						testcaserange = testcaserange + TestCases.get(i);
//					else
						testcaserange = testcaserange + TestCases.get(i) + ",";
			  }
			System.out.println("testcaserange" +testcaserange);
			testcaserange = testcaserange.substring(0, testcaserange.length()-1);
			System.out.println("testcaserange" +testcaserange);
			command = "Select * from ltbltestcasemstr where TCaseID in (" + testcaserange + ")";
			System.out.println(command);
			
			rs = db.SelectFromTable(command);
			//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			while(rs.next())
			{
				java.util.Date date =null; 
				String filename = "ServerVitals" + df.format(new Date());
				String Serverfile = "ServerVitals"  ;
				Calendar currentDate =  Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("dd/Mmm/yyyy");
				String mon = monthName[currentDate.get(Calendar.MONTH)];
				int day =  currentDate.get(Calendar.DATE);
				String day1 = String.valueOf(day);
				if(day1.length()== 1)
				{
					day1 = "0" + day1;
				}
				int hour = currentDate.get(Calendar.HOUR_OF_DAY);
//				System.out.println(currentDate.get(Calendar.DATE));
//				System.out.println(currentDate.get(Calendar.YEAR));
				Serverfile = "ServerVitals_"+mon+day1+hour;
				System.out.println(Serverfile);
				String TestcaseTitle =rs.getString("TCaseTitle");
				String Testcaseid = rs.getString("TCaseId");
				int TCId = Integer.parseInt(Testcaseid);
				String command1 = "select * from ltbltestdata where TcaseID = " + TCId;
				System.out.println(command1);
				ResultSet rs1 = db.SelectFromTable(command1);
				rs1.first();
				String Server1 = rs1.getString("Server1");
				//System.out.println(Server1);
				String Server2 = rs1.getString("Server2");
				//System.out.println(Server2);
				String Server3 = rs1.getString("Server3");
				//System.out.println(Server3);
				String Server4 = rs1.getString("Server4");
				//System.out.println(Server4);
				Process p = null;
				
				if(Server1!= null)
				{
					String[] Serverdata = Server1.split(",");
					String Buildcommand = "start c:\\PT-Framework-files\\Putty.bat " + Serverdata[1] + " " + Serverdata[0] + " " + Serverdata[2] + " Server1.sh";
					System.out.println(Buildcommand);
					p = new ProcessBuilder("cmd.exe", "/C", Buildcommand ) .redirectErrorStream(true).start();
					p.waitFor();
					Thread.sleep(30000);					
					Serverdata = null;
				}
				
				if(Server2!= null)
				{
					String[] Serverdata = Server2.split(",");
					String Buildcommand = "start c:\\PT-Framework-files\\Putty.bat " + Serverdata[1] + " " + Serverdata[0] + " " + Serverdata[2] + " Server1.sh";
					System.out.println(Buildcommand);
					p = new ProcessBuilder("cmd.exe", "/C", Buildcommand ) .redirectErrorStream(true).start();
					p.waitFor();
					Thread.sleep(30000);					
					Serverdata = null;
				}
				
				if(Server3!= null)
				{
					String[] Serverdata = Server3.split(",");
					String Buildcommand = "start c:\\PT-Framework-files\\Putty.bat " + Serverdata[1] + " " + Serverdata[0] + " " + Serverdata[2] + " Server1.sh";
					System.out.println(Buildcommand);
					p = new ProcessBuilder("cmd.exe", "/C", Buildcommand ) .redirectErrorStream(true).start();
					p.waitFor();
					Thread.sleep(30000);					
					Serverdata = null;
				}
				
				if(Server4!= null)
				{
					String[] Serverdata = Server4.split(",");
					String Buildcommand = "start c:\\PT-Framework-files\\Putty.bat " + Serverdata[1] + " " + Serverdata[0] + " " + Serverdata[2] + " Server1.sh";
					System.out.println(Buildcommand);
					p = new ProcessBuilder("cmd.exe", "/C", Buildcommand ) .redirectErrorStream(true).start();
					p.waitFor();
					Thread.sleep(30000);					
					Serverdata = null;
				}
				System.out.println(TestcaseTitle);
				//p = new ProcessBuilder("cmd.exe", "/C", "C:\\eviware\\loadUI-1.5.0\\loadui-cmd.bat -p c:\\LoadUi\\" + TestcaseTitle + ".xml -r c:\\Loaduiresults -F XML>c:\\loadui.txt") .redirectErrorStream(true).start();
				String Buildcommand = "start c:\\PT-Framework-files\\loadui.bat " + TestcaseTitle + " " + Folderpath;
				p = new ProcessBuilder("cmd.exe", "/C", Buildcommand) .redirectErrorStream(true).start();
				p.waitFor();
				Thread.sleep(10000);
				
				File f = new File("C:\\loadui.txt");
				while(f.exists())
				{
					try
					{
						
						f.delete();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
					Thread.sleep(1000);
				}
				
				
				p = new ProcessBuilder("cmd.exe", "/C", "start c:\\PT-Framework-files\\Stopputty.bat") .redirectErrorStream(true).start();
				p.waitFor(); 
				Thread.sleep(10000);
				if(Server1!= null)
				{
					String[] Serverdata = Server1.split(",");
					String fname = Folderpath+"\\" +filename+"1.csv";
					String Bcommand = "C:\\PT-Framework-files\\PuTTY\\pscp.exe -pw "+Serverdata[2]+" "+ Serverdata[1]+"@"+Serverdata[0]+":Sat/Results/"+Serverfile+".csv "+fname+">c:\\server.txt";
					System.out.println(Bcommand);
					p = new ProcessBuilder("cmd.exe", "/C",Bcommand) .redirectErrorStream(true).start();
					p.waitFor();					
					
					f = new File("C:\\server.txt");
					while(f.exists())
					{
						try
						{
							
							f.delete();
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						Thread.sleep(1000);
					}
					String serverip = Serverdata[0];
					Serverdata = null;
					new CSVReader(TCId,TestcaseTitle,serverip,fname,database);
				}
				
				if(Server2!= null)
				{
					String[] Serverdata = Server2.split(",");
					String fname = Folderpath+"\\" +filename+"1.csv";
					String Bcommand = "C:\\PT-Framework-files\\PuTTY\\pscp.exe -pw "+Serverdata[2]+" "+ Serverdata[1]+"@"+Serverdata[0]+":Sat/Results/"+Serverfile+".csv "+fname+">c:\\server.txt";
					System.out.println(Bcommand);
					p = new ProcessBuilder("cmd.exe", "/C",Bcommand) .redirectErrorStream(true).start();
					p.waitFor();					
					
					f = new File("C:\\server.txt");
					while(f.exists())
					{
						try
						{
							
							f.delete();
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						Thread.sleep(1000);
					}
					String serverip = Serverdata[0];
					Serverdata = null;
					new CSVReader(TCId,TestcaseTitle,serverip,fname,database);
				}
				
				if(Server3!= null)
				{
					String[] Serverdata = Server3.split(",");
					String fname = Folderpath+"\\" +filename+"1.csv";
					String Bcommand = "C:\\PT-Framework-files\\PuTTY\\pscp.exe -pw "+Serverdata[2]+" "+ Serverdata[1]+"@"+Serverdata[0]+":Sat/Results/"+Serverfile+".csv "+fname+">c:\\server.txt";
					System.out.println(Bcommand);
					p = new ProcessBuilder("cmd.exe", "/C",Bcommand) .redirectErrorStream(true).start();
					p.waitFor();					
					
					f = new File("C:\\server.txt");
					while(f.exists())
					{
						try
						{
							
							f.delete();
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						Thread.sleep(1000);
					}
					String serverip = Serverdata[0];
					Serverdata = null;
					new CSVReader(TCId,TestcaseTitle,serverip,fname,database);
				}
				
				if(Server4!= null)
				{
					String[] Serverdata = Server4.split(",");
					String fname = Folderpath+"\\" +filename+"1.csv";
					String Bcommand = "C:\\PT-Framework-files\\PuTTY\\pscp.exe -pw "+Serverdata[2]+" "+ Serverdata[1]+"@"+Serverdata[0]+":Sat/Results/"+Serverfile+".csv "+fname+">c:\\server.txt";
					System.out.println(Bcommand);
					p = new ProcessBuilder("cmd.exe", "/C",Bcommand) .redirectErrorStream(true).start();
					p.waitFor();					
					
					f = new File("C:\\server.txt");
					while(f.exists())
					{
						try
						{
							
							f.delete();
						}
						catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
						Thread.sleep(1000);
					}
					String serverip = Serverdata[0];
					Serverdata = null;
					new CSVReader(TCId,TestcaseTitle,serverip,fname,database);
				}
				 
//				p = new ProcessBuilder("cmd.exe", "/C", "start c:\\Stopputty.bat") .redirectErrorStream(true).start();
//				p.waitFor();
//				Thread.sleep(2000);
				p.destroy();
//				Thread.sleep(10000);
//				GetFiles();
//				postMail(message);
//				MoveFiles();
//				
				
				String[] ArrFiles;
				try
				{
					filepaths = GetFiles(Folderpath);
				//	filepaths = filepaths.substring(0, filepaths.length()-1);
					System.out.println("xparser filepath : " + filepaths);
					ArrFiles = filepaths.split(",");
					
					for(int i=0;i<ArrFiles.length;i++)
					{
						if(ArrFiles[i].length() > 0)
						{
							filepath = ArrFiles[i];
							System.out.println(filepath);
							if(filepath.contains(".xml"))
							{
								if(filepath.toLowerCase().contains(TestcaseTitle.toLowerCase()))
								{
									XMLParser xparse = new XMLParser();
									System.out.println("xml parse database" +database);
									xparse.updateXMLinDatabase(tclog,Testcaseid,database,filepath);
								}
							}
							
						}
//						if(filepath.length() > 0)
//							MoveFiles(filepath);
					}
//					File dir = new File(folderpath);
//					dir.delete();
//					postMail();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			}
			
//			LoadTestReport LTR = new LoadTestReport();
			String TxtGenerateData =LoadTestReport.generateData(tclog);
			LoadTestReport.GenerateReport(TxtGenerateData, Resultpath);
			String Filenames = MoveFiles(Folderpath,ReadConfigDetails.FinalResultsPath);
			if(ComFilenames != null)
			{
				ComFilenames = Filenames;
			}
			else
			{
				Filenames = Filenames + "," + Filenames;
			}
			
			System.out.println(Filenames);
			Sendmail SMail = new Sendmail();
			try
			{
				SMail.postMail(Resultpath);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	

	public  String  GetFiles(String Folderpath)throws InterruptedException
    {	
		
    	try{
    		
    		//File folder = new File("C:\\Loaduiresults");
    		File folder = new File(Folderpath);
    		File[] listOfFiles = folder.listFiles();
			
    		 for (int i = 0; i < listOfFiles.length; i++) 
    		 {
    			 if(i==0)
    				 filepath = Folderpath + "\\" + listOfFiles[i].getName();
    			 else
    			     filepath = filepath + "," + Folderpath + "\\" + listOfFiles[i].getName();	     	    
	    	 
    		 }
 
    	}
    	catch(Exception e){
    	    e.printStackTrace();
    	}
    	return filepath;
    }
	
	public  String MoveFiles(String FromFolder, String ToFolder)throws InterruptedException
    {	
 
    	InputStream inStream = null;
    	OutputStream outStream = null;
    	String Filenames = null;
    	try{
    		String filepaths = "";
    		File folder = new File(FromFolder);
    		File[] listOfFiles = folder.listFiles();
			
    		 for (int i = 0; i < listOfFiles.length; i++) 
    		 {
    			 File afile =new File(FromFolder+ "\\" + listOfFiles[i].getName());
    			 File bfile =new File(ToFolder+ "\\" + afile.getName());
    			 if (i==0)
    			 {
    				 Filenames = afile.getName();
    			 }
    			 else
    			 {
    				 Filenames = Filenames + "," +afile.getName();
    			 }
 
    			 inStream = new FileInputStream(afile);
    			 outStream = new FileOutputStream(bfile);
 
    			 byte[] buffer = new byte[1024];
 
    			 int length;
    	    //copy the file content in bytes 
    			 while ((length = inStream.read(buffer)) > 0)
    			 {
 
    				 outStream.write(buffer, 0, length);
    			 }
 
	    	    inStream.close();
	    	    Thread.sleep(500);
	    	    outStream.close();
	    	    Thread.sleep(500);
	    	    //delete the original file
	    	    filepath = filepath + afile.getName() +",";	     	    
	    	    afile.delete();
	    	    
	 
	    	    System.out.println("File is copied successful!");
    		 }
 
    	}
    	catch(IOException e){
    	    e.printStackTrace();
    	}
    	//Filenames = Filenames.substring(0, Filenames.length()-1);
    	return Filenames;
    }
	
	public void TestcaseRange()
	{
		try
		{
			DatabaseMethods db = new DatabaseMethods(database);

			ResultSet rs = db.SelectFromTable("Select * from Ltblconfigdetails");

			

			ArrayList<Integer> SkippedCases;
	  
		
			while(rs.next())
			{
				
				SkippedCases = new ArrayList<Integer>();
				//code for Skipped Cases
				String SkippedRange =rs.getString("SkippedTCaseRange");
				recipents = rs.getString("Recipents");
				if(SkippedRange== null) 
					SkippedRange="0";
			    String[] Skiptokens = SkippedRange.split(",");									    
			    for(int i=0;i<Skiptokens.length;i++)
			    {
			    	//System.out.println("Skipped Tokens: " + Skiptokens[i]);									    	
			    	if(!Skiptokens[i].contains("-"))
			    		SkippedCases.add(Integer.parseInt(Skiptokens[i]));
			    	else
			    	{
			    		String[] range=Skiptokens[i].split("-");
			    		String from = range[0];
			    		String to =  range[1];
			    		int f = Integer.parseInt(from);
			    		int t = Integer.parseInt(to);
			    		SkippedCases.add(f);
			    		while(f!=t)
			    		{	
			    			f=f+1;
			    			int s = f;									    			
			    			SkippedCases.add(s);
			    	     }
			    	}
			  }
			  System.out.print("Skipped TEST CASES ARE HERE :");
			  for(int k=0;k<SkippedCases.size();k++)
			  {
				  System.out.print(SkippedCases.get(k)+ ", ");
			  }
			 //Code for TestRangeBuild
			 String TCRange =rs.getString("TCaseRange");
			 String[] tokens = TCRange.split(",");
			 int sz=SkippedCases.size();
			 for(int i=0;i<tokens.length;i++)
			 {
			  	int b=1;
			   	if(!tokens[i].contains("-"))
			   	{
			       for(int ii=0;ii<sz;ii++)
				     if(SkippedCases.get(ii)==(Integer.parseInt(tokens[i])))
					   b=0;  
			 	    if(b!=0) TestCases.add((Integer.parseInt(tokens[i])));  
			    }
			    else
			    {
			    	String[] range=tokens[i].split("-");
			    	String from = range[0];
			    	String to =  range[1];
			    	int chk=1;
			    	int f = Integer.parseInt(from);
			    	int t = Integer.parseInt(to);
			    	for(int ii=0;ii<sz;ii++)
						   if(SkippedCases.get(ii)==f) chk=0;
			    	if(chk!=0) TestCases.add(f);
			    	while(f!=t)
			    	{	
			    		f=f+1;
			    		int sk=1;
			    		int s = f;
				    	for(int x=0;x<sz;x++)
							   if(SkippedCases.get(x)==s) sk=0;
				    	if(sk!=0) TestCases.add(s);
			    	}
			     }
			  }
		//		 log.debug("ranges of - : " + TestCases);
			  System.out.print("\nTEST CASES ARE HERE :");
			  for(int i=0;i<TestCases.size();i++)
			  {
				   System.out.print(TestCases.get(i)+ ",");
			  }
			  
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
			public static void main( String args[]) throws InterruptedException
		   {
						 
			   new Framework();
		   }
}
