package com.lartduniss.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull; 
import jakarta.transaction.Transactional;

import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.repository.InventarioRepository;

@Service
public class InventarioService 
{
    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }

    // Método individual por ID único exigido para el mapeo hipermedia HATEOAS
    public Optional<Inventario> buscarPorId(@NonNull Long id) {
        return inventarioRepository.findById(id);
    }

    public Optional<Inventario> buscarPorProducto(@NonNull Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    @Transactional // Asegura la atomicidad de la inserción o actualización directa
    public Inventario guardar(Inventario nuevoInventario) {
        return inventarioRepository.save(nuevoInventario);
    }

    @Transactional 
    public boolean descontarStock(@NonNull Long productoId, @NonNull Integer cantidadDescontar) 
    {
        Optional<Inventario> optInventario = inventarioRepository.findByProductoId(productoId);
        
        if (optInventario.isPresent()) {
            Inventario inventario = optInventario.get();
            
            // Regla de negocio: Validar si hay suficiente stock disponible
            if (inventario.getCantidadDisponible() >= cantidadDescontar) {
                int nuevoStock = inventario.getCantidadDisponible() - cantidadDescontar;
                inventario.setCantidadDisponible(nuevoStock);
                
                inventarioRepository.save(inventario);
                
                // Alerta de stock crítico por consola
                if (nuevoStock <= inventario.getStockMinimoAlerta()) {
                    System.out.println("ALERTA CRÍTICA: Stock bajo para el producto ID " + productoId + ". Quedan únicamente: " + nuevoStock + " unidades.");
                }
                
                return true; // Descuento exitoso
            }
        }
        return false; // No hay stock suficiente o el producto no existe en inventario
    }
}