package edu.psu.sweng888.finalpractice;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesViewModel extends ViewModel {
    private final MutableLiveData<List<LatLng>> coordinatesList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<LatLng>> getCoordinates() {
        return coordinatesList;
    }

    public void addCoordinate(LatLng coordinate) {
        List<LatLng> currentList = coordinatesList.getValue();
        if (currentList != null) {
            currentList.add(coordinate);
            coordinatesList.setValue(currentList);
        }
    }
    // Clear method to reset the coordinates list
    public void clearCoordinates() {
        coordinatesList.setValue(new ArrayList<>());  // Reset the list
    }
}
