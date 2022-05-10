package pimienta.julian.mydigimind

import java.io.Serializable

data class Recordatorio( var dias: String,var tiempo: String,var nombre: String) : Serializable
data class RecordatorioFB(val id: String,var lu:Boolean,var ma:Boolean,var mi:Boolean,var ju:Boolean,var vi:Boolean,var sa:Boolean,var dom :Boolean,var tiempo: String,var nombre: String) : Serializable