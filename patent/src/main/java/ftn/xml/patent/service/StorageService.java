package ftn.xml.patent.service;

import org.apache.jena.dboe.base.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.regex.Pattern;


@Service
public class StorageService {
    private final Path rootLocation = Paths.get("src/main/resources/data/files");

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileName = fileName.replace(" ", "_");
        Path filePath = this.rootLocation.resolve(fileName);

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + fileName);
            }
            while (Files.exists(filePath)) {
                fileName = fileName.split(Pattern.quote("."))[0] + "_copy" + "." + fileName.split(Pattern.quote("."))[1];
                filePath = this.rootLocation.resolve(fileName);
            }

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }

        return fileName;
    }
}

