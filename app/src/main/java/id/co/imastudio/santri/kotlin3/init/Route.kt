package id.co.imastudio.santri.kotlin3.init

import com.google.gson.annotations.SerializedName


class Route {

    @SerializedName("bounds")
    var bounds: Bounds? = null
    @SerializedName("copyrights")
    var copyrights: String? = null
    @SerializedName("legs")
    var legs: List<Leg>? = null
    @SerializedName("overview_polyline")
    var overviewPolyline: OverviewPolyline? = null
    @SerializedName("summary")
    var summary: String? = null
    @SerializedName("warnings")
    var warnings: List<Any>? = null
    @SerializedName("waypoint_order")
    var waypointOrder: List<Any>? = null

}
