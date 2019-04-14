package com.beijiake.web.service;

import com.beijiake.web.config.StorageProperties;
import com.beijiake.data.domain.resouce.File;
import com.beijiake.repository.resource.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FileRepository fileRepository;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file)  {



        try {

            String checksum = checksum(file.getInputStream());
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            String filename = checksum + "." + extension;


            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty File " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store File with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);

                System.out.println(System.getProperty("user.home"));

                logger.info(String.format("成功将上传的文件%s 保存到%s", file.getOriginalFilename(), this.rootLocation.resolve(filename).toString()));


                /* 记录上传的文件到数据库 */
                Optional<File> optFileNote = fileRepository.findByFilename(filename);

                if (!optFileNote.isPresent()) {
                    File fileNote = new File();
                    fileNote.setFilename(filename);
                    fileNote.setUrl("/");
                    fileNote.setType(new MimetypesFileTypeMap().getContentType(file.getOriginalFilename()));
                    fileNote.setSize(file.getSize());

                    fileRepository.save(fileNote);
                }
                else {
                    fileRepository.save(optFileNote.get());
                }



            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store File", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read File: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read File: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }


    protected String checksum(InputStream inputStream) throws IOException{
        return DigestUtils.md5DigestAsHex(inputStream);
    }
}
