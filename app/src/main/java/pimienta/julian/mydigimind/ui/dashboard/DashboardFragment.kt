package pimienta.julian.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import pimienta.julian.mydigimind.R
import pimienta.julian.mydigimind.Recordatorio
import pimienta.julian.mydigimind.RecordatorioFB
import pimienta.julian.mydigimind.databinding.FragmentDashboardBinding
import pimienta.julian.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment() {

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private lateinit var dashboardViewModel: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       dashboardViewModel =
           ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard,container,false)
        dashboardViewModel.text.observe(viewLifecycleOwner,{})

        auth = FirebaseAuth.getInstance()


        val btnTime = root.findViewById(R.id.btn_time) as Button
        val btnSave = root.findViewById(R.id.btn_save) as Button


        btnTime.setOnClickListener {
            set_time(root)
        }

        btnSave.setOnClickListener {
          guardar(root)
        }


        return root
    }


    fun guardar(view: View){

        val name = view.findViewById(R.id.etTask) as EditText
        var titulo = name.getText().toString()

        val time = view.findViewById(R.id.btn_time) as Button
        var tiempo : String = time.getText().toString()


        val Mon = view.findViewById(R.id.Mon) as CheckBox
        val Tues = view.findViewById(R.id.Tues) as CheckBox
        val Wed = view.findViewById(R.id.Wed) as CheckBox
        val Thur = view.findViewById(R.id.Thur) as CheckBox
        val Fri = view.findViewById(R.id.Fri) as CheckBox
        val Sat = view.findViewById(R.id.Sat) as CheckBox
        val Sun = view.findViewById(R.id.Sun) as CheckBox
        var mon = false
        var tue = false
        var wed = false
        var thu = false
        var fri = false
        var sat = false
        var sun = false
        var dia: String = ""


        if (Mon.isChecked) {
            if (dia != "") dia += getString(R.string.Monday)

            dia = getString(R.string.Monday)
            mon = true
        }
        if (Tues.isChecked){
            if (dia != "") dia += getString(R.string.Tuesday)
            dia = getString(R.string.Tuesday)
            tue = true
        }
        if (Wed.isChecked){
            if (dia != "") dia += getString(R.string.Wednesday)
            dia = getString(R.string.Wednesday)
            wed = true
        }
        if (Thur.isChecked){
            if (dia != "") dia += getString(R.string.Thursday)
            dia = getString(R.string.Thursday)
            thu = true
        }
        if (Fri.isChecked){
            if (dia != "") dia += getString(R.string.Friday)
            dia = getString(R.string.Friday)
            fri = true
        }
        if (Sat.isChecked){
            if (dia != "") dia += getString(R.string.Saturday)
            dia = getString(R.string.Saturday)
            sat = true
        }
        if (Sun.isChecked){
            if (dia != "") dia += getString(R.string.Sunday)
            dia = getString(R.string.Sunday)
            sun = true
        }

        if (Mon.isChecked && Tues.isChecked && Wed.isChecked && Thur.isChecked && Fri.isChecked && Sat.isChecked && Sun.isChecked){
            dia = "Everyday"
            mon = true
            tue = true
            wed = true
            thu = true
            fri = true
            sat = true
            sun = true
        }

        val reminder = hashMapOf(
            "actividad" to titulo,
            "lu" to mon,
            "ma" to tue,
            "mi" to wed,
            "ju" to thu,
            "vi" to fri,
            "sa" to sat,
            "do" to sun,
            "tiempo" to tiempo
        )

        db.collection("actividades")
            .add(reminder)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


        var tareaCar = Recordatorio(dia,tiempo,titulo)

        HomeFragment.carrito.agregar(tareaCar)
        Toast.makeText(context,"The reminder was added",Toast.LENGTH_SHORT).show()

       // guardar_json()
    }



//    fun guardar_json(){
//        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
//        val editor = preferencias?.edit()
//        val gson : Gson = Gson()
//
//        var json = gson.toJson(HomeFragment.carrito)
//
//        editor?.putString("Reminders",json)
//
//        editor?.apply()
//
//    }

    fun set_time(view: View){
       val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{timerPicker, hour, minute->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)

            val btnTime = view.findViewById(R.id.btn_time) as Button

           btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}