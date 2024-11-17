package edu.psu.sweng888.finalpractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<LatLng> coordinatesList;

    public MyAdapter(List<LatLng> coordinatesList) {
        this.coordinatesList = coordinatesList;
    }

    public void setCoordinatesList(List<LatLng> newCoordinatesList) {
        this.coordinatesList = newCoordinatesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LatLng coordinate = coordinatesList.get(position);
        holder.latitudeTextView.setText("Latitude: " + coordinate.latitude);
        holder.longitudeTextView.setText("Longitude: " + coordinate.longitude);
    }

    @Override
    public int getItemCount() {
        return coordinatesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView latitudeTextView;
        TextView longitudeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            latitudeTextView = itemView.findViewById(R.id.latitude);
            longitudeTextView = itemView.findViewById(R.id.longitude);
        }
    }
}

