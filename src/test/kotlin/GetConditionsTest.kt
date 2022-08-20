import com.google.common.truth.Truth.assertThat
import data.Condition
import org.junit.jupiter.api.Test
import java.io.File

class GetConditionsTest {

    @Test
    fun `it gets all conditions from remote config json`() {
        // given
        val json = String(File("./src/test/resources/template.json").readBytes())

        // when
        val conditionList = GetConditions().invoke(json)

        // then
        assertThat(conditionList).containsExactly(
            Condition(
                name = "Bar_Feature_4%",
                expression = "percent \u003c= 4 && app.id == '1:72203376800:android:0b97fbed5421afa16e51c5'",
                tagColor = "GREEN"
            )
        )
    }
}