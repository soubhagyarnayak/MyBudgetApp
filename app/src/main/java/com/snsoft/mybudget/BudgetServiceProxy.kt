package com.snsoft.mybudget

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class BudgetServiceProxy {

    public fun postExpense(expenseEntry: ExpenseEntry){

        var client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonContent = getPostExpenseBody(expenseEntry)
        val body: RequestBody = jsonContent.toRequestBody(mediaType)

        val request: Request = Request.Builder()
                .url("http://random.ngrok.io/api/expense")
                .post(body)
                .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body!!.string()

        //Response
        println("Response Body: " + responseBody)
    }

    private fun getPostExpenseBody(expenseEntry: ExpenseEntry):String{
        return Gson().toJson(expenseEntry)
    }
}