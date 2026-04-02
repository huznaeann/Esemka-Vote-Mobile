package com.esemkavotemobile.parser

import com.esemkavotemobile.model.Response
import com.esemkavotemobile.model.VotingHeader
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object VotingHeaderParser {
    fun responseToVotingHeader(response: Response) : List<VotingHeader> {
        val votingHeaders = mutableListOf<VotingHeader>()
        val dataArray = JSONArray(response.data)

        for (votingHeader in 0 until dataArray.length()) {
            val json = dataArray.getJSONObject(votingHeader)

            val id = json.getInt("id")
            val name = json.getString("name")
            val description = json.getString("description")
            val startDateString = json.getString("startDate")
            val startDateRaw = LocalDate.parse(startDateString)
            val startDate = startDateRaw.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            val endDateString = json.getString("endDate")
            val endDateRaw = LocalDate.parse(endDateString)
            val endDate = endDateRaw.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            val voters = json.getInt("voters")

            val header = VotingHeader(id, name, description, startDate, endDate, voters)

            votingHeaders.add(header)
        }

        return votingHeaders
    }
}