package com.example.gettoknowip.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gettoknowip.Constants;
import com.example.gettoknowip.R;
import com.example.gettoknowip.entity.IPDetails;
import com.example.gettoknowip.network.ApiClient;
import com.example.gettoknowip.network.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IPInfoActivity extends AppCompatActivity {

    private GoogleMap mapInstance = null;
    private MarkerOptions mapMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinfo);

        setUpGoogleMap();

        String ipAddress = getIntent().getStringExtra(Constants.IP_ADDRESS_ENTERED);
        updateUi(ipAddress);
    }

    public void setUpGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentGoogleMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(mapReady);
    }

    private void updateUi(String ipAddress) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<IPDetails> call = apiInterface.getIpAddressDetails(ipAddress, Constants.API_TOKEN);
        call.enqueue(new Callback<IPDetails>() {
            @Override
            public void onResponse(Call<IPDetails> call, Response<IPDetails> response) {
                IPDetails details = response.body();
                TextView textViewCountry = findViewById(R.id.textViewCountry);
                TextView textViewRegion = findViewById(R.id.textViewRegion);
                TextView textViewCity = findViewById(R.id.textViewCity);
                TextView textViewPostal = findViewById(R.id.textViewPostal);
                TextView textViewOrganization = findViewById(R.id.textViewOrganization);

                textViewCountry.setText(details.getCountry());
                textViewRegion.setText(details.getRegion());
                textViewCity.setText(details.getCity());
                textViewOrganization.setText(details.getOrganization());
                textViewPostal.setText(details.getPostal());

                //Update map here.
                updateMapPointer(details.getLatLong(), details.getRegion());
            }

            @Override
            public void onFailure(Call<IPDetails> call, Throwable t) {
                call.cancel();
            }
        });

        Button buttonUpdateUi = findViewById(R.id.buttonUpdateUi);
        buttonUpdateUi.setOnClickListener(updateUiClickListener);
    }

    private final OnMapReadyCallback mapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mapInstance = googleMap;
        }
    };

    private void updateMapPointer(String location, String region) {
        if (location != null && mapInstance != null) {
            String[] latLong = location.split(",");
            double latitude = Double.parseDouble(latLong[0]);
            double longitude = Double.parseDouble(latLong[1]);

            LatLng latLng = new LatLng(latitude, longitude);
            mapInstance.clear();
            mapInstance.addMarker(new MarkerOptions().position(latLng).title(region));
            mapInstance.animateCamera(CameraUpdateFactory.zoomTo(Constants.ZOOM_BY));
            mapInstance.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } else {
            Toast.makeText(this, Constants.COULD_NOT_OPEN_MAP, Toast.LENGTH_LONG).show();
        }
    }

    private final View.OnClickListener updateUiClickListener = v -> {
        EditText editTextReEnterIP = findViewById(R.id.editTextIPAddress);
        updateUi(editTextReEnterIP.getText().toString());
        editTextReEnterIP.setText(Constants.EMPTY_TEXT);
    };
}