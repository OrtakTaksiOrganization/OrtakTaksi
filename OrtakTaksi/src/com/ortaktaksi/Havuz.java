package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Havuz extends Activity implements OnClickListener   {
	
	TableLayout tl;
	ResultSetMetaData metaData;
	TableRow renkTableRow;

	int kolonSayisi = 0, tvId = 0, otoID = 1;
	String url, driver, userName, password;

	ArrayList<String> arrayResults = new ArrayList<String>();
	public static List<String> SeciliSatirBilgileri = new ArrayList<String>();
	
	String Sorgu= "SELECT Users.Adi + ' ' +Users.Soyadi as Kullanici,"+
	"Guzergah.BaslangicNoktasi, Guzergah.VarisNoktasi, Guzergah.BulusmaNoktasi, "+
	"Guzergah.GuzergahID FROM Guzergah INNER JOIN  Users ON	Guzergah.OlusturanID = Users.UserID";
	
	
	 
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.havuz);
		
		url = "jdbc:jtds:sqlserver://"+Database.DbServerIP +";databaseName="+Database.DbName+"";
		driver = "net.sourceforge.jtds.jdbc.Driver";		
		ayarla();
		Sorgula(Sorgu);
	}
	
	@Override
	public void onClick(View v) 
	{
		//Sorgula(Sorgu);
		// TODO Auto-generated method stub
		
	}
	
	
	private void ayarla()
	{
		tl= (TableLayout)findViewById(R.id.tlSonuc);
	}


	private void SatirGoster(int otoId) {
		String satir = "";
		SeciliSatirBilgileri.clear();
		for (int i = 1; i < kolonSayisi + 1; i++) {
			try 
			{
				

				satir += metaData.getColumnName(i).toString() + " : \r\n";
				satir += arrayResults.get(otoId * kolonSayisi - 1 + i).toString() + "\r\n\r\n";
				SeciliSatirBilgileri.add(arrayResults.get(otoId * kolonSayisi - 1 + i).toString());
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		Builder builder = new Builder(this);
		
		//builder.setMessage(satir).show();
	}
	
	private void SatirlariEkle(ResultSet results) {
		try {
			int satirSayisi = 0;

			while (results.next()) {
				TableRow row = new TableRow(this);
				row.setBackgroundColor(Color.DKGRAY);
				
				row.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						SatirGoster(((TableRow) view).getId());
						
						
//						if(Database.CurrentUserEmailAddress=="")
//						{
							Intent i2 = new Intent(getApplicationContext(), SeyahatOlusturan.class );
							startActivity(i2);							
//						}
//						else
//						{
//							Intent iwe = new Intent(getApplicationContext(), Seyahat.class );
//							startActivity(iwe);
//						}
					}
				});

//				TextView tvID = new TextView(this);
//				tvID.setText(String.valueOf(otoID));
				row.setId(otoID);
//				row.addView(tvID);


				otoID++;

				for (int i = 1; i < kolonSayisi + 1; i++) {
					TextView tv = new TextView(this);
					tv.setTextColor(Color.LTGRAY);
					
					String s = "";
					if (results.getString(i) != null)
						s = results.getString(i).toString() + "";
					else
						s = "Null";
					arrayResults.add(s);
					if (s.length() > 8) {
						s = s.substring(0, 8) + ".";
					}
					tv.setText(s + " ");
					tv.setId(tvId);
					row.addView(tv);
					tvId++;
				}
				satirSayisi++;
				tl.addView(row);
			}
			Toast.makeText(this, satirSayisi + " tane Aktif Güzergah Listelendi.",Toast.LENGTH_LONG).show();
		} 
		catch (Exception e) 
		{
			Toast.makeText(this, "Hata : " + e.toString(), Toast.LENGTH_SHORT).show();
			return;
		}
	}

	private void BasliklariEkle() {
		try {
			TableRow rowHeader = new TableRow(this);

			TextView ID = new TextView(this);
			ID.setText("Key ");
//			rowHeader.addView(ID);

			for (int i = 1; i < kolonSayisi + 1; i++) {
				TextView tv = new TextView(this);
				tv.setBackgroundColor(Color.WHITE);
				tv.setTextColor(Color.BLACK);
				String cName;

				cName = metaData.getColumnName(i);

				arrayResults.add(cName);
				if (cName.length() > 7) {
					cName = cName.substring(0, 7) + ".";
				}
				tv.setText(cName);
				tv.setId(tvId);
				rowHeader.addView(tv);
				tvId++;
			}
			tl.addView(rowHeader);
		} catch (SQLException e) {
			Toast.makeText(this, "Hata : " + e.toString(), Toast.LENGTH_SHORT)
					.show();
			return;
		}
	}
	
	private void Sorgula(String sorgu) {
		try {
			
				if (!isOnline()) 
				{
				Toast.makeText (this, "Internet Baglantinizi Kontrol Ediniz.", Toast.LENGTH_LONG).show();
								return;
				}
				else
				{				

				ResultSet results = TabloSorgula(sorgu);
				
				metaData = results.getMetaData();
				kolonSayisi = metaData.getColumnCount();
				
	
				otoID = 1;
	
				arrayResults.clear();
				tl.removeAllViews();
	
				BasliklariEkle();
				SatirlariEkle(results);
				}

		} catch (Exception e) {
			Toast.makeText(this, "HATA :" + e.toString(), Toast.LENGTH_LONG).show();
			return;
		}
	}
	
	private ResultSet TabloSorgula(String sorgu) {
		ResultSet results = null;
		try 
		{
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url, Database.DbUser,Database.DbPass);
			Statement statement = conn.createStatement();
			results = statement.executeQuery(sorgu);
			
		} 
		catch (Exception e) 
		{
			Toast.makeText(this, "Hata : " + e.toString(), Toast.LENGTH_SHORT)
					.show();
		}
		return results;
	}
		
		private boolean isOnline() {
		    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
		}

}