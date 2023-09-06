package com.khaledamin.pharmacy_android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentMapsBinding
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.util.Locale

@AndroidEntryPoint
class MapsFragment : BaseFragmentWithViewModel<FragmentMapsBinding, MapsViewModel>(),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0
    private lateinit var geocoder: Geocoder
    override val viewModelClass: Class<MapsViewModel>
        get() = MapsViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("MAPS","onViewCreated()")
        // Initializing Places using API Key
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.API_KEY)
            Log.i("MAPS","onViewCreated() Places initialized")
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun setupListeners() {
//        TODO("Not yet implemented")
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    override val layout: Int
        get() = R.layout.fragment_maps

    override fun onMapReady(googleMap: GoogleMap) {
        Log.i("MAPS","onMapsReady() started")
        map = googleMap
        val cairo = LatLng(30.04670384770477, 31.23408763555915)
        val zoomLevel = Constants.STREET_VIEW
        // Initializing Geocoder
        geocoder = Geocoder(requireContext(), Locale.getDefault())
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

        var addressName = ""
        // Reverse Geocoding
        try {
            val addressList: List<Address> =
                geocoder.getFromLocation(latitude!!, longitude!!, 1) as List<Address>
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
                stringBuilder.append(address.subAdminArea).append("\n")
                    .append(address.locality).append(", ")
                    .append(address.adminArea).append(", ")
                    .append(address.countryName)
                addressName = stringBuilder.toString()

            }
        } catch (exception: IOException) {
            Toast.makeText(requireContext(), "Unable to connect to Geocoder", Toast.LENGTH_SHORT).show()
        }

            viewBinding.title.text = "Lat: $latitude \nLong: $longitude \nAddress: $addressName"
            }
//        setOnMapLongClick(map)
        setOnPoiClick(map)
        enableMyLocation()
        Log.i("MAPS","onMapReady() ended")
    }

//    private fun setOnMapLongClick(googleMap: GoogleMap) {
//        googleMap.setOnMapLongClickListener {
//            val snippet = String.format(
//                Locale.getDefault(),
//                getString(R.string.lat_long_snippet, it.latitude, it.longitude)
//            )
//            googleMap.addMarker(
//                MarkerOptions().position(it).title(getString(R.string.dropped_pin)).snippet(snippet)
//                    .icon(
//                        BitmapDescriptorFactory.defaultMarker(
//                            HUE_AZURE
//                        )
//                    )
//            )
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, Constants.STREET_VIEW))
//        }
//    }

    private fun setOnPoiClick(googleMap: GoogleMap) {
        Log.i("MAPS","setOnPoiClick() called")
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
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latLng.latitude,it.latLng.longitude),Constants.STREET_VIEW))
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
            Log.i("MAPS","No Permissions Granted")
        } else {
            map.isMyLocationEnabled = true
            Log.i("MAPS","enableMyLocation() permission granted")

        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}