package com.example.finaleproject.network

import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.model.detailed.HistoricalPriceResponse
import com.example.finaleproject.model.exchange.Converter
import com.example.finaleproject.model.exchange.Currency
import com.example.finaleproject.model.exchange.OfficialRatesItem

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoNetwork {


    @GET("v3/coins/markets")
    suspend fun getCoinList(@Query("vs_currency") currency: String = CURRENCY,
                            @Query("order") order:String = MARKET_CAP,
                            @Query("per_page") page:String = NUMBER_OF_CRYPTO,
                            @Query("page")pages:String = "1",
                            @Query("sparkline")spark:String = "false"):Response<List<CryptoItem>>


    @GET("v3/coins/{id}/market_chart")
    suspend fun historicalPrice(
        @Path("id") id: String,
        @Query("vs_currency") targetCurrency: String,
        @Query("days") days: Int
    ): Response<HistoricalPriceResponse>

    @Headers("apikey: tBXoEW51JcJJYOa6PDNwnPg3ikNSACux")
    @GET("https://test-api.tbcbank.ge/v1/exchange-rates/commercial")
    suspend fun getExchange():Response<Currency>

    @Headers("apikey: tBXoEW51JcJJYOa6PDNwnPg3ikNSACux")
    @GET("https://test-api.tbcbank.ge/v1/exchange-rates/nbg")
    suspend fun getOfficialExchange():Response<List<OfficialRatesItem>>

    @Headers("apikey: tBXoEW51JcJJYOa6PDNwnPg3ikNSACux")
    @GET("https://test-api.tbcbank.ge/v1/exchange-rates/commercial/convert")
    suspend fun getConvertedValue(
        @Query("amount") amount:Double,
        @Query("from") from:String,
        @Query("to") to:String,
    ):Response<Converter>


}