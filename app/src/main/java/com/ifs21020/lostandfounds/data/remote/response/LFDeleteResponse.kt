package com.ifs21020.lostandfounds.data.remote.response

import com.google.gson.annotations.SerializedName

data class LFDeleteResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
