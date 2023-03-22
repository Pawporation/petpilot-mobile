package com.pawporation.petpilot.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.models.PawDataModel
import com.pawporation.petpilot.utils.MapMarkerUtil

class MapFragment(private val dataList: ArrayList<PawDataModel>,
                  private val indexToMarkerMapping: HashMap<Int, Marker?>,
                  private val markerToIndexMapping: HashMap<Marker?, Int>,
                  private val markerToPawDataMapping: HashMap<Marker?, PawDataModel>) :
    Fragment(), GoogleMap.OnMarkerClickListener {

    private var map: GoogleMap? = null

    private var locationPermissionGranted = false

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null

    private var activityResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = true
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }

            if(allAreGranted) {
                locationPermissionGranted = true
            }
        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to
     * install it inside the SupportMapFragment. This method will only be triggered once the
     * user has installed Google Play services and returned to the app.
     */
    private val callback = OnMapReadyCallback { map ->
        this.map = map

        // Removes all of Google's POI
        map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this.requireContext(), R.raw.map_style))

        var counter = 0
        dataList.forEach {dataModel ->
            val marker = map.addMarker(MarkerOptions()
                .position(dataModel.location)
                .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(), dataModel.type))
                .title(dataModel.title))
            marker?.tag = dataModel.type
            indexToMarkerMapping[counter] = marker
            markerToIndexMapping[marker] = counter
            markerToPawDataMapping[marker] = dataModel
            counter += 1
        }

        // Prompt the user for permission.
        getLocationPermission()

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireActivity().applicationContext)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val parentView = view?.rootView
        val rv = parentView?.findViewById<RecyclerView>(R.id.card_rv)
        val layoutManager = rv?.layoutManager as LinearLayoutManager

        val offset = view?.width?.minus(800)?.div(2)
        markerToIndexMapping[marker]?.let {index ->
            if (offset != null) {
                layoutManager.scrollToPositionWithOffset(index, offset)
            }
        }
        rv.let {
            val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(rv)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        marker.showInfoWindow()

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return true
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.e("TAG", "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception huh: %s", e.message, e)
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            val appPerms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            activityResultLauncher.launch(appPerms)
        }
    }

    companion object {
        private val DEFAULT_LOCATION = LatLng(37.414728, -122.0811)
        private const val DEFAULT_ZOOM = 15

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }
}