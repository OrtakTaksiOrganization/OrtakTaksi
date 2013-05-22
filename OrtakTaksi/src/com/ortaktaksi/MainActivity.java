package com.ortaktaksi;

import java.util.Arrays;

import com.facebook.*;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String TAG = "MainActivity";
	private TextView lblEmail;
	private static String APP_ID = "264464070362498";
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (!isOnline()) 
		{
			Toast.makeText(getApplicationContext(), "Ýnternet Baðlantýnýzý Kontrol Ediniz.",Toast.LENGTH_LONG).show();
			return;
		}
		else{
		

		lblEmail = (TextView) findViewById(R.id.lblEmail);			
		
		LoginButton loginButton = (LoginButton) findViewById(R.id.authButton);
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginToFacebook();
				
			}
		});
		//Çýkýþ Activity
		Button btnCikis = (Button) findViewById(R.id.btn_cikis);
		View.OnClickListener lstCikis= new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
			//finish();
                Intent i = new Intent(getApplicationContext(), Anamenu.class );
                startActivity(i);
				
			}
		};
		btnCikis.setOnClickListener(lstCikis);
	}
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	public void loginToFacebook() {
		Session.openActiveSession(this, true, new Session.StatusCallback() {

		      // callback when session changes state
		      @Override
		      public void call(Session session, SessionState state, Exception exception) {
		        if (session.isOpened()) {

		          // make request to the /me API
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		            // callback after Graph API response with user object
		            @Override
		            public void onCompleted(GraphUser user, Response response) {
		              if (user != null) {
		               /* TextView welcome = (TextView) findViewById(R.id.welcome);
		                welcome.setText("Hello " + user.getName() + "!");*/
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
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}