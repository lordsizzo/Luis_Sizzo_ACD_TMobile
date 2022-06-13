package luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_info

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user_info_table", primaryKeys = ["id"])
data class UserInfo(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "avatar_url") val avatar_url: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "created_at") val created_at: String?,
    @ColumnInfo(name = "followers") val followers: String?,
    @ColumnInfo(name = "following") val following: String?,
    @ColumnInfo(name = "public_repos") val public_repos: Int?
)
