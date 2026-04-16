package pt.appinionsdk.appinion.android.connect.api.endpoint

import android.net.Uri


enum class ENV{
    PROD,
    DEV
}

private val _ENV = ENV.PROD
enum class Scheme(val value: String) {
    SAFE("https"),
    NOT_SAFE("http")
}

enum class Host(val value: String) {
    DEV("192.168.1.20"),
    PROD("revault-api.herokuapp.com")
}

class Endpoint<T>(
    val path: String,
    val queryItems: Map<String, String>? = null, // Usamos Map para pares chave-valor
    val body: T? = null
) {
    val url: String
        get() {
            val builder = when(_ENV){
                ENV.PROD -> Uri.Builder()
                    .scheme(Scheme.SAFE.value)
                    .encodedAuthority(Host.PROD.value)
                    .path(path)
                ENV.DEV -> Uri.Builder()
                    .scheme(Scheme.NOT_SAFE.value)
                    .encodedAuthority("${Host.DEV.value}:8080")
                    .path(path)
            }
            queryItems?.forEach { (key, value) ->
                builder.appendQueryParameter(key, value)
            }

            val builtUrl = builder.build().toString()
            if (builtUrl.isEmpty()) {
                error("Invalid url components for path: $path")
            }

            return builtUrl
        }

    companion object
}


