package org.team3.admin.option.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team3.admin.option.entities.Options;

public interface OptionRepository extends JpaRepository<Options, String> {

}
