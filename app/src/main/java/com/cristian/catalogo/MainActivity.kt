package com.cristian.catalogo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val listaProductos = mutableListOf<Producto>()

    private lateinit var etNombre: EditText
    private lateinit var etCantidad: EditText
    private lateinit var etPrecio: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        etNombre = findViewById(R.id.etNombre)
        etCantidad = findViewById(R.id.etCantidad)
        etPrecio = findViewById(R.id.etPrecio)

        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarProductos()

        adapter = ProductoAdapter(listaProductos)
        recyclerView.adapter = adapter

        btnAgregar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val cantidad = etCantidad.text.toString()
            val precio = etPrecio.text.toString()

            if (nombre.isNotEmpty() && cantidad.isNotEmpty() && precio.isNotEmpty()) {

                val producto = Producto(
                    nombre,
                    cantidad.toInt(),
                    precio.toFloat()
                )

                listaProductos.add(producto)
                adapter.notifyDataSetChanged()

                // Limpiar campos
                etNombre.text.clear()
                etCantidad.text.clear()
                etPrecio.text.clear()
            }
        }

        btnGuardar.setOnClickListener {
            guardarProductos()
        }
    }

    private fun guardarProductos() {
        val sharedPref = getSharedPreferences("productos", MODE_PRIVATE)
        val editor = sharedPref.edit()

        val jsonArray = JSONArray()

        for (producto in listaProductos) {
            val obj = JSONObject()
            obj.put("nombre", producto.nombre)
            obj.put("cantidad", producto.cantidad)
            obj.put("precio", producto.precio)
            jsonArray.put(obj)
        }

        editor.putString("lista", jsonArray.toString())
        editor.apply()
    }

    private fun cargarProductos() {
        val sharedPref = getSharedPreferences("productos", MODE_PRIVATE)
        val jsonString = sharedPref.getString("lista", null)

        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                val producto = Producto(
                    obj.getString("nombre"),
                    obj.getInt("cantidad"),
                    obj.getDouble("precio").toFloat()
                )

                listaProductos.add(producto)
            }
        } else {
            // Datos iniciales
            listaProductos.add(Producto("Laptop", 5, 2500f))
            listaProductos.add(Producto("Mouse", 10, 50f))
            listaProductos.add(Producto("Teclado", 7, 120f))
        }
    }
}