package edu.psu.sweng888.finalpractice;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private CoordinatesViewModel coordinatesViewModel;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private TextView placeholderText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Views
        recyclerView = view.findViewById(R.id.recycler_view);
        placeholderText = view.findViewById(R.id.placeholder_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize ViewModel
        coordinatesViewModel = new ViewModelProvider(requireActivity()).get(CoordinatesViewModel.class);

        // Observe changes in the coordinates list
        coordinatesViewModel.getCoordinates().observe(getViewLifecycleOwner(), coordinates -> {
            if (coordinates == null || coordinates.isEmpty()) {
                // Show placeholder text if the list is empty
                placeholderText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                // Show RecyclerView if the list is not empty
                placeholderText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                // Update or initialize the adapter
                if (adapter == null) {
                    adapter = new MyAdapter(coordinates);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setCoordinatesList(coordinates);
                }
            }
        });

        // Set up the floating action button to clear coordinates
        FloatingActionButton clearFab = view.findViewById(R.id.clear_fab);
        clearFab.setOnClickListener(v -> {
            if (coordinatesViewModel.getCoordinates().getValue().size() > 0) {
                // Clear the list of coordinates in the ViewModel
                coordinatesViewModel.clearCoordinates();
            }else{
                // Show a toast message if the list is already empty
                Toast.makeText(requireContext(), "Coordinates list is already empty!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}


