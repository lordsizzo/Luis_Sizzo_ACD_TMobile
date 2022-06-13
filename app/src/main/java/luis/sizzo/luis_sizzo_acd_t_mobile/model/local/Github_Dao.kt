package luis.sizzo.luis_sizzo_acd_t_mobile.model.local

import androidx.room.*
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity.UserSearchEntity

@Dao
interface Github_Dao {

    //USER INFO
    @Query("SELECT * FROM user_search_table")
    suspend fun getAllUserInfo(): List<UserSearchEntity>

    @Insert()
    suspend fun insertAllUserInfo(userSearch: UserSearchEntity)

    @Query("DELETE FROM user_search_table")
    suspend fun deleteUserInfo()


    //USER REPO
    @Query("SELECT * FROM user_repositories_table")
    suspend fun getAllRepo(): List<UserRepositoriesEntity>

    @Query("SELECT * FROM user_repositories_table WHERE name LIKE '%' || :search || '%'")
    suspend fun searchUserRepo(search: String): List<UserRepositoriesEntity>

    @Insert()
    suspend fun insertAllRepo(userRepo: UserRepositoriesEntity)

    @Query("DELETE FROM user_repositories_table")
    suspend fun deleteRepos()
}