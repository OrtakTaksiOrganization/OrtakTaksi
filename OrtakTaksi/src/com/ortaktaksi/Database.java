package com.ortaktaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.widget.Toast;

public class Database extends Activity
{
	public static String DbServerIP = "192.168.43.196:1433";
	public static String DbName= "projedb";
	public static String DbUser = "sa";
	public static String DbPass= "123";
	
	public static  String CurrentUserEmailAddress="beytullahguney@gmail.com";
	public int UserID=1;
	public String GlobalUserID;
	public static int ConnectionCount=0;
	
	
	public void GuzergahEkle
	( 		final String BaslNok , 
			final String VarisNokt, 
			final String BulusNokt, 
			final int KullaniciID,
			final int KisiSayisi,
			final String BulusZamani,
			final String xkoord,
			final String ykoord){
		
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				} 
			catch (ClassNotFoundException e1) 
			{
				ConnectionCount=-1;
				e1.printStackTrace();
			}

	        new Thread(new Runnable() {
	 
	            public void run() {
	 
	                runOnUiThread(new Runnable() {
	                    public void run() {
	                        ResultSet results = null;	                        
	                        try {
	                            Connection conn = DriverManager
	                            .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);
	 
	                            Statement statement = conn.createStatement();
	                            statement = conn.createStatement(
	                            ResultSet.TYPE_SCROLL_INSENSITIVE,
	                            ResultSet.CONCUR_READ_ONLY);

	                            
	                            String sorgu = "exec spGuzergahEkle " +
	                            		"'"+ BaslNok +"','"+ 
	                            		VarisNokt +"','"+BulusNokt +"','"+ 
	                            		KullaniciID+ "','"+KisiSayisi+"','"+ 
	                            		BulusZamani+"','"+xkoord+"','"+ykoord+"'";
	                            
	                            results = statement.executeQuery(sorgu);
	                            
	                            results.last();
	                            int kayitSayisi = 0;
	                            if (results != null) 
	                            {
	                                kayitSayisi = results.getRow();
	                            }
	                            results.beforeFirst();
	 
	                        } 
	                        	catch (SQLException e) 
	                        {
	                        		ConnectionCount=-2;
	                        		e.printStackTrace();
	                        }
	                    }
	                });
	            }			
	        }).start();
	     }			
}