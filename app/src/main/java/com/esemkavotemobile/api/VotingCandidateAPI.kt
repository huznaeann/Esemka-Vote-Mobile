package com.esemkavotemobile.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.esemkavotemobile.model.Response
import com.esemkavotemobile.model.VoteCandidateRequest
import com.esemkavotemobile.parser.ResponseParser
import com.esemkavotemobile.parser.VotingCandidateParser
import com.esemkavotemobile.parser.VotingHeaderParser
import java.net.HttpURLConnection
import java.net.URL

object VotingCandidateAPI {
    private val votingCandidateLinkAPI = "${BaseLinkAPI.BASE_API}/VotingCandidate"

    fun getAllCandidateByVotingHeaderId(token: String, votingHeaderId : Int) : Response {
        val link = "${votingCandidateLinkAPI}/${votingHeaderId}"
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.setRequestProperty("Authorization", "Bearer $token")

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
            return Response(
                message = "Something went wrong",
                data = ""
            )
        }
        finally {
            connection.disconnect()
        }
    }

    fun voteCandidate(token: String, request : VoteCandidateRequest) : Response {
        val url = URL(votingCandidateLinkAPI)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.doOutput = true
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val json = VotingCandidateParser.voteCandidateRequest(request)
            connection.outputStream.use {
                it.write(json.toByteArray())
            }

            val stream = if (connection.responseCode in 200..209) {
                connection.inputStream
            }
            else {
                connection.errorStream
            }

            val response = stream.bufferedReader().use {
                it.readText()
            }

            return ResponseParser.jsonToResponse(response)
        }
        catch (e : Exception) {
            e.printStackTrace()
            return Response(
                message = "Something went wrong",
                data = ""
            )
        }
        finally {
            connection.disconnect()
        }
    }

    fun getImage(photo: String) : Bitmap {
        val imageLink = "${BaseLinkAPI.BASE_API_IMAGES}/$photo"
        val url = URL(imageLink)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val stream = connection.inputStream
            return BitmapFactory.decodeStream(stream)
        }
        finally {
            connection.disconnect()
        }
    }
}