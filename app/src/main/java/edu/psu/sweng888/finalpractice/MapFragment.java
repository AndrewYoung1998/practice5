package edu.psu.sweng888.finalpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// Inside MapFragment
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private CoordinatesViewModel coordinatesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get ViewModel instance
        coordinatesViewModel = new ViewModelProvider(requireActivity()).get(CoordinatesViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(40.7128, -74.0060);  // Example coordinate
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker in NYC"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));

        // Add coordinate to ViewModel list
        coordinatesViewModel.addCoordinate(location);
    }
}

