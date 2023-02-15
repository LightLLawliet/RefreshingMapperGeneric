interface Person {

    fun <T> map(mapper: Mapper<T>): T

    class Base(private val id: Int, private val name: String, private val surname: String) : Person {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(id, name, surname)
        }
    }

    interface Mapper<T> {
        fun map(id: Int, name: String, surname: String): T
    }
}

fun main() {
    val person = Person.Base(1, "John", "Big")
}