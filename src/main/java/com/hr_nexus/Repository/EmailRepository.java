package com.hr_nexus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr_nexus.Entity.EmalLogs;

@Repository
public interface EmailRepository extends JpaRepository<EmalLogs, Long> {
}
