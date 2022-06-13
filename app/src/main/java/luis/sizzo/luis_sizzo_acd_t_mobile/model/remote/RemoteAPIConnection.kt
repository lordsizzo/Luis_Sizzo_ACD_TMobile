package luis.sizzo.luis_sizzo_acd_t_mobile.model.remote

import luis.sizzo.luis_sizzo_acd_t_mobile.common.*
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_info.UserInfo
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity
import retrofit2.*
import retrofit2.http.*

interface RemoteAPIConnection{

    @GET(END_POINT_SEARCH_USERS)
    suspend fun getSearchUsers(
        @Query(PARAM_SEARCH) search: String,
        @Query(PARAM_AUTH) auth: String = AUTORIZATION,
        @Query(PARAM_ACCEPT) accept: String = ACCEPT,
    ): Response<UserSearchObject>

    @GET("$PARAM_USER/{username}")
    suspend fun getUsers(
        @Path("username") username: String,
        @Query(PARAM_AUTH) auth: String = AUTORIZATION,
        @Query(PARAM_ACCEPT) accept: String = ACCEPT,
    ): Response<UserInfo>

    @GET("$PARAM_USER/{username}/repos")
    suspend fun getUsersRepo(
        @Path("username") username: String,
        @Query(PARAM_AUTH) auth: String = AUTORIZATION,
        @Query(PARAM_ACCEPT) accept: String = ACCEPT,
    ): Response<List<UserRepositoriesEntity>>

    @GET("$PARAM_REPOS/{username}/{reponame}")
    suspend fun getUsersSearchRepo(
        @Path("username") username: String,
        @Path("reponame") reponame: String,
        @Query(PARAM_AUTH) auth: String = AUTORIZATION,
        @Query(PARAM_ACCEPT) accept: String = ACCEPT,
    ): Response<List<UserRepositoriesEntity>>
}