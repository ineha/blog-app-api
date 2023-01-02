package com.myproject.blog_app.Service.impl;

import com.myproject.blog_app.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File name
        String name = file.getOriginalFilename();
        //abc.png

        String random = UUID.randomUUID().toString();
        String Filename1 = random.concat(name.substring(name.lastIndexOf(".")));

        //Fullpath
        String filePath = path +File.separator + Filename1;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return Filename1;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {

        String fullPath = path+File.separator+filename;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }


}
