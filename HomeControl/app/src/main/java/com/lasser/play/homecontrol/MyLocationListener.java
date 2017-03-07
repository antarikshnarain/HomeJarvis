package com.lasser.play.homecontrol;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by antar on 27-Jan-17.
 */

/*----------Listener class to get coordinates ------------- */
public abstract class MyLocationListener implements LocationListener {
    private String data;
    @Override
    public void onLocationChanged(Location loc) {
        String longitude = "Longitude: " +loc.getLongitude();
        String latitude = "Latitude: " +loc.getLatitude();

    /*----------to get City-Name from coordinates ------------- */
        String cityName=null;
        Geocoder gcd = new Geocoder(null,
                Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc
                    .getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName=addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = longitude+"\n"+latitude +
                "\n\nMy Currrent City is: "+cityName;
        data=s;
    }
    public String returnData(){
        return data;
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

}

