package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Havuz extends Activity {
	
	private ListView listview;
	private HavuzAdapter adapter;
	

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
				//bu alana seyahat ekranlarýna geçiþ kodlarý eklenecek
				//ve Routes Id verilerek exec projedb.dbo.spGetRoutePoolData [RoutesID] olarak
				//gönderilip seyhat bilgileri gösterilecek
				Toast.makeText( Havuz.this,"Týklanan RoutesID ="
				+ adapter.getItem(position).getRoutesId(), Toast.LENGTH_SHORT).show();
			}			
		});		
	}
	
		
	public List<HavuzClass> HavuzLoad( )
	{	
		String DbServerIP = Database.DbServerIP;;
		String DbName= "projedb";
		String DbUser = "sa";
		String DbPass= "123";
		String Sorgu=	"exec projedb.dbo.spGetRoutesList";
		try
	    {
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(Sorgu);
	        List<HavuzClass> listHavuz = new ArrayList<HavuzClass>(2);
	        
	        
	        while(resultSet.next())
	        {
	            String StrtPoint = resultSet.getString("StartPoint");
	            String DestPoint = resultSet.getString("DestinationPoint");
	            String NameSurname = resultSet.getString("NameSurname");
	            String MeetingPoint = resultSet.getString("MeetingPoint");
	            int RoutesId= resultSet.getInt("RoutesID");
	            int CreteUserId= resultSet.getInt("CreateUserId");
	            listHavuz.add(new HavuzClass(StrtPoint,DestPoint,MeetingPoint,NameSurname,CreteUserId, RoutesId));	            
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