package com.ifs21020.lostandfounds.data.remote.retrofit

import com.ifs21020.lostandfounds.data.remote.response.*
import okhttp3.MultipartBody

import retrofit2.http.*

interface IApiService {

    // Add New Lost & Found
    @FormUrlEncoded
    @POST("lost-and-found")
    suspend fun addLostAndFound(
        @Field("title") title: String,
        @Field("description") description: String
    ): LFAddResponse

    // Add Cover Lost & Found
    @Multipart
    @POST("lost-and-found/{id}/cover")
    suspend fun addCoverLostAndFound(
        @Path("id") id: String,
        @Part coverImage: MultipartBody.Part
    ): LFAddCoverResponse

    // Update Lost & Found
    @FormUrlEncoded
    @PUT("lost-and-found/{id}")
    suspend fun updateLostAndFound(
        @Path("id") id: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): LFUpdateResponse

    // Get All Lost & Found
    @GET("lost-and-found")
    suspend fun getAllLostAndFound(): LFGetAllResponse

    // Detail Lost & Found
    @GET("lost-and-found/{id}")
    suspend fun getLostAndFoundDetail(@Path("id") id: String): LFDetailResponse

    // Delete Lost & Found
    @DELETE("lost-and-found/{id}")
    suspend fun deleteLostAndFound(@Path("id") id: String): LFDeleteResponse

    // Register
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): LFResponse

    // Login
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LFLoginResponse

    // Get User Profile
    @GET("users/me")
    suspend fun getMe(): LFUserResponse
}
