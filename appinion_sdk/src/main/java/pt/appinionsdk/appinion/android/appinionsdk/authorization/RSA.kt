package pt.appinionsdk.appinion.android.appinionsdk.authorization

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

internal class RSA {
    companion object {
        //==================================================================================================
        //                                     *** SECURITY ***
        //==================================================================================================

        fun encryptToSend(data: String, publicKey: String?): String? {

            try {
                publicKey?.let {
                    val pk = loadPublicKeyFromPEM(it)
                    val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
                    cipher.init(Cipher.ENCRYPT_MODE, pk)
                    val encryptedBytes = cipher.doFinal(data.replace("\n", "").trim().toByteArray())
                    val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
                    return encryptedBase64
                }
                return null
            } catch (e: Exception) {
                println("Erro durante a criptografia: ${e.message}")
                e.printStackTrace()
                return null
            }

        }

        fun loadPublicKeyFromPEM(pemKey: String): java.security.PublicKey {
            // Remove headers e footers PEM
            val publicKeyPEM = pemKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "")
                .trim()

            // Decodifica o conteúdo Base64
            val encoded = Base64.decode(publicKeyPEM, Base64.DEFAULT)
            // Cria a spec X509 para a chave pública
            val keySpec = X509EncodedKeySpec(encoded)
            // Gera a chave pública
            val keyFactory = KeyFactory.getInstance("RSA")
            return keyFactory.generatePublic(keySpec)
        }
    }
}
