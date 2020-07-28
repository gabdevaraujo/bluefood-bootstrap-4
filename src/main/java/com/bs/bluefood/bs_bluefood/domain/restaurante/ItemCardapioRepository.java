package com.bs.bluefood.bs_bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//JpaRepository<T, IDr> - Recebe por generics o tipo que será trabalhado, no caso, Cliente, e o tipo do ID que o identifica, Integer
public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
    
    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer restauranteId, boolean destaque);

    public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer restauranteId, boolean destaque, String categoria);

    //Procura categorias excluindo as duplicidades
    @Query("SELECT DISTINCT ic.categoria FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
    public List<String> findCategorias(Integer restauranteId);

}
