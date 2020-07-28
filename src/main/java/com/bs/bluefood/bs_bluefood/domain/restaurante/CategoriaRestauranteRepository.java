package com.bs.bluefood.bs_bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<T, IDr> - Recebe por generics o tipo que será trabalhado, no caso, Cliente, e o tipo do ID que o identifica, Integer
public interface CategoriaRestauranteRepository extends JpaRepository<CategoriaRestaurante, Integer> {
	
}
