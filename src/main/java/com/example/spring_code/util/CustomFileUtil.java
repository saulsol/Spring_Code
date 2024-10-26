package com.example.spring_code.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {


    @Value("${saulSol.file.path}")
    private String uploadPath;


    // FILE 폴더를 만들어주려는 용도
    // 이미지 서버같은 경우 NginX로 서버를 분리하는 것을 권장
    @PostConstruct
    public void init(){
        File tempFolder = new File(uploadPath);

        if(!tempFolder.exists()){
            tempFolder.mkdirs();
        }else{
            tempFolder.getAbsolutePath();
        }

    }

    public List<String> saveFile (List<MultipartFile> files) throws RuntimeException{
        if(files.isEmpty() || files.size() == 0) {
            return List.of(); // 빈 List 객체를 리턴
        }

        ArrayList<String> uploadList = new ArrayList<>();
        for(MultipartFile multipartFile : files) {

            String savedName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(multipartFile.getInputStream(), savePath);
                uploadList.add(savedName);
            } catch (IOException e){
                throw new RuntimeException(e);
            }

        }

        return uploadList;
    }

}
