package com.hhpn.tata;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hhpn.tata.util.TataPreference;
import com.hhpn.tata.util.Util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    AccessToken acToken;
    LoginButton loginButton;
    TextView tvTitle;
    AccessTokenTracker accessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                acToken = currentAccessToken;
                // currentAccessToken when it's loaded or set.
            }
        };
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvTitle = (TextView)findViewById(R.id.tv_title);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        tvTitle.startAnimation(rotate);


        acToken = AccessToken.getCurrentAccessToken();
        if(acToken==null) {

            loginButton = (LoginButton) findViewById(R.id.login_button);

            new CountDownTimer(2000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.startAnimation(fadeInAnimation);

                }
            }.start();

            loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code
                            acToken = loginResult.getAccessToken();
                            getFacebookData();
                            Log.i("AA", "onSuccess : " + loginResult.getAccessToken().getUserId());
                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                            Log.i("AA", "onSuccess : "+ exception.toString());
                        }
                    });

        }
        else
        {
            goToMainActivity(TataPreference.getInstance(getApplicationContext()).getString("user_name",""));
        }
        // Other app specific specializ



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(acToken==null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public void goToMainActivity(String name){
        Intent a = new Intent(SplashActivity.this,MainActivity.class);
        a.putExtra("name",name);
        startActivity(a);
        /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                SplashActivity.this, android.R.transition.fade, MainActivity.EXTRA_IMAGE);
        ActivityCompat.startActivity(SplashActivity.this, new Intent(SplashActivity.this, MainActivity.class),
                options.toBundle());*/
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }
    private void getFacebookData(){

        GraphRequest request = GraphRequest.newMeRequest(
                acToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        //Util.Log(jsonObject.toString());
                        TataPreference.getInstance(getApplicationContext()).setString("user_name",jsonObject.optString("name"));
                        TataPreference.getInstance(getApplicationContext()).setString("user_email",jsonObject.optString("email"));
                        goToMainActivity(jsonObject.optString("name"));
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
