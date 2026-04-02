package com.esemkavotemobile.api

import android.util.Log
import com.esemkavotemobile.model.Employee
import com.esemkavotemobile.model.LoginRequest
import com.esemkavotemobile.model.Response
import com.esemkavotemobile.parser.AuthParser
import com.esemkavotemobile.parser.ResponseParser
import java.net.HttpURLConnection
import java.net.URL

object AuthAPI {

    private val AuthLinkAPI = "${BaseLinkAPI.BASE_API}/Auth"

    fun login(request: LoginRequest) : Response {
        val link = "$AuthLinkAPI/Login"
        val url = URL(link)
        val connection = url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.readTimeout = 5000
            connection.connectTimeout = 5000

            val jsonRequest = AuthParser.loginRequest(request)
            connection.outputStream.use {
                it.write(jsonRequest.toByteArray())
            }

            val stream = if (connection.responseCode in 200 .. 209) {
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

    fun getMe(token: String) : Response {
        val link = "$AuthLinkAPI/Me"
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
            Log.d("Get Me Response", response)
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