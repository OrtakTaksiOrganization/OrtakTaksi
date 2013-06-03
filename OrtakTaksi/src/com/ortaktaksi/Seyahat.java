package com.ortaktaksi;

import com.ortaktaksi.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Seyahat extends Activity  
{		

	
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat);			
		 
		EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslnokt);
		EditText etarisNokt= (EditText)findViewById(R.id.tx_varisnok);
		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
		EditText etbaslSaat= (EditText)findViewById(R.id.tx_bassaati);
		etbaslNokt.setText(Havuz.StartPoint);
		etarisNokt.setText(Havuz.DestinationPoint);
		etbulsnokt.setText(Havuz.MeetingPoint);
		etbaslSaat.setText(Havuz.MeetingTime);
		
		
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
			public void onClick(View v) {
				 finish();			
			}
		};
		btnSeyahatKatil.setOnClickListener(lstnKatil);
		
	}

}
