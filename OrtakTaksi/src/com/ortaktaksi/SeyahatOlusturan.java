package com.ortaktaksi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SeyahatOlusturan extends Activity {
	
	
	
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seyahat_olusturan);
		String sifir 	=Havuz.SeciliSatirBilgileri.get(0).toString();
		String birinci 	=Havuz.SeciliSatirBilgileri.get(1).toString();
		String ikinci 	=Havuz.SeciliSatirBilgileri.get(2).toString();
		String ucuncu 	=Havuz.SeciliSatirBilgileri.get(3).toString();
		String dort 	=Havuz.SeciliSatirBilgileri.get(4).toString();
		String bes  	=Havuz.SeciliSatirBilgileri.get(5).toString();
		EditText etbaslNokt= (EditText)findViewById(R.id.tx_baslNokt);
		EditText etarisNokt= (EditText)findViewById(R.id.tx_varsNokt);
		EditText etbulsnokt= (EditText)findViewById(R.id.tx_bulsnokt);
		EditText etbaslSaat= (EditText)findViewById(R.id.txt_basl_Saati);
		etbaslNokt.setText(birinci);
		etarisNokt.setText(ikinci);
		etbulsnokt.setText(ucuncu);
		etbaslSaat.setText(bes);
		
	}

}
