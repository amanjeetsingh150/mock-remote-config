import com.google.gson.JsonParser
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.File

class GetRemoteConfigTemplate(private val auth: Auth, private val secretsFilePath: String) {

    fun invoke(): String {
        val secrets = String(File(secretsFilePath).inputStream().readBytes())
        val projectId = JsonParser.parseString(secrets).asJsonObject.get("project_id").asString
        val httpClient = HttpClients.custom().build()
        val request = RequestBuilder.get()
            .setUri(BASE_URL + REMOTE_CONFIG_ENDPOINT.format(projectId))
            .setHeader("Authorization", "Bearer ${auth.getAccessToken()}")
            .setHeader("Content-Type", "application/json; UTF-8")
            .build()
        val entity = httpClient.execute(request).entity
        return EntityUtils.toString(entity, "UTF-8")
    }

    companion object {
        private const val BASE_URL = "https://firebaseremoteconfig.googleapis.com"
        private const val REMOTE_CONFIG_ENDPOINT = "/v1/projects/%s/remoteConfig"
    }
}
