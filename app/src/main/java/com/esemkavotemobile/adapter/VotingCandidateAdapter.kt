package com.esemkavotemobile.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esemkavotemobile.VoteActivity
import com.esemkavotemobile.api.VotingCandidateAPI
import com.esemkavotemobile.api.VotingHeaderAPI
import com.esemkavotemobile.databinding.RowCandidateBinding
import com.esemkavotemobile.model.Candidate
import kotlin.concurrent.thread

class VotingCandidateAdapter(private val candidates: List<Candidate>, private val listener: setClickListener, private val activity: VoteActivity) : RecyclerView.Adapter<VotingCandidateAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RowCandidateBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return candidates.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(candidates[position])
    }

    inner class ViewHolder(private val binding: RowCandidateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(candidate: Candidate) {
            thread {
                val bitmap = VotingCandidateAPI.getImage(candidate.photo)
                activity.runOnUiThread {
                    binding.photo.setImageBitmap(bitmap)
                }
            }

            binding.name.text = candidate.name
            binding.division.text = candidate.division

            binding.container.setOnClickListener {
                listener.setCandidateClickListener(candidate)
            }
        }
    }
    interface setClickListener {
        fun setCandidateClickListener(candidate: Candidate)
    }
}