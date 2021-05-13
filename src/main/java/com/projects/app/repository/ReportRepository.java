package com.projects.app.repository;

import com.projects.app.models.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findReportByReportDateBetween(Date start, Date end, Pageable pageable);

    Page<Report> findReportByReportDate(Date date, Pageable pageable);
}
