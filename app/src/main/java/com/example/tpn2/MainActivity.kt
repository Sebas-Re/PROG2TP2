package com.example.tpn2

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpn2.ui.theme.TPNº2Theme
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
        composable("PantallaMasDatosContactos") { PantallaMasDatosContactos(navController) }
        composable("PantallaAgregarContactos") { /*PantallaAgregarContactos*/ }
        composable("PantallaListadoContactos") { /*PantallaListadoContactos*/ }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal_Header() {
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
    PantallaMasDatosContactos(navController)
}



@Suppress("PreviewAnnotationInFunctionWithParameters")
@Composable
fun PantallaPrincipal(navController: NavController) {
    var Nombre by remember { mutableStateOf("") }
    var Apellido by remember { mutableStateOf("") }
    var Telefono by remember { mutableStateOf("") }
    var Email by remember { mutableStateOf("") }
    var Direccion by remember { mutableStateOf("") }
    var FechaNacimiento by remember { mutableStateOf("") }

    Scaffold(topBar = {PantallaPrincipal_Header() }) { padding ->
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
                                    setText(Nombre)  // Configura el texto inicial
                                    addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                            // Filtrar caracteres no permitidos (solo letras y espacios)
                                            val filteredText = s?.filter { it.isLetter() || it.isWhitespace() }
                                            if (filteredText.toString() != s.toString()) {
                                                this@apply.setText(filteredText)
                                                this@apply.setSelection(filteredText?.length ?: 0)
                                            } else {
                                                Nombre = s.toString()
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
                                setText(Apellido)  // Configura el texto inicial
                                addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        // Filtrar caracteres no permitidos (solo letras y espacios)
                                        val filteredText = s?.filter { it.isLetter() || it.isWhitespace() }
                                        if (filteredText.toString() != s.toString()) {
                                            this@apply.setText(filteredText)
                                            this@apply.setSelection(filteredText?.length ?: 0)
                                        } else {
                                            Nombre = s.toString()
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
                                    setText(Telefono)  // Configura el texto inicial
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
                                                Telefono = s.toString()
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
                                    setText(Email)  // Configura el texto inicial
                                    addTextChangedListener(object : TextWatcher {
                                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                            val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                                            val filteredText = s?.filter { !it.isWhitespace() }

                                            if (filteredText.toString() != s.toString()) {
                                                this@apply.setText(filteredText)
                                                this@apply.setSelection(filteredText?.length ?: 0)
                                            } else {
                                                Email = s.toString()
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
                                setText(Direccion)  // Configura el texto inicial
                                addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        Direccion = s.toString()
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
                                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            FechaNacimiento = sdf.format(selectedDate)
                                            setText(FechaNacimiento)
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
                        Button(onClick = {navController.navigate("PantallaMasDatosContactos")}) {
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
fun PantallaMasDatosContactos(navController: NavController) {
    var Nombre by remember { mutableStateOf("") }
    var Apellido by remember { mutableStateOf("") }
    var Telefono by remember { mutableStateOf("") }
    var Email by remember { mutableStateOf("") }
    var Direccion by remember { mutableStateOf("") }
    var FechaNacimiento by remember { mutableStateOf("") }

    Scaffold(topBar = {PantallaPrincipal_Header() }) { padding ->
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
                                orientation = RadioGroup.VERTICAL // Establece orientacion verrical en el radiogroup

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

                                // Add more RadioButtons as needed
                            }
                        },
                        modifier = Modifier
                            .width(250.dp)
                            .padding(20.dp)
                    )

                }


                Row {
                    Column {
                        Button(onClick = {navController.navigate("PantallaMasDatosContacto")}) {
                            Text("GUARDAR")
                        }
                    }
                }
            }

        }
    }
}







