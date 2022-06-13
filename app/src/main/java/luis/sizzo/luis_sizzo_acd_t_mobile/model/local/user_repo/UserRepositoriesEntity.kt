package luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user_repositories_table", primaryKeys = ["id"])
data class UserRepositoriesEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "visibility") val visibility: Boolean?,
    @ColumnInfo(name = "stargazers_count") val stargazers_count: Int?,
    @ColumnInfo(name = "forks") val forks: Int?,
    @ColumnInfo(name = "html_url") val html_url: String?
)