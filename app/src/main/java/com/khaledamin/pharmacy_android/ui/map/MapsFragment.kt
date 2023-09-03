package com.khaledamin.pharmacy_android.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
        map.addMarker(MarkerOptions().position(homeLatLng).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        setOnLongClickListener(map)
        setPoiClickListener(map)
    }

    private fun setOnLongClickListener(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener {
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet),
                it.latitude,
                it.longitude
            )
            googleMap.addMarker(
                MarkerOptions().position(it)
                    .title(getString(R.string.dropped_pin)).snippet(snippet)
            )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), Constants.STREET_VIEW
                )
            )
        }
    }

    private fun setPoiClickListener(googleMap: GoogleMap) {
        googleMap.setOnPoiClickListener {
            val snippet = String.format(
                Locale.getDefault(),
                getString(R.string.lat_long_snippet),
                it.latLng.latitude,
                it.latLng.longitude
            )
            val poiMarker = googleMap.addMarker(
                MarkerOptions().position(it.latLng).title(it.name).snippet(snippet)
            )
            poiMarker!!.showInfoWindow()
        }
    }
}