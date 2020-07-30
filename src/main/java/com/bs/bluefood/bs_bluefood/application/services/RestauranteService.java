package com.bs.bluefood.bs_bluefood.application.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.bluefood.bs_bluefood.domain.cliente.Cliente;
import com.bs.bluefood.bs_bluefood.domain.cliente.ClienteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import com.bs.bluefood.bs_bluefood.domain.restaurante.RestauranteComparator;
import com.bs.bluefood.bs_bluefood.domain.restaurante.RestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.SearchFilter;
import com.bs.bluefood.bs_bluefood.domain.restaurante.SearchFilter.SearchType;
import com.bs.bluefood.bs_bluefood.utils.SecurityUtils;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository rr;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ClienteRepository clienteRepository;
	
	public void saveRestaurante(Restaurante restaurante) throws ValidationException{
		if(!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("O E-mail está duplicado");
		}
		if(restaurante.getId()!= null) {
			Restaurante restauranteDB = rr.findById(restaurante.getId()).orElseThrow();
			restaurante.setSenha(restauranteDB.getSenha());
			restaurante.setLogotipo(restauranteDB.getSenha());
			rr.save(restaurante);
		}
		else {
			restaurante.encryptPassword();
			restaurante = rr.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
		rr.save(restaurante);
	}
	
	private boolean validateEmail(String email, Integer id) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if(cliente != null) return false;

		Restaurante restaurante = rr.findByEmail(email);
		if(restaurante != null) {
			if(id == null) {
				return false;
			}
			if(!restaurante.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}

	public List<Restaurante> search(SearchFilter filter){
		
		List<Restaurante> restaurantes;

		if(filter.getSearchType() == SearchType.TEXTO){
			restaurantes = rr.findByNomeIgnoreCaseContaining(filter.getTexto());
		}
		else if(filter.getSearchType() == SearchType.CATEGORIA){
			restaurantes = rr.findByCategorias_Id(filter.getCategoriaId());
		}
		else throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + " não é suportado");
		
		Iterator<Restaurante> it = restaurantes.iterator();

		while(it.hasNext()){
			Restaurante restaurante = it.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();

			if(!filter.isEntregaGratis() && !(taxaEntrega == 0) || filter.isEntregaGratis() && taxaEntrega == 0){
				it.remove();
			}
		}
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);

		return restaurantes;
	}
}
