package id.co.imastudio.santri.kotlin3.init

import com.google.gson.annotations.SerializedName


class GeocodedWaypoint {

    @SerializedName("geocoder_status")
    var geocoderStatus: String? = null
    @SerializedName("partial_match")
    var partialMatch: Boolean? = null
    @SerializedName("place_id")
    var placeId: String? = null
    @SerializedName("types")
    var types: List<String>? = null

}
