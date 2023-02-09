import kotlin.random.Random

fun <T> List<T>.randomList(): List<T> {
    val size = Random.nextInt(456, 456 * 5)
    return List(size) { get(it % 456) }
}
