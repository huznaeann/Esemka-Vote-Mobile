package com.esemkavotemobile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esemkavotemobile.adapter.VotingCandidateAdapter
import com.esemkavotemobile.api.VotingCandidateAPI
import com.esemkavotemobile.databinding.ActivityVoteBinding
import com.esemkavotemobile.model.Candidate
import com.esemkavotemobile.model.VoteCandidateRequest
import com.esemkavotemobile.parser.VotingCandidateParser
import kotlin.concurrent.thread

class VoteActivity : AppCompatActivity(), VotingCandidateAdapter.setClickListener {

    private lateinit var binding: ActivityVoteBinding
    private val headerId by lazy {
        intent.getIntExtra("votingHeaderId", 0)
    }
    private val token by lazy {
        intent.getStringExtra("token")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        thread {
            val response = VotingCandidateAPI.getAllCandidateByVotingHeaderId(token!!, headerId)
            val candidates = VotingCandidateParser.responseToCandidatesResponse(response)
            runOnUiThread {
                binding.recyclerView.adapter = VotingCandidateAdapter(candidates, this, this)
            }
        }
    }

    override fun setCandidateClickListener(candidate: Candidate) {
        binding.votingLayout.visibility = View.VISIBLE
        binding.tvCurrentVote.text = "Currently Voting : ${candidate.name}"
        binding.btnVote.setOnClickListener {
            val request = VoteCandidateRequest(
                candidateId = candidate.candidateId,
                employeeId = 2 // -> Need to fix to be getMe (user that logged in)
            )
            thread {
                val response = VotingCandidateAPI.voteCandidate(token!!, request)
                runOnUiThread {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}