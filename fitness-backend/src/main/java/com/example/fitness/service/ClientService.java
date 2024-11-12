package com.example.fitness.service;

import com.example.fitness.model.Client;
import com.example.fitness.model.User;
import com.example.fitness.repository.ClientRepository;
import com.example.fitness.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.fitness.constants.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public Page<Client> getAllClients(int page, int size) {
        return clientRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Client getClientById(String id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client createClient(Client client, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        client.setUser(user);  // Set the User entity on Client
        return clientRepository.save(client);
    }

    public Page<Client> getClientsByUserId(Long userId, int page, int size) {
        return clientRepository.findByUserId(userId, PageRequest.of(page, size, Sort.by("name")));
    }

    public void deleteClient(String clientId) {
        log.info("Deleting client with id: {}", clientId);
        Client client = getClientById(clientId);

        // If the client has a photo, delete the file
        if (client.getPhotoUrl() != null) {
            String filename = client.getPhotoUrl().substring(client.getPhotoUrl().lastIndexOf("/") + 1);
            try {
                Path filePath = Paths.get(PHOTO_DIRECTORY).resolve(filename).normalize();
                Files.deleteIfExists(filePath);
                log.info("Photo deleted: {}", filePath);
            } catch (Exception e) {
                log.error("Failed to delete photo for client with id: {}", clientId, e);
            }
        }

        // Delete the client from the database
        clientRepository.delete(client);
        log.info("Client deleted: {}", clientId);
    }

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Uploading photo for client with id: {}", id);
        Client client = getClientById(id);
        String photoUrl = photoFunction.apply(id, file);
        client.setPhotoUrl(photoUrl);
        clientRepository.save(client);
        return photoUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> filename.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        try {
            String filename = id + fileExtension.apply(image.getOriginalFilename());
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/clients/image/" + filename).toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to save file");
        }
    };

}
