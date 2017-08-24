package id.co.imastudio.santri.kotlin3.init

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

/**
 * Created by Server on 24/08/2017.
 */
class InitRetrofit {
    fun getInit():Retrofit{
        return  Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getInitIstance():ApiService{
        return getInit().create(ApiService::class.java)
    }
}
interface ApiService{
    @GET("json")
    fun  request_route(
            @Query("origin") awal : String,
            @Query("destination") tujuan : String
            ): Call<ReponseJSON>

}
