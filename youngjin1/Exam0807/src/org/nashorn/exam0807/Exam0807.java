package org.nashorn.exam0807;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import android.app.Activity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Exam0807 extends Activity {
	private EditText 	idText;
	private EditText 	passwordText;
	private EditText 	emailAddressText;
	private EditText 	titleText;	
	private EditText 	commentText;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        idText = (EditText) findViewById(R.id.id);
        passwordText = (EditText) findViewById(R.id.password);
        emailAddressText	= (EditText) findViewById(R.id.recieve_email);
        titleText = (EditText) findViewById(R.id.title);
        commentText = (EditText) findViewById(R.id.text);
        
        Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new sendEmail().execute("");
			}
		});
    }


	public boolean sendMail() {
		MailSender mailSender = new MailSender();
    	try{
    		mailSender.sendMail(titleText.getText().toString(), commentText.getText().toString(),	
					idText.getText().toString(), emailAddressText.getText().toString());
    	}catch(AuthenticationFailedException e){
    		return false;
		}catch(AddressException e){
			return false;
		}catch(MessagingException e){
			return false;
		} catch (FileNotFoundException e) {
			return false;
		}finally{
			mailSender = null;
		}
		return true;
	}    
	
	public class MailSender extends javax.mail.Authenticator {    
		private Session session;
	    public MailSender() {    
		    
		    Properties props = new Properties();    
		    props.setProperty("mail.transport.protocol", "smtp");    
		    props.setProperty("mail.host", "smtp.gmail.com");    
		    props.put("mail.smtp.auth", "true");    
		    props.put("mail.smtp.port", "465");    
		    props.put("mail.smtp.socketFactory.port", "465");    
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
		    props.put("mail.smtp.socketFactory.fallback", "false");    
		    props.setProperty("mail.smtp.quitwait", "false");    
		    	      
		    session = Session.getInstance(props, this);
	    }    
	
		public synchronized void sendMail(String subject, String body, String sender, String recipients) 
			throws AuthenticationFailedException,AddressException,MessagingException, FileNotFoundException {
	
			MimeMessage message = new MimeMessage(session);
			message.setSender(new InternetAddress(sender));
			message.setSubject(subject);
			message.setSentDate(new Date());
			message.setRecipients(Message.RecipientType.TO, recipients);
	
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
		 
			message.setContent(multipart);
			Transport.send(message);
	  }
	  
	  protected PasswordAuthentication getPasswordAuthentication() {    
	       return new PasswordAuthentication(idText.getText().toString(), passwordText.getText().toString());    
	  }
		
	}
	
	private class sendEmail extends AsyncTask<String, Integer, Boolean> {
		private ProgressDialog Dialog = new ProgressDialog(Exam0807.this);   
		protected void onPreExecute() {
			Dialog.setMessage("email을 전송하는 중입니다...");
			Dialog.show();
		}
	
		@Override
		protected Boolean doInBackground(String... params) {
			return sendMail();
		}
	
		protected void onPostExecute(Boolean flag) {
			if(flag){
				Toast.makeText(getBaseContext(), "email 전송 완료", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getBaseContext(), "email 전송 실패", Toast.LENGTH_LONG).show();
			}
			Dialog.dismiss();
		};
	
		protected void onCancelled() {
		}
	}        
}