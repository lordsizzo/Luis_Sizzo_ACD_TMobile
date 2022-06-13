package luis.sizzo.luis_sizzo_acd_t_mobile.di

import android.content.Context
import androidx.room.Room
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.Github_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    private const val NameDatabase = "user_github_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            Github_DB::class.java,
            NameDatabase
        ).build()

    @Provides
    @Singleton
    fun provideGithubDao(db: Github_DB) = db.getGithubDao()

}