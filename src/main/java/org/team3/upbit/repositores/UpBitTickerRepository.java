package org.team3.upbit.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.upbit.entities.UpBitTicker;

public interface UpBitTickerRepository extends JpaRepository<UpBitTicker, String>, QuerydslPredicateExecutor<UpBitTicker> {

}
