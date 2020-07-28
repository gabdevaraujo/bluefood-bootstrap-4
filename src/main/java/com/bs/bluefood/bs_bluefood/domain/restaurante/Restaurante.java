package com.bs.bluefood.bs_bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.bs.bluefood.bs_bluefood.domain.usuario.Usuario;
import com.bs.bluefood.bs_bluefood.infrastructure.web.validator.UploadConstraint;
import com.bs.bluefood.bs_bluefood.utils.FileType;
import com.bs.bluefood.bs_bluefood.utils.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario{
    
    @NotBlank(message = "CNPJ não pode ser vazio")
	@Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
	@Column(length = 14, nullable = false)
    private String cnpj;

    @NotBlank(message = "CEP inválido")
	@Pattern(regexp = "[0-9]{8}")
	@Column(length = 8, nullable = false)
	private String cep;

    @Size(max = 80)
    private String logotipo;

    @UploadConstraint(acceptedTypes = FileType.PNG, message = "O arquivo não é de uma extensão válida. Apenas 'PNG' ")
    private transient MultipartFile logotipoFile;

    @NotNull(message = "Taxa de Entrega não pode ser vazia")
    @Min(0)
    @Max(99)
    private BigDecimal taxaEntrega;

    @NotNull(message = "Tempo de Entrega não pode ser vazia")
    @Min(0)
    @Max(120)
    private Integer tempoEntrega;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "restaurante_has_categoria",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
    )
    @Size(min = 1, message = "O Restaurante precisa ter pelo menos uma categoria")
    @ToString.Exclude
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);

    @OneToMany(mappedBy = "restaurante")
    private Set<ItemCardapio> itensCardapios = new HashSet<>(0);

    public void setLogotipoFileName(){
        if(getId() == null){
            throw new IllegalStateException("");
        }
        this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());
    }

    public String getCategoriasAsText(){
        Set<String> strings = new LinkedHashSet<>();

        for(CategoriaRestaurante categoria: categorias){
            strings.add(categoria.getNome());
        }

        return StringUtils.concatenate(strings);
    }

    public List<String> getCategoriasRestaurante(){
        List<String> categoriasR = new ArrayList<>();

        for(CategoriaRestaurante categoria : categorias){
            categoriasR.add(categoria.getNome());
        }

        return categoriasR;
    }

    public Integer calcularTempoEntrega(String cep){
        int soma = 0;

        for(char c : cep.toCharArray()){
            int v = Character.getNumericValue(c);
            if(v > 0){
                soma += v;
            }
        }
        soma /= 2;

        return tempoEntrega + soma;
    }
}