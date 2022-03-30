package pimienta.julian.mydigimind.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pimienta.julian.mydigimind.R
import pimienta.julian.mydigimind.Recordatorio

class AdaptadorRecordatorio: BaseAdapter {
    lateinit var context: Context
    var reminders: ArrayList<Recordatorio> = ArrayList<Recordatorio>()

    constructor(context: Context,recordatorio: ArrayList<Recordatorio>){
        this.context = context
        this.reminders = recordatorio
    }

    override fun getCount(): Int {
        return  reminders.size
    }

    override fun getItem(p0: Int): Any {
        return reminders[p0]
    }

    override fun getItemId(p0: Int): Long {
      return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.recordatorio,null)
        var reminder = reminders[p0]

        val tv_titulo: TextView = vista.findViewById(R.id.txtNombreRecordatorio)
        val tv_time: TextView = vista.findViewById(R.id.textTiempoRecordatorio)
        val tv_dia: TextView = vista.findViewById(R.id.textDiasRecordatorio)

        tv_titulo.setText(reminder.nombre)
        tv_time.setText(reminder.tiempo)
        tv_dia.setText(reminder.dias)


        return vista

    }


}