package com.example.georg.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.net.*;


public class MainActivity extends AppCompatActivity {


    //Änderung von Markus!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        email = (TextView) findViewById(R.id.editText2);
        password = (TextView) findViewById(R.id.editText);
        output = (TextView) findViewById(R.id.textView4);
        btnlogin = (Button) findViewById(R.id.button);
        btnregister = (Button) findViewById(R.id.button2);

       // btnlogin.setOnClickListener(MainActivity.this);
       // btnregister.setOnClickListener(MainActivity.this);

    }
     /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();

    }
    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        output.setText("");
    }

    protected void onStop() {
        super.onStop();

    }

    public void onDestroy() {
        super.onDestroy();
    }


    Button btnlogin;
    Button btnregister;
    TextView email;
    TextView password;
    TextView output;




    public void onClick(View v) {


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            // Internet available
            String urlstr = "http://champagne.hol.es/login.php?name=" + email.getText() + "&pw=" + password.getText();
            //Toast.makeText(getApplicationContext(), urlstr, Toast.LENGTH_LONG).show();

            try {
                dummy = downloadUrl(urlstr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),dummy,Toast.LENGTH_LONG).show();

        } else {

            // Show error no internet
            String result = "No internet connection available!";
            output.setText(result);
        }

    }


    private static final String DEBUG_TAG = "HttpExample";
    private static String dummy;

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();


            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            dummy =  readIt(is, len);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    // onPostExecute displays the RESULTS of the AsyncTask.
    protected void onPostExecute(String result) {
       // output.setText(result);
    }

    public void test(View v){
       // Activity register = new RegisterActivity();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        output.setText("Dummy");
       // setTitle("Dummy");
    }

}
