package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Message extends Activity {
	
	private ListView listview;
	private MessageAdapter adapter;	
	public static String Name	;
	public static String Message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messages);
		OnInýt();
		
		Button btnGonder=(Button)findViewById(R.id.MessageButton);
		final EditText textMessages=(EditText)findViewById(R.id.MessageText);
		
		btnGonder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String MessagesText=textMessages.getText().toString();
				String sorgu="Insert Into Messages(RouteID,Message,SenderID) values ("+Havuz.routesID+",'"+MessagesText+"',"+Database.UserID+")";
				try
			    {
			        Class.forName("net.sourceforge.jtds.jdbc.Driver");
			        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+Database.DbServerIP+";databaseName="+Database.DbName+"",Database.DbUser ,Database.DbPass);	        
			        Statement statement = connection.createStatement();
			        statement.executeUpdate(sorgu);
			        OnInýt();
			        textMessages.setText(" ");
			    }
				catch (Exception e)
			    {
			        e.printStackTrace();
			    }	
			}
		});
		
	}
	
	public void OnInýt()
	{
		listview = (ListView)findViewById(R.id.MessageList);		 
		adapter = new MessageAdapter(this, R.layout.message_list_item, 0, MessageLoad() );
		listview.setAdapter(adapter);
	}
		
	public List<MessaageClass> MessageLoad( )
	{	
		int RouteID=Havuz.routesID;
		String Sorgu=	"SELECT Users.NameSurname, Messages.Message, Messages.RouteId From Messages " +
				"INNER JOIN Users ON Messages.SenderId = Users.UserID Where Messages.RouteId="+RouteID+"";
		try
	    {
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+Database.DbServerIP+";databaseName="+Database.DbName+"",Database.DbUser ,Database.DbPass);	        

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(Sorgu);
	        List<MessaageClass> listMessage = new ArrayList<MessaageClass>(2);
	        
	        
	        while(resultSet.next())
	        {
	           
	             Message = resultSet.getString("Message");
	             Name = resultSet.getString("NameSurname");
	            listMessage.add(new MessaageClass(Name,Message));	            
	        }

	        connection.close();
	        return listMessage;
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	        System.err.println("Problem Connecting!");
	        return null;
	    }	
	
	}

}