package pimienta.julian.mydigimind

import java.io.Serializable

class Carrito : Serializable {

    var recordatorios = ArrayList<Recordatorio>()

    fun agregar(p: Recordatorio) : Boolean{
        return recordatorios.add(p)
    }

    fun remove(p: Recordatorio) : Boolean{
        return recordatorios.remove(p)
    }
}