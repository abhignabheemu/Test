//import EmailTestReport.SMTPAuthenticator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;


public class Sendmail {
	
	Properties props = new Properties();
	private String smtpHostName = null;
	private String recipient = null;
	private String subject = null;
	private String from = null;
	private String port = null;
	private String smtp = null;
	//private String Os=null;
	public String filepath = null;
	
	public Sendmail() throws IOException
	{
		//String ErrorMessage ="";
//		try {
//			
//			postMail("C:\\Resultpath\\FinalReport2012-04-04-07-20-23");
//			//postMail("C:\\Loaduiresults\\ServerVitals2012-04-04-06-15-521.csv");
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
		
	public void postMail(String Attachmentpath) throws MessagingException, IOException
	{
		boolean debug = false;
		
		Message msg;
		new ReadConfigDetails();
		String ErrorMessage = "passed";//LoadTestReport.finalTestResult;
		smtpHostName = ReadConfigDetails.SMTPServer;//"mx.valuelabs.net";
		
		recipient = Framework.recipents;//"abhigna.bheemu@valuelabs.net";
		System.out.println("Recpient " + recipient);
		System.out.println("Attachment path " + Attachmentpath);
		from = ReadConfigDetails.FromMailAddress;//"abhigna.bheemu@valuelabs.net";
		subject = "Testmail";
		smtp = ReadConfigDetails.SMTPServer;//"mx.valuelabs.net";
		port = ReadConfigDetails.SMTPPort;//"25";
		//Os= props.getProperty("OS");
		
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.auth", "true");
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", smtp);
		props.setProperty("mail.host", ReadConfigDetails.SMTPServer);
		props.setProperty("mail.port", port);
		Authenticator auth = new SMTPAuthenticator();
		
		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(debug);
		
		// create a message
		msg = new MimeMessage(session);
		
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		String[] recipients = recipient.split(";");
		InternetAddress[] addressTo =new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		// Setting the Subject and Content Type
		//int passedcnt=2;
		//int failedcnt=1;
		//int skippedcnt=0;
		//int totalcnt=passedcnt + failedcnt + skippedcnt;
		//subject=subject +" - "+ (new java.util.Date()).toString();
		
		int wfailedCnt= LoadTestReport.wfcnt;
		int wpassedCnt = LoadTestReport.wpcnt;
		int wexecCnt = wfailedCnt + wpassedCnt;
		
		int lfailedCnt= LoadTestReport.lfcnt;
		int lpassedCnt = LoadTestReport.lpcnt;
		int lexecCnt = lfailedCnt + lpassedCnt;
		if (ErrorMessage.equalsIgnoreCase("passed"))
		{
			subject="PerformanceReport - Passed - "+ (new java.util.Date()).toString();
		}
		else
		{
			subject="PerformanceReport - Failed - "+ (new java.util.Date()).toString();
		}
		//if(failedcnt >= 1) subject=subject + " -- Contains Failed Cases";
		msg.setSubject(subject);
		String message="<b>Hi All,</b><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Following is the high level Performance Automation Execution Summary <br/><ul><table border=3 cellpadding=5 bgcolor=#C6DEFF ><tr> <th align=center >Performance Testing:</th><td align=center width =80 style=color:#000000><b>WAP Site</b></td><td align=center width =80 style=color:#000000><b>Web Services</b></td></tr><tr> <th align=center >Cases Executed:</th><td align=center width =80 style=color:#000000><b>"+wexecCnt+"</b></td><td align=center width =80 style=color:#000000><b>"+lexecCnt+"</b></td></tr><tr> <th align=center>Passed:</th> <td align=center width =80 style=color:#387C44><b>"+wpassedCnt+"</b></td><td align=center width =80 style=color:#387C44><b>"+lpassedCnt+"</b></td></tr><tr><th align=center>Failed:</th><td align=center width =80 style=color:red><b>"+wfailedCnt+"</b></td><td align=center width =80 style=color:red><b>"+lfailedCnt+"</b></td></tr></table></ul><br/>&nbsp;&nbsp;&nbsp;Attached is the detailed Performance test Automation results report.</br></br>Thanks,<br/>ValueLabs<br/><STYLE>BODY{color:#000001;font-size:10pt; font-family:Verdana }</STYLE><BODY>";
		
		msg.setContent(message, "text/html");
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		String AttachFiles= Attachmentpath+".html";//"D:\\websites.txt,c:\\loadui.txt";
		System.out.println("Attachment path " + AttachFiles);
		String testResultPath = "";
		String[]ArrAttFiles = null;
		
		ArrAttFiles = AttachFiles.split(",");
		System.out.println(ArrAttFiles.length);
		for(int k=0;k<=ArrAttFiles.length-1;k++ )
		{
			messageBodyPart = new MimeBodyPart();
			
			testResultPath = ArrAttFiles[k];
			System.out.println("test result path   " +testResultPath);
			DataSource tResult = new FileDataSource(ArrAttFiles[k]);
			messageBodyPart.setDataHandler(new DataHandler(tResult));
			messageBodyPart.setFileName(testResultPath.substring(testResultPath.lastIndexOf('/')+ 1, testResultPath.length()));
			multipart.addBodyPart(messageBodyPart);
		}
		
		//messageBodyPart = new MimeBodyPart();
		
		//testResultPath = ArrAttFiles[k];
//		System.out.println(AttachFiles);
//		DataSource tResult = new FileDataSource(AttachFiles);
//		messageBodyPart.setDataHandler(new DataHandler(tResult));
//		messageBodyPart.setFileName(AttachFiles.substring(AttachFiles.lastIndexOf('/')+ 1, AttachFiles.length()));
//		multipart.addBodyPart(messageBodyPart);
		
		msg.setContent(multipart);
		try{
			Transport.send(msg);
		}
		catch(SendFailedException sfe)
		{
			sfe.printStackTrace();
		} 
	} 
	
	/**
	* SimpleAuthenticator is used to do simple authentication
	* when the SMTP server requires it.
	*/
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		private PasswordAuthentication authentication;
		
		public SMTPAuthenticator() {
			new ReadConfigDetails();
			String username= ReadConfigDetails.SMTPusername;
			String password = ReadConfigDetails.SMTPpassword;	
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	
	
	

//	public static void main(String args[])
//	{
//			
//		try {
//			new Sendmail();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}


