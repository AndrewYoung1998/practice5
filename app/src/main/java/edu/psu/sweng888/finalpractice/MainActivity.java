package edu.psu.sweng888.finalpractice;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer layout and toggle setup
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView setup
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Load HomeFragment initially
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment())
                    .commit();
        }
        // Assume MyAdapter is a custom adapter for RecyclerView
    }
    // Helper method to update the toolbar title
    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
    // Handling Navigation Drawer item selections
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_map) {
            // Show Google Maps Fragment when "Map" is selected
            showMapFragment();
            setToolbarTitle("Map");
            drawerLayout.closeDrawers();
            return true;
        } else if (id == R.id.nav_home) {
            setToolbarTitle("Coords List");
            // Show RecyclerView or main content when "Home" is selected
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new HomeFragment())  // Assuming HomeFragment shows the RecyclerView
                    .commit();
            drawerLayout.closeDrawers();
            return true;
        }
        return false;
    }
    // Inside MainActivity.java
    private void showMapFragment() {
        // Create a new instance of SupportMapFragment
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        // Replace the content_frame with the map fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mapFragment)
                .commit();

        // Set the callback to initialize the map when ready
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(40.7128, -74.0060);  // Example coordinates for NYC
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker in NYC"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        // Enable zoom controls (buttons) on the map
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        // Initialize CoordinatesViewModel
        CoordinatesViewModel coordinatesViewModel = new ViewModelProvider(this).get(CoordinatesViewModel.class);

        // Set up map click listener to drop a pin and save coordinates
        googleMap.setOnMapClickListener(latLng -> {
            // Add a marker at the clicked location
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Dropped Pin"));

            // Save the latitude and longitude to the ViewModel
            coordinatesViewModel.addCoordinate(latLng);
        });
    }
}
