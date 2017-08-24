package id.co.imastudio.santri.kotlin3.init

import com.google.gson.annotations.SerializedName


class Leg {

    @SerializedName("distance")
    var distance: Distance? = null
    @SerializedName("duration")
    var duration: Duration? = null
    @SerializedName("end_address")
    var endAddress: String? = null
    @SerializedName("end_location")
    var endLocation: EndLocation? = null
    @SerializedName("start_address")
    var startAddress: String? = null
    @SerializedName("start_location")
    var startLocation: StartLocation? = null
    @SerializedName("steps")
    var steps: List<Step>? = null
    @SerializedName("via_waypoint")
    var viaWaypoint: List<Any>? = null

}
