import org.junit.Test
import kotlin.test.assertEquals

internal class PersonTest {

    @Test
    fun testMapper() {
        val person = Person.Base(0, "b", "c")
        val personTwo = Person.Base(0, "b", "c")
        val actual = person.map(Person.Mapper.SameContent(personTwo))
        val expected = true
        assertEquals(expected, actual)
    }
}
