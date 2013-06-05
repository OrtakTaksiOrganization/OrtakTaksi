package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.ortaktaksi.R.id;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class SeyahatOlusturan extends Activity {
	
	private static ListView ListView = null;

	private RequestListAdapter adapter;
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat_olusturan);

		EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslNokt);
		EditText etvarisNokt= (EditText)findViewById(R.id.tx_varsNokt);
		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
		EditText etbaslSaat= (EditText)findViewById(R.id.txt_basl_Saati);
		etbaslNokt.setText(Havuz.StartPoint);
		etvarisNokt.setText(Havuz.DestinationPoint);
		etbulsnokt.setText(Havuz.MeetingPoint);
		etbaslSaat.setText(Havuz.MeetingTime);				
	

		
		ListView = (ListView)findViewById(id.lstView_UserRequest);
		adapter = new RequestListAdapter(this, R.layout.seyahatolusturan_listview_row, 0, RequestListLoad(Havuz.routesID));		
		ListView.setAdapter(adapter);
		
//		ListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) 
//			{				
//				Boolean status			=adapter.getItem(position).getStatus();
//				thisRequestId 			=adapter.getItem(position).getRequestId();
//
//			}
//		});
	}

	private void CheckBoxStatusChanged(Boolean Status,int RequestID)
	{
		String Sorgu;
		if(Status==true)
		{
			Sorgu="UPDATE projedb.DBO.Request set Status=0 where RequestId="+RequestID+"";
		}
		else
		{
			Sorgu="UPDATE projedb.DBO.Request set Status=1 where RequestId="+RequestID+"";
		}
		
					
			try
		    {
		        Class.forName("net.sourceforge.jtds.jdbc.Driver");
		        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+
		        Database.DbServerIP+";databaseName="+Database.DbName+"",Database.DbUser ,Database.DbPass);	        

		        Statement statement = connection.createStatement();
		        statement.executeUpdate(Sorgu);
		        connection.close();
		      
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace();
		        System.err.println("Problem Connecting!");
		        
		    }
		}
	
	
	public class RequestListClass 
	{
		private int RequestId;
		private int UserID;
		private int RoutesID; 	
		private String NameSurname;
		private Boolean Status;
		private String JRStartPoint;
		private String JRDestinationPoint;
		private int JourneyID;
		
		public RequestListClass(int RequestId,int UserID , int RoutesID,int JourneyID, String NameSurname, Boolean Status,String JRStartPoint, String JRDestinationPoint) 
		{
			this.RequestId=RequestId;
			this.UserID= UserID;
			this.RoutesID=RoutesID;		
			this.NameSurname = NameSurname;	
			this.Status=Status;
			this.JRStartPoint=JRStartPoint;
			this.JRDestinationPoint=JRDestinationPoint;
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
		public String getJRStartPoint()
		{
			return JRStartPoint;
		}
		public String getJRDestinationPoint()
		{
			return JRDestinationPoint;
		}
	}
	
	public class RequestListAdapter extends ArrayAdapter<RequestListClass> 
	{

		public RequestListAdapter(Context context, int resource, int textViewResourceId,
				List<RequestListClass> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}
		//int listeiciUserId;
		//Boolean bools;
		TextView tv;
		CheckBox cb;
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			final RequestListClass rList = getItem(position);
			final ListViewHolder holder;
			if (convertView == null) 
			{
				convertView = View.inflate(getContext(), R.layout.seyahatolusturan_listview_row, null);
				holder = new ListViewHolder();			
				holder.textNameSurname=(TextView)convertView.findViewById(id.tv_NameSurname);
				holder.chkStatus=(CheckBox)convertView.findViewById(id.chkbox_status);								
				
				convertView.setTag(holder);			
			}
			else
			{
				holder=(ListViewHolder)convertView.getTag();
			}

				holder.textNameSurname.setText(rList.getNameSurname());
				holder.chkStatus.setChecked(rList.getStatus());
				
				cb=(CheckBox)holder.chkStatus.findViewById(id.chkbox_status);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						// TODO Auto-generated method stub
						
						//listeiciUserId= rList.getUserId();
						//bools= rList.getStatus();					
						CheckBoxStatusChanged(rList.getStatus(),rList.getRequestId());
						
					
					}});
				
				
				tv=(TextView)holder.textNameSurname.findViewById(id.tv_NameSurname);				
				tv.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String str="";
						str+="Kullanýcý Adý \n"+rList.getNameSurname()+"\n\n";
						str+="Baslangýc Noktasý: \n"+rList.getJRStartPoint()+"\n\n";
						str+="Varýs Noktasý \n "+rList.getJRDestinationPoint()+"\n\n\n\n";
						
						Builder builder = new Builder(SeyahatOlusturan.this);
						builder.setMessage(str).show();						
					}
				});

				return convertView;				
		}
		
		private class ListViewHolder {
			TextView textNameSurname;
			CheckBox chkStatus;
		}

	}
	
	public List<RequestListClass> RequestListLoad(int RoutesID )
	{	
		String Sorgu=	"exec projedb.dbo.spGetRequestList "+RoutesID+"";
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
	        	int RequestId=resultSet.getInt("RequestId");
	            String NameSurname = resultSet.getString("NameSurname");
	            int RoutesId= resultSet.getInt("RoutesId");
	            int JourneyID= resultSet.getInt("JourneyID");
	            int UserId= resultSet.getInt("UserId");
	            Boolean status= resultSet.getBoolean("Status");
	            String JR_StartPoint=resultSet.getString("JR_StartPoint");
	            String JR_DestinationPoint=resultSet.getString("JR_DestinationPoint");
	            
	            requestList.add(new RequestListClass(RequestId,UserId,RoutesId,JourneyID, NameSurname,status,JR_StartPoint,JR_DestinationPoint));	            
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