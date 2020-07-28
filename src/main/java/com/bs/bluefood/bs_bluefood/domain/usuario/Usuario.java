package com.bs.bluefood.bs_bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bs.bluefood.bs_bluefood.utils.StringUtils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@MappedSuperclass
public class Usuario implements Serializable{
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 80)
	@Column(length = 80, nullable = false)
	private String nome;
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Email(message = "E-mail inválido")
	@Size(max = 60)
	@Column(length = 60, nullable = false)
	private String email;
	
	@NotBlank(message = "Senha não pode ser vazia")
	@Size(max = 80)
	@Column(length = 80, nullable = false)
	private String senha;

	
	@NotBlank(message = "Telefone não pode ser vazio")
	@Size(max = 10)
	@Column(length = 10, nullable = false)
	@Pattern(regexp = "[0-9]{10}")
	private String telefone;
	
	public void encryptPassword() {
		this.senha = StringUtils.encrypt(this.senha);
	}

}
