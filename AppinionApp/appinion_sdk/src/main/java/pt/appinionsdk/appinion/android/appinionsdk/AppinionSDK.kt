package pt.appinionsdk.appinion.android.appinionsdk

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pt.appinionsdk.appinion.android.appinionsdk.authorization.Crypto
import pt.appinionsdk.appinion.android.connect.services.authentication.AuthorizationService
import pt.appinionsdk.appinion.android.connect.model.Configuration

internal class AppinionSDK(private val configuration: Configuration?) {
    init {
        val authorizationService = AuthorizationService()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            configuration?.let {
                authorizationService.authorize(
                    app = configuration.appID,
                    key = configuration.clientID,
                    secret = configuration.clientSecret,
                    scope = configuration.scope,
                    completionHandler = { authorization ->
                        authorization?.let {
                            AuthorizationData.authorizationData = it
                            AuthorizationData.authorizationData?.app = configuration.appID
                        } ?: run {
                            AuthorizationData.authorizationData = null
                            Log.e("AppinionSDK", "Authentication Erro")
                        }
                    }
                )
            } ?: run {
                AuthorizationData.authorizationData = null
                Log.e("AppinionSDK", "Authentication Erro")
            }
        }
    }
}
