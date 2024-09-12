package com.example.tpn2

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiApp()
        }
    }
}

@Composable
fun MiApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "PantallaPrincipal") {
        composable("PantallaPrincipal") { PantallaPrincipal(navController) }
        composable(route = "guardar/{nombre}/{apellido}/{telefono}/{email}/{direccion}/{fechanacimiento}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("apellido") { type = NavType.StringType },
                navArgument("telefono") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("direccion") { type = NavType.StringType },
                navArgument("fechanacimiento") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val apellido = backStackEntry.arguments?.getString("apellido") ?: ""
            val telefono = backStackEntry.arguments?.getString("telefono") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val direccion = backStackEntry.arguments?.getString("direccion") ?: ""
            val fechanacimiento = backStackEntry.arguments?.getString("fechanacimiento") ?: ""
            PantallaMasDatosContactos(navController, nombre, apellido, telefono, email, direccion, fechanacimiento)
        }
        composable("PantallaAgregarContactos") { PantallaPrincipalPreview() }
        composable("PantallaListadoContactos") { PantallaListadoContactosPreview() }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal_Header(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text("Ejercicio Controles") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Menu"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }  // Cuando se hace clic fuera, cierra el menú
            ) {
                DropdownMenuItem(
                    text = { Text("Agregar contactos") },
                    onClick = {
                            navController.navigate("PantallaAgregarContactos")
                            expanded = false
                        }
                )
                DropdownMenuItem(
                    text = { Text("Listado de contactos") },
                    onClick = {
                        navController.navigate("PantallaListadoContactos")
                        expanded = false
                    }
                )
            }
        }
    )
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview() {
    val navController = rememberNavController()
    PantallaPrincipal(navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaMasDatosContactosPreview() {
    val navController = rememberNavController()
    PantallaMasDatosContactos(
        navController = navController,
        nombre= "",
        apellido= "",
        telefono= "",
        email= "",
        direccion= "",
        fechanacimiento= "")
}



@Suppress("PreviewAnnotationInFunctionWithParameters")
@Composable
fun PantallaPrincipal(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechanacimiento by remember { mutableStateOf("") }


    Scaffold(topBar = {PantallaPrincipal_Header(navController) }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Column(
            ) {
                Row {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "FORMULARIO DE CONTACTO"  // Establece el texto
                                    textSize = 30f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                }
                Row {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "Este es un formulario destinado al registro de personas, sus datos personales, e intereses"  // Establece el texto
                                    textSize = 15f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )

                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                )
                    {

                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "Nombre"  // Establece el texto
                                    textSize = 20f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(bottom = 8.dp)
                        )
                        AndroidView(
                            factory = { context ->
                                EditText(context).apply {
                                    hint = "Nombre"
                                    inputType = android.text.InputType.TYPE_CLASS_TEXT  //
                                    textSize = 20f
                                    setText(nombre)  // Configura el texto inicial
                                    addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                            // Filtrar caracteres no permitidos (solo letras y espacios)
                                            val filteredText = s?.filter { it.isLetter() || it.isWhitespace() }
                                            if (filteredText.toString() != s.toString()) {
                                                this@apply.setText(filteredText)
                                                this@apply.setSelection(filteredText?.length ?: 0)
                                            } else {
                                                nombre = s.toString()
                                            }
                                        }
                                        override fun afterTextChanged(s: Editable?) {}
                                    })
                                }
                            },
                            modifier = Modifier
                                .width(250.dp)
                        )
                }

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    // TextView
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = "Apellido"  // Establece el texto
                                textSize = 20f  // Establece el tamaño del texto
                            }
                        },
                        modifier = Modifier
                            //.weight(1f)
                            .padding(top = 8.dp)
                    )
                    AndroidView(
                        factory = { context ->
                            EditText(context).apply {
                                hint = "Apellido"
                                inputType = android.text.InputType.TYPE_CLASS_TEXT  //
                                textSize = 20f
                                setText(apellido)  // Configura el texto inicial
                                addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        // Filtrar caracteres no permitidos (solo letras y espacios)
                                        val filteredText = s?.filter { it.isLetter() || it.isWhitespace() }
                                        if (filteredText.toString() != s.toString()) {
                                            this@apply.setText(filteredText)
                                            this@apply.setSelection(filteredText?.length ?: 0)
                                        } else {
                                            apellido = s.toString()
                                        }
                                    }
                                    override fun afterTextChanged(s: Editable?) {}
                                })
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                    )


                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    // TextView
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = "Telefono"  // Establece el texto
                                textSize = 20f  // Establece el tamaño del texto
                            }
                        },
                        modifier = Modifier
                            //.weight(1f)
                            .padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    {
                        AndroidView(
                            factory = { context ->
                                EditText(context).apply {
                                    hint = "Telefono"
                                    inputType = android.text.InputType.TYPE_CLASS_PHONE  //
                                    textSize = 20f
                                    setText(telefono)  // Configura el texto inicial
                                    addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(
                                            s: CharSequence?,
                                            start: Int,
                                            count: Int,
                                            after: Int
                                        ) {
                                        }

                                        override fun onTextChanged(
                                            s: CharSequence?,
                                            start: Int,
                                            before: Int,
                                            count: Int
                                        ) {
                                            // Filtrar para que solo permita números y guiones
                                            val filteredText = s?.filter { it.isDigit() || it == '-' }
                                            if (filteredText.toString() != s.toString()) {
                                                this@apply.setText(filteredText)
                                                this@apply.setSelection(filteredText?.length ?: 0)
                                            } else {
                                                telefono = s.toString()
                                            }
                                        }

                                        override fun afterTextChanged(s: Editable?) {}
                                    })
                                }
                            },
                            modifier = Modifier
                                .width(250.dp)
                        )

                        //DROP DOWN LIST O SPINNER
                        AndroidView(
                            factory = { context ->
                                Spinner(context).apply {
                                    val items = listOf("Casa", "Trabajo", "Movil")
                                    val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, items).apply {
                                        setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                                    }
                                    setAdapter(adapter)
                                }
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .wrapContentWidth()
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    // TextView
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = "Email"  // Establece el texto
                                textSize = 20f  // Establece el tamaño del texto
                            }
                        },
                        modifier = Modifier
                            //.weight(1f)
                            .padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    {
                        AndroidView(
                            factory = { context ->
                                EditText(context).apply {
                                    hint = "Email"
                                    inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                                    textSize = 20f
                                    setText(email)  // Configura el texto inicial
                                    addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                            val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                                            val filteredText = s?.filter { !it.isWhitespace() }

                                            if (filteredText.toString() != s.toString()) {
                                                this@apply.setText(filteredText)
                                                this@apply.setSelection(filteredText?.length ?: 0)
                                            } else {
                                                email = s.toString()
                                            }
                                        }

                                        override fun afterTextChanged(s: Editable?) {
                                            val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                                            if (!s.isNullOrEmpty() && !emailPattern.matches(s)) {
                                                this@apply.error = "Formato de correo inválido"
                                            }
                                        }
                                    })
                                }
                            },
                            modifier = Modifier.width(250.dp)
                        )


                        //DROP DOWN LIST O SPINNER
                    AndroidView(
                        factory = { context ->
                            Spinner(context).apply {
                                val items = listOf("Personal", "Laboral")
                                val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, items).apply {
                                    setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                                }
                                setAdapter(adapter)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .wrapContentWidth()
                    )
                    }

                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    // TextView
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = "Direccion"  // Establece el texto
                                textSize = 20f  // Establece el tamaño del texto
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    AndroidView(
                        factory = { context ->
                            EditText(context).apply {
                                hint = "Direccion"
                                inputType = android.text.InputType.TYPE_CLASS_TEXT  //
                                textSize = 20f
                                setText(direccion)  // Configura el texto inicial
                                addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        direccion = s.toString()
                                    }
                                    override fun afterTextChanged(s: Editable?) {}
                                })
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                    )


                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {

                    // TextView
                    AndroidView(
                        factory = { context ->
                            TextView(context).apply {
                                text = "Fecha De Nacimiento"  // Establece el texto
                                textSize = 20f  // Establece el tamaño del texto
                            }
                        },
                        modifier = Modifier
                            //.weight(1f)
                            .padding(top = 8.dp)
                    )
                    AndroidView(
                        factory = { context ->
                            EditText(context).apply {
                                hint = "Fecha Nacimiento"
                                textSize = 20f
                                isFocusable = false
                                isClickable = true
                                inputType = EditorInfo.TYPE_NULL
                                setOnClickListener {
                                    val calendar = Calendar.getInstance()
                                    val datePickerDialog = DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            val selectedDate = Calendar.getInstance().apply {
                                                set(year, month, dayOfMonth)
                                            }.time
                                            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                            fechanacimiento = sdf.format(selectedDate)
                                            setText(fechanacimiento)
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    )

                                    datePickerDialog.datePicker.maxDate = System.currentTimeMillis() // no permite seleccionar una fecha mayor a hoy
                                    datePickerDialog.show()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                    )


                }

                Row {
                    Column {
                        Button(onClick = {navController.navigate("guardar/$nombre/$apellido/$telefono/$email/$direccion/$fechanacimiento")}) {
                            Text("CONTINUAR")
                        }
                    }
                }
            }

        }
    }
}


@Suppress("PreviewAnnotationInFunctionWithParameters")
@Composable
fun PantallaMasDatosContactos(navController: NavController,nombre: String, apellido: String, telefono: String, email: String, direccion: String, fechanacimiento: String) {
    val ctx = LocalContext.current
    var radiotext by remember { mutableStateOf<String?>(null) }
    val opcionesCheckbox = remember { mutableStateListOf<String>() }
    val switchInformacion = remember { mutableStateOf(false) }

    Scaffold(topBar = {PantallaPrincipal_Header(navController) }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Column(
            ) {
                Row {
                    Column {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "FORMULARIO DE CONTACTO"  // Establece el texto
                                    textSize = 30f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                    }
                }
                Row {
                    Column {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "Este es un formulario destinado al registro de personas, sus datos personales, e intereses"  // Establece el texto
                                    textSize = 15f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                    }
                }
                Row {

                    AndroidView(
                        factory = { context ->
                            RadioGroup(context).apply {
                                orientation = RadioGroup.VERTICAL // Establece orientacion vertical en el radiogroup

                                // Se añaden los botones a continuacion
                                android.widget.RadioButton(context).apply {
                                    text = "Primario Incompleto"
                                    id = View.generateViewId() // Se genera un id
                                }.also { addView(it) } // Se lo añade al radiobutton

                                android.widget.RadioButton(context).apply {
                                    text = "Primario Completo"
                                    id = View.generateViewId()
                                }.also { addView(it) }

                                android.widget.RadioButton(context).apply {
                                    text = "Secundario Incompleto"
                                    id = View.generateViewId()
                                }.also { addView(it) }

                                android.widget.RadioButton(context).apply {
                                    text = "Secundario Completo"
                                    id = View.generateViewId()
                                }.also { addView(it) }

                                android.widget.RadioButton(context).apply {
                                    text = "Otros"
                                    id = View.generateViewId()
                                }.also { addView(it) }

                                setOnCheckedChangeListener { group, checkedId ->
                                    val radioButton = group.findViewById<RadioButton>(checkedId)
                                    radiotext = radioButton?.text.toString()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                            .padding(20.dp)
                    )

                }

                Row {
                    Column {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "Intereses"  // Establece el texto
                                    textSize = 16f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                    }
                }

                Row{
                    Column{
                        AndroidView(
                            factory = { context ->

                                LinearLayout(context).apply {
                                    orientation = LinearLayout.VERTICAL

                                    // Añadir checkboxes
                                    for (option in listOf("Deporte", "Musica", "Arte", "Tecnologia")) {
                                        CheckBox(context).apply {
                                            text = option

                                            // Establecer estado inicial basado en la lista de opciones seleccionadas
                                            isChecked = opcionesCheckbox.contains(option)

                                            setOnCheckedChangeListener { _, isChecked ->
                                                if (isChecked) {
                                                    opcionesCheckbox.add(option)
                                                } else {
                                                    opcionesCheckbox.remove(option)
                                                }

                                            }
                                        }.also { addView(it) }
                                    }}
                            },
                            modifier = Modifier
                                .width(250.dp)
                                .padding(20.dp)
                        )
                        // Botón para mostrar la lista seleccionada
                        Button(
                            onClick = {
                                println("Opciones seleccionadas: ${opcionesCheckbox.joinToString(", ")}")
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Guardar Selecciones")
                        }
                    }
                }

                Row{
                    Column {
                        AndroidView(
                            factory = { context ->
                                android.widget.Switch(context).apply {
                                    text = "Desea recibir informacion?" // Set thetext for the switch

                                    // Establece el estado inicial
                                    isChecked = switchInformacion.value

                                    setOnCheckedChangeListener { _, isChecked ->
                                        // Handle the switch state change here
                                        switchInformacion.value = isChecked
                                    }
                                }
                            },
                            modifier = Modifier
                                .width(250.dp)
                                .padding(20.dp)
                        )
                    }

                }

                Row {
                    Column {
                        Button(onClick = {
                            var escritor: OutputStreamWriter? = null

                            val datos = buildString {
                                append("$nombre;")
                                append("$apellido;")
                                append("$telefono;")
                                append("$email;")
                                append("$direccion;")
                                append("$fechanacimiento;")
                                append("$radiotext;")
                                // Asegurarse de que la lista de intereses esté en el formato correcto
                                append(opcionesCheckbox.joinToString(", ")) // Convertimos la lista a una cadena separada por comas
                                append(";")
                                append(switchInformacion.value.toString()) // Convertimos el booleano a una cadena de texto
                                append("\n") // Añadir nueva línea al final de los datos
                            }

                            try {
                                escritor = OutputStreamWriter(ctx.openFileOutput("FicheroDatos.txt", Context.MODE_APPEND))
                                escritor.write(datos)
                            } catch (ex: Exception) {
                                Log.e("Append", "Error al escribir fichero a memoria interna")
                            } finally {
                                try {
                                    escritor?.close()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }

                            navController.navigate("PantallaPrincipal") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }

                        }) {
                            Text("GUARDAR")
                        }
                    }
                }
            }

        }
    }

}


@Suppress("PreviewAnnotationInFunctionWithParameters")
@Composable
fun PantallaListadoContactos(navController: NavController) {

    Scaffold(topBar = {PantallaPrincipal_Header(navController) }) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
            Column(
            ) {
                Row {
                    Column {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "CONTACTOS"  // Establece el texto
                                    textSize = 30f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                    }
                }
                Row {
                    Column {
                        // TextView
                        AndroidView(
                            factory = { context ->
                                TextView(context).apply {
                                    text = "Listado Contactos"  // Establece el texto
                                    textSize = 15f  // Establece el tamaño del texto
                                }
                            },
                            modifier = Modifier
                                //.weight(1f)
                                .padding(top = 4.dp)
                        )
                    }
                }

                Row {
                    Column {
                        val context = LocalContext.current // obtener contexto
                        mostrarContenidoFichero(context)
                    }
                }

            }

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaListadoContactosPreview() {
    val navController = rememberNavController()
    PantallaListadoContactos(navController)
}


fun AppendFichero(ctx: Context, names: String) {
    var escritor: OutputStreamWriter? = null
    try {
        escritor = OutputStreamWriter(ctx.openFileOutput("FicheroDatos.txt", Context.MODE_APPEND))
        escritor.write(names)
    } catch (ex: Exception) {
        Log.e("Append", "Error al escribir fichero a memoria interna")
    } finally {
        try {
            escritor?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

fun leerFichero(ctx: Context): List<String> {
    val lines = mutableListOf<String>()
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(InputStreamReader(ctx.openFileInput("FicheroDatos.txt")))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            lines.add(line!!)
        }
    } catch (ex: Exception) {
        Log.e("Read", "Error al leer fichero de memoria interna")
    } finally {
        try {
            reader?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return lines
}


/*@Composable
fun mostrarContenidoFichero(context: Context) {

    val fileContent = leerFichero(context)

    Column {// Using Text composable for short content
        if (fileContent.size <= 5) { // Example threshold for using Text
            fileContent.forEach { line ->
                Text(text = line)
            }
        } else {
            // Using LazyColumn for longer content
            LazyColumn {
                items(fileContent) { line ->
                    Text(text = line)
                }
            }
        }
    }
}*/

@Composable
fun mostrarContenidoFichero(context: Context) {
    // Leer el contenido del fichero
    val fileContent = leerFichero(context)

    // Procesar y formatear los datos para que sean más legibles
    val formattedContent = fileContent.map { line ->
        // Suponiendo que cada línea tiene los datos separados por ';'
        val datos = line.split(";")
        // Formatear los datos de manera más legible
        """
        Nombre: ${datos.getOrNull(0) ?: "Desconocido"}
        Apellido: ${datos.getOrNull(1) ?: "Desconocido"}
        Teléfono: ${datos.getOrNull(2) ?: "Desconocido"}
        Email: ${datos.getOrNull(3) ?: "Desconocido"}
        Dirección: ${datos.getOrNull(4) ?: "Desconocido"}
        Fecha de Nacimiento: ${datos.getOrNull(5) ?: "Desconocido"}
        Educación: ${datos.getOrNull(6) ?: "Desconocido"}
        Intereses: ${datos.getOrNull(7)?.removeSurrounding("[", "]") ?: "Desconocido"}
        Recibir Información: ${datos.getOrNull(8) ?: "Desconocido"}
        """.trimIndent()
    }

    // Mostrar el contenido formateado utilizando composables de Jetpack Compose
    Column {
        if (formattedContent.size <= 5) { // Threshold para usar Text en lugar de LazyColumn
            formattedContent.forEach { line ->
                Text(text = line)
            }
        } else {
            LazyColumn {
                items(formattedContent) { line ->
                    Text(text = line)
                }
            }
        }
    }
}

