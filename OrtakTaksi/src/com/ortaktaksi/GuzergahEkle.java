package com.ortaktaksi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ortaktaksi.R.id;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class GuzergahEkle extends Activity {		
	//getText().length() == 0
	//otomatik tamamlama deðiþkenler
		private static final String LOG_TAG = "ExampleApp"; 
		private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
		private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
		private static final String OUT_JSON = "/json";
		private static final String API_KEY = "AIzaSyA1D3UgZJve9TbEdplzXiv3uxbwPCTusNU";
		private TimePickerDialog timePickDialog = null;
		private AutoCompleteTextView Baslangic_Noktasi;
		private AutoCompleteTextView Varis_Noktasi;
	    private AutoCompleteTextView Bulusma_Noktasi;
	    public static String Bas_Nok;
	    public static String Var_Nok;
	    private Intent intent=null;
	    private Thread thread;

	protected void onCreate (Bundle savedInstanceState)
	{
		int UserID=7;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guzergahekle);
		//baslangýç,varýþ ve buluþma noktalarý otomatik tamamlama
		
		 Baslangic_Noktasi = (AutoCompleteTextView) findViewById(R.id.txt_baslangicNokt);
		 Baslangic_Noktasi.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		 Varis_Noktasi = (AutoCompleteTextView) findViewById(R.id.txt_varisNokt);
		 Varis_Noktasi.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item)); 
		 Bulusma_Noktasi = (AutoCompleteTextView) findViewById(R.id.txt_BulusmaNoktasi);
		 Bulusma_Noktasi.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		 
		 if (Bas_Nok!=null) {
			 Baslangic_Noktasi.setText(Bas_Nok);	
		}
		 if (Var_Nok!=null) {
			 Varis_Noktasi.setText(Var_Nok);	
		}
		 //bulusma noktasý map 
		 Button bulusmaMap= (Button)findViewById(R.id.bulusmagps);   
		 View.OnClickListener Bulusma = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bas_Nok=Baslangic_Noktasi.getText().toString();
				Var_Nok=Varis_Noktasi.getText().toString();
				Intent i = new Intent(getApplicationContext(), PlaceActivity.class );
				startActivity(i);
				
			}
		};
		bulusmaMap.setOnClickListener(Bulusma);
		try {
			Bundle bundle=getIntent().getExtras();
			
			String Name=bundle.getString("Adres");;
		
			Bulusma_Noktasi.setText(Name);
		} catch (Exception e) {
			// TODO: handle exception
		}

		//Baslangýc saati 
				TextView Bas_saat= (TextView)findViewById(R.id.txt_Basl_Saati);
				View.OnClickListener Saat= new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Time today = new Time();
						today.setToNow();
						String time =today.format("%k:%M:%S");

		                if (time != null && !time.equals("")) {
		                    StringTokenizer st = new StringTokenizer(time, ":");
		                    String timeHour = st.nextToken();
		                    String timeMinute = st.nextToken();

		                    timePickDialog = new TimePickerDialog(v.getContext(),
		                            new TimePickHandler(), Integer.parseInt(timeHour),
		                            Integer.parseInt(timeMinute), true);
		                } else {
		                    timePickDialog = new TimePickerDialog(v.getContext(),
		                            new TimePickHandler(), 10, 45, true);
		                }

		                timePickDialog.show();
					}
				};
				Bas_saat.setOnClickListener(Saat);

		//Anasayfa Ýptal butonu
		Button btnIptal= (Button)findViewById(id.btn_Iptal);
		View.OnClickListener lstnIptl= new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				 finish();			
			}
		};
		btnIptal.setOnClickListener(lstnIptl);
		
		
		
		Button btnKaydet= (Button)findViewById(id.btn_Kaydet);
		View.OnClickListener lstnKaydet= new OnClickListener() {			
							
			@Override
		    public void onClick(View v) 
			{
				if (!isOnline()) 
				{
					Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý Kontrol Ediniz.",Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
				String strBaslNok, strVarisNokt, strBulsNokt, strBulsZamn, strKisiSay;
                int kisiSayisi;
				
				TextView baslNokt= (TextView)findViewById(R.id.txt_baslangicNokt);
				TextView VarsNokt= (TextView)findViewById(R.id.txt_varisNokt);
				TextView BulsNokt= (TextView)findViewById(R.id.txt_BulusmaNoktasi);
				TextView kisiSayi= (TextView)findViewById(R.id.txt_KisiSayisi);
				TextView bulsZmn=  (TextView)findViewById(R.id.txt_Basl_Saati);
				
				if(baslNokt.getText().equals("")| VarsNokt.getText().equals("")|
						BulsNokt.getText().equals("")| kisiSayi.getText().equals("")|
						bulsZmn.getText().equals("") )
					{
					Toast.makeText(getApplicationContext(), "Ýlgili Alanlarý Kontrol Ediniz.",Toast.LENGTH_LONG).show();
					return;
					}
				
				strBaslNok	=baslNokt.getText().toString();
				strVarisNokt=VarsNokt.getText().toString();
				strBulsNokt	=BulsNokt.getText().toString();
				strBulsZamn	=bulsZmn.getText().toString();
				strKisiSay	=kisiSayi.getText().toString();
				kisiSayisi	=Integer.parseInt(strKisiSay);
				
				try
				{
					Database db = new Database();
					db.AddRoutes(strBaslNok, strVarisNokt, strBulsNokt, Database.UserID, kisiSayisi, strBulsZamn);
					//Toast.makeText(getApplicationContext(),"Ýþleminiz baþarýyla gerçekleþtirildi.",Toast.LENGTH_LONG ).show();
					Intent hvz = new Intent(getApplicationContext(), Havuz.class );
					startActivity(hvz);
				}
				catch(Exception e)
				{
					//Toast.makeText(getApplicationContext(), "Bir sorun oluþtu..TEKRAR DENEYÝNÝZ", Toast.LENGTH_LONG).show();
				}
			  }	
				
		    }
			
		};
			btnKaydet.setOnClickListener(lstnKaydet);
			
		
					
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
		    
		    //Time picker sýnýfý
		    private class TimePickHandler implements OnTimeSetListener {
		    	final TextView Bas_Saat= (TextView)findViewById(R.id.txt_Basl_Saati);
		        @Override
		        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		        	Bas_Saat.setText(hourOfDay + ":" + minute);
		            timePickDialog.hide();

		        }

		    }

	//Internet Baðlantýsý Kontrolü
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}