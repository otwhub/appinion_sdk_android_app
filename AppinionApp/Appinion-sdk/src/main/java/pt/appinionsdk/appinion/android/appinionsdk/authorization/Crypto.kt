package pt.appinionsdk.appinion.android.appinionsdk.authorization

import java.util.Date

internal class Crypto {
    companion object{
        fun crypton(appID: String, appSecret: String): String {
            /*
            <appID>/<appSecret>/<timestamp>
             */
            val timestamp = (Date().time/1000).toString()
            val info = "$appID/$appSecret/$timestamp"
            return RSA.encryptToSend(info, PUBLIC_KEY) ?: ""
        }
    }
}