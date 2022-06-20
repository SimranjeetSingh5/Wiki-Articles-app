package com.example.wikiarticlesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.build.jetifier.core.utils.Log
import com.example.wikiarticlesapp.R
import com.example.wikiarticlesapp.utils.Resource
import com.example.wikiarticlesapp.activities.MainActivity
import com.example.wikiarticlesapp.adapters.ArticlesAdapter
import com.example.wikiarticlesapp.databinding.FragmentArticlesBinding
import com.example.wikiarticlesapp.viewmodel.ArticlesViewModel

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    lateinit var viewModel: ArticlesViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var binding: FragmentArticlesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentArticlesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()


        viewModel.randomArticles.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                            randomResponse ->

                        articlesAdapter.randiffer.submitList(randomResponse.query?.pages?.values?.toList())
//                        val data = randomResponse.query?.pages?.values
//                        saveData(data!!)
//                        Toast.makeText(context,randomResponse.query?.pages?.values?.toString(),Toast.LENGTH_LONG).show()
//                            Log.e("Random data",randomResponse.query.pages.values.toString())
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e("Random Articles Call Failed",message)
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })

    }



    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility =View.INVISIBLE
        isLoading =false
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility =View.VISIBLE

        isLoading = true
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPositon = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
//
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPositon + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPositon >= 0
//            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning  && isScrolling

            if (shouldPaginate){

//                    Toast.makeText(context,shouldPaginate.toString(), Toast.LENGTH_LONG).show()
                viewModel.getRandomArticles()

                isScrolling = false
            }
        }
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }


    private fun setupRecyclerView(){
        articlesAdapter = ArticlesAdapter("Random")
        binding.rvBreakingNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = articlesAdapter
            addOnScrollListener(this@ArticlesFragment.scrollListener)
        }

    }
}
