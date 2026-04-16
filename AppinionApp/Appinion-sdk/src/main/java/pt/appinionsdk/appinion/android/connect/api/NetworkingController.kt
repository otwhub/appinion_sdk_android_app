package pt.appinionsdk.appinion.android.connect.api

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import pt.appinionsdk.appinion.android.connect.client.Client


internal class NetworkingController {
    private val client = Client.client

    suspend inline fun <reified R> get(url: String, headers: Map<String, String>): Result<R> {
        return try {
            val response = client.get(urlString = url) {
                makeHeader(headers)
            }
            if (response.status.value in 200..299) {
                val resp: R = response.body()
                Result.success(resp)
            } else {
                Result.failure(exception = Exception("ERRO ${response.status.value}"))
            }
        } catch (cause: Exception) {
            return Result.failure(exception = Exception(cause.message))
        }
    }

    suspend inline fun <reified B, reified R> post(
        url: String,
        body: B?,
        headers: Map<String, String>
    ): Result<R?> {
        return try {
            val response = client.post(url) {
                makeHeader(headers)
                setBody(body)
            }
            if (response.status.value in 200..299) {
                val resp: R = response.body()
                Result.success(resp)
            } else {
                Result.failure(exception = Exception("ERRO ${response.status.value}"))
            }
        } catch (cause: Throwable) {
            return Result.failure(exception = Exception(cause.message))
        }
    }

    private fun HttpRequestBuilder.makeHeader(headers: Map<String, String>) {
        contentType(ContentType.Application.Json)
        headers.forEach { (key, value) ->
            header(key, value)
        }
    }
}