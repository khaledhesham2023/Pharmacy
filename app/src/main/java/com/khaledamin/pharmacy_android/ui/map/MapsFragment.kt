package com.khaledamin.pharmacy_android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.utils.Constants
import java.util.Locale

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val lat = 37.422160
        val long = -122.084270
        val homeLatLng = LatLng(lat, long)
        val zoomLevel = Constants.STREET_VIEW
        map.addMarker(
            MarkerOptions().position(homeLatLng).title("Marker in Sydney").icon(
                BitmapDescriptorFactory.defaultMarker(
                    HUE_AZURE
                )
            )
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        setOnMapLongClick(map)
        setOnPoiClick(map)
        enableMyLocation()
    }

    private fun setOnMapLongClick(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener {
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet, it.latitude, it.longitude)
            )
            googleMap.addMarker(
                MarkerOptions().position(it).title(getString(R.string.dropped_pin)).snippet(snippet)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            HUE_AZURE
                        )
                    )
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, Constants.STREET_VIEW))
        }
    }

    private fun setOnPoiClick(googleMap: GoogleMap) {
        googleMap.setOnPoiClickListener {
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet, it.latLng.latitude, it.latLng.longitude)
            )
            val poiMarker = googleMap.addMarker(
                MarkerOptions().position(it.latLng).title(it.name).snippet(snippet).icon(
                    BitmapDescriptorFactory.defaultMarker(
                        HUE_AZURE
                    )
                )
            )
            poiMarker!!.showInfoWindow()
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}