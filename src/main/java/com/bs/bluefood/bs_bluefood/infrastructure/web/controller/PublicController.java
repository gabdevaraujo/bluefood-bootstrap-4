package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.bluefood.bs_bluefood.application.services.ClienteService;
import com.bs.bluefood.bs_bluefood.application.services.RestauranteService;
import com.bs.bluefood.bs_bluefood.application.services.ValidationException;
import com.bs.bluefood.bs_bluefood.domain.cliente.Cliente;
import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService cs;

	@Autowired
	private RestauranteService rs;

	@Autowired
	private CategoriaRestauranteRepository crr;
	
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		HelperController.setEditMode(model, false);
		return "cliente-cadastro";
	}

	@GetMapping("/restaurante/new")
	public String newRestaurante(Model model) {
		model.addAttribute("restaurante", new Restaurante());
		HelperController.setEditMode(model, false);
		HelperController.addCategoriasToRequest(crr, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping("/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, Errors errors, Model model) {
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

	@PostMapping("/restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante, Errors errors, Model model) {
		
		if(!errors.hasErrors()) {
			try {
				rs.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante cadastrado com sucesso");
			} catch (ValidationException e) {
				System.out.println("Erro Aqui");
				errors.rejectValue("email", null, e.getMessage());
			}
			
		}
		HelperController.setEditMode(model, false);
		HelperController.addCategoriasToRequest(crr, model);
		return "restaurante-cadastro";
	}
	
}
