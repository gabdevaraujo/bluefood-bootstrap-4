//Método para setar valores tanto para o searchType quanto para o categoriaId
//searchType é o enum do backend que pode ser: TEXTO ou CATEGORIA
//Em caso do searchType receber algum valor, temos que passar então o valor para categoriaId para que a busca possa ser feita no BD posteriormente
function searchRest(categoriaId) {
	
	var t = document.getElementById("searchType");
	
	if (categoriaId == null) {
		t.value = "TEXTO";
	
	} else {
		t.value = "CATEGORIA";
		document.getElementById("categoriaId").value = categoriaId;
	}
	
	document.getElementById("form").submit();
}

//Atribui o valor passado em cmd para ser passado ao enum Order
//Submete o formulário
function setCmd(cmd){
	document.getElementById("cmd").value = cmd;
	document.getElementById("form").submit();
}

function setCmdCaixa(){
    cmd = document.getElementById("filtros").value;
    alert(cmd);
    setCmd(cmd);
}


function setEntregaGratis(){
    cmd = document.getElementById("entregaGratis").value;
}

function verificaEntregaGratis(){
    gratis = document.getElementsByName("entregaGratis");
    if(gratis.checked == true){
        
        setCmd(setEntregaGratis());
        
    }
}

function add(){
	var valor = Number.parseInt(document.getElementById("qtd").value);
	document.getElementById("qtd").value = valor + 1;
}

function sub(){
	var valor = Number.parseInt(document.getElementById("qtd").value);
	if(valor > 1){
		document.getElementById("qtd").value = valor - 1;
	}
	else if(valor == 1){
		document.getElementById("qtd").value = 1;
	}
}

function filterCardapio(categoria){
	document.getElementById("categoria").value = categoria;
	document.getElementById("filterForm").submit();
}