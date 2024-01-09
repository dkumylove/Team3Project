package org.team3.admin.config.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team3.admin.config.entities.Configs;

public interface ConfigsRepository extends JpaRepository<Configs, String> {

}
