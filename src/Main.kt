import kotlin.math.min

class Tiempo(var hora: Int, var min: Int, var seg: Int) {

    constructor(hora: Int) : this(hora, 0, 0)
    constructor(hora: Int, min: Int) : this(hora, min, 0)

    init {
        require(min >= 0) { "Min debe ser positivo o cero!" }
        require(seg >= 0) { "Seg debe ser positivo o cero!" }
        require(hora >= 0) { "Hora debe ser positivo o cero!" }

        if (seg > 59) {
            min += (seg / 60)
            seg %= 60
        }

        if (min > 59) {
            hora += (min / 60)
            min %= 60
        }

        require((hora in 0..23) || (hora == 24 && min == 0 && seg == 0)) { "Hora no válida (máx 24:00:00)" }
    }

    fun incrementar(t: Tiempo): Boolean {
        var horaFinal = hora + t.hora
        var minFinal = min + t.min
        var segFinal = seg + t.seg

        if (segFinal > 59) {
            minFinal += (segFinal / 60)
            segFinal %= 60
        }

        if (minFinal > 59) {
            horaFinal += (minFinal / 60)
            minFinal %= 60
        }

        return if (horaFinal > 24 || (horaFinal == 24 && (minFinal > 0 || segFinal > 0))) {
            false
        } else {
            hora = horaFinal
            min = minFinal
            seg = segFinal
            true
        }
    }

    fun decrementar(t: Tiempo): Boolean {
        val totalSegundos = aSegundos() - t.aSegundos()
        return if (totalSegundos >= 0) {
            val nuevoTiempo = desdeSegundos(totalSegundos)
            hora = nuevoTiempo.hora
            min = nuevoTiempo.min
            seg = nuevoTiempo.seg
            true
        } else {
            false
        }
    }

    fun comparar(t: Tiempo): Int = this.aSegundos().compareTo(t.aSegundos())

    fun copiar(): Tiempo = Tiempo(hora, min, seg)

    fun copiar(t: Tiempo) {
        hora = t.hora
        min = t.min
        seg = t.seg
    }

    fun sumar(t: Tiempo): Tiempo? {
        val nuevoTiempo = Tiempo(hora + t.hora, min + t.min, seg + t.seg)
        return if (nuevoTiempo.hora < 24 || (nuevoTiempo.hora == 24 && nuevoTiempo.min == 0 && nuevoTiempo.seg == 0)) nuevoTiempo else null
    }

    fun restar(t: Tiempo): Tiempo? {
        val totalSegundos = aSegundos() - t.aSegundos()
        return if (totalSegundos >= 0) desdeSegundos(totalSegundos) else null
    }

    fun esMayorQue(t: Tiempo): Boolean = this.aSegundos() > t.aSegundos()

    fun esMenorQue(t: Tiempo): Boolean = this.aSegundos() < t.aSegundos()

    private fun aSegundos(): Int = hora * 3600 + min * 60 + seg

    private fun desdeSegundos(segundos: Int): Tiempo {
        val h = segundos / 3600
        val m = (segundos % 3600) / 60
        val s = segundos % 60
        return Tiempo(h, m, s)
    }

    override fun toString(): String {
        return "${"%02d".format(hora)}h ${"%02d".format(min)}m ${"%02d".format(seg)}s"
    }
}


fun pedirTiempo(msj: String): Int {
    print("$msj: ")
    return readLine()?.toIntOrNull() ?: 0
}

fun main() {
    val hora = pedirTiempo("Introduzca la hora")
    val min = pedirTiempo("Introduzca el minuto")
    val seg = pedirTiempo("Introduzca los segundos")

    val tiempo1 = Tiempo(hora, min, seg)
    val tiempo2 = Tiempo(23, 46, 48)

    if (tiempo1.incrementar(tiempo2)) {
        println("Tiempo actualizado: $tiempo1")
    } else {
        println("No se pudo incrementar, supera las 24 horas.")
    }
}
