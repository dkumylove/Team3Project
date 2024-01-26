package org.team3.admin.option.Repository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.admin.option.entities.Options;

public interface OptionRepository extends JpaRepository<Options, String>, QuerydslPredicateExecutor<Options> {


}
