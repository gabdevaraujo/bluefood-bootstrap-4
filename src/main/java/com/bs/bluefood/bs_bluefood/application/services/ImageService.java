package com.bs.bluefood.bs_bluefood.application.services;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bs.bluefood.bs_bluefood.utils.IOUtils;

@Service
public class ImageService {

    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    @Value("${bluefood.files.categoria}")
    private String categoriasDir;

    @Value("${bluefood.files.comida}")
    private String comidasDir;

    public void uploadLogotipo(MultipartFile multiPartFile, String fileName) {
        try {
            IOUtils.copy(multiPartFile.getInputStream(), fileName, logotiposDir);
        } catch (IOException e) {
            throw new ApplicationServiceException(e);
        }
    }

    public byte[] getBytes(String type, String imgName){

        try{
            String dir;

            if("logotipos".equals(type)){
                dir = logotiposDir;
            }
            else if("categorias".equals(type)){
                dir = categoriasDir;
            }
            else if("comidas".equals(type)){
                dir = comidasDir;
            }
            else {
                throw new Exception(type + " não é um tipo de imagem válido");
            }

            return IOUtils.getBytes(Paths.get(dir, imgName));
        }catch(Exception e){
            throw new ApplicationServiceException(e);
        }
    }
}