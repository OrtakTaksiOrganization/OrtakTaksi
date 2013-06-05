package com.ortaktaksi;

import java.util.Arrays;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	 private String TAG = "MainActivity";
	 private Intent intent=null;
	 
	 public static Session S;
	Database db = new Database();
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  intent =new Intent(getApplicationContext(),Anamenu.class);
	  LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
	  S=Session.getActiveSession();
	  if(S!=null){
		  if(S.getState().isOpened()) 
		  {
		  S.getAuthorizationBundle();
		  Request.executeMeRequestAsync(S,
          new Request.GraphUserCallback() {
              @Override
              public void onCompleted(GraphUser user,Response response) {
            	  if (user!=null) {
            		  Database.FbEmail=user.asMap().get("email").toString();
                      Database.FbName=user.getName();
                      Database.FbUserID=user.getId();
                      
                      
                      
                      Database.UserID = db.GetCurrentUserID(Database.FbEmail);
                      
                      
                      //Kýyaslamada hata var gender datasý hiç gelmiyor. Bakýlacak
                      if(user.asMap().get("gender")!=null)
                      {
	                      if(user.asMap().get("gender").toString()=="male")
	                      {
	                   	   	Database.FbGender="Erkek";
	                      }
	                      else 
	                      {
	                   	   	Database.FbGender="Kadýn";
						  }
                      }
                      
				}
              }
          });		 				  
	  	//Toast.makeText(this, "Merhaba "+Database.FbName+"."+" \n Anamenüye yönlendiriliyorsunuz.", Toast.LENGTH_LONG).show();
	  	
        startActivity(intent);	
        } 
	  }
	  // set permission list, Don't foeget to add email
	  authButton.setReadPermissions(Arrays.asList("basic_info","email"));
	  
	  // session state call back event Start
	  authButton.setSessionStatusCallback(new Session.StatusCallback() {
		  
	   @Override
	   public void call(final Session session, SessionState state, Exception exception) {
		//Logout Button Click Event
	    if (session.isClosed()) {
	    	
			session.closeAndClearTokenInformation();
			Database.FbEmail=null;
			Database.FbName=null;
			Database.FbUserID=null;
			Database.FbGender=null;
			if (S!=null) {
				S.closeAndClearTokenInformation();
			}
			
		}
	    //Login Button Click Event Username password enter step Start
	    else if (session.isOpened()) {
	              Log.i(TAG,"Access Token"+ session.getAccessToken());
	              Request.executeMeRequestAsync(session,
	                      new Request.GraphUserCallback() {
	                          @Override
	                          public void onCompleted(GraphUser user,Response response) {
	                        	  
	                        	  if (user != null) { 
	                               Log.i(TAG,"User ID "+ user.getId());
	                               Log.i(TAG,"Email "+ user.asMap().get("email"));
	                               Database.FbEmail=user.asMap().get("email").toString();
	                               Database.FbUserName= user.getUsername();
	                               Database.FbName=user.getName();	                               
	                               Database.FbUserID=user.getId();
	                               
	                               
	                               //gender kontrolü null geliyor kontrol hep kadýn a geçiyo kontrol edilecek	                               
	                               if(user.asMap().get("gender").toString()=="male")
	                               {
	                            	   Database.FbGender="Erkek";
	                               }
	                               else 
	                               {
	                            	   Database.FbGender="Kadýn";
								   }
	                               
	                               Database.UserID = db.GetCurrentUserID(Database.FbEmail);
	                               startActivity(intent);
	                        	  
	                               }
	                          }
	                      });
	          }//Login Button Click Event Username password enter step Finish
	    }
	   
	  }); // session state call back event Finish
	
	  Button btnCikis = (Button)findViewById(R.id.btn_cikis);		
	  View.OnClickListener lstn1= new OnClickListener() 
		{			
			public void onClick(View v) {
				
				startActivity(intent);
				
			}
		};		
		btnCikis.setOnClickListener(lstn1);
	  
	 }
	 protected void OnResume() {
		super.onResume();
	
	 //Session s=Session.getActiveSession();
	 //Toast.makeText(this, "onresume", Toast.LENGTH_LONG).show();
	 }
	

	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	 }
	 

	}