package id.co.imastudio.santri.kotlin3.init

import com.google.gson.annotations.SerializedName


class Step {

    @SerializedName("distance")
    var distance: Distance? = null
    @SerializedName("duration")
    var duration: Duration? = null
    @SerializedName("end_location")
    var endLocation: EndLocation? = null
    @SerializedName("html_instructions")
    var htmlInstructions: String? = null
    @SerializedName("maneuver")
    var maneuver: String? = null
    @SerializedName("polyline")
    var polyline: Polyline? = null
    @SerializedName("start_location")
    var startLocation: StartLocation? = null
    @SerializedName("travel_mode")
    var travelMode: String? = null

}
