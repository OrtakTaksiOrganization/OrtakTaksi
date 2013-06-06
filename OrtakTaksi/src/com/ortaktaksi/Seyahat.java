package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.ortaktaksi.R.id;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Seyahat extends Activity  
{		

	private static ListView listView;
	private RequestListAdapter adapter;
	
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat);
		final Database DB=new Database();
		 
		final EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslnokt);
		final EditText etarisNokt= (EditText)findViewById(R.id.tx_varisnok);
		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
		final EditText etbaslSaat= (EditText)findViewById(R.id.tx_bassaati);
		etbaslNokt.setText(Havuz.StartPoint);
		etarisNokt.setText(Havuz.DestinationPoint);
		etbulsnokt.setText(Havuz.MeetingPoint);
		etbaslSaat.setText(Havuz.MeetingTime);
		etbaslNokt.setKeyListener(null);
		etbulsnokt.setKeyListener(null);
		TextView btnmessage = (TextView)findViewById(R.id.btnmsg);
		btnmessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),Message.class);
				startActivity(i);	
			}
		});
		//Ýptal butonu
		Button btnIptal= (Button)findViewById(id.btn_Iptal_Seyahat);
		View.OnClickListener lstnIptl= new OnClickListener() {			
			public void onClick(View v) {
				 finish();			
			}
		};
		btnIptal.setOnClickListener(lstnIptl);
		
		Button btnSeyahatKatil= (Button)findViewById(id.btn_seyahat_katilan);
		View.OnClickListener lstnKatil= new OnClickListener() {			
			public void onClick(View v) 
			{
				
				String StartPoint=etbaslNokt.getText().toString();
				String DestPoint=etarisNokt.getText().toString();
				String StartTime=etbaslSaat.getText().toString();
				try{
				//Katýlan için ayný bilgilerle sadece varýþ ve buluþma noktasý farklý olabilen
				//Seyahat oluþtur. Oluþan bu seyahati Request Tablosuna girilen RoutesID Ve JourneyID UserID Ekle ekle.
				DB.AddJourney(Havuz.routesID, Database.UserID, StartPoint , DestPoint, StartTime);
				DB.SendRequest(Havuz.routesID, Database.UserID, Havuz.Last_JourneyID);
				Toast.makeText(getApplicationContext(), "Seyahatiniz Oluþturuldu. Talebiniz kullanýcýya iletildi.\n Kullanýcý ile iletiþime geçebilirsiniz.", Toast.LENGTH_LONG).show();
				  }
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), "Seyahat bilgileriniz iletilemedi. Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();
				}
			}
		};
		btnSeyahatKatil.setOnClickListener(lstnKatil);
		
		//Varýþ Noktasý Edit ve Click to Clear
		final TextView txVarisNok=(TextView)findViewById(id.tx_varisnok);
		txVarisNok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txVarisNok.setText("");				
			}
		});
		//Varýþ Noktasý Edit ve Click to ClearFinish
		
		
		listView = (ListView)findViewById(id.lstviewTalepEden);
		adapter = new RequestListAdapter(this, R.layout.seyahatdahilolanlist, 0, RequestListLoad(Havuz.routesID));		
		listView.setAdapter(adapter);
		
		
	}
	
	public class RequestListClass 
	{
		private int RequestId;
		private int UserID;
		private int RoutesID; 	
		private String NameSurname;
		private Boolean Status;
		private int JourneyID;
		
		public RequestListClass(int RequestId,int UserID , int RoutesID,int JourneyID, String NameSurname, Boolean Status ) 
		{
			this.RequestId=RequestId;
			this.UserID= UserID;
			this.RoutesID=RoutesID;		
			this.NameSurname = NameSurname;	
			this.Status=Status;
			this.JourneyID=JourneyID;
		}
		
		public int getRequestId()
		{
			return RequestId;
		}
		public int getUserId()
		{
			return UserID;
		}
	
		public int getRoutesId()
		{
			return RoutesID;
		}
		
		public int getJourneyID()
		{
			return JourneyID;
		}
	
		public String getNameSurname() 
		{
			return NameSurname;
		}
		public Boolean getStatus()
		{
			return Status;
		}
	}
	
	public class RequestListAdapter extends ArrayAdapter<RequestListClass> 
	{

		public RequestListAdapter(Context context, int resource, int textViewResourceId,
				List<RequestListClass> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			final RequestListClass rList = getItem(position);
			final ListViewHolder holder;
			if (convertView == null) 
			{
				convertView = View.inflate(getContext(), R.layout.seyahatdahilolanlist, null);
				holder = new ListViewHolder();			
				holder.textNameSurname=(TextView)convertView.findViewById(id.tv_NameSurname2);											
				convertView.setTag(holder);			
			}
			else
			{
				holder=(ListViewHolder)convertView.getTag();
			}

				holder.textNameSurname.setText(rList.getNameSurname());
				return convertView;				
		}
		
		private class ListViewHolder {
			TextView textNameSurname;
		}

	}
	
	public List<RequestListClass> RequestListLoad(int RoutesID )
	{	
		String Sorgu=	"exec [spGetActiveRequestList] "+RoutesID+"";
		try
	    {
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+
	        Database.DbServerIP+";databaseName="+Database.DbName+"",Database.DbUser ,Database.DbPass);	        

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(Sorgu);
	        List<RequestListClass> requestList = new ArrayList<RequestListClass>(2);
	        
	        
	        while(resultSet.next())
	        {
	        	String NameSurname = resultSet.getString("NameSurname");
	        	int RequestId=resultSet.getInt("RequestId");	            
	            int RoutesId= resultSet.getInt("RoutesId");
	            int JourneyID= resultSet.getInt("JourneyID");
	            int UserId= resultSet.getInt("UserId");
	            Boolean Status= resultSet.getBoolean("Status");
	            
	            requestList.add(new RequestListClass(RequestId,UserId,RoutesId,JourneyID, NameSurname,Status));	            
	        }

	        connection.close();
	        return requestList;
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	        System.err.println("Problem Connecting!");
	        return null;
	    }	
	
	}
	
	

}
