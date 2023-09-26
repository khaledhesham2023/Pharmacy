package com.khaledamin.pharmacy_android.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.khaledamin.pharmacy_android.R
import com.khaledamin.pharmacy_android.databinding.FragmentMapsBinding
import com.khaledamin.pharmacy_android.ui.addresses.AddAddressViewModel
import com.khaledamin.pharmacy_android.ui.addresses.AddressesMapsSharedViewModel
import com.khaledamin.pharmacy_android.ui.base.BaseFragment
import com.khaledamin.pharmacy_android.ui.base.BaseFragmentWithViewModel
import com.khaledamin.pharmacy_android.utils.Constants
import com.khaledamin.pharmacy_android.utils.DisplayManager.showErrorAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0
    private lateinit var reverseGeocoder: Geocoder
    private lateinit var geocoder: Geocoder
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var addressName: String

    //    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var addressesMapsSharedViewModel : AddressesMapsSharedViewModel

    override val layout: Int
        get() = R.layout.fragment_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            addressesMapsSharedViewModel = ViewModelProvider(requireActivity())[AddressesMapsSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        addressesMapsSharedViewModel.addressName.value = "Hello World !!"
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()
        // Initializing Places using API Key
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), Constants.API_KEY)
            Log.i("MAPS", "onViewCreated() Places initialized")
        }
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        Log.i("MAPS", "onMapsReady() started")
        map = googleMap
        if (isPermissionGranted()) {
            viewLocationOnMap()
        }
        reverseGeocoder = Geocoder(requireContext(), Locale.getDefault())


        Log.i("MAPS", "onMapReady() ended")
    }

    @SuppressLint("MissingPermission")
    private fun viewLocationOnMap() {
        val currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        latitude = currentLocation!!.latitude
        longitude = currentLocation.longitude
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latitude!!, longitude!!),
                Constants.STREET_VIEW
            )
        )
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

                    // Various Parameters of an Address are appended
                    // to generate a complete Address
                    if (address.premises != null) {
                        stringBuilder.append(address.premises).append(", ")
                    }
                    stringBuilder.append(address.getAddressLine(0))
//                    stringBuilder.append(address.featureName).append("\n")
//                        .append(address.locality).append(", ")
//                        .append(address.adminArea).append(", ")
//                        .append(address.countryName).append(", ")
//                        .append(address.postalCode)
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
        setOnPoiClick(map)
        setOnMapLongClick(map)
        enableMyLocation()

    }

    private fun setOnMapLongClick(googleMap: GoogleMap) {
        googleMap.setOnMapLongClickListener {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, Constants.STREET_VIEW))
        }
    }

    private fun setOnPoiClick(googleMap: GoogleMap) {
        Log.i("MAPS", "setOnPoiClick() called")
        googleMap.setOnPoiClickListener {
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
        } else {
            map.isMyLocationEnabled = true

        }
    }

    private fun isPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_FINE_LOCATION_PERMISSION
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == Constants.REQUEST_FINE_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewLocationOnMap()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (isPermissionGranted()) {
//            fusedLocationProviderClient =
//                LocationServices.getFusedLocationProviderClient(requireActivity())
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
//                if (it != null) {
//                    latitude = it.latitude
//                    longitude = it.longitude
//                }
//            }
//            } else {
//            showErrorAlertDialog(requireContext(), R.string.warning,getString(R.string.permission_denied_explanation),R.string.check,R.string.cancel){
//                    _,_->
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),Constants.REQUEST_FINE_LOCATION_PERMISSION)
//            }
//        }
        } else {

        }
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
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.address_not_available),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
                return true
            }

        })

        viewBinding.select.setOnClickListener {
            Log.i("Map", latitude.toString())

            findNavController().navigate(
                MapsFragmentDirections.actionMapsFragmentToAddAddressFragment2(
                    latitude!!.toFloat(), longitude!!.toFloat(), addressName
                )
            )
        }
    }
}