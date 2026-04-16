package pt.appinionsdk.appinion.android.appinionsdk

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import android.content.pm.PackageManager
import pt.appinionsdk.appinion.android.connect.model.Configuration

internal class SdkInitializer : Initializer<Unit> {
    internal val CLIENTID = "pt.appinionsdk.appinion.android.CLIENTID"
    internal val CLIENTSECRET = "pt.appinionsdk.appinion.android.CLIENTSECRET"
    internal val APPID = "pt.appinionsdk.appinion.android.APPID"
    internal val SCOPE = "pt.appinionsdk.appinion.android.SCOPE"
    internal val DEBUG = "pt.appinionsdk.appinion.android.DEBUG"

    override fun create(context: Context) {
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            val clientID: String? = appInfo.metaData.getString(CLIENTID)
            val clientSecret: String? = appInfo.metaData.getString(CLIENTSECRET)
            val appID: String? = appInfo.metaData.getString(APPID)
            val scope: String? = appInfo.metaData.getString(SCOPE)
            val debug: Boolean = appInfo.metaData.getBoolean(DEBUG)

            if (clientID == null || clientSecret == null || appID == null || scope == null) {
                throw Exception("Erro metadados on Manifest\n${CLIENTID}\n${CLIENTSECRET}\n${APPID}\n${SCOPE}")
            } else {
                val configuration = Configuration(
                    clientID = clientID,
                    clientSecret = clientSecret,
                    appID = appID,
                    scope = scope,
                    debug = debug
                )
                AppinionSDK(configuration)
            }
        } catch (e: Exception) {
            Log.e("AppinionSDK","___________________________________________",  e)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}