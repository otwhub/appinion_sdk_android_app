package pt.appinionsdk.appinion.android.connect.model

enum class AppinionError {
    errorParsingInput,
    invalidUrl,
    emptyData,
    invalidServerResponse,
    expiredSubscription
}