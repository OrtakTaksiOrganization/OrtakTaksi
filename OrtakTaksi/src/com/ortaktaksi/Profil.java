package com.ortaktaksi;

import java.net.URL;

import com.ortaktaksi.R.id;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Profil extends Activity   
{
	
	ImageView userpicture;
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);
		try
		{
			//Profil Resmini çekmek ve Facebook Adý Soyadý Gösterimi
			userpicture=(ImageView)findViewById(R.id.imageview_profil);
			URL img_value = null;
			img_value = new URL("http://graph.facebook.com/"+Database.FbUserID+"/picture?type=large");
			Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
			TextView tx= (TextView)findViewById(id.tx_profil_FBName);
			tx.setText(Database.FbName);
			userpicture.setImageBitmap(mIcon1);
		}
		catch(Exception ex)
		{
			//
		}
	}

}
