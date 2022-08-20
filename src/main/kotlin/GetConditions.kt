import com.google.gson.JsonElement
import com.google.gson.JsonParser
import data.Condition

class GetConditions {

    fun invoke(remoteConfigJson: String): List<Condition> {
        val remoteConfigJsonElement = JsonParser.parseString(remoteConfigJson)
        return getConditions(remoteConfigJsonElement)
    }

    private fun getConditions(remoteConfigJsonElement: JsonElement): List<Condition> {
        val conditionsJson = remoteConfigJsonElement.asJsonObject.get("conditions").asJsonArray
        val conditions = conditionsJson.map {
            val name = it.asJsonObject.get("name").asString
            val expression = it.asJsonObject.get("expression").asString
            val tagColor = it.asJsonObject.get("tagColor").asString
            Condition(name, expression, tagColor)
        }
        return conditions
    }
}