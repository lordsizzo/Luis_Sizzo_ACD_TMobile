package luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user_search_table", primaryKeys = ["id"])
data class UserSearchEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "avatar_url") val avatar_url: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "score") val score: Float?,
    @ColumnInfo(name = "public_repos") val public_repos: Int?
)
