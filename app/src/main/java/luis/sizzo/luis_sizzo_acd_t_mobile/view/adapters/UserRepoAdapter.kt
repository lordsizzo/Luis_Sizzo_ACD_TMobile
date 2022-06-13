package luis.sizzo.luis_sizzo_acd_t_mobile.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.*
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import luis.sizzo.luis_sizzo_acd_t_mobile.R
import luis.sizzo.luis_sizzo_acd_t_mobile.common.Utilities.Companion.getRandomColor
import luis.sizzo.luis_sizzo_acd_t_mobile.common.*
import luis.sizzo.luis_sizzo_acd_t_mobile.databinding.ItemsReposBinding
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity

class UserRepoAdapter(private val items: List<UserRepositoriesEntity>) :
    RecyclerView.Adapter<UserRepoAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: ItemsReposBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemsReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        try {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)
            holder.binding.repoName.text = items[position].name
            holder.binding.forks.text = "${items[position].forks} Forks"
            holder.binding.stars.text = "${items[position].stargazers_count} Stars"
            holder.binding.lyForksStars.setBackgroundColor(getRandomColor(holder.binding.root.context));

            holder.binding.root.click {
                val openURLRepo = Intent(Intent.ACTION_VIEW)
                openURLRepo.data = Uri.parse(items[position].html_url)
                holder.binding.root.context.startActivity(openURLRepo)
            }

        } catch (e: Exception) {

            holder.binding.root.context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}