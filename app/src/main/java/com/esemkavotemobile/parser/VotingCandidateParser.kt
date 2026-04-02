package com.esemkavotemobile.parser

import com.esemkavotemobile.model.Candidate
import com.esemkavotemobile.model.Response
import com.esemkavotemobile.model.VoteCandidateRequest
import org.json.JSONArray
import org.json.JSONObject

object VotingCandidateParser {
    fun responseToCandidatesResponse(response: Response) : List<Candidate> {
        val candidates = mutableListOf<Candidate>()
        val dataArray = JSONArray(response.data)

        for (item in 0 until dataArray.length()) {
            val json = dataArray.getJSONObject(item)

            val candidateId = json.getInt("candidateId")
            val name = json.getString("name")
            val division = json.getString("division")
            val photo = json.getString("photo")

            val candidate = Candidate(candidateId, name, division, photo)

            candidates.add(candidate)
        }

        return candidates
    }

    fun voteCandidateRequest(request: VoteCandidateRequest) : String {
        val json = JSONObject()
        json.put("candidateId", request.candidateId)
        json.put("employeeId", request.employeeId)

        return json.toString()
    }
}