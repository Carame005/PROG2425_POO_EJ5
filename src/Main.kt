import kotlin.math.min

class Tiempo(var hora: Int, var min : Int, var seg : Int){

    constructor(hora: Int) : this(hora,  0, 0)

    constructor(hora : Int,min : Int) : this(hora, min, 0)

    init{
        require(min >= 0){"Min dee ser positivo o cero!"}
        require(seg >= 0){"Seg dee ser positivo o cero!"}
        require(hora >= 0){"Hora dee ser positivo o cero!"}

        if (seg > 59){
            min += (seg / 60)
            seg %= 60
        }

        if (min > 59){
            hora += (min / 60)
            min %= 60
        }
        require((hora in 0..23) || (hora == 24 && min == 0 && seg == 0)){"Hora no válida (max 24:00:00)"}
    }

    fun incrementar(t : Tiempo): Boolean{
        var horaFinal = hora + t.hora
        var minFinal = min + t.min
        var segFinal = seg + t.seg

        if (segFinal > 59){
            minFinal += (segFinal / 60)
            segFinal %= 60
        }

        if (minFinal > 59){
            horaFinal += (minFinal / 60)
            minFinal %= 60
        }

        if (horaFinal >= 24){
            return false
        }else{
            hora = horaFinal
            min = minFinal
            seg = segFinal
            return true
        }

    }

    override fun toString(): String {
        return "${"%02d".format(hora)}h ${"%02d".format(min)}m ${"%02d".format(seg)}s"
    }
}




fun main(){

    val hora = pedirTiempo("Introduzca la hora")
    val min = pedirTiempo("Introduzca el min")
    val seg = pedirTiempo("Introduzca el seg")

    val tiempo1 = Tiempo(hora, min, seg)

    val tiempo2 = Tiempo(23, 46, 48)

    tiempo1.incrementar(tiempo2)
}
