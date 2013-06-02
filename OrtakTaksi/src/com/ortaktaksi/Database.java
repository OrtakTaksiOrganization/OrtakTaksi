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
	public static String DbServerIP = "192.168.1.44:1433";
	public static String DbName= "projedb";
	public static String DbUser = "sa";
	public static String DbPass= "123";
	
	//FAcebookBilgileri Ve User Bilgileri
	public static String CurrentUserEmailAddress;
	public static int 	UserID;//OrtakTaksi KullanýcýID.
	public static int 	ConnectionCount=0;
	public static String FbEmail;
	public static String FbUserID;//FacebookUserID => Resim çekerken kullanýlýr.
	public static String FbUserName;
	public static String FbName;
	public static String FbSex;
	public static String UserPassword;
	
	public ResultSet DBRunQuery(String sorgu)
	{
		try
		{      
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	        		"jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        
	        Statement statement = connection.createStatement();
	        ResultSet resultset= statement.executeQuery(sorgu);
	        connection.close();
        return resultset;
        
		}
		catch(Exception ex)
		{
			return null;			
		}
		
	}
	
	//Add User from email refer
	public void AddUser(String Email)
	{	
		String sorgu= "exec spAddUsers '"+FbName+"','"+FbEmail+"','"+FbUserName+"','"+FbUserName+"','"+ UserPassword+"','"+FbSex+"'";
                        
		try 
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection conn = DriverManager
            .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);
			Statement statement = conn.createStatement();
			statement.executeQuery(sorgu);
		} 
		catch (SQLException e)
		{
			ConnectionCount=-2;
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
	
	//Get Current User ID but return null  Add Login User System
	public int 	GetCurrentUserID(String FbEmailAdress) 
	{
		int ID = 0;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection conn = DriverManager
            .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);
			Statement statement = conn.createStatement();	
			String GetUserID_Query="exec projedb.dbo.spGetUserID '"+FbEmailAdress+"'";			
			
			ResultSet rs=statement.executeQuery(GetUserID_Query);			
//			if (rs!=null) 
//			{
				 while (rs.next())
				 {
					ID=Integer.parseInt(rs.getString("UserID").toString()); 
				 }
				return ID;
//			}
//			else 
//			{
//				AddUser(FbEmail);
//				return GetCurrentUserID(FbEmail);
//			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Toast.makeText(this, "Hata : " + e.toString()+" -GetCurrentUserId Blogu", Toast.LENGTH_SHORT).show();
			return -1;
		}
		finally
		{
			
		}
		
	}
	
	//Güzergah Ekleme 
	public void AddRoutes(final String StartPoint , 
			final String DestinationPoint, 
			final String MeetingPoint, 
			final int CreateUserID,
			final int PeopleCount,
			final String MeetingTime)
		{
		try
		{ 
			String Sorgu="exec spAddRoutes '"+StartPoint+"', '"+DestinationPoint+"', '"+MeetingPoint+"',"+CreateUserID+" , "+PeopleCount+", '"+MeetingTime+"'";
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	        "jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        
	        Statement statement = connection.createStatement();
	        //ResultSet resultset= 
	        statement.executeQuery(Sorgu);
	        connection.close();
	        //Toast.makeText(this, " Güzergah Eklendi.",Toast.LENGTH_LONG).show();
		}
		catch(Exception ex)
		{
			Toast.makeText(this, "Hata : " + ex.toString(), Toast.LENGTH_SHORT).show();
			return;
		}
		
			
	}//Güzergah Ekleme Bitiþ  AddRoutes Finish

	//Seyahat ekleme Baþlangýcý
	public void AddJourney( 		
			final int RoutesID , 
			final int UserID, 
			final String StarrPoint,
			final String DestinationPoint,
			final String StartTime)
	{ 
		try
	    {
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        

	        Statement statement = connection.createStatement();
	        String query = "exec spAddJourney "+RoutesID+","+UserID+", '"+StarrPoint +"', '"+DestinationPoint +"', '"+StartTime+"'";
	        statement.executeQuery(query);
	        connection.close();
	        Toast.makeText(this, "Seyahat Eklendi.",Toast.LENGTH_LONG).show();
	    }
	    catch (Exception ex)
	    {
	    	Toast.makeText(this, "Hata : " + ex.toString(), Toast.LENGTH_SHORT).show();
			return;
	    }		
	}//AddJourney Finish Statement => Seyahat ekleme Son	

}