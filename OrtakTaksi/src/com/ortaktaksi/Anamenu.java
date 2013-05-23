package com.ortaktaksi;


import com.ortaktaksi.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Anamenu extends Activity 
{
	GPSTracker gps;
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anamenu);
		
		gps = new GPSTracker(Anamenu.this);

		
		if(gps.canGetLocation()){
	        	
	        	double latitude = gps.getLatitude();
	        	double longitude = gps.getLongitude();
	        	
	        	// \n is for new line
	        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
	        }else{
	        	// can't get location
	        	// GPS or Network is not enabled
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
	        }
		//Ana menü geçiþi
		Button btnAnamenu = (Button)findViewById(id.btn_anamenu);		
		View.OnClickListener lstn1= new OnClickListener() 
		{			
			public void onClick(View v) {
				getApplicationContext();
				finish();				
			}
		};		
		btnAnamenu.setOnClickListener(lstn1);
		
		//Güzergah Ekle Sayfasý geçiþ
		Button btnGuzEkle= (Button)findViewById(id.btn_guzergahekle);
		View.OnClickListener lstn2= new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), GuzergahEkle.class );
				startActivity(i);				
			}
		};
		btnGuzEkle.setOnClickListener(lstn2);
		
		
		//Havuz Sayfasý Geçiþ
		
		Button btnhavuz= (Button)findViewById(id.btn_guzergahlistele);
		View.OnClickListener lstnhavuz= new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i2 = new Intent(getApplicationContext(), Havuz.class );
				startActivity(i2);				
			}
		};
		btnhavuz.setOnClickListener(lstnhavuz);
		
		//Profil Sayfasý Geçiþ.
		Button btnprofil= (Button)findViewById(id.btn_profilbilgisi);
		View.OnClickListener lstnprofil= new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i3 = new Intent(getApplicationContext(), Profil.class );
				startActivity(i3);				
			}
		};
		btnprofil.setOnClickListener(lstnprofil);
	}
	
}
