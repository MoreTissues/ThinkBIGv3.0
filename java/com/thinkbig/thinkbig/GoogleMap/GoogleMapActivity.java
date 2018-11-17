package com.thinkbig.thinkbig.GoogleMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thinkbig.thinkbig.R;

import java.util.ArrayList;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap gmap;
    ArrayList<FTSMObject> locations;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.googlemap_layout);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Navigate To FTSM");
        MapFragment mapfragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapfragment.getMapAsync(this);
        locations = new ArrayList<FTSMObject>();
        FTSMObject location = new FTSMObject(2.918261, 101.771310, "FTSM");
        locations.add(location);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        for (FTSMObject object : locations) {
            LatLng sc = new LatLng(object.getLati(), object.getLongi());
            gmap.addMarker(new MarkerOptions().position(sc).title(object.getLocname()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sc));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sc,16));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
            gmap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String label = marker.getTitle();
        String uriBegin = "geo:" + marker.getPosition().latitude + "," + marker.getPosition().longitude;
        String query = marker.getPosition().latitude + "," + marker.getPosition().longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery;
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        return false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
