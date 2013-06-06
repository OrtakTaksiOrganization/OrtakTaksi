package com.ortaktaksi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ortaktaksi.R.id;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Seyahat extends Activity  
{		

	private static ListView listView;
	private RequestListAdapter adapter;
	private static final String LOG_TAG = "ExampleApp"; 
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = "AIzaSyA1D3UgZJve9TbEdplzXiv3uxbwPCTusNU";
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat);
		final Database DB=new Database();
		 
		final AutoCompleteTextView etbaslNokt=(AutoCompleteTextView) findViewById(R.id.tx_baslnokt);
		etbaslNokt.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		final AutoCompleteTextView etarisNokt= (AutoCompleteTextView)findViewById(R.id.tx_varisnok);
		etarisNokt.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		
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
	//otomatik tamammlama fonk.ve sýnýf baþlangýc
	 private ArrayList<String> autocomplete(String input) {
	        ArrayList<String> resultList = null;
	        
	        HttpURLConnection conn = null;
	        StringBuilder jsonResults = new StringBuilder();
	        try {
	            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	            sb.append("?sensor=false&key=" + API_KEY);
	            sb.append("&components=country:tr");
	            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
	            
	            URL url = new URL(sb.toString());
	            conn = (HttpURLConnection) url.openConnection();
	            InputStreamReader in = new InputStreamReader(conn.getInputStream());
	            
	            // Load the results into a StringBuilder
	            int read;
	            char[] buff = new char[1024];
	            while ((read = in.read(buff)) != -1) {
	                jsonResults.append(buff, 0, read);
	            }
	        } catch (MalformedURLException e) {
	            Log.e(LOG_TAG, "Error processing Places API URL", e);
	            return resultList;
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "Error connecting to Places API", e);
	            return resultList;
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }

	        try {
	            // Create a JSON object hierarchy from the results
	            JSONObject jsonObj = new JSONObject(jsonResults.toString());
	            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
	            
	            // Extract the Place descriptions from the results
	            resultList = new ArrayList<String>(predsJsonArray.length());
	            for (int i = 0; i < predsJsonArray.length(); i++) {
	                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	            }
	        } catch (JSONException e) {
	            Log.e(LOG_TAG, "Cannot process JSON results", e);
	        }
	        
	        return resultList;
	    }
	    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	        private ArrayList<String> resultList;
	        
	        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
	            super(context, textViewResourceId);
	        }
	        
	        @Override
	        public int getCount() {
	            return resultList.size();
	        }

	        @Override
	        public String getItem(int index) {
	            return resultList.get(index);
	        }

	        @Override
	        public Filter getFilter() {
	            Filter filter = new Filter() {
	                @Override
	                protected FilterResults performFiltering(CharSequence constraint) {
	                    FilterResults filterResults = new FilterResults();
	                    if (constraint != null) {
	                        // Retrieve the autocomplete results.
	                        resultList = autocomplete(constraint.toString());
	                        
	                        // Assign the data to the FilterResults
	                        filterResults.values = resultList;
	                        filterResults.count = resultList.size();
	                    }
	                    return filterResults;
	                }

	                @Override
	                protected void publishResults(CharSequence constraint, FilterResults results) {
	                    if (results != null && results.count > 0) {
	                        notifyDataSetChanged();
	                    }
	                    else {
	                        notifyDataSetInvalidated();
	                    }
	                }};
	            return filter;
	        }
	    }
	  //otomatik tamammlama fonk.ve sýnýf bitiþ
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
