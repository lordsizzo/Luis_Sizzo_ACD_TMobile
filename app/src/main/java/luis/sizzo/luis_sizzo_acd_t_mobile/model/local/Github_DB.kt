package luis.sizzo.luis_sizzo_acd_t_mobile.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_info.UserInfo
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity.UserSearchEntity

@Database(
    entities = [
        UserInfo::class,
        UserSearchEntity::class,
        UserRepositoriesEntity::class
    ],
    version = 1
)
abstract class Github_DB : RoomDatabase() {
    abstract fun getGithubDao(): Github_Dao
}