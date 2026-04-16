package pt.appinionsdk.appinion.android.connect.model

/**
Initializes a new configuration with the provided specifications.

- Parameters:
- clientID: Your client ID. This information is available uppon registration.
- clientSecret: Your client Secret. This information is available uppon registration
- appID: Your App ID. This information is available on your Feedback pannel on appinion.
- scope: The scope for the SDK usage. String separated by ',' for more than one scope.
-  debug: **true** if you want to print console logs. Default as **false**

- Returns: A new SDK configuration.

 */
class Configuration( val clientID: String,
                    val clientSecret: String,
                    val appID: String,
                    val scope: String,
                    val debug: Boolean = false)