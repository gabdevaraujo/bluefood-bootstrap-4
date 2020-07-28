package com.bs.bluefood.bs_bluefood.domain.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1")
    public List<Pedido> listPedidoByCLiente(Integer clienteId);
}