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
	public static String DbServerIP = "192.168.1.36:1433";
	public static String DbName = "projedb";
	public static String DbUser = "sa";
	public static String DbPass = "123";
	
	//FAcebookBilgileri Ve User Bilgileri
	public static String CurrentUserEmailAddress;
	public static int 	UserID;//OrtakTaksi KullanýcýID.
	public static int 	ConnectionCount=0;
	public static String FbEmail;
	public static String FbUserID;//FacebookUserID => Resim çekerken kullanýlýr.
	public static String FbUserName;
	public static String FbName;
	public static String FbGender;
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
		String sorgu= "exec spAddUsers '"+FbName+"','"+FbEmail+"','"+FbUserName+"','"+FbUserName+"','"+UserPassword+"','"+FbGender+"';";
                        
		try 
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection conn = DriverManager
            .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);
			Statement statement = conn.createStatement();
			statement.executeQuery(sorgu);
			conn.close();
			statement.close();
		} 
		catch (SQLException e)
		{
			ConnectionCount=-2;
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
   }
	
	//Get Current User ID but return null  Add Login User System
	public int 	GetCurrentUserID(String FbEmailAdress) 
	{
		int ID = -1;
		try 
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection conn = DriverManager
            .getConnection("jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);
			Statement statement = conn.createStatement();	
			//String GetUserID_Query="exec projedb.dbo.spGetUserID '"+FbEmailAdress+"'";			
			String GetUserID_Query="Select UserID,NameSurname from projedb.dbo.Users where Email='"+FbEmailAdress+"'";
			
			ResultSet rs=statement.executeQuery(GetUserID_Query);
 
			while (rs.next())
			 {
				ID=rs.getInt(1);				
			 }
			
			rs.close();
			statement.close();
			if(ID==-1)
			 {
				 AddUser(FbEmail);
				 ID = GetCurrentUserID(FbEmail);
			 }
			
			return ID;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//Toast.makeText(this, "Hata : " + e.toString()+" -GetCurrentUserId Blogu", Toast.LENGTH_SHORT).show();
			return -1;
		}
		finally
		{
			
		}
		
	}
	
	//Güzergah Ekleme 
	public void AddRoutes(
			final String StartPoint , 
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
	        statement.executeUpdate(Sorgu);
	        connection.close();
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
			final String StartPoint,
			final String DestinationPoint,
			final String StartTime)
	{ 
		ResultSet rs=null;
		try
	    {
			String Sorgu="INSERT INTO Journeys (RouteId, UserID, JR_StartPoint, JR_DestinationPoint, StartTime) VALUES ("+ 
					+RoutesID+", "+UserID+",'"+StartPoint+"','"+DestinationPoint+"','"+StartTime+"')";
			//String Sorgu="exec spAddJourney "+RoutesID+", "+UserID+",'"+StartPoint+"','"+DestinationPoint+"','"+StartTime+"')";
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	        "jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        
	        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE );
	        statement.executeUpdate(Sorgu,Statement.RETURN_GENERATED_KEYS);
	        rs=statement.getGeneratedKeys();
	        while(rs.next())
	        {
	        	Havuz.Last_JourneyID=rs.getInt(1);
	        }
	        rs.close();
	        connection.close();
	    }
	    catch (Exception ex)
	    {
	    	Toast.makeText(this, "Hata : " + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
			return;
	    }		
	}//AddJourney Finish Statement => Seyahat ekleme Son	

	//Güzergah Ekleme 
	public void SendRequest(
			final int RoutesID, 		
			final int CreateUserID,
			final int JourneyID)
		{
		try
		{ 	 
			String Sorgu="exec projedb.dbo.spSendRequest "+RoutesID+", "+ JourneyID +", "+CreateUserID+","+ 0 +" ";
	        Class.forName("net.sourceforge.jtds.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	        "jdbc:jtds:sqlserver://"+DbServerIP+";databaseName="+DbName+"",DbUser ,DbPass);	        
	        Statement statement = connection.createStatement();
	        statement.executeUpdate(Sorgu);
	        connection.close();
	        statement.close();	        
		}
		catch(Exception ex)
		{
			Toast.makeText(this, "Hata : " + ex.toString(), Toast.LENGTH_SHORT).show();
			return;
		}
		
			
	}//Güzergah Ekleme Bitiþ  AddRoutes Finish
}