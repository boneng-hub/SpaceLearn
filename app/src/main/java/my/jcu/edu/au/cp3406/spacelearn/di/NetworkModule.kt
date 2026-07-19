package my.jcu.edu.au.cp3406.spacelearn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import my.jcu.edu.au.cp3406.spacelearn.BuildConfig
import my.jcu.edu.au.cp3406.spacelearn.data.remote.NasaApodApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNasaApodApi(
        retrofit: Retrofit
    ): NasaApodApi {
        return retrofit.create(
            NasaApodApi::class.java
        )
    }

    @Provides
    @Named(NASA_API_KEY_NAME)
    fun provideNasaApiKey(): String {
        return BuildConfig.NASA_API_KEY
    }

    const val NASA_API_KEY_NAME =
        "nasa_api_key"

    private const val NASA_BASE_URL =
        "https://api.nasa.gov/"
}