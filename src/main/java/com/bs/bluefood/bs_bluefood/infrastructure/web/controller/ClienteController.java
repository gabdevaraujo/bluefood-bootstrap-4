package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bs.bluefood.bs_bluefood.application.services.ClienteService;
import com.bs.bluefood.bs_bluefood.application.services.RestauranteService;
import com.bs.bluefood.bs_bluefood.application.services.ValidationException;
import com.bs.bluefood.bs_bluefood.domain.cliente.Cliente;
import com.bs.bluefood.bs_bluefood.domain.cliente.ClienteRepository;
import com.bs.bluefood.bs_bluefood.domain.pedido.Pedido;
import com.bs.bluefood.bs_bluefood.domain.pedido.PedidoRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestaurante;
import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapio;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapioRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import com.bs.bluefood.bs_bluefood.domain.restaurante.RestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.SearchFilter;
import com.bs.bluefood.bs_bluefood.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteService cs;
	
	@Autowired
	private CategoriaRestauranteRepository crr;

	@Autowired
	private ClienteRepository cr;

	@Autowired
	private RestauranteService rs;

	@Autowired
	private  RestauranteRepository rr;

	@Autowired
	private ItemCardapioRepository icr;

	@Autowired
	private PedidoRepository pr;

	@GetMapping(path = "/home")
	public String home(Model model) {

		List<CategoriaRestaurante> categorias = crr.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());

		List<Pedido> pedidos = pr.listPedidoByCLiente(SecurityUtils.loggedCliente().getId());
		model.addAttribute("pedidos", pedidos);

		return "cliente-home";
	}
	
	@GetMapping(path = "/edit")
	public String edit(Model model){
		//Integer clienteId = SecurityUtils.loggedCliente().getId();
		//Cliente cliente = cr.findById(SecurityUtils.loggedCliente().getId()).orElseThrow();
		model.addAttribute("cliente", cr.findById(SecurityUtils.loggedCliente().getId()).orElseThrow());
		HelperController.setEditMode(model, true);
		return "cliente-cadastro";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente, Errors errors, Model model) {
		if(!errors.hasErrors()) {
			try {
				cs.saveCLiente(cliente);
				model.addAttribute("msg", "Cliente cadastrado com sucesso");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
			
		}
		HelperController.setEditMode(model, true);
		return "cliente-cadastro";
	}

	@GetMapping(path = "/search")
	public String search(@ModelAttribute ("searchFilter") SearchFilter filter,
						 @RequestParam(value = "cmd", required = false) String command, 
						 Model model){

		filter.processFilter(command);

		//Pode ser feito assim:

		/*List<Restaurante> restaurantes = rs.search(filter);
		model.addAttribute("restaurantes", restaurantes);*/

		//Ou assim:
		model.addAttribute("restaurantes", rs.search(filter));

		//Adiciona novamente ao model para que esteja disponível na página cliente-busca
		model.addAttribute("searchFilter", filter);

		HelperController.addCategoriasToRequest(crr, model);
		return "cliente-busca";
	}

	@GetMapping(path = "/restaurante")
	//@RequestParam ("restauranteId") Integer restauranteId - Recupera da Request o valor de restauranteId e coloca na variável declarada
	public String viewRestaurante(
		@RequestParam ("restauranteId") Integer restauranteId,
		@RequestParam(value = "categoria", required = false) String categoria, 
		Model model){

		Restaurante restaurante = rr.findById(restauranteId).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());

		List<String> categorias = icr.findCategorias(restauranteId);
		model.addAttribute("categorias", categorias);

		List<ItemCardapio> itensCardapioDestaque;
		List<ItemCardapio> itensCardapioNaoDestaque;

		//Se categoria for nula, pesquisa sem categoria
		if(categoria == null){
			itensCardapioDestaque = icr.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);
			itensCardapioNaoDestaque = icr.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);
		}
		else {
			itensCardapioDestaque = icr.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);
			itensCardapioNaoDestaque = icr.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
		}
		
		
		model.addAttribute("destaque", itensCardapioDestaque);
		model.addAttribute("naoDestaque", itensCardapioNaoDestaque);
		
		model.addAttribute("categoriaSelecionada", categoria);

		return "cliente-restaurante";
	}

	@GetMapping(path = "/pedidos")
    public String viewPedidos(Model model){

        List<Pedido> pedidos = pr.listPedidoByCLiente(SecurityUtils.loggedCliente().getId());
		model.addAttribute("pedidos", pedidos);

        return "cliente-pedidos";
    }
}
