package com.assessment.teladochealth.hilt

import android.content.Context
import com.assessment.teladochealth.BuildConfig
import com.assessment.teladochealth.data.ITunesSearchDataSource
import com.assessment.teladochealth.data.remote.ImagesRemoteDataSource
import com.assessment.teladochealth.data.remote.retrofit.Endpoints
import com.assessment.teladochealth.data.repository.ITunesSearchRepository
import com.assessment.teladochealth.domain.usecase.SearchAlbumTitlesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.task.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.ITUNE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): Endpoints = retrofit.create(Endpoints::class.java)

    @Provides
    @Singleton
    fun provideSearchImageUseCase(repository: ITunesSearchRepository): SearchAlbumTitlesUseCase {
        return SearchAlbumTitlesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchImageRepository(
        remoteDataSource: ITunesSearchDataSource.Remote,
        networkHelper: NetworkHelper
    ): ITunesSearchRepository {
        return ITunesSearchRepository(remoteDataSource, networkHelper)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(endpoints: Endpoints): ITunesSearchDataSource.Remote {
        return ImagesRemoteDataSource(endpoints)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}
