package com.bs.bluefood.bs_bluefood.utils;

public enum FileType {
    
    PNG("image/png", "png"),
    JPG("image/jpeg", "jpg");

    private String mimeType;
    private String extension;

    FileType(String mymeType, String extension){
        this.mimeType = mymeType;
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }

    //Recebe um mimeType e retorna true se o tipo for igual ou false se for diferente
    public boolean sameOf(String mimeType){
        return this.mimeType.equalsIgnoreCase(mimeType);
    }

    //Método para identicar um ou mais ENUMs específicos, nesse caso, procurar por PNG e JPG
    public static FileType of(String mimeType){
        for(FileType fileType: values()){
            if(fileType.sameOf(mimeType)){
                return fileType;
            }
        }

        return null;
    }

}