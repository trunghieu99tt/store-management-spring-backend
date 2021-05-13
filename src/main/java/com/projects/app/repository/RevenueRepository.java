package com.projects.app.repository;

import com.projects.app.models.Revenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long>, JpaSpecificationExecutor<Revenue> {

    List<Revenue> getRevenueByCreatedAtBetween(Date startDate, Date endDate);

    Page<Revenue> getRevenueByCreatedAt(Date day, Pageable pageable);

}
