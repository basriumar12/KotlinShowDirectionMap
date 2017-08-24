package id.co.imastudio.santri.kotlin3

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.co.imastudio.santri.kotlin3.init.InitRetrofit
import id.co.imastudio.santri.kotlin3.init.ReponseJSON
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Response

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

                private var mMap: GoogleMap? = null
                var awal :LatLng?=null
                var akhir :LatLng?=null

                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.activity_maps)



                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)

                    //permission marsmellow
                    var permission = (android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(permission), 2)
                }



            override fun onMapReady(googleMap: GoogleMap) {
                mMap = googleMap
                //auto complete dari google draimana
                edtdarimanan.setOnClickListener {
                   //function filter dan intent
                    completeAuto(1)

                    /* var intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).
                            build(this@MapsActivity)
                    startActivityForResult(intent,1)*/
                }
                ////auto complete dari google jalan kemana
                edtdkemana.setOnClickListener {
                        completeAuto(2)

                    }

                // Add a marker in Sydney and move the camera
                val sydney = LatLng(-6.1880883,106.7471083)
                //mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))

                //set type map
                mMap!!.mapType=GoogleMap.MAP_TYPE_TERRAIN
                //aoutozoom
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.toFloat()))
                //set zomm control
                mMap!!.uiSettings.isZoomControlsEnabled=true
                mMap!!.uiSettings.isCompassEnabled=true
                mMap!!.isBuildingsEnabled = true
                //mMap!!.isMyLocationEnabled = true
                //mMap!!.uiSettings.isMyLocationButtonEnabled=true

                //permission marsmellow
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    ActivityCompat.checkSelfPermission(this@MapsActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MapsActivity,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                }

                //mMap!!.uiSettings.setAllGesturesEnabled(true)

            }
            //function untuk set complete
            private fun completeAuto(i: Int) {
                val typeFilter = AutocompleteFilter.Builder()
                        .setTypeFilter(Place.TYPE_AIRPORT).setCountry("ID")
                        .build()

                var intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter)
                        .build(this@MapsActivity)
                startActivityForResult(intent,i)
            }



            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

                if(requestCode == 2){
                    mMap!!.isMyLocationEnabled = true
                }
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)

                try {


                //check key
                if(requestCode==1 && requestCode!= null){
                    //get data pengembalian
                    var place = PlaceAutocomplete.getPlace(this,data)
                    var lat = place.latLng.latitude
                    var lon = place.latLng.longitude


                    mMap!!.clear()

                    //include to latlang
                     awal = LatLng (lat,lon)

                    edtdarimanan.setText(place.address.toString())
                    if (edtdkemana.text.toString().length >0){
                        mMap!!.addMarker(MarkerOptions().position(akhir!!).title(place.address.toString()).icon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

                    }
                    //add marker
                    mMap!!.addMarker(MarkerOptions().position(awal!!).title(place.address.toString()).icon(BitmapDescriptorFactory.
                            defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
                    //set camera
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(awal,20.toFloat()))
                }

                    if(requestCode==2 && requestCode!= null){
                        //get data pengembalian
                        var place = PlaceAutocomplete.getPlace(this,data)
                        var lat = place.latLng.latitude
                        var lon = place.latLng.longitude


                        //include to latlang
                         akhir = LatLng (lat,lon)



                        edtdkemana.setText(place.address.toString())


                        if (edtdarimanan.text.toString().length >0){
                            mMap!!.addMarker(MarkerOptions().position(awal!!).title(place.address.toString()).icon(BitmapDescriptorFactory.
                                    defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))

                        }
                        //add marker
                        mMap!!.addMarker(MarkerOptions().position(akhir!!).title(place.address.toString()).icon(BitmapDescriptorFactory.
                                defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                        //set camera
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(awal,20.toFloat()))

                        actionRoute()
                    }


                else if (resultCode == 0){


                }}catch (e : Exception){
                    Toast.makeText(applicationContext,"anda belum pilih", Toast.LENGTH_SHORT).show()
                }
            }

        //functio
        private fun actionRoute() {
            //get ini retrofit
            var api = InitRetrofit().getInitIstance()
            //get request to server
            var call = api.request_route(edtdarimanan.text.toString(),
                    edtdkemana.text.toString())

        //get respon
        call.enqueue(object : retrofit2.Callback<ReponseJSON> {
            override fun onResponse(call: Call<ReponseJSON>?, response: Response<ReponseJSON>?) {
                Log.d("Reponse : ", response?.message())

                Toast.makeText(applicationContext,"sukses ", Toast.LENGTH_LONG).show()
                if (response!=null){
                    if (response.isSuccessful){
                        //get json array rpute
                        var route = response.body()?.routes
                        //get object overwiew polyline
                        var overview = route?.get(0)?.overviewPolyline
                        //get string json point
                        var point = overview?.points

                        Log.d("poinst",point.toString())
                        var direction = DirectionMapsV2(this@MapsActivity)
                     direction.gambarRoute(mMap!!,point!!)

                        var legs = route?.get(0)?.legs

                        var distance = legs?.get(0)?.distance
                        var dist =Math.ceil(distance?.value?.toDouble()!!/1000)
                        textjarak.setText(distance?.text.toString())

                        var duration = legs?.get(0)?.duration
                        texttime.setText(duration?.text.toString())

                        textharga.setText("Rp. " + (dist* 2500).toString())



                    }
                }

                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(call: Call<ReponseJSON>?, t: Throwable?) {

                Log.d("Error",t.toString())
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
