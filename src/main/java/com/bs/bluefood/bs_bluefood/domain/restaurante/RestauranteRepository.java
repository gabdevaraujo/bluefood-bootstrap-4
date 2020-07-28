package com.bs.bluefood.bs_bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository<T, IDr> - Recebe por generics o tipo que será trabalhado, no caso, Cliente, e o tipo do ID que o identifica, Integer
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer>{
	
	public Restaurante findByEmail(String email);

	//Procura restaurantes pelo nome, ignorando o case e pegando qualquer um que contenha no nome o que for digitado na busca
	public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);

	//Procurando pelo ID da categoria
	List<Restaurante> findByCategorias_Id(Integer categoriaId);
}
