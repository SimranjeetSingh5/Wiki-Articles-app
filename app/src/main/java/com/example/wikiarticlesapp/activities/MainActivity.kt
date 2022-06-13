package com.example.wikiarticlesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wikiarticlesapp.R
//import com.example.wikiarticlesapp.database.ArticleDatabase
import com.example.wikiarticlesapp.databinding.ActivityMainBinding
import com.example.wikiarticlesapp.repository.ArticlesRepository
import com.example.wikiarticlesapp.viewmodel.ArticleViewModelProviderFactory
import com.example.wikiarticlesapp.viewmodel.ArticlesViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val fm:FragmentManager = supportFragmentManager
//        viewModel.makeApiCall()
//        val repository = ArticlesRepository(ArticleDatabase(this))
        val repository = ArticlesRepository()
        val viewModelProviderFactory = ArticleViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[ArticlesViewModel::class.java]
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.articleNavHostFrag) as NavHostFragment
        val navController = navHostFragment.navController
        binding.topNavigationView.setupWithNavController(navController)




    }

}