package com.ifs21020.lostandfounds.data.repository

import com.ifs21020.lostandfounds.data.remote.MyResult
import com.ifs21020.lostandfounds.data.remote.retrofit.IApiService
import com.ifs21020.lostandfounds.data.remote.response.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException

class LFRepository private constructor(private val apiService: IApiService) {

    fun addLostAndFound(title: String, description: String): Flow<MyResult<LFAddResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.addLostAndFound(title, description)))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    fun addCoverLostAndFound(id: String, coverImage: MultipartBody.Part): Flow<MyResult<LFAddCoverResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.addCoverLostAndFound(id, coverImage)))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    fun updateLostAndFound(id: String, title: String, description: String): Flow<MyResult<LFUpdateResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.updateLostAndFound(id, title, description)))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    fun getAllLostAndFound(): Flow<MyResult<LFGetAllResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.getAllLostAndFound()))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    fun getLostAndFoundDetail(id: String): Flow<MyResult<LFDetailResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.getLostAndFoundDetail(id)))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    suspend fun deleteLostAndFound(id: String): Flow<MyResult<LFDeleteResponse>> = flow {
        emit(MyResult.Loading)
        try {
            emit(MyResult.Success(apiService.deleteLostAndFound(id)))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string()
            emit(MyResult.Error(errorMessage ?: "An error occurred"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LFRepository? = null

        fun getInstance(apiService: IApiService): LFRepository {
            synchronized(LFRepository::class.java) {
                INSTANCE = INSTANCE ?: LFRepository(apiService)
            }
            return INSTANCE!!
        }
    }
}
