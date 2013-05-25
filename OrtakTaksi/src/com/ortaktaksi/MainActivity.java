package com.ortaktaksi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.facebook.*;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.google.android.gms.internal.s;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	 private String TAG = "MainActivity";
	 private Intent intent=null;
	 
	 public static Session S;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
	  S=Session.getActiveSession();
	  if(S!=null){
	  if (S.getState().isOpened()) {
		  S.getAuthorizationBundle();
		  Request.executeMeRequestAsync(S,
          new Request.GraphUserCallback() {
              @Override
              public void onCompleted(GraphUser user,Response response) {
            	  if (user!=null) {
            		  Database.FbEmail=user.asMap().get("email").toString();
                      Database.FbName=user.getName();
                      Database.FbUserID=user.getId();
                      if(user.getProperty("gender").toString()=="male")
                      {
                   	   Database.FbSex="Erkek";
                      }
                      else 
                      {
                   	   Database.FbSex="Kadýn";
					   }
                      
				}
              }
          });
		 
		
		Toast.makeText(this, "Merhaba"+Database.FbName+"."+"Anamenüye yönlendiriliyorsunuz.", Toast.LENGTH_LONG).show();
		intent =new Intent(getApplicationContext(),Anamenu.class);
        startActivity(intent);
	  	} 
	  }
	  // set permission list, Don't foeget to add email
	  authButton.setReadPermissions(Arrays.asList("basic_info","email"));
	  // session state call back event
	  authButton.setSessionStatusCallback(new Session.StatusCallback() {
		  
	   @Override
	   public void call(final Session session, SessionState state, Exception exception) {
		   
	    if (session.isClosed()) {
	    	
			session.closeAndClearTokenInformation();
			Database.FbEmail=null;
			Database.FbName=null;
			Database.FbUserID=null;
			Database.FbSex=null;
			if (S!=null) {
				S.closeAndClearTokenInformation();
			}
			
		}
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
	                               Database.FbName=user.getName();
	                               Database.FbUserID=user.getId();
	                               if(user.getProperty("gender").toString()=="male")
	                               {
	                            	   Database.FbSex="Erkek";
	                               }
	                               else 
	                               {
	                            	   Database.FbSex="Kadýn";
								   }
	                               Database db = new Database();
	                               db.CurrentUserID();
	                               //intent =new Intent(getApplicationContext(),Anamenu.class);
	                               //startActivity(intent);
	                        	  
	                               }
	                          }
	                      });
	          }
	    }
	   
	  });
	
	  Button btnCikis = (Button)findViewById(R.id.btn_cikis);		
		View.OnClickListener lstn1= new OnClickListener() 
		{			
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Anamenu.class);
				startActivity(i);
			}
		};		
		btnCikis.setOnClickListener(lstn1);
	  
	 }
	 protected void OnResume() {
		super.onResume();
	
	 Session s=Session.getActiveSession();
	 Toast.makeText(this, "onresume", Toast.LENGTH_LONG).show();
	 }
	

	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	 }
	 

	}