package com.mohseen78.countryfacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohseen78.countryfacts.databinding.ItemFactBinding
import com.mohseen78.countryfacts.model.Fact

class FactsAdapter(var facts : List<Fact>) : RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        return FactViewHolder(
            ItemFactBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.binding.also {
            it.property = facts[position]
        }
    }

    override fun getItemCount() = facts.size

    class FactViewHolder(var binding: ItemFactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fact: Fact) {
            binding.property = fact
            binding.executePendingBindings()

            if(fact.factImgSrcUrl == null){
                binding.factDescriptionTxt.visibility = View.GONE
            }

            if(fact.factTitle == null){
                binding.factDescriptionTxt.visibility = View.GONE
            }

            if(fact.factDescription == null){
                binding.factDescriptionTxt.visibility = View.GONE
            }
        }
    }
}