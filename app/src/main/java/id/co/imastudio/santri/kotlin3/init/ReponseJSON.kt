package id.co.imastudio.santri.kotlin3.init

import com.google.gson.annotations.SerializedName


class ReponseJSON {

    @SerializedName("geocoded_waypoints")
    var geocodedWaypoints: List<GeocodedWaypoint>? = null
    @SerializedName("routes")
    var routes: List<Route>? = null
    @SerializedName("status")
    var status: String? = null

}
