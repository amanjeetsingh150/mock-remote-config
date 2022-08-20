import com.google.common.truth.Truth.assertThat
import data.Condition
import org.junit.jupiter.api.Test
import java.io.File

class GetParamsValueMapTest {

    @Test
    fun `it picks default value for params where condition is not present`() {
        // given
        val json = String(File("./src/test/resources/template.json").readBytes())
        val condition = Condition(
            name = "Bar_Feature_4%",
            expression = "percent \u003c= 4 && app.id == '1:72203376800:android:0b97fbed5421afa16e51c5'",
            tagColor = "GREEN"
        )

        // when
        val paramsValueMap = GetParamsValueMap().invoke(json, condition)

        // then
        val value = paramsValueMap["foo"]
        assertThat(value).isEqualTo("1")
    }

    @Test
    fun `it picks conditional value when given condition is present`() {
        // given
        val json = String(File("./src/test/resources/template.json").readBytes())
        val condition = Condition(
            name = "Bar_Feature_4%",
            expression = "percent \u003c= 4 && app.id == '1:72203376800:android:0b97fbed5421afa16e51c5'",
            tagColor = "GREEN"
        )

        // when
        val paramsValueMap = GetParamsValueMap().invoke(json, condition)

        // then
        val value = paramsValueMap["bar"]
        assertThat(value).isEqualTo("true")
    }

    @Test
    fun `it picks default value when given condition is not present`() {
        // given
        val json = String(File("./src/test/resources/template.json").readBytes())
        val condition = Condition(
            name = "Baz_Feature_4%",
            expression = "percent \u003c= 4 && app.id == '1:72203376800:android:0b97fbed5421afa16e51c5'",
            tagColor = "GREEN"
        )

        // when
        val paramsValueMap = GetParamsValueMap().invoke(json, condition)

        // then
        val value = paramsValueMap["bar"]
        assertThat(value).isEqualTo("false")
    }
}