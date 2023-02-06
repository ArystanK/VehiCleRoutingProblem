import kotlin.random.Random

fun <T> List<T>.randomList(): List<T> = List(Random.nextInt()) { get(Random.nextInt(size)) }
