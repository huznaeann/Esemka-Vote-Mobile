package com.esemkavotemobile.parser

import com.esemkavotemobile.model.Response
import org.json.JSONObject

object ResponseParser {
    fun jsonToResponse(stringResponse: String) : Response {
        val jsonResponse = JSONObject(stringResponse)
        return Response(
            message = jsonResponse.getString("message"),
            data = jsonResponse.getString("data")
        )
    }
}