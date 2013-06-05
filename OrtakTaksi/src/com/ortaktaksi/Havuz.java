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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Havuz extends Activity {
	
	private ListView listview;
	private HavuzAdapter adapter;
	public static int routesID;
	public static int Last_JourneyID;
	public static String StartPoint	;
	public static String DestinationPoint; 	
	public static String MeetingPoint;
	public static String MeetingTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listview = (ListView) View.inflate(this, R.layout.havuz, null);
		setContentView(listview);		 
		adapter = new HavuzAdapter(this, R.layout.havuz_list_item, 0, HavuzLoad() );
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
			{				
				StartPoint		=adapter.getItem(position).getStartPoint();
				DestinationPoint=adapter.getItem(position).getDestinationPoint();
				MeetingPoint	=adapter.getItem(position).getMeetingPoint();
				routesID		=adapter.getItem(position).getRoutesId();
				MeetingTime		=adapter.getItem(position).getMeetingTime();
				if(adapter.getItem(position).getCreateUserId()==Database.UserID)
				{
					Intent olstrn= new Intent(getApplicationContext(),SeyahatOlusturan.class);
					startActivity(olstrn);
					
				}
				else
				{
					Intent dahilolan=new Intent(getApplicationContext(),Seyahat.class);
					startActivity(dahilolan);					
				}
				
				//Toast.makeText( Havuz.this,"Týklanan RoutesID ="
				//+ adapter.getItem(position).getCreateUserId(), Toast.LENGTH_SHORT).show();
			}			
		});		
	}
	
		
	public List<HavuzClass> HavuzLoad( )
	{	
		String Sorgu=	"exec projedb.dbo.spGetRoutesList";
		try
	    {
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+Database.DbServerIP+";databaseName="+Database.DbName+"",Database.DbUser ,Database.DbPass);	        

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(Sorgu);
	        List<HavuzClass> listHavuz = new ArrayList<HavuzClass>(2);
	        
	        
	        while(resultSet.next())
	        {
	            String StrtPoint = resultSet.getString("StartPoint");
	            String DestPoint = resultSet.getString("DestinationPoint");	            
	            String MeetingPoint = resultSet.getString("MeetingPoint");
	            String MeetingTime = resultSet.getString("MeetingTime");
	            String NameSurname = resultSet.getString("NameSurname");
	            int RoutesId= resultSet.getInt("RoutesID");
	            int CreteUserId= resultSet.getInt("CreateUserId");
	            listHavuz.add(new HavuzClass(StrtPoint,DestPoint,MeetingPoint,MeetingTime,NameSurname,CreteUserId, RoutesId));	            
	        }

	        connection.close();
	        return listHavuz;
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	        System.err.println("Problem Connecting!");
	        return null;
	    }	
	
	}

}