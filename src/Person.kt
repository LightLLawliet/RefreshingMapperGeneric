interface Person {

    fun <T> map(mapper: Mapper<T>): T

    class Base(private val id: Int, private val name: String, private val surname: String) : Person {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, surname)
    }

    interface Mapper<T> {
        fun map(id: Int, name: String, surname: String): T

        class CompareId(private val id: Int) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean = this.id == id
        }

        class Same(private val person: Person) : Mapper<Boolean> {
            override fun map(id: Int, name: String, surname: String): Boolean = person.map(CompareId(id))
        }

        class SaveId(private val idContainer: Save<Int>) : Mapper<Unit> {
            override fun map(id: Int, name: String, surname: String) {
                idContainer.save(id)
            }
        }

        class FullName() : Mapper<String> {
            override fun map(id: Int, name: String, surname: String): String {
                return "$name $surname"
            }
        }
    }
}

interface Save<T> {
    fun save(data: T)
}

interface Read {
    fun read(idConsumer: IdConsumer)
}

interface IdConsumer {
    fun useId(id: Int)
}

class SavePersonId : Save<Int>, Read {
    private var id: Int = 0
    override fun save(data: Int) {
        id = data
    }

    override fun read(idConsumer: IdConsumer) = idConsumer.useId(id)
}

fun main() {
    val person = Person.Base(10, "John", "Big")
    val personTwo = Person.Base(2, "Jim", "Little")
    val result = person.map(Person.Mapper.Same(personTwo))
    println(result)

    val showId = object : IdConsumer {
        override fun useId(id: Int) {
            println(id)
        }
    }

    val save = SavePersonId()

    person.map(Person.Mapper.SaveId(save))

    save.read(showId)

    println(person.map(Person.Mapper.FullName()))
    println(personTwo.map(Person.Mapper.FullName()))
}