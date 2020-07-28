package com.bs.bluefood.bs_bluefood.domain.pedido;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@SuppressWarnings("serial")
@Embeddable
public class ItemPedidoPK implements Serializable{

    @NotNull
	@ManyToOne
	private Pedido pedido;
	
	@NotNull
	private Integer ordem;
}