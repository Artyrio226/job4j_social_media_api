package ru.job4j.socialmediaapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.dto.ImageDto;
import ru.job4j.socialmediaapi.model.Image;
import ru.job4j.socialmediaapi.repository.ImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String storageDirectory;

    public ImageService(ImageRepository imageRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.imageRepository = imageRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image createImage(ImageDto imageDto) {
        var path = getNewFilePath(imageDto.getName());
        writeFileBytes(path, imageDto.getContent());
        return imageRepository.save(new Image(imageDto.getName(), path));
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ImageDto> getImageDtoById(int id) {
        var fileOptional = imageRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new ImageDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImageById(int id) {
        var fileOptional = imageRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            imageRepository.deleteById(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
