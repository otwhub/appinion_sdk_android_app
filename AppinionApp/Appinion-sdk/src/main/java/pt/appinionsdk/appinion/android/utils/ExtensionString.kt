package pt.appinionsdk.appinion.android.utils

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.util.Date
import java.time.Instant
import java.nio.charset.StandardCharsets

// 1. Decode Base64
fun String.fromBase64(): String? {
    return try {
        val data = Base64.decode(this, Base64.DEFAULT)
        String(data, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}

// 2. Encode Base64
fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
}

// 3. String para Date (ISO8601)
@RequiresApi(Build.VERSION_CODES.O)
fun String.toDate(): Date {
    return try {
        // Para Android API 26+ (O) - Se o teu minSDK for menor, precisas de desugaring
        val instant = Instant.parse(this)
        Date.from(instant)
    } catch (e: Exception) {
        Date() // Fallback para a data atual se falhar, como no teu Swift
    }
}