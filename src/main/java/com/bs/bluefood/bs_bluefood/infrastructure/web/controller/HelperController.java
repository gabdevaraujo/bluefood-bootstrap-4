package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestaurante;
import com.bs.bluefood.bs_bluefood.domain.restaurante.CategoriaRestauranteRepository;

public class HelperController {

	public static void setEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
	
	public static void addCategoriasToRequest(CategoriaRestauranteRepository repository, Model model){
		List<CategoriaRestaurante> categorias =  repository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
	}
}