package com.example.showlocation;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.showlocation.tools.Glob;
import com.example.showlocation.tools.PanelSlideViews;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PanelSlideViews panelSlideViews;
    Button btnShowLocation;
    LinearLayout linearHideShow;
    LinearLayout linearBottomMenuParent;
    ImageView imHideShow;
    TextView tvHideShow;
    TextView tvCity;
    TextView tvState;
    TextView tvCountry;
    GPSTracker gps;
    String CityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        imHideShow = (ImageView) findViewById(R.id.imHideShow);
        linearHideShow = (LinearLayout) findViewById(R.id.linearHideShow);
        linearBottomMenuParent = (LinearLayout) findViewById(R.id.linearBottomMenuParent);
        tvHideShow = (TextView) findViewById(R.id.tvHideShow);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvState = (TextView) findViewById(R.id.tvState);
        tvCountry = (TextView) findViewById(R.id.tvCountry);

//        btnShowLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        int expandedHeight = Glob.convertDpToPx(MainActivity.this, R.dimen.menu_height);
        panelSlideViews = new PanelSlideViews(MainActivity.this, linearHideShow, linearBottomMenuParent, 0, expandedHeight, PanelSlideViews.SWIPE_DIRECTION.BOTTOM_TO_UP);
        panelSlideViews.active();
        panelSlideViews.setPanelSlideListener(new PanelSlideViews.PanelSlideListener() {
            @Override
            public void onExpanded() {
                imHideShow.setImageResource(R.drawable.ic_up);
                tvHideShow.setText("Göster");

                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if (gps.getLocation() != null) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

//                    Geocoder geocoder = new Geocoder(
//                            MainActivity.this, Locale
//                            .getDefault());
                    List<Address> addresses;
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String cityName = addresses.get(0).getAddressLine(0);
                    tvCity.setText(cityName);
                    String stateName = addresses.get(0).getAddressLine(1);
                    tvState.setText(stateName);
                    String countryName = addresses.get(0).getAddressLine(2);
                    tvCountry.setText(countryName);

                    // \n is for new line
//                    Toast.makeText(
//                            getApplicationContext(),
//                            "Your Location is - \nLat: " + latitude
//                                    + "\nLong: " + longitude, Toast.LENGTH_LONG)
//                            .show();
                } else {

                    gps.askUserToOpenGPS();
                }
            }

            @Override
            public void onCollapsed() {
                imHideShow.setImageResource(R.drawable.ic_down);
                tvHideShow.setText("Gizle");
            }
        });
    }
}
