package luis.sizzo.luis_sizzo_acd_t_mobile.di

import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import luis.sizzo.luis_sizzo_acd_t_mobile.common.BASE_URL
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.Github_Dao
import luis.sizzo.luis_sizzo_acd_t_mobile.model.remote.RemoteAPIConnection
import luis.sizzo.luis_sizzo_acd_t_mobile.model.res.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideApiService(): RemoteAPIConnection =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteAPIConnection::class.java)

    @Provides
    fun provideRepositoryLayer(service: RemoteAPIConnection, dao: Github_Dao): Repository =
        RepositoryImpl(service, dao)

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)


}