package com.esemkavotemobile.api

import com.esemkavotemobile.model.Response
import com.esemkavotemobile.parser.ResponseParser
import java.net.HttpURLConnection
import java.net.URL

object VotingHeaderAPI {
    private val votingHeaderLinkAPI = "${BaseLinkAPI.BASE_API}/VotingHeader"

    fun getAllVotingHeader(token : String) : Response {
        val url = URL(votingHeaderLinkAPI)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.setRequestProperty("Authorization", "Bearer ${token}")

            val stream = if (connection.responseCode in 200..209) {
                connection.inputStream
            }
            else {
                connection.errorStream
            }

            val response = stream.bufferedReader().use { it.readText() }
            return ResponseParser.jsonToResponse(response)
        }
        catch (e : Exception) {
            e.printStackTrace()
            return Response (
                message = "Something went wrong",
                data = ""
            )
        }
        finally {
            connection.disconnect()
        }
    }
}