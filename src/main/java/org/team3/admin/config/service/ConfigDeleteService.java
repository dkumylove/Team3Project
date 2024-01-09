package org.team3.admin.config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.admin.config.entities.Configs;
import org.team3.admin.config.repositories.ConfigsRepository;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs config = repository.findById(code).orElse(null);
        if (config == null) {
            return;
        }

        repository.delete(config);
        repository.flush();
    }
}