package com.bs.bluefood.bs_bluefood.domain.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.bs.bluefood.bs_bluefood.domain.usuario.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{

	@NotBlank(message = "CPF inválido")
	@Pattern(regexp = "[0-9]{11}")
	@Column(length = 11, nullable = false)
	private String cpf;
	
	@NotBlank(message = "CEP inválido")
	@Pattern(regexp = "[0-9]{8}")
	@Column(length = 8, nullable = false)
	private String cep;
	
}
