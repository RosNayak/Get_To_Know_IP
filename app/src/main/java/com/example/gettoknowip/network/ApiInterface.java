package com.example.gettoknowip.network;

import com.example.gettoknowip.entity.IPDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/{ip_address}/json")
    Call<IPDetails> getIpAddressDetails(
            @Path(value = "ip_address") String ipAddress,
            @Query(value = "token", encoded = true) String token
    );
}
