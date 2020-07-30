package com.bs.bluefood.bs_bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bs.bluefood.bs_bluefood.domain.cliente.Cliente;
import com.bs.bluefood.bs_bluefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable{
    
    public enum Status{
        Producao(1, "Em produção", false),
        Entrega(2, "Saiu para Entrega", false),
        Concluido(3, "Concluído", true);

        Status(int ordem, String descricao, boolean ultimo){
            this.ordem = ordem;
            this.descricao = descricao;
            this.ultimo = ultimo;
        }

        int ordem;
        String descricao;
        boolean ultimo;

        public int getOrdem(){
            return ordem;
        }

        public String getDescricao(){
            return descricao;
        }

        public boolean getUltimo(){
            return ultimo;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime data;

    //@NotNull
    private Status status;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @NotNull
    private Restaurante restaurante;

    @NotNull
    private BigDecimal subTotal;

    @NotNull
    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
    private Set<ItemPedido> itens;


    public String getFormattedId(){
        return String.format("#%04d",id);
    }

    public String getDiaPedido(){
        return String.valueOf(data.getDayOfMonth());
    }

    public String getMesPedido(){
        return String.valueOf(data.getMonth());
    }

    /*public String primeiroItem(){
        ItemPedido item = (ItemPedido) Arrays.asList(this.itens).get(0);
        System.out.println("AQUI: " + item.getItemCardapio().getNome());
        return item.getItemCardapio().getNome();
    }*/
}