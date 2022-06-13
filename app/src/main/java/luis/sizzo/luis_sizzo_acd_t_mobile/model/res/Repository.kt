package luis.sizzo.luis_sizzo_acd_t_mobile.model.res

import android.content.ContentValues
import android.util.Log
import kotlinx.coroutines.flow.*
import luis.sizzo.luis_sizzo_acd_t_mobile.common.CheckConnection
import luis.sizzo.luis_sizzo_acd_t_mobile.model.UI_State
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.Github_Dao
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_info.UserInfo
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity.UserSearchEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.model.remote.RemoteAPIConnection
import javax.inject.Inject

interface Repository {
    fun getSearchUser(search: String): Flow<UI_State>
    fun getUser(username: String): Flow<UI_State>
    fun getUserRepo(username: String): Flow<UI_State>
    fun getUserSearchRepo(username: String, reponame: String): Flow<UI_State>
}

class RepositoryImpl @Inject constructor(
    private val service: RemoteAPIConnection,
    private val schoolDao: Github_Dao,
) : Repository {

    override fun getSearchUser(search: String) = flow {
        emit(UI_State.LOADING)
        try {
            if (CheckConnection().isConnected()) {
                val response = service.getSearchUsers(search)
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        schoolDao.deleteUserInfo()
                        //Loop for Search Results
                        result.items.forEach { res ->
                            //Check if login is not empty
                            res.login?.let { login ->
                                //Connection with User API for catching Public Repos
                                val user = service.getUsers(login)
                                if(user.isSuccessful){
                                    //If body is not empty
                                    user.body()?.let { userResult->
                                        val userSearch = UserSearchEntity(
                                            res.id,
                                            res.login,
                                            res.avatar_url,
                                            res.url,
                                            res.score,
                                            userResult.public_repos
                                        )
                                        schoolDao.insertAllUserInfo(userSearch)
                                    }
                                }
                            }
                        }
                        val cache = schoolDao.getAllUserInfo()
                        emit(UI_State.SUCCESS(cache))
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception(response.errorBody().toString())
                }
            } else {
                val cache = schoolDao.getAllUserInfo()
                if (!cache.isEmpty()) {
                    emit(UI_State.SUCCESS(cache))
                } else {
                    throw Exception("Cache failed")
                }
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getSchools: ${e.message}")
            emit(UI_State.ERROR(e))
        }
    }

    override fun getUser(username: String) = flow {
        emit(UI_State.LOADING)
        try {
            if (CheckConnection().isConnected()) {
                val response = service.getUsers(username)
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        emit(UI_State.SUCCESS(result))
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception(response.errorBody().toString())
                }
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getUser: ${e.message}")
            emit(UI_State.ERROR(e))
        }
    }

    override fun getUserRepo(username: String) = flow {
        emit(UI_State.LOADING)
        try {
            if (CheckConnection().isConnected()) {
                val response = service.getUsersRepo(username)
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        schoolDao.deleteRepos()
                        result.forEach {
                            val repo = UserRepositoriesEntity(
                                it.id,
                                it.name,
                                it.description,
                                it.visibility,
                                it.stargazers_count,
                                it.forks,
                                it.html_url
                            )
                            schoolDao.insertAllRepo(repo)
                        }
                        val cache = schoolDao.getAllRepo()
                        emit(UI_State.SUCCESS(cache))
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception(response.errorBody().toString())
                }
            } else {
                val cache = schoolDao.getAllRepo()
                if (cache.isNotEmpty()) {
                    emit(UI_State.SUCCESS(cache))
                } else {
                    throw Exception("Cache failed")
                }
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getUserRepo: ${e.message}")
            emit(UI_State.ERROR(e))
        }
    }

    override fun getUserSearchRepo(username: String, reponame: String) = flow {
        emit(UI_State.LOADING)
        try {
            val cache = schoolDao.searchUserRepo(reponame)
            if (cache.isNotEmpty()) {
                emit(UI_State.SUCCESS(cache))
            } else {
                throw Exception("Cache failed")
            }
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getUserSearchRepo: ${e.message}")
            emit(UI_State.ERROR(e))
        }
    }
}