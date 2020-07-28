package com.bs.bluefood.bs_bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bs.bluefood.bs_bluefood.application.services.RestauranteDiferenteException;
import com.bs.bluefood.bs_bluefood.domain.restaurante.ItemCardapio;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class Carrinho implements Serializable{

    private List<ItemPedido> itens = new ArrayList<>();
    private Restaurante restaurante;

    public void adicionarItem(ItemCardapio itemCardapio, Integer quantidade, String observacoes) throws RestauranteDiferenteException{

        if(itens.size() == 0){
            restaurante = itemCardapio.getRestaurante();
        }
        else if(!itemCardapio.getRestaurante().getId().equals(restaurante.getId())){
            throw new RestauranteDiferenteException();
        }

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setItemCardapio(itemCardapio);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setObservacoes(observacoes);
        itemPedido.setPreco(itemCardapio.getPreco());
        itens.add(itemPedido);  
    }

    public void adicionarItem(ItemPedido itemPedido){
        try {
            adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacoes());
        } catch (RestauranteDiferenteException e) {

        }
    }

    public void removerItem(ItemCardapio itemCardapio){
        for(Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();){
            ItemPedido itemPedido = iterator.next();
            if(itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())){
                iterator.remove();
            }
        }
        if(itens.size() == 0){
            restaurante = null;
        }
    }

    public void limparCarrinho(){
        itens.clear();
        restaurante = null;
    }

    public boolean isEmpty(){
        return itens.size() == 0;
    }

    public BigDecimal getSubTotal(){
        BigDecimal soma = BigDecimal.ZERO;

        for(ItemPedido item: itens){
            soma = soma.add(item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        return soma;
    }

    public BigDecimal getPrecoTotal(boolean adicionarTaxaEntrega){
        BigDecimal soma = BigDecimal.ZERO;

        for(ItemPedido item: itens){
            soma = soma.add(item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        if(adicionarTaxaEntrega){
            soma = soma.add(restaurante.getTaxaEntrega());
        }

        return soma;
    }
}