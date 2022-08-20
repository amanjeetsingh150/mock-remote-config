import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dadb.Dadb
import data.Condition
import java.io.File

class CreateActivatedRemoteConfig(private val appId: String) {

    fun invoke(condition: Condition, paramsValueMap: Map<String, String>) {
        val activatedRemoteConfigFile = File.createTempFile("remote_config_activated", ".json")
        val dadb = Dadb.create("localhost", 5555)
        val fileName = dadb.shell("ls /data/data/$appId/files | grep _activate.json")
            .allOutput.trimEnd{ it == '\n' }
        dadb.pull(activatedRemoteConfigFile, "/data/data/$appId/files/$fileName")
        val activatedRemoteConfigJson = JsonParser.parseString(String(activatedRemoteConfigFile.readBytes()))
        val jsonObject = JsonObject()
        paramsValueMap.forEach { (param, value) ->
            jsonObject.addProperty(param, value)
        }
        activatedRemoteConfigJson.asJsonObject.add("configs_key", jsonObject)
        File("./mocked_configs").mkdir()
        File("./mocked_configs/${condition.name}").mkdir()
        File("./mocked_configs/${condition.name}/$fileName")
            .writeBytes(activatedRemoteConfigJson.asJsonObject.toString().toByteArray())
    }
}