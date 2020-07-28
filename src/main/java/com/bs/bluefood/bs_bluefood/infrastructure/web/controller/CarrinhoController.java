package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bs.bluefood.bs_bluefood.application.services.RestauranteDiferenteException;
import com.bs.bluefood.bs_bluefood.domain.pedido.Carrinho;
import com.bs.bluefood.bs_bluefood.domain.pedido.ItemPedido;
import com.bs.bluefood.bs_bluefood.domain.pedido.Pedido;
import com.bs.bluefood.bs_bluefood.domain.pedido.PedidoRepository;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapio;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioRepository icr;

    @Autowired
    private PedidoRepository pr;

    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }

    @GetMapping(path = "/visualizar")
    public String viewCarrinho(){
        return "cliente-carrinho";
    }

    // Chamado quando o botão adicionar item ao carrinho é chamado
    @GetMapping(path = "/adicionar")
    public String adicionarItem(@RequestParam("itemId") Integer itemId, @RequestParam("qtd") Integer quantidade,
            @RequestParam("observacoes") String observacoes, @ModelAttribute("carrinho") Carrinho carrinho,
            Model model) {

        ItemCardapio itemCardapio = icr.findById(itemId).orElseThrow();

        try {
            carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
        } catch (RestauranteDiferenteException e) {
            model.addAttribute("msg", "Não é possivel adicionar itens de restaurantes diferentes");
        }

        return "cliente-carrinho";
    }

    
    @GetMapping(path = "/remover")
    public String removerItem(
        @RequestParam("itemId") Integer itemId,
        @ModelAttribute("carrinho") Carrinho carrinho,
        SessionStatus sessionStatus,
        Model model) {

        ItemCardapio itemCardapio = icr.findById(itemId).orElseThrow();
        carrinho.removerItem(itemCardapio);
        
        if(carrinho.isEmpty()){
            sessionStatus.setComplete();
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/refazerCarrinho")
    public String refazerCarrinho(
        @RequestParam("pedidoId") Integer pedidoId,
        @ModelAttribute("carrinho") Carrinho carrinho,
        Model model) {

        Pedido pedido = pr.findById(pedidoId).orElseThrow();
        carrinho.limparCarrinho();
        
        for(ItemPedido item : pedido.getItens()){
            carrinho.adicionarItem(item);
        }

        return "cliente-carrinho";
    }
    
}