package com.ortaktaksi;

import java.util.Arrays;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.ortaktaksi.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	 private String TAG = "MainActivity";
	 private TextView lblEmail;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  lblEmail = (TextView) findViewById(R.id.lblEmail);
	  
	  
	  Button btnCikis = (Button)findViewById(R.id.btn_cikis);		
		View.OnClickListener lstn1= new OnClickListener() 
		{			
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Anamenu.class);
				startActivity(i);
			}
		};		
		btnCikis.setOnClickListener(lstn1);
	  
	  LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
	  authButton.setOnErrorListener(new OnErrorListener() {
	   
	   @Override
	   public void onError(FacebookException error) {
	    Log.i(TAG, "Error " + error.getMessage());
	   }
	  });
	  // set permission list, Don't foeget to add email
	  authButton.setReadPermissions(Arrays.asList("basic_info","email"));
	  // session state call back event
	  authButton.setSessionStatusCallback(new Session.StatusCallback() {
	   
	   @Override
	   public void call(Session session, SessionState state, Exception exception) {
	    
	    if (session.isOpened()) {
	              Log.i(TAG,"Access Token"+ session.getAccessToken());
	              Request.executeMeRequestAsync(session,
	                      new Request.GraphUserCallback() {
	                          @Override
	                          public void onCompleted(GraphUser user,Response response) {
	                              if (user != null) { 
	                               Log.i(TAG,"User ID "+ user.getId());
	                               Log.i(TAG,"Email "+ user.asMap().get("email"));
	                               lblEmail.setText(user.asMap().get("email").toString());
	                              }
	                          }
	                      });
	          }
	    
	   }
	  });
	 }
	 
	

	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	 }
	 

	}