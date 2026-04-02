package com.esemkavotemobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esemkavotemobile.adapter.VotingHeaderAdapter
import com.esemkavotemobile.api.VotingHeaderAPI
import com.esemkavotemobile.databinding.ActivityMainBinding
import com.esemkavotemobile.parser.VotingHeaderParser
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), VotingHeaderAdapter.setClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        token = intent.getStringExtra("token")!!

        thread {
            val response = VotingHeaderAPI.getAllVotingHeader(token)
            val votingHeaders = VotingHeaderParser.responseToVotingHeader(response)
            runOnUiThread {
                binding.recyclerView.adapter = VotingHeaderAdapter(votingHeaders, this)
            }
        }
    }

    override fun setClickVotingHeaderListener(votingHeaderId : Int) {
        val intent = Intent(this, VoteActivity::class.java)
        intent.putExtra("token", token)
        intent.putExtra("votingHeaderId", votingHeaderId)
        startActivity(intent)
    }


}