package com.example.challangeset_mar.data.di

import com.example.challangeset_mar.BuildConfig
import com.example.challangeset_mar.data.api.ApiService
import com.example.challangeset_mar.data.repository.AstroRepositoryImp
import com.example.challangeset_mar.domain.repository.AstroRepository
import com.example.challangeset_mar.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides an [HttpLoggingInterceptor] for logging network requests and responses.
     * Set the level to [HttpLoggingInterceptor.Level.BODY] for full details in debug builds.
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Provides an [OkHttpClient] instance configured with timeouts and interceptors.
     * @param loggingInterceptor The logging interceptor to add for debugging.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            // Add other interceptors here (e.g., for authentication, headers)
            // .addInterceptor(AuthInterceptor())
            .build()
    }

    /**
     * Provides a [Retrofit] instance.
     * @param okHttpClient The OkHttpClient to use for network requests.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    /**
     * Provides the [ApiService] using the Retrofit instance.
     * This is the main interface for making API calls.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAstroRepository(
        astroRepositoryImp: AstroRepositoryImp
    ): AstroRepository
}