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
	public static int UserID=1;
//	public String GlobalUserID;
	public static int ConnectionCount=0;
	public static String FbEmail;
	public static String FbUserID;
	public static String FbUserName;
	public static String FbName;
	public static String FbSex;
	 
	
	public void AddUser(String Email)
	{
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

		                            
		                            String sorgu = "exec spAddUsers " +
		                            		"'"+ FbName +"','"+ 
		                            		FbEmail +"','"+FbUserName +"','"+ 
		                            		FbSex+"'";
		                            
		                         statement.executeQuery(sorgu);
		 
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
	
	
	public int CurrentUserID() 
	{
		String sonuc=null ;
		int ID = 0;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			} 
		catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		try {
			
			 Connection conn = DriverManager
                     .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);

                     Statement statement = conn.createStatement();
                     statement = conn.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY);
			//sonuc = statement.executeQuery("SELECT UserID FROM Users WHERE Email=FbEmail").toString();
			String sorgu="SELECT UserID FROM Users WHERE Email=FbEmail";
			if (statement.executeQuery(sorgu)!=null) {
				sonuc = statement.executeQuery(sorgu).toString();
				ID=Integer.parseInt(sonuc);
			}
			else {
				AddUser(FbEmail);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return ID;
	}
	
	public void GuzergahEkle
	( 		final String BaslNok , 
			final String VarisNokt, 
			final String BulusNokt, 
			final int KullaniciID,
			final int KisiSayisi,
			final String BulusZamani
			){
		
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
	                            		BulusZamani+"'";
	                            
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