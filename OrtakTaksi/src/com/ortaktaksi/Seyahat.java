package com.ortaktaksi;

import com.ortaktaksi.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Seyahat extends Activity  
{		

	
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
		
		
		
		//�ptal butonu
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
				//Kat�lan i�in ayn� bilgilerle sadece var�� ve bulu�ma noktas� farkl� olabilen
				//Seyahat olu�tur. Olu�an bu seyahati Request Tablosuna girilen RoutesID Ve JourneyID UserID Ekle ekle.
				DB.AddJourney(Havuz.routesID, Database.UserID, StartPoint , DestPoint, StartTime);
				DB.SendRequest(Havuz.routesID, Database.UserID, Havuz.Last_JourneyID);
				Toast.makeText(getApplicationContext(), "Seyahatiniz Olu�turuldu. Talebiniz kullan�c�ya iletildi.\n Kullan�c� ile ileti�ime ge�ebilirsiniz.", Toast.LENGTH_LONG).show();
				  }
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), "Seyahat bilgileriniz iletilemedi. Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();
				}
			}
		};
		btnSeyahatKatil.setOnClickListener(lstnKatil);
		
		//Var�� Noktas� Edit ve Click to Clear
		final TextView txVarisNok=(TextView)findViewById(id.tx_varisnok);
		txVarisNok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txVarisNok.setText("");				
			}
		});
		//Var�� Noktas� Edit ve Click to ClearFinish
		
	}

}
