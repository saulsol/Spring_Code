package com.example.spring_code.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
                // 원본 파일 업로드
                Files.copy(multipartFile.getInputStream(), savePath);

                String contentType = multipartFile.getContentType(); // Mine Type

                // 파일이 이미지인 경우 섬네일 이미지 제작
                if(contentType != null || contentType.startsWith("image")){
                    Path thumbnailPath = Paths.get(uploadPath, "s_"+savedName);
                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile());
                }

                uploadList.add(savedName);
            } catch (IOException e){
                throw new RuntimeException(e);
            }

        }

        return uploadList;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        if(!resource.isReadable()){
            resource = new FileSystemResource(uploadPath+File.separator+"default.png");
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames){
        if(fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(
                fileName -> {
                    String thumbImage = "s_" + fileName;
                    Path thumbnailFilePath = Paths.get(uploadPath, thumbImage);
                    Path filePath = Paths.get(uploadPath, thumbImage);

                    try {
                        Files.deleteIfExists(thumbnailFilePath);
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                });
    }


}
