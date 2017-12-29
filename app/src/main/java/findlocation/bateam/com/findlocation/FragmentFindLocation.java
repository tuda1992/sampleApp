package findlocation.bateam.com.findlocation;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import findlocation.bateam.com.R;
import findlocation.bateam.com.base.BaseFragment;
import findlocation.bateam.com.constant.Constants;
import findlocation.bateam.com.listener.IPermissionCallBack;
import findlocation.bateam.com.model.MyClusterItem;
import findlocation.bateam.com.model.PlaceModel;
import findlocation.bateam.com.util.JSONResourceReader;
import findlocation.bateam.com.util.MyItemReaderUtil;
import findlocation.bateam.com.util.PermissionUtils;
import findlocation.bateam.com.widget.LayoutPlace;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by acv on 12/4/17.
 */

public class FragmentFindLocation extends BaseFragment implements OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , LocationListener
        , GoogleMap.OnCameraIdleListener
        , GoogleMap.OnMarkerClickListener
        , PlaceSelectionListener
        , LayoutPlace.LayoutPlaceListener {

    private GoogleMap mGoogleMap;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 0; // 10 meters
    private static final int REQUEST_SELECT_PLACE = 1000;
    private ClusterManager<MyClusterItem> mClusterManager;
    private boolean mIsHavePlace;

    // Bind View

    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_search_place)
    TextView mTvSearchPlace;
    @BindView(R.id.cv_data)
    CardView mCvData;
    @BindView(R.id.layout_result_place)
    LayoutPlace mLayoutPlace;
    @BindView(R.id.cv_layout_place)
    CardView mCvLayoutPlace;
    @BindView(R.id.tv_data)
    TextView mTvData;

    // Bind Event

    @OnClick(R.id.cv_search_place)
    public void onClickSearchPlace() {
        try {
            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("VN")
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_FULLSCREEN).setFilter(autocompleteFilter)
                    .build(getActivity());
            startActivityForResult(intent, REQUEST_SELECT_PLACE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.cv_data)
    public void onClickShowData() {
        showOrHideView();
    }

    private void showOrHideView() {
        if (mCvLayoutPlace.getVisibility() == View.GONE) {
            mCvLayoutPlace.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mCvLayoutPlace.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
        } else {
            mCvLayoutPlace.animate()
                    .translationY(mCvLayoutPlace.getHeight())
                    .alpha(0.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mCvLayoutPlace.setVisibility(View.GONE);
                        }
                    });
        }
    }

    @OnClick(R.id.btn_find)
    public void onClickFindPlace() {
        String jsonReader;
        try {
            jsonReader = JSONResourceReader.readFileJSONFromRaw(getActivity());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PlaceModel>>() {
            }.getType();
            List<PlaceModel> placeModels = (List<PlaceModel>) gson.fromJson(jsonReader, listType);

            mCvData.setVisibility(View.VISIBLE);

            mTvData.setText(placeModels.get(0).addressDetail);

            mLayoutPlace.setDataForLayoutPlace(placeModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBackPressFragment() {
        getActivity().finish();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_find_location;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkPermission(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_ACCESS_AND_COARSE_LOCATION, new IPermissionCallBack() {
                @Override
                public void onPermissionReady() {
                    setUpListenerForMapView();
                }
            });
        } else {
            setUpListenerForMapView();
        }

        mLayoutPlace.setLayoutListener(this);

    }

    private void setUpListenerForMapView() {
        mMapView.getMapAsync(this);
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void getData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLocationUpdates();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnCameraIdleListener(mClusterManager);
        mGoogleMap.setOnMarkerClickListener(this);

        // Add location
//        try {
//            readItems();
//        } catch (JSONException e) {
//            Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
//        }
    }

    private void readItems() throws JSONException {
//        InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
//        List<MyClusterItem> items = new MyItemReaderUtil().read(inputStream);
//        for (int i = 0; i < 10; i++) {
//            double offset = i / 60d;
//            for (MyClusterItem item : items) {
//                LatLng position = item.getPosition();
//                double lat = position.latitude + offset;
//                double lng = position.longitude + offset;
//                MyClusterItem offsetItem = new MyClusterItem(lat, lng);
//                mClusterManager.addItem(offsetItem);
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.REQUEST_ACCESS_AND_COARSE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults.length > 0) {
                        //Check dialog not show again
                        boolean isNotAgain = false;
                        boolean isPermissionGranted = false;
                        for (int i = 0; i < permissions.length; i++) {
                            if (!shouldShowRequestPermissionRationale(
                                    permissions[i]) && (grantResults[i] != PackageManager.PERMISSION_GRANTED)) {
                                isNotAgain = true;
                                break;
                            } else if (getActivity().checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = true;
                            } else if (getActivity().checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                                isPermissionGranted = false;
                                break;
                            }
                        }
                        if (isNotAgain) {
                            PermissionUtils.showDialogTurnOnPermission(getString(R.string.text_permission_camera), getActivity());
                        }

                        if (isPermissionGranted) {
                            setUpListenerForMapView();
                        }
                    }
                } else {
                    setUpListenerForMapView();
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Once connected with google api, get the location
//        displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Toast.makeText(getApplicationContext(), "Location changed! lat long = " + latitude + " --- " + longitude,
                    Toast.LENGTH_SHORT).show();

            // For dropping a marker at a point on the Map
            LatLng sydney = new LatLng(latitude, longitude);
            mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("My Location").snippet("Vị trí hiện tại của "));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(24).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            Log.d(TAG, "(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;
        if (mIsHavePlace) {
            stopLocationUpdates();
            return;
        }
        // Displaying the new location on UI
//        displayLocation();
        getCompleteAddressString(location.getLatitude(), location.getLongitude());
        stopLocationUpdates();
        moveCamera(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mIsHavePlace = true;
    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "marker = " + marker.getId());

        return false;
    }

    @Override
    public void onPlaceSelected(Place place) {
        mIsHavePlace = true;
        mTvSearchPlace.setText(place.getAddress());
        moveCamera(place.getLatLng().latitude, place.getLatLng().longitude);
    }

    private void moveCamera(double latitude, double longtitute) {
        LatLng position = new LatLng(latitude, longtitute);
        mGoogleMap.addMarker(new MarkerOptions().position(position).title("My Location").snippet("Vị trí hiện tại của ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(16).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private String getCompleteAddressString(double latitude, double longtitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longtitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            }
            mTvSearchPlace.setText(strAdd);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Exception");
            mTvSearchPlace.setText("");
        }
        return strAdd;
    }

    @Override
    public void onHideLayout() {
        showOrHideView();
    }

    @Override
    public void onItemLayoutClick(int position, PlaceModel item) {
        mTvData.setText(item.addressDetail);
        showOrHideView();
        if (TextUtils.isEmpty(item.lattitude) || TextUtils.isEmpty(item.longitude)) {
            return;
        }
        moveCamera(Double.parseDouble(item.lattitude), Double.parseDouble(item.longitude));
    }

    @Override
    public void onItemInfoClick(int position, PlaceModel item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_PLACE_ITEM, item);
        startActivityAnim(InfoPlaceActivity.class, bundle);
    }
}
