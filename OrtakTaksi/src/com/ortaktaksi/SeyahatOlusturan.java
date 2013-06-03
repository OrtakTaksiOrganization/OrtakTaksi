package com.ortaktaksi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SeyahatOlusturan extends Activity {
	
	
	
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat_olusturan);

		EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslNokt);
		EditText etarisNokt= (EditText)findViewById(R.id.tx_varsNokt);
		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
		EditText etbaslSaat= (EditText)findViewById(R.id.txt_basl_Saati);
		etbaslNokt.setText(Havuz.StartPoint);
		etarisNokt.setText(Havuz.DestinationPoint);
		etbulsnokt.setText(Havuz.MeetingPoint);
		etbaslSaat.setText(Havuz.MeetingTime);
		
	}

}
