package com.lartduniss.inventario.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> listarTodo() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> buscarPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario guardar(Inventario nuevoInventario) {
        return inventarioRepository.save(nuevoInventario);
    }

    @Transactional
    public boolean descontarStock(Long productoId, Integer cantidadDescontar) {
        Optional<Inventario> optInventario = inventarioRepository.findByProductoId(productoId);
        
        if (optInventario.isPresent()) {
            Inventario inventario = optInventario.get();
            
            if (inventario.getCantidadDisponible() >= cantidadDescontar) {
                int nuevoStock = inventario.getCantidadDisponible() - cantidadDescontar;
                inventario.setCantidadDisponible(nuevoStock);
                
                inventarioRepository.save(inventario);
                
                if (nuevoStock <= inventario.getStockMinimoAlerta()) {
                    System.out.println("No hay stock disponible de: " + productoId + " Queda unicamente; " + nuevoStock);
                }
                
                return true; 
            }
        }
        return false; 
    }
}