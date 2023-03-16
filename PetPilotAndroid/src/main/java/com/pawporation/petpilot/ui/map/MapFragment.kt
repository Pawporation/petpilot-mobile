package com.pawporation.petpilot.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import com.pawporation.petpilot.android.R
import com.pawporation.petpilot.utils.MapMarkerUtil

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener {

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

        // map.addMarker(MarkerOptions().position(DEFAULT_LOCATION).title("Marker in mountainView"))

        // below line is use to add marker to each location of our array list.
        val marker = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.RESTAURANT,
                com.pawpals.petpilot.R.drawable.restaurant_18))
            .title("Cuddle Cafe with photo eventually"))
        marker?.tag = MarkerType.RESTAURANT

        val marker1 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION1)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.OUTDOOR,
                com.pawpals.petpilot.R.drawable.outdoors_18))
            .title("Sierra Vista Park"))
        marker1?.tag = MarkerType.OUTDOOR

        val marker2 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION2)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.STORE,
                com.pawpals.petpilot.R.drawable.store_18))
            .title("Famous Store"))
        marker2?.tag = MarkerType.STORE

        val marker3 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION3)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.CLINIC,
                com.pawpals.petpilot.R.drawable.medical_services_18))
            .title("Vet Services"))
        marker3?.tag = MarkerType.CLINIC

        val marker4 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION4)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.EVENT,
                com.pawpals.petpilot.R.drawable.events_18))
            .title("Poodle Romp"))
        marker4?.tag = MarkerType.EVENT

        val marker5 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION5)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.OUTDOOR,
                com.pawpals.petpilot.R.drawable.outdoors_18))
            .title("Charleston Park"))
        marker5?.tag = MarkerType.OUTDOOR

        val marker6 = map.addMarker(MarkerOptions()
            .position(DEFAULT_LOCATION6)
            .icon(MapMarkerUtil.bitmapDescriptorFromVector(requireContext(),
                MarkerType.RESTAURANT,
                com.pawpals.petpilot.R.drawable.restaurant_18))
            .title("Be My Mate"))
        marker6?.tag = MarkerType.RESTAURANT

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
    ): View? {
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

    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val markerType = marker.tag as? MarkerType

        // Check if a click count was set, then display the click count.
        var toastText = ""
        markerType?.let { type ->
            toastText = when(type) {
                MarkerType.OUTDOOR -> MarkerType.OUTDOOR.toString()
                MarkerType.STORE -> MarkerType.STORE.toString()
                MarkerType.RESTAURANT -> MarkerType.RESTAURANT.toString()
                MarkerType.CLINIC -> MarkerType.CLINIC.toString()
                MarkerType.EVENT -> MarkerType.EVENT.toString()
            }
        }

//        Toast.makeText(
//            requireContext(),
//            toastText,
//            Toast.LENGTH_SHORT
//        ).show()
//        Log.d("CUDDLE CAFE", toastText)

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
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
        private val DEFAULT_LOCATION1 = LatLng(37.416856, -122.089430)
        private val DEFAULT_LOCATION2 = LatLng(37.415129, -122.077435)
        private val DEFAULT_LOCATION3 = LatLng(37.415034, -122.086125)
        private val DEFAULT_LOCATION4 = LatLng(37.416884, -122.077306)
        private val DEFAULT_LOCATION5 = LatLng(37.422029, -122.081691)
        private val DEFAULT_LOCATION6 = LatLng(37.421892, -122.084804)


        private const val DEFAULT_ZOOM = 15

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }
}