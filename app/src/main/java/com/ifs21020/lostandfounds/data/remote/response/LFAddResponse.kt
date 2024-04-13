package com.ifs21020.lostandfounds.data.remote.response

import com.google.gson.annotations.SerializedName

data class LFAddResponse(

	@field:SerializedName("data")
	val data: DataLFAddResponse,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataLFAddResponse(

	@field:SerializedName("lost_found_id")
	val lostFoundId: Int
)
