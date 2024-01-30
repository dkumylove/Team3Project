package org.team3.admin.option.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.entities.Options;

public interface OptionRankRepository extends JpaRepository<OptionRank, String>, QuerydslPredicateExecutor<OptionRank> {
}
