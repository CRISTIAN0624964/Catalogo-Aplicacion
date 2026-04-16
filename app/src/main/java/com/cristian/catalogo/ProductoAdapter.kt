package com.cristian.catalogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val lista: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val cantidad: TextView = itemView.findViewById(R.id.txtCantidad)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.cantidad.text = "Cantidad: ${producto.cantidad}"
        holder.precio.text = "Precio: S/. ${producto.precio}"
    }

    override fun getItemCount(): Int = lista.size
}