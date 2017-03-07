package com.lasser.play.homecontrol;

import android.Manifest;
import android.app.AlertDialog;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import static com.lasser.play.homecontrol.R.id.toolbar;
import static java.net.Proxy.Type.HTTP;

public class HomeActivity extends AppCompatActivity {

    int device1_state=0,device2_state=0;
    TextView httpresp;
    TextView httpdata;
    LocationManager locationManager;
    LocationListener locationListener;
    URL url =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        httpdata = (TextView) findViewById(R.id.textView_output);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,LocationManager.GPS_PROVIDER);
        // Check Permissions
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
        }
        else{
            alertbox("Yo","Bro!");
        }
        setSupportActionBar(toolbar);
        //final ViewFlipper myflipper = (ViewFlipper) findViewById(R.id.viewFlip);
        //myflipper.setDisplayedChild(1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toolbar.setTitle("Add Device");
                myflipper.showNext();
            }
        });
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });
        */
    }
    private void myclick(){

    }
    public void device1(View v){
        device1_state=1-device1_state;
        try {
            String data = new phpRequest(this).execute("deviceid=1&status="+device1_state+"&pin=8", "updateDevice.php").get();
            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
        }
        catch (InterruptedException e){

        }
        catch (ExecutionException e){

        }

    }
    public void device2(View v){
        device2_state=1-device2_state;
        try {
            String data = new phpRequest(this).execute("deviceid=2&status="+device2_state+"&pin=9", "updateDevice.php").get();
            Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
        }
        catch (InterruptedException e){

        }
        catch (ExecutionException e){

        }

    }
    public void nodeClick(View v){
        EditText myurl = (EditText) findViewById(R.id.editText);
        try {
            Toast.makeText(getApplicationContext(),myurl.getText().toString(),Toast.LENGTH_SHORT).show();
            String data = new nodeRequest(this).execute(myurl.getText().toString()).get();
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
            httpdata.setText(httpdata.getText()+data);
        }
        catch (InterruptedException e){
            Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_SHORT).show();
        }
        catch (ExecutionException e){
            Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_SHORT).show();
        }

    }
    public void nodeGeo(View v){
        // LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean flag = displayGpsStatus();
        if (flag){
            httpdata.setText("Move ur ass from here!!!\nWait!");
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        String str = "Latitude: "+location.getLatitude()+"Longitude: "+location.getLongitude();
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }
    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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
