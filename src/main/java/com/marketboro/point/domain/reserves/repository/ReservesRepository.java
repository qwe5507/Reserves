package com.marketboro.point.domain.reserves.repository;

import com.marketboro.point.domain.reserves.Reserves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservesRepository extends JpaRepository<Reserves, Long>, ReservesRepositoryCustom {


}
