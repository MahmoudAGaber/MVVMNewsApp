package com.androiddevs.mvvmnewsapp.ui.mvvmNewsApp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.Adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.ui.util.Constants.Companion.Search_News_Delay
import com.androiddevs.mvvmnewsapp.ui.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel:NewsViewModel
    lateinit var newsAdaper:NewsAdapter
    val TAG  = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setupRecycleView()

        newsAdaper.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment4,
                bundle
            )
        }

        var job:Job?=null

        etSearch.addTextChangedListener{ editable ->
            job?.cancel()

            job = MainScope().launch {
                delay(Search_News_Delay)
                if(editable.toString().isNotEmpty()){
                    viewModel.searchNews(editable.toString())
                }
            }

        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { respone ->
            when(respone){
                is Resource.Success -> {
                    hideProgessBar()
                    respone.data?.let { newsResponse ->
                        newsAdaper.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgessBar()
                    respone.message?.let { message ->
                        Log.e(TAG,"An Error Occured:$message")
                    }
                }

                is Resource.Loading ->{
                    showProgessBar()
                }

            }
        })

    }

    private fun hideProgessBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }


    private fun showProgessBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecycleView(){
        newsAdaper = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdaper
            layoutManager = LinearLayoutManager(activity)
        }
    }
    }