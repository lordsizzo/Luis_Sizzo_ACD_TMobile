package luis.sizzo.luis_sizzo_acd_t_mobile.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import coil.load
import luis.sizzo.luis_sizzo_acd_t_mobile.R
import luis.sizzo.luis_sizzo_acd_t_mobile.common.click
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity.UserSearchEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.common.toast
import luis.sizzo.luis_sizzo_acd_t_mobile.databinding.ItemsUsersBinding
import luis.sizzo.luis_sizzo_acd_t_mobile.view.DetailsViewActivity

class UserSearchAdapter(private val items: List<UserSearchEntity>) :
    RecyclerView.Adapter<UserSearchAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: ItemsUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemsUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        try {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rotate)
                holder.binding.nameSchool.text = items[position].login

                holder.binding.imgAvatar.load(items[position].avatar_url)
                holder.binding.idRepos.text = "Repo: ${items[position].public_repos}"
            holder.binding.root.click{
                val intent = Intent(holder.binding.root.context, DetailsViewActivity::class.java)
                val bundle = bundleOf("id" to items[position].id,
                    "login" to items[position].login,
                    "avatar_url" to items[position].avatar_url)
                intent.putExtras(bundle)
                holder.binding.root.context.startActivity(intent)
            }
        } catch (e: Exception) {

            holder.binding.root.context.toast(e.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}