package eg.esperantgada.imagegallery.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.repository.FlickrImageRepository
import eg.esperantgada.imagegallery.room.ImageDatabase
import eg.esperantgada.imagegallery.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideImageDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        ImageDatabase::class.java,
        "image_database"
    ).fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideImageDao(imageDatabase: ImageDatabase) = imageDatabase.getImageDao()

    @Singleton
    @Provides
    fun provideSearchPhotoDao(imageDatabase: ImageDatabase) = imageDatabase.getSearchPhotoDao()


    @Singleton
    @Provides
    fun provideRemoteKeyDao(imageDatabase: ImageDatabase) = imageDatabase.getRemoteKeyDao()


}