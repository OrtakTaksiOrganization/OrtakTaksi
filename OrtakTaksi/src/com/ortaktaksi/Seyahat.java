package com.ortaktaksi;

import com.ortaktaksi.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Seyahat extends Activity  
{
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat);
//		String sifir 	=Havuz.SeciliSatirBilgileri.get(0).toString();
//		final String birinci 	=Havuz.SeciliSatirBilgileri.get(1).toString();
//		final String ikinci 	=Havuz.SeciliSatirBilgileri.get(2).toString();
//		String ucuncu 	=Havuz.SeciliSatirBilgileri.get(3).toString();
//		final String dort 	=Havuz.SeciliSatirBilgileri.get(4).toString();
//		final String bes  	=Havuz.SeciliSatirBilgileri.get(5).toString();
//		EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslnokt);
//		EditText etarisNokt= (EditText)findViewById(R.id.tx_varisnok);
//		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
//		EditText etbaslSaat= (EditText)findViewById(R.id.tx_bassaati);
//		etbaslNokt.setText(birinci);
//		etarisNokt.setText(ikinci);
//		etbulsnokt.setText(ucuncu);
//		etbaslSaat.setText(bes);
		
		
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
				
				//Database DB=new Database();
				//int guzID= Integer.parseInt(dort);
				//int CurrentUserID = 1;
				//, CurrentUserID, SeyahatID, Durum
				//DB.AddJourney(guzID, CurrentUserID, birinci, ikinci, bes);				
				 //Katýlma Talebi			
			}
		};
		btnSeyahatKatil.setOnClickListener(lstnKatil);
		
	}

}
