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
		
		
		//Ýptal butonu
		Button btnIptal= (Button)findViewById(id.btn_Iptal_Seyahat);
		View.OnClickListener lstnIptl= new OnClickListener() {			
			public void onClick(View v) {
				 finish();			
			}
		};
		btnIptal.setOnClickListener(lstnIptl);
		
	}

}
