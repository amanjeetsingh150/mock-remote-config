import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import data.Condition

class GetParamsValueMap {

    fun invoke(remoteConfigJson: String, condition: Condition): Map<String, String> {
        val remoteConfigJsonElement = JsonParser.parseString(remoteConfigJson)
        val params = remoteConfigJsonElement.asJsonObject.get("parameters").asJsonObject.keySet()
        return params.associateWith { param ->
            val parameterJson = remoteConfigJsonElement
                .asJsonObject.get("parameters")
                .asJsonObject.get(param)
                .asJsonObject
            val hasConditions = parameterJson.has("conditionalValues")
            val value = if (hasConditions) {
                getResolvedValue(parameterJson, condition)
            } else {
                parameterJson.get("defaultValue").asJsonObject.get("value").asString
            }
            value
        }
    }

    private fun getResolvedValue(parameterJson: JsonObject, condition: Condition): String {
        val hasGivenCondition = parameterJson.get("conditionalValues")
            .asJsonObject.keySet().any { it == condition.name }
        return if (hasGivenCondition) {
            parameterJson.get("conditionalValues")
                .asJsonObject.get(condition.name).asJsonObject
                .get("value").asString
        } else {
            parameterJson.get("defaultValue").asJsonObject.get("value").asString
        }
    }


}