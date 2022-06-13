package luis.sizzo.luis_sizzo_acd_t_mobile.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import luis.sizzo.luis_sizzo_acd_t_mobile.common.*
import luis.sizzo.luis_sizzo_acd_t_mobile.databinding.SearchViewActivityBinding
import luis.sizzo.luis_sizzo_acd_t_mobile.model.UI_State
import luis.sizzo.luis_sizzo_acd_t_mobile.model.local.user_search_entity.UserSearchEntity
import luis.sizzo.luis_sizzo_acd_t_mobile.view.adapters.UserSearchAdapter
import luis.sizzo.luis_sizzo_acd_t_mobile.view_model.MainActivityViewModel


@AndroidEntryPoint
class SearchViewActivity : AppCompatActivity() {

    private var howShowIt = true
    private lateinit var binding: SearchViewActivityBinding
    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    private val dialog =  Dialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchViewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initOberserver()
    }

    private fun initViews() {
        binding.btnSearch.click {
            if(binding.searchUser.text.toString().isNotEmpty()){
                viewModel.getSearchUser(binding.searchUser.text.toString())
            }else{
                binding.lyViewList.setVisibility(View.GONE);
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            if(binding.searchUser.text.toString().isNotEmpty()){
                viewModel.getSearchUser(binding.searchUser.text.toString())
            }else{
                binding.swipeRefresh.isRefreshing = false
                binding.lyViewList.setVisibility(View.GONE)
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
            }
        }

        binding.listView.click {
            howShowIt = false
            viewModel.getStateView(false)
        }
        binding.tableView.click {
            howShowIt = true
            viewModel.getStateView(true)
        }
    }

    private fun initOberserver() {
        viewModel.stateView.observe(this) {
            howShowIt = it
            if (it) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.listView.visibility = View.VISIBLE
                binding.tableView.visibility = View.GONE

            } else {
                binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.listView.visibility = View.GONE
                binding.tableView.visibility = View.VISIBLE
            }
        }
        viewModel.getStateView(howShowIt)
        viewModel.searchUserResponse.observe(this) {
            it.let { result ->
                try {
                    when (result) {
                        is UI_State.LOADING -> {
                            binding.lyViewList.setVisibility(View.GONE);
                            binding.shimmerViewContainer.setVisibility(View.VISIBLE);
                            binding.shimmerViewContainer.startShimmerAnimation()
                        }
                        is UI_State.SUCCESS<*> -> {

                            val userSearch = result.response as? List<UserSearchEntity>
                            if (userSearch != null) {
                                userSearch.let {
                                    UserSearchAdapter(it).apply {
                                        if (howShowIt)
                                            binding.recyclerView.settingsLinearVertical(this)
                                        else
                                            binding.recyclerView.settingsGrid(this)

                                    }
                                    binding.lyViewList.setVisibility(View.VISIBLE);
                                    binding.shimmerViewContainer.stopShimmerAnimation();
                                    binding.shimmerViewContainer.setVisibility(View.GONE);
                                }
                            }else{
                                dialog.showError(this, "Error at casting")
                            }
                        }
                        is UI_State.ERROR -> {
                            result.error.localizedMessage?.let { error -> toast(error) }
                        }
                    }

                } catch (e: Exception) {
                    dialog.showError(this,e.toString())

                }
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }



    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

}