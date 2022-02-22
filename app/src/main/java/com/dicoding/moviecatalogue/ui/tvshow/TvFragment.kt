package com.dicoding.moviecatalogue.ui.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.adapter.ListAdapter
import com.dicoding.moviecatalogue.data.CatalogueEntity
import com.dicoding.moviecatalogue.databinding.FragmentTvBinding
import com.dicoding.moviecatalogue.ui.detail.DetailActivity
import com.dicoding.moviecatalogue.viewmodel.ViewModelFactory

class TvFragment : Fragment() {
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(
                this,
                factory
            )[TvViewModel::class.java]

            binding.progressBar.visibility = View.VISIBLE
            viewModel.getTvs().observe(viewLifecycleOwner) { catalogue ->
                binding.progressBar.visibility = View.GONE
                val listAdapter = ListAdapter()
                listAdapter.setCatalogues(catalogue)

                with(binding.rvTv) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = listAdapter
                }

                listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                    override fun onItemClicked(catalogue: CatalogueEntity) {
                        val intent = Intent(requireActivity(), DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_ID, catalogue.id)
                        intent.putExtra(DetailActivity.EXTRA_TYPE, TYPE)
                        startActivity(intent)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TYPE = "tv"
    }
}