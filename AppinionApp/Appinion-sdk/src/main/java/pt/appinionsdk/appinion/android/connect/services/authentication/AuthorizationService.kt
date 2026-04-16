package pt.appinionsdk.appinion.android.connect.services.authentication

import android.util.Log
import pt.appinionsdk.appinion.android.appinionsdk.authorization.Crypto
import pt.appinionsdk.appinion.android.connect.api.NetworkingController
import pt.appinionsdk.appinion.android.connect.api.endpoint.Endpoint
import pt.appinionsdk.appinion.android.connect.api.endpoint.authorize
import pt.appinionsdk.appinion.android.connect.model.Authorization
import pt.appinionsdk.appinion.android.connect.model.AuthorizationResponse
import pt.appinionsdk.appinion.android.utils.toBase64

class AuthorizationService {
    private val networkingController: NetworkingController = NetworkingController()

    suspend fun authorize(
        app: String,
        key: String,
        secret: String,
        scope: String,
        completionHandler: (authorization: AuthorizationResponse?) -> Unit
    ) {
        val headers: MutableMap<String, String> = mutableMapOf()

        val info = Crypto.crypton(key, secret)

        headers["Authorization"] = info
        val endpoint: Endpoint<Authorization> =
            Endpoint.authorize(app = app, key = key, secret = secret, scope = scope)

        val response = networkingController.post<Authorization, AuthorizationResponse>(
            url = endpoint.url,
            body = endpoint.body,
            headers = headers
        )

        response.onSuccess { authorization ->
            completionHandler(authorization)
        }
        response.onFailure {
            completionHandler(null)
        }
    }
}
