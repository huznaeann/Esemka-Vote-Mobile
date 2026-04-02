package com.esemkavotemobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esemkavotemobile.databinding.RowVotingHeaderBinding
import com.esemkavotemobile.model.VotingHeader

class VotingHeaderAdapter(private val votingHeaders: List<VotingHeader>, private val listener: setClickListener) : RecyclerView.Adapter<VotingHeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RowVotingHeaderBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return votingHeaders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(votingHeaders[position])
    }

    inner class ViewHolder(private val binding: RowVotingHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(votingHeader: VotingHeader) {
            binding.name.text = votingHeader.name
            binding.description.text = votingHeader.description
            binding.period.text = "${votingHeader.startDate} - ${votingHeader.endDate}"
            binding.voters.text = "${votingHeader.voters} voters"

            binding.container.setOnClickListener {
                listener.setClickVotingHeaderListener(votingHeader.id)
            }
        }
    }

    interface setClickListener {
        fun setClickVotingHeaderListener(votingHeaderId : Int)
    }
}