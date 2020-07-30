package com.bs.bluefood.bs_bluefood.domain.restaurante;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class SearchFilter {
 
    public enum SearchType{
        TEXTO,
        CATEGORIA;
    }

    public enum Order{
        TAXA, TEMPO;
    }

    //Controla qual botão foi pressionado
    public enum Command{
        ENTREGAGRATIS, MAIORTAXA, MENORTAXA, MAIORTEMPO, MENORTEMPO;
    }

    private String texto;
    private SearchType searchType;
    private Integer categoriaId;
    private Order order = Order.TAXA;
    private boolean asc;
    private boolean entregaGratis = true;
    

    public void processFilter(String cmdString){

        if(!StringUtils.isEmpty(cmdString)){
            Command cmd = Command.valueOf(cmdString);

            if(cmd == Command.ENTREGAGRATIS){
                entregaGratis = !entregaGratis;
            }
            else if(cmd == Command.MAIORTAXA){
                order = Order.TAXA;
                asc = false;

            }else if(cmd == Command.MENORTAXA){
                order = Order.TAXA;
                asc = true;
            }
            else if(cmd == Command.MAIORTEMPO){
                order = Order.TEMPO;
                asc = false;
            }
            else if(cmd == Command.MENORTEMPO){
                order = Order.TEMPO;
                asc = true;
            }
        }
        

        if(searchType == SearchType.TEXTO) {
            categoriaId = null;
        }
        else if(searchType == SearchType.CATEGORIA) {
            texto = null;
        }
    }
}