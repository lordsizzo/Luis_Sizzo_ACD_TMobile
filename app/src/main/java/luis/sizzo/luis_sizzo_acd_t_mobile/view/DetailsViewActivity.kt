package luis.sizzo.luis_sizzo_acd_t_mobile.view

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.*
import androidx.lifecycle.ViewModelProvider
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.luis_sizzo_acd_t_mobile.common.*
import luis.sizzo.luis_sizzo_acd_t_mobile.model.UI_State
import luis.sizzo.luis_sizzo_acd_t_mobile.databinding.DetailsActivityViewBinding
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_info.UserInfo
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_repo.UserRepositoriesEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.view.adapters.UserRepoAdapter
import luis.sizzo.luis_sizzo_acd_t_mobile.view_model.MainActivityViewModel

@AndroidEntryPoint
class DetailsViewActivity : AppCompatActivity() {

    var username: String = ""
    lateinit var bindingSat: DetailsActivityViewBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    private val dialog =  Dialogs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingSat = DetailsActivityViewBinding.inflate(layoutInflater)
        setContentView(bindingSat.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        initViews()
        initOberserver()
    }

    private fun initViews() {
        intent.getStringExtra("login").let {
            if (it != null) {
                username = it
            }
        }
        bindingSat.swipeRefresh.setOnRefreshListener {
            viewModel.getUser(username)
        }

        intent.getStringExtra("avatar_url").let {
            bindingSat.imgAvatar.load(it)
        }
        intent.getStringExtra("description").let {
            //bindingSat.schoolDescription.text = it
        }

        bindingSat.searchRepos.textWatcher{
            it.toString().let {search ->
                viewModel.getUserSearchRepo(username, search)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initOberserver() {
        viewModel.getUserResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            bindingSat.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            val userInfo = result.response as? UserInfo

                            userInfo?.let { response ->
                                bindingSat.userName.text = "${response.login}"
                                response.email?.let {
                                    bindingSat.lyEmail.setVisibility(View.VISIBLE);
                                    bindingSat.userEmail.text = "Email: $it"
                                } ?: bindingSat.lyEmail.setVisibility(View.GONE);

                                response.location?.let {
                                    bindingSat.lyLocation.setVisibility(View.VISIBLE);
                                    bindingSat.userLocation.text = "Location: $it"
                                } ?: bindingSat.lyLocation.setVisibility(View.GONE);

                                bindingSat.userJoinDate.text = "Join date: ${response.created_at?.substring(0, 10)}"
                                bindingSat.userFollowers.text = "Followers: ${response.followers}"
                                bindingSat.userFollowing.text = "Following: ${response.following}"

                                bindingSat.shimmerViewContainer.stopShimmerAnimation();
                                bindingSat.shimmerViewContainer.setVisibility(View.GONE);
                                bindingSat.lyContent.setVisibility(View.VISIBLE);
                            } ?: dialog.showError(this, "Error at casting")
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> dialog.showError(this, error) }
                        }
                    }
                } catch (e: Exception) {
                    dialog.showError(this, e.toString())
                }
                bindingSat.swipeRefresh.isRefreshing = false
            }
        }
        viewModel.getUser(username)

        viewModel.getUserRepoResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            bindingSat.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            val userRepo = result.response as? List<UserRepositoriesEntity>

                            if (userRepo != null) {
                                userRepo.let {
                                    UserRepoAdapter(it).apply {
                                        bindingSat.recyclerViewRepos.settingsLinearVerticalRepo(this)
                                    }
                                }
                            }else{
                                dialog.showError(this, "Error at casting")
                            }
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> dialog.showError(this, error) }
                        }
                    }
                } catch (e: Exception) {
                    dialog.showError(this, e.toString())
                }
                bindingSat.swipeRefresh.isRefreshing = false
            }
        }
        viewModel.getUserRepo(username)

        viewModel.getSearchUserRepoResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            bindingSat.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            val userRepo = result.response as? List<UserRepositoriesEntity>

                            if (userRepo != null) {
                                userRepo.let {
                                    UserRepoAdapter(it).apply {
                                        bindingSat.recyclerViewRepos.settingsLinearVerticalRepo(this)
                                    }
                                }
                            }else{
                                dialog.showError(this, "Error at casting")
                            }
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> dialog.showError(this, error) }
                        }
                    }
                } catch (e: Exception) {
                    dialog.showError(this, e.toString())
                }
                bindingSat.swipeRefresh.isRefreshing = false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        bindingSat.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        bindingSat.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

}