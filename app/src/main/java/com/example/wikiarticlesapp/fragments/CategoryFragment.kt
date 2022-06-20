package com.example.wikiarticlesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.build.jetifier.core.utils.Log
import com.example.wikiarticlesapp.utils.Constants
import com.example.wikiarticlesapp.R
import com.example.wikiarticlesapp.utils.Resource
import com.example.wikiarticlesapp.activities.MainActivity
import com.example.wikiarticlesapp.adapters.ArticlesAdapter
import com.example.wikiarticlesapp.databinding.FragmentCategoryBinding
import com.example.wikiarticlesapp.viewmodel.ArticlesViewModel

class CategoryFragment : Fragment(R.layout.fragment_category){

    lateinit var viewModel: ArticlesViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()


        viewModel.category.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                            categoryResponse ->
                        articlesAdapter.catDiffer.submitList(categoryResponse.query.allcategories.toList())
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e("Category Call Failed",message)
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
        isLoading = false
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility =View.VISIBLE
        isLoading = true
    }


    var isLoading = false
    var isScrolling = false


    private val scrollListener = object : RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPositon = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount


            val isNotLoading = !isLoading
            val isAtLastItem = firstVisibleItemPositon + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPositon >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoading && isAtLastItem && isNotAtBeginning  && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){

//                Toast.makeText(context,shouldPaginate.toString(), Toast.LENGTH_LONG).show()
                viewModel.getCategory()
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
        articlesAdapter = ArticlesAdapter("Category")
        binding.rvCategory.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@CategoryFragment.scrollListener)
        }

    }
}