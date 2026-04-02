package com.esemkavotemobile.model

import java.util.Date


data class VotingHeader(
    val id: Int,
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val voters: Int
)
