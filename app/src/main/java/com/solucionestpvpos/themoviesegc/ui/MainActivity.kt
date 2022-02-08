package com.solucionestpvpos.themoviesegc.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.solucionestpvpos.themoviesegc.R
import com.solucionestpvpos.themoviesegc.Repository
import com.solucionestpvpos.themoviesegc.adapter.PlayingAdapter
import com.solucionestpvpos.themoviesegc.adapter.PopularAdapter
import com.solucionestpvpos.themoviesegc.databinding.ActivityMainBinding
import com.solucionestpvpos.themoviesegc.interfaces.Constant
import com.solucionestpvpos.themoviesegc.interfaces.PlayingSelectListener
import com.solucionestpvpos.themoviesegc.interfaces.PopularSelectListener
import com.solucionestpvpos.themoviesegc.model.ResultPlaying
import com.solucionestpvpos.themoviesegc.model.ResultsPopular
import com.solucionestpvpos.themoviesegc.util.UIUtil
import com.solucionestpvpos.themoviesegc.util.exchangeView
import com.solucionestpvpos.themoviesegc.util.exchangeViewShimmerGone
import com.solucionestpvpos.themoviesegc.util.exchangeViewShimmerVisible
import com.solucionestpvpos.themoviesegc.viewmodel.PlayingViewModel
import com.solucionestpvpos.themoviesegc.viewmodel.PopularViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_layout.*
import kotlinx.android.synthetic.main.shimmer_gird_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PlayingSelectListener, PopularSelectListener,
    SwipeRefreshLayout.OnRefreshListener, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob()

    private lateinit var binding: ActivityMainBinding
    private val persistence by lazy { Repository(cacheDir)}

    private val playingViewModel: PlayingViewModel by viewModels()
    private lateinit var adapterPlaying: PlayingAdapter

    private val popularViewModel: PopularViewModel by viewModels()
    private lateinit var adapterPopular: PopularAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch (Dispatchers.Main) {

        }

        Home_Swipe_Refresh_Layout.setOnRefreshListener {
            playingViewModel.getCallPlayings()
            popularViewModel.getCallPopular()

            getCallPlaying()
            getCallPopular()                  // refresh your list contents somehow
            Home_Swipe_Refresh_Layout.isRefreshing =
                false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }

        playingViewModel.getCallPlayings()
        popularViewModel.getCallPopular()

        getCallPlaying()
        getCallPopular()
    }

    private fun getCallPlaying() {
        exchangeView(layoutProgress, movie_list_recycleview)
        exchangeViewShimmerVisible(Movies_Shimmer_Layout)

        playingViewModel.callPlaying().observe(this, Observer {
            if (it == null) {
                exchangeView(movie_list_recycleview, layoutProgress)
                exchangeViewShimmerVisible(Movies_Shimmer_Layout)

                UIUtil.popError(this, getString(R.string.oops), getString(R.string.network_error)) {
                    if (it) getCallPlaying()
                }
                return@Observer
            }
            adapterPlaying = PlayingAdapter(this, ArrayList(it), this@MainActivity)

            movie_list_recycleview.layoutManager = GridLayoutManager(this, 3)
            movie_list_recycleview.adapter = adapterPlaying
            exchangeView(movie_list_recycleview, layoutProgress)
            exchangeViewShimmerGone(Movies_Shimmer_Layout)


        })
    }

    private fun getCallPopular() {

        exchangeView(layoutProgress, home_popularMovies_list_Recycler_View)
        exchangeViewShimmerVisible(Movies_Shimmer_Layout)
        popularViewModel.callPopular().observe(this, Observer {
            if (it == null) {
                exchangeView(home_popularMovies_list_Recycler_View, layoutProgress)
                exchangeViewShimmerVisible(Movies_Shimmer_Layout)

                UIUtil.popError(this, getString(R.string.oops), getString(R.string.network_error)) {
                    if (it) getCallPopular()
                }
                return@Observer
            }
            adapterPopular = PopularAdapter(this, ArrayList(it), this@MainActivity)
            home_popularMovies_list_Recycler_View.layoutManager = GridLayoutManager(this, 3)
            home_popularMovies_list_Recycler_View.adapter = adapterPopular
            exchangeView(home_popularMovies_list_Recycler_View, layoutProgress)
            exchangeViewShimmerGone(Movies_Shimmer_Layout)

        })
    }

    override fun onPlayingSelect(playing: ResultPlaying) {
        DetailMovieActivity.run(this, playing)
    }

    override fun onPopularSelect(popular: ResultsPopular) {
        DetailPopularActivity.run(this, popular)
    }

    override fun onRefresh() {
        playingViewModel.getCallPlayings()
        popularViewModel.getCallPopular()

        getCallPlaying()
        getCallPopular()
    }

}