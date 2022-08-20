import com.google.auth.oauth2.GoogleCredentials
import java.io.File

class Auth {

    fun getAccessToken(): String? {
        val googleCredentials = GoogleCredentials.fromStream(File("./service_account.json").inputStream())
            .createScoped(listOf(REMOTE_CONFIG_SCOPE))
        googleCredentials.refreshIfExpired()
        return googleCredentials.accessToken.tokenValue
    }

    companion object {
        private const val REMOTE_CONFIG_SCOPE = "https://www.googleapis.com/auth/firebase.remoteconfig"
    }
}