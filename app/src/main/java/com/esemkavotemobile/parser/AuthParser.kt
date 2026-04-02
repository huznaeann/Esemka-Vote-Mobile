package com.esemkavotemobile.parser

import com.esemkavotemobile.model.Employee
import com.esemkavotemobile.model.LoginRequest
import com.esemkavotemobile.model.LoginResponse
import com.esemkavotemobile.model.Response
import org.json.JSONObject

object AuthParser {
    fun loginRequest(request: LoginRequest) : String {
        val json = JSONObject()
        json.put("email", request.email)
        json.put("password", request.password)

        return json.toString()
    }

    fun loginResponse(response: Response) : LoginResponse {
        val json = JSONObject(response.data)
        return LoginResponse(
            token = json.getString("token")
        )
    }

    fun getMe(response: Response) : Employee {
        val json = JSONObject(response.data)
        return Employee(
            id = json.getInt("id"),
            name = json.getString("name"),
            email = json.getString("email"),
            photo = json.getString("photo"),
            division = json.getString("division")
        )
    }
}