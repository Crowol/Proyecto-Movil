package org.omarcrz.proyectof.View.Screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ReportesView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp)
            .padding(bottom = 40.dp)
    ) {
        // Contenido de los reportes
        ReportesContent()
    }
}

@Composable
fun ReportesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título de la lista de reportes
        Text(
            text = "Lista de Reportes",
            color = RedCatalunya,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ReporteItem(titulo = "Compra de Plumas", descripcion = "El dia se compraron 1000 plumas")
        ReporteItem(titulo = "Compra de Lapices", descripcion = "El dia 12/12/2021 se compraron Lapices " +
                "de la marca BIC")
        ReporteItem(titulo = "Compra de Resistol", descripcion = "El dia 11/12/2024 se compro resistol 1/2 litro " +
                "de resistol liquido")

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ReporteItem(titulo: String, descripcion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
    ) {
        Text(text = "Título: $titulo", color = Color.Black)
        Text(text = "Descripción: $descripcion", color = Color.Black)
    }
}
