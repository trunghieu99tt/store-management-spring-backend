package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.Report;
import com.projects.app.models.Revenue;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ReportDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.repository.ReportRepository;
import com.projects.app.repository.RevenueRepository;
import com.projects.app.repository.StaffRepository;
import com.projects.app.repository.expense.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    RevenueRepository revenueRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    StaffRepository staffRepository;

    public Page<Report> getAllReport(Date dateFrom, Date dateTo, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        if (dateFrom == null)
            return reportRepository.findAll(pageable);
        if (dateTo == null)
            return reportRepository.findReportByReportDate(dateFrom, pageable);
        return reportRepository.findReportByReportDateBetween(dateFrom, dateTo, pageable);
    }

    /**
     * create new report
     *
     * @param report new report
     * @return report returned report from backend
     */
    public Report add(Report report) {
        System.out.println(report.toString());
        report.setId((long) (Math.random() * 1000));
        return reportRepository.save(report);
    }


    /**
     * Get Information for report (for GET method)
     *
     * @param dateFrom dateFrom
     * @param dateTo   dateTo
     * @return reportDTO
     */
    public ReportDTO getInformationForReport(Date dateFrom, Date dateTo, Long staffID) {
        ReportDTO reportDTO = new ReportDTO();
        List<Revenue> revenues = revenueRepository.getRevenueByCreatedAtBetween(dateFrom, dateTo
        );
        List<Expense> expenses = expenseRepository.findByDateBetween(dateFrom, dateTo);
        reportDTO.setExpenses(expenses);
        reportDTO.setRevenues(revenues);
        float revenueTotal = 0;
        for (Revenue r : revenues) {
            revenueTotal += r.getTotal();
            System.out.println("r from getInfo: " + r.toString());
        }
        // TODO: get actual profit
        reportDTO.setStaffID(staffID);
        reportDTO.setProfit(0);
        reportDTO.setDateFrom(dateFrom);
        reportDTO.setDateTo(dateTo);
        reportDTO.setDescription("");
        reportDTO.setRevenue(revenueTotal);
        return reportDTO;
    }


    /**
     * parse from DTO model to actual model
     *
     * @param reportDTO DTO model
     * @return Report
     * @throws BackendError custom error
     */
    public Report parseReportDTOToReport(ReportDTO reportDTO) throws BackendError {
        Report report = new Report();
        // TODO: get actual staff
        Long staffID = reportDTO.getStaffID();
        report.setReportFrom(reportDTO.getDateFrom());
        report.setReportTo(reportDTO.getDateTo());
        report.setDescription(reportDTO.getDescription());
        report.setRevenue(reportDTO.getRevenue());
        List<Revenue> temp = (List<Revenue>) reportDTO.getRevenues();
        for (Revenue r : temp) {
            System.out.println("r from parse : " + r.toString());
        }
        report.setExpenses(reportDTO.getExpenses());
        report.setRevenues(reportDTO.getRevenues());
        Optional<Staff> staff = staffRepository.findById(staffID);
        if (staff.isPresent()) {
            report.setStaff(staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Thông tin staff không đúng");
        }
        return report;
    }

    /**
     * @param reportID
     * @return
     */
    public Report getReport(Long reportID) {
        Optional<Report> report = reportRepository.findById(reportID);
        return report.orElse(null);
    }

}
