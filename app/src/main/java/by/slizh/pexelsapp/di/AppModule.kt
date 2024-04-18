package by.slizh.pexelsapp.di

import android.app.Application
import androidx.room.Room
import by.slizh.pexelsapp.api.PexelsApi
import by.slizh.pexelsapp.data.local.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import by.slizh.pexelsapp.util.Constans.Companion.BASE_URL


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providePexelsApi(): PexelsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(PexelsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoDB(app: Application): PhotoDatabase {
        return Room.databaseBuilder(
            app,
            PhotoDatabase::class.java,
            "photodb.db"
        ).build()
    }

}