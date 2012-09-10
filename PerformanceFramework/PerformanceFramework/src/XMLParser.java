import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
//import org.jaxen.XPath;;


public class XMLParser {
	
	Double TCAvgrespTime = null;
	Double TCHitspersec = null;
	Double TCFailHitspersec = null;
	Double TCToterrors = null;
	Double TCFailAss = null;
	Double TCTotAss = null;
	java.sql.Timestamp TCStartTime =null;
	java.sql.Timestamp TCExecutionTime = null;
	java.sql.Timestamp TCEndTime = null;
	String TCStatus = null;
	int TCId = 0;
	String TCTitle = null;
	String RepTitle = null;
	
	XPath  xpathInstance   =   null;
	java.text.DateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss"); 
	//String[] path = null;
	//function will giv etive the value of node based on XPath
	public String FindnodevaluewithXpath(Element rootnode,String Xpath)
	
	{
		String[] path = null;
		String Elmname =null;
		int indent = 0;
		int Actnindent = 1;
		String nodevalue = null;
		Element node = null;
		String pathname = null;
		try
		{

			
			if (Xpath.contains("/"))
				path = Xpath.split("/");
			else
				path[0] = Xpath;
			
			for(int i=0; i<path.length ; i++)
			{	
								
				List list = rootnode.getChildren();
				
				Iterator elemIterator2 = list.iterator();
				Actnindent = 1;
				indent =0;
				while (elemIterator2.hasNext() )
				{
		        	
					node = (Element) elemIterator2.next();
		        	//System.out.println(node.getName());
		        	//System.out.println(path[i]);
		        	Elmname = node.getName();		
		        	
		        	if(path[i].contains("["))
		        	{
		        		int x = path[i].indexOf("[");	        		
		        		
		        		pathname = path[i].substring(0,x);
		        		//System.out.println(indent);
		        	}
		        	else
		        	{
		        		pathname = path[i];
		        	}
		            if (pathname.equals(Elmname) )
		            {            	
		            	if(path[i].contains("["))
			        	{
			        		int x = path[i].indexOf("[");
			        		int y = path[i].indexOf("]");
			        		//System.out.println(path[i].substring(x+1,y));
			        		indent = Integer.parseInt(path[i].substring(x+1,y));
			        		//System.out.println(indent);
			        		
			        	}
		            	if(indent > 0)
	            		{
	            			if(indent == Actnindent)
	            			{
	            				if(i+1 == path.length )
	            				{
	    	            			nodevalue =  node.getValue();
	            					break;
	            				}
	            				else
	    		            	{
//	    		            		int j=i+1;
//	    			            	String xpath = path[j] ;
//	    			            	for (j=i+2;j<path.length;j++)
//	    			            	{
//	    			            		xpath = xpath + "/" + path[j];
//	    			            	}
	            					
//	    			            	System.out.println(xpath);
	    			            	//System.out.println(node.getName());
	    			            	//System.out.println(node);
	    			            	rootnode = node;
	    			            	break;
	    			            	//nodevalue = FindnodevaluewithXpath(node,xpath);
	    		            	}
	            				
	            			}
	            			Actnindent = Actnindent+1;
	            		}
		            	else
		            	{
		            		if(i+1 == path.length )
		            		{
		            			nodevalue =  node.getValue();
	        					break;
	        				}
		            		else
			            	{
//			            		int j=i;
//				            	String xpath = path[j] ;
//				            	for (j=i+1;j<path.length;j++)
//				            	{
//				            		xpath = xpath + "/" + path[j];
//				            	}
				            	rootnode = node;
				            	break;
				            	//nodevalue = FindnodevaluewithXpath(node,xpath);
			            	}
		            		
		            	}
		            	
		            	
		            }
		        }
			}
			
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(nodevalue);
		return nodevalue;
	}
	
	public java.sql.Timestamp ConvertStringToDate(String StrDate)
	{	
		
		
		java.util.Date date =null;  
		
//		java.sql.Date sqlDate = null;
		java.sql.Timestamp timestamp =null;
		
		 Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		  //System.out.println(currentDate.getTime());
		  String dateNow = formatter.format(currentDate.getTime());
		  //System.out.println(dateNow);
		  StrDate = dateNow+ " " + StrDate;
		  //System.out.println(StrDate);
		  
		  sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		try
		{ 
		           
			 date = sdf.parse(StrDate.trim()); 
			 
//			 sqlDate = new java.sql.Date(date.getTime());
			 
			 timestamp = new java.sql.Timestamp(date.getTime());

		 //  time = date.;
			 
//		   System.out.println("Date and Time: " + date);
//		   System.out.println("Date and Time: " + sqlDate);
//		   System.out.println("Date and Time: " + sdf.format(sqlDate));
//		   System.out.println("Date and Time: " + sdf.format(timestamp));
		   //System.out.println(date);
			      
		}
		catch (Exception e)
		{ 
		           
			e.printStackTrace(); 
	        
		} 
		return timestamp;
	}
	
	public void updateXMLinDatabase(int tclog,String testcaseid,String database,String filepath)
	{
		int i=0;
		int TCExecutionlog=0;
		java.sql.Date sqldate = null;
		ResultSet rs = null;
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(filepath);

		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren();
			Iterator elemIterator2 = list.iterator();
			
            if (elemIterator2.hasNext() ){
            	
            	String Tempvalue =null;
            	Element Elm = (Element) elemIterator2.next();
            	RepTitle = Elm.getAttributeValue("title");
            	System.out.println("project"+ RepTitle);
            	if(list.size() == 1)
            	{
            		TCId = Integer.parseInt(testcaseid);
                	TCTitle =RepTitle.trim();
                	TCStatus = FindnodevaluewithXpath(Elm,"section[1]/status");
                	//System.out.println(TCStatus);
                	TCExecutionlog = tclog;
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/duration");
                	//System.out.println(Tempvalue);
                	TCExecutionTime = ConvertStringToDate(Tempvalue);
                	//TCExecutionTime = sdf.format(sqldate);
                	System.out.println("ExecTime:" + TCExecutionTime);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/start_time");
                	TCStartTime = ConvertStringToDate(Tempvalue);
                	System.out.println("StartTime" + TCStartTime);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/end_time");
                	TCEndTime =ConvertStringToDate(Tempvalue);
                	System.out.println("EndTime" + TCEndTime);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[3]/runners/row/avg");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCAvgrespTime = Double.parseDouble(Tempvalue);
                	System.out.println("TCAvgrespTime" + TCAvgrespTime);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_requests");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCHitspersec= Double.parseDouble(Tempvalue);
                	System.out.println("TCHitspersec" + TCHitspersec);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_failed_requests");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCFailHitspersec= Double.parseDouble(Tempvalue);
                	System.out.println("TCFailHitspersec" + TCFailHitspersec);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[3]/runners/row/err");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCToterrors= Double.parseDouble(Tempvalue);
                	System.out.println("TCToterrors" + TCToterrors);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_assertions");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCTotAss= Double.parseDouble(Tempvalue);
                	System.out.println("TCTotAss" + TCTotAss);
                	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_failed_assertions");
                	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
                	{
                		Tempvalue = "0";
                	}
                	TCFailAss= Double.parseDouble(Tempvalue);
                	System.out.println("TCFailAss" + TCFailAss);
                	String command = "select TCaseID from ltbltestcasemstr where TCaseTitle = "  + (char)34  + RepTitle.trim() + (char)34  ;
                	DatabaseMethods db = new DatabaseMethods(database);
//                	rs = db.SelectFromTable(command);
//                	rs.first();
//                	TCId = Integer.parseInt(rs.getString("TCaseID"));
                	
                	//command = "INSERT INTO ltbltestresults (`TCaseID`, `TCaseTitle`, `TCaseResult`, `TCaseExecutionLog`, `TcaseExecutionTime`, `TCaseStartTime`, `TCaseEndTime`, `TCaseResponsepersec`, `TCaseTotalRequestsorhitspersec`, `TCaseFailedRequestsorhitspersec`, `TCaseTotalerrors`, `TCaseTotalAssertions`, `TCaseFailedassertions`) VALUES ("+ TCId + ",`" + TCTitle + "`,`" + TCStatus + "`," + TCExecutionlog + ",`" + TCExecutionTime + "`,`" + TCStartTime + "`,`" + TCEndTime + "`," + TCAvgrespTime + "," + TCHitspersec + "," + TCFailHitspersec +	"," + TCToterrors + "," + TCTotAss + "," + TCFailAss + ")";
                	command = "INSERT INTO ltbltestresults (`TCaseID`, `TCaseTitle`, `TCaseResult`, `TCaseExecutionLog`, `TcaseExecutionTime`, `TCaseStartTime`, `TCaseEndTime`, `TCaseResponsepersec`, `TCaseTotalRequestsorhitspersec`, `TCaseFailedRequestsorhitspersec`, `TCaseTotalerrors`, `TCaseTotalAssertions`, `TCaseFailedassertions`) VALUES (" + TCId + ", '" + TCTitle + "', '" + TCStatus+ "', "+ TCExecutionlog + ", '"+ TCExecutionTime + "', '"+ TCStartTime + "', '"+ TCEndTime + "', "+ TCAvgrespTime +", "+ TCHitspersec +", "+ TCFailHitspersec + ", "+ TCToterrors + ", "+ TCTotAss + ", "+ TCFailAss + ")";
                	System.out.println(command);
                	db.UpdateInsertToTable(command);
                	
                }
            	
            }
            i=2;
            while (elemIterator2.hasNext() ){
            	
            	Element Elm = (Element) elemIterator2.next();
            	String Attributevalue = Elm.getAttributeValue("title");
            	String Tempvalue = "";
            	TCId = 0;
            	TCTitle =Attributevalue.trim();
            	TCStatus = FindnodevaluewithXpath(Elm,"section[1]/status");
            	//System.out.println(TCStatus);
            	TCExecutionlog = tclog;
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/duration");
            	//System.out.println(Tempvalue);
            	TCExecutionTime = ConvertStringToDate(Tempvalue);
            	//TCExecutionTime = sdf.format(sqldate);
            	System.out.println("ExecTime:" + TCExecutionTime);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/start_time");
            	TCStartTime = ConvertStringToDate(Tempvalue);
            	System.out.println("StartTime" + TCStartTime);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/end_time");
            	TCEndTime =ConvertStringToDate(Tempvalue);
            	System.out.println("EndTime" + TCEndTime);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[3]/runners/row/avg");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCAvgrespTime = Double.parseDouble(Tempvalue);
            	System.out.println("TCAvgrespTime" + TCAvgrespTime);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_requests");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCHitspersec= Double.parseDouble(Tempvalue);
            	System.out.println("TCHitspersec" + TCHitspersec);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_failed_requests");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCFailHitspersec= Double.parseDouble(Tempvalue);
            	System.out.println("TCFailHitspersec" + TCFailHitspersec);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[3]/runners/row/err");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCToterrors= Double.parseDouble(Tempvalue);
            	System.out.println("TCToterrors" + TCToterrors);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_assertions");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCTotAss= Double.parseDouble(Tempvalue);
            	System.out.println("TCTotAss" + TCTotAss);
            	Tempvalue = FindnodevaluewithXpath(Elm,"section[2]/total_number_of_failed_assertions");
            	if(Tempvalue.trim().equalsIgnoreCase("n/a"))
            	{
            		Tempvalue = "0";
            	}
            	TCFailAss= Double.parseDouble(Tempvalue);
            	System.out.println("TCFailAss" + TCFailAss);
            	String command = "select TCaseID from ltbltestcasemstr where TCaseTitle = "  + (char)34  + RepTitle.trim() + (char)34  ;
            	DatabaseMethods db = new DatabaseMethods(database);
            	rs = db.SelectFromTable(command);
            	rs.first();
            	TCId = Integer.parseInt(rs.getString("TCaseID"));
            	
            	//command = "INSERT INTO ltbltestresults (`TCaseID`, `TCaseTitle`, `TCaseResult`, `TCaseExecutionLog`, `TcaseExecutionTime`, `TCaseStartTime`, `TCaseEndTime`, `TCaseResponsepersec`, `TCaseTotalRequestsorhitspersec`, `TCaseFailedRequestsorhitspersec`, `TCaseTotalerrors`, `TCaseTotalAssertions`, `TCaseFailedassertions`) VALUES ("+ TCId + ",`" + TCTitle + "`,`" + TCStatus + "`," + TCExecutionlog + ",`" + TCExecutionTime + "`,`" + TCStartTime + "`,`" + TCEndTime + "`," + TCAvgrespTime + "," + TCHitspersec + "," + TCFailHitspersec +	"," + TCToterrors + "," + TCTotAss + "," + TCFailAss + ")";
            	command = "INSERT INTO `ericsson_database`.`ltbltestresults` (`TCaseID`, `TCaseTitle`, `TCaseResult`, `TCaseExecutionLog`, `TcaseExecutionTime`, `TCaseStartTime`, `TCaseEndTime`, `TCaseResponsepersec`, `TCaseTotalRequestsorhitspersec`, `TCaseFailedRequestsorhitspersec`, `TCaseTotalerrors`, `TCaseTotalAssertions`, `TCaseFailedassertions`) VALUES (" + TCId + ", '" + TCTitle + "', '" + TCStatus+ "', "+ TCExecutionlog + ", '"+ TCExecutionTime + "', '"+ TCStartTime + "', '"+ TCEndTime + "', "+ TCAvgrespTime +", "+ TCHitspersec +", "+ TCFailHitspersec + ", "+ TCToterrors + ", "+ TCTotAss + ", "+ TCFailAss + ")";
            	System.out.println(command);
            	db.UpdateInsertToTable(command);
            	i=i++;
            }

		
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}
	
	

	public XMLParser()
	{
//		int tclog = 1;
//		String databse = "ericsson_database";
//		String filepath = "c:\\Loaduiresults\\Sat-Sample-summary-1333084239967.xml";
//		updateXMLinDatabase(tclog,databse,filepath);
	}

//	public static void main( String args[])
//	{
//		new XMLParser();
//	}
}
