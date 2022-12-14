package com.marketboro.point.domain.reserves;

import com.marketboro.point.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservesRepository extends JpaRepository<Reserves, Long> {
}
