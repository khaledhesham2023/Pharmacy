package com.khaledamin.pharmacy_android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentMapsBinding
import com.khaledamin.pharmacy_android.ui.addresses.AddAddressViewModel
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MapsFragment : BaseFragmentWithViewModel<FragmentMapsBinding, AddAddressViewModel>(),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0
    private lateinit var reverseGeocoder: Geocoder
    private lateinit var geocoder: Geocoder
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var addressName : String
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationSettingsRequest: LocationSettingsRequest.Builder
//    private lateinit var locationCallback: LocationCallback
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private lateinit var currentLocation : Location

    override val viewModelClass: Class<AddAddressViewModel>
        get() = AddAddressViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("MAPS", "onViewCreated()")
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
//        locationRequest = LocationRequest().apply {
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 300
//            fastestInterval = 300
//        }
//        locationSettingsRequest =
//            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
//        var result: Task<LocationSettingsResponse> =
//            LocationServices.getSettingsClient(requireContext())
//                .checkLocationSettings(locationSettingsRequest.build())
//        result.addOnFailureListener(object : OnFailureListener {
//            override fun onFailure(p0: java.lang.Exception) {
//                if (p0 is ResolvableApiException) {
//                    try {
//                        val resolvable = p0 as ResolvableApiException
//                        resolvable.startResolutionForResult(
//                            requireActivity(),
//                            Constants.EXCEPTION_REQUEST_CODE
//                        )
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//        })
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                for (location in locationResult.locations){
//                    currentLocation = location
//                }
//            }
//        }
        // Initializing Places using API Key
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.API_KEY)
            Log.i("MAPS", "onViewCreated() Places initialized")
        }
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun setupListeners() {
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    val location = query.toString()
                    var addresses: List<Address>? = null
                    geocoder = Geocoder(requireContext())
                    try {
                        addresses = geocoder.getFromLocationName(location, 1)
                    } catch (_: Exception) {
                    }
                    val searchedAddress: Address = addresses!![0]
                    var latLng = LatLng(searchedAddress.latitude, searchedAddress.longitude)
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng,
                            Constants.STREET_VIEW
                        )
                    )
                    return true
                } catch (e:Exception){
                    Toast.makeText(requireContext(),getString(R.string.address_not_available),Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
                return true
            }

        })

        viewBinding.select.setOnClickListener {
            Log.i("Map",latitude.toString())

            findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToAddAddressFragment2(
                latitude!!.toFloat(), longitude!!.toFloat(),addressName))
        }
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override val layout: Int
        get() = R.layout.fragment_maps

    override fun onMapReady(googleMap: GoogleMap) {
        Log.i("MAPS", "onMapsReady() started")
        map = googleMap
        val cairo = LatLng(30.04670384770477, 31.23408763555915)
//        val cairo = LatLng(currentLocation.latitude,currentLocation.longitude)
        val zoomLevel = Constants.STREET_VIEW
        // Initializing Geocoder
        reverseGeocoder = Geocoder(requireContext(), Locale.getDefault())
//        map.addMarker(
//            MarkerOptions().position(cairo).title("Marker in Cairo").icon(
//                BitmapDescriptorFactory.defaultMarker(
//                    HUE_AZURE
//                )
//            )
//        )
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(cairo, zoomLevel))
        map.setOnCameraIdleListener {
            latitude = map.cameraPosition.target.latitude
            longitude = map.cameraPosition.target.longitude

            addressName = ""
            // Reverse Geocoding
            try {
                val addressList: List<Address> =
                    reverseGeocoder.getFromLocation(latitude!!, longitude!!, 1) as List<Address>
                if (addressList.isNotEmpty() || addressList != null) {
                    val address = addressList[0]
                    val stringBuilder = StringBuilder()
//                for (i in 0 .. address.maxAddressLineIndex) {
//                    stringBuilder.append(address.getAddressLine(i)).append("\n")
//                }
                    // Various Parameters of an Address are appended
                    // to generate a complete Address
                    if (address.premises != null) {
                        stringBuilder.append(address.premises).append(", ")
                    }
                    stringBuilder.append(address.featureName).append("\n")
                        .append(address.locality).append(", ")
                        .append(address.adminArea).append(", ")
                        .append(address.countryName).append(", ")
                        .append(address.postalCode)
                    addressName = stringBuilder.toString()

                }
            } catch (exception: IOException) {
                Toast.makeText(
                    requireContext(),
                    "Unable to connect to Geocoder",
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewBinding.title.text = addressName
        }
//        setOnMapLongClick(map)
        setOnPoiClick(map)
        setOnMapLongClick(map)
        enableMyLocation()
        Log.i("MAPS", "onMapReady() ended")
    }

    private fun setOnMapLongClick(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, Constants.STREET_VIEW))
        }
    }

    private fun setOnPoiClick(googleMap: GoogleMap) {
        Log.i("MAPS", "setOnPoiClick() called")
        googleMap.setOnPoiClickListener {
//            val snippet = String.format(
//                Locale.getDefault(),
//                getString(R.string.lat_long_snippet, it.latLng.latitude, it.latLng.longitude)
//            )
//            val poiMarker = googleMap.addMarker(
//                MarkerOptions().position(it.latLng).title(it.name).snippet(snippet).icon(
//                    BitmapDescriptorFactory.defaultMarker(
//                        HUE_AZURE
//                    )
//                )
//            )
//            poiMarker!!.showInfoWindow()
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latLng.latitude,
                        it.latLng.longitude
                    ), Constants.STREET_VIEW
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_FINE_LOCATION_PERMISSION
            )
            Log.i("MAPS", "No Permissions Granted")
        } else {
            map.isMyLocationEnabled = true
            Log.i("MAPS", "enableMyLocation() permission granted")

        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

//    @SuppressLint("MissingPermission")
//    private fun startLocationUpdates(){
//        if (fusedLocationProviderClient != null){
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,
//                Looper.getMainLooper())
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        startLocationUpdates()
//    }

//    override fun onPause() {
//        super.onPause()
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//    }
}