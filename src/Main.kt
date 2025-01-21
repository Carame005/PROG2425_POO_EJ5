class Tiempo(private var hora: Int = 0, private var minuto: Int = 0, private var segundo: Int = 0) {
    init {
        if (hora !in 0..23) throw IllegalArgumentException("La hora debe estar entre 0 y 23.")
        normalizarTiempo()
    }

    private fun normalizarTiempo() {
        minuto += segundo / 60
        segundo %= 60
        hora += minuto / 60
        minuto %= 60
        if (hora >= 24) throw IllegalArgumentException("La hora no puede ser mayor que 23.")
    }

    override fun toString(): String {
        return "%02dh %02dm %02ds".format(hora, minuto, segundo)
    }

    fun incrementar(t: Tiempo): Boolean {
        val nuevoTiempo = Tiempo(hora + t.hora, minuto + t.minuto, segundo + t.segundo)
        return if (nuevoTiempo.hora < 24) {
            hora = nuevoTiempo.hora
            minuto = nuevoTiempo.minuto
            segundo = nuevoTiempo.segundo
            true
        } else {
            false
        }
    }

    fun decrementar(t: Tiempo): Boolean {
        val totalSegundos = aSegundos() - t.aSegundos()
        return if (totalSegundos >= 0) {
            val nuevoTiempo = desdeSegundos(totalSegundos)
            hora = nuevoTiempo.hora
            minuto = nuevoTiempo.minuto
            segundo = nuevoTiempo.segundo
            true
        } else {
            false
        }
    }

    fun comparar(t: Tiempo): Int {
        return this.aSegundos().compareTo(t.aSegundos())
    }

    fun copiar(): Tiempo {
        return Tiempo(hora, minuto, segundo)
    }

    fun copiar(t: Tiempo) {
        hora = t.hora
        minuto = t.minuto
        segundo = t.segundo
    }

    fun sumar(t: Tiempo): Tiempo? {
        val nuevoTiempo = Tiempo(hora + t.hora, minuto + t.minuto, segundo + t.segundo)
        return if (nuevoTiempo.hora < 24) nuevoTiempo else null
    }

    fun restar(t: Tiempo): Tiempo? {
        val totalSegundos = aSegundos() - t.aSegundos()
        return if (totalSegundos >= 0) desdeSegundos(totalSegundos) else null
    }

    fun esMayorQue(t: Tiempo): Boolean {
        return this.aSegundos() > t.aSegundos()
    }

    fun esMenorQue(t: Tiempo): Boolean {
        return this.aSegundos() < t.aSegundos()
    }

    private fun aSegundos(): Int {
        return hora * 3600 + minuto * 60 + segundo
    }

    private fun desdeSegundos(segundos: Int): Tiempo {
        val h = segundos / 3600
        val m = (segundos % 3600) / 60
        val s = segundos % 60
        return Tiempo(h, m, s)
    }
}

fun main() {
    println("Introduce la hora, minuto y segundo (puedes omitir algunos valores):")
    val entrada = readln().split(" ").map { it.toIntOrNull() ?: 0 }
    val tiempo = when (entrada.size) {
        3 -> Tiempo(entrada[0], entrada[1], entrada[2])
        2 -> Tiempo(entrada[0], entrada[1])
        1 -> Tiempo(entrada[0])
        else -> Tiempo()
    }
    println("Tiempo ingresado: $tiempo")

    println("Introduce una nueva hora, minuto y segundo para incrementar:")
    val incremento = readln().split(" ").map { it.toIntOrNull() ?: 0 }
    val tIncremento = Tiempo(incremento[0], incremento[1], incremento[2])
    if (tiempo.incrementar(tIncremento)) {
        println("Tiempo incrementado: $tiempo")
    } else {
        println("Error: Se superan las 23:59:59")
    }
}
