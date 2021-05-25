package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.Budget;
import com.projects.app.models.Report;
import com.projects.app.models.Revenue;
import com.projects.app.models.expense.Expense;
import com.projects.app.models.request.ReportDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.repository.BudgetRepository;
import com.projects.app.repository.ReportRepository;
import com.projects.app.repository.RevenueRepository;
import com.projects.app.repository.expense.ExpenseRepository;
import com.projects.app.repository.user.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    RevenueRepository revenueRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    BudgetRepository budgetRepository;


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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        int monthFrom = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(dateTo);
        int monthTo = calendar.get(Calendar.MONTH) + 1;

        List<Budget> budgets = budgetRepository.findAll();
        ArrayList<Budget> filteredBudgets = new ArrayList<>();
        for (Budget budget : budgets){
            if(monthFrom <= budget.getMonth() && budget.getMonth() <= monthTo){
                filteredBudgets.add(budget);
            }
        }
        reportDTO.setBudgets(filteredBudgets);
        reportDTO.setExpenses(expenses);
        reportDTO.setRevenues(revenues);
        float totalRevenue = 0;
        for (Revenue r : revenues) {
            totalRevenue += r.getTotal();
        }
        reportDTO.setRevenue(totalRevenue);
        float totalExpense = 0;
        for (Expense e : expenses) {
            totalExpense += e.getTotal();
        }
        float totalBudget = 0;
        for (Budget b : filteredBudgets) {
            System.out.println(b.toString());
            totalBudget += b.getTotal();
        }
        reportDTO.setStaffID(staffID);
        // TODO: get actual profit
        reportDTO.setProfit(0);
        reportDTO.setDateFrom(dateFrom);
        reportDTO.setDateTo(dateTo);
        reportDTO.setDescription("");
        reportDTO.setExpense(totalExpense);
        reportDTO.setBudget(totalBudget);
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
        report.setExpenses(reportDTO.getExpenses());
        report.setRevenues(reportDTO.getRevenues());
        report.setBudget(reportDTO.getBudget());
        report.setBudgets(reportDTO.getBudgets());
        Optional<Staff> staff = staffRepository.findById(staffID);
        if (staff.isPresent()) {
            report.setStaff(staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Thông tin user không đúng");
        }
        return report;
    }

    /**
     * @param reportID
     * @return
     */
    public Report getReportById(Long reportID) {
        Optional<Report> report = reportRepository.findById(reportID);
        if (report.isPresent()) {
            float totalRevenue = 0;
            float totalExpense = 0;
            float totalBudget = 0;
            List<Revenue> revenues = (List<Revenue>) report.get().getRevenues();
            for (Revenue r : revenues) {
                totalRevenue += r.getTotal();
            }
            List<Expense> expenses = (List<Expense>) report.get().getExpenses();
            for (Expense e : expenses) {
                totalExpense += e.getTotal();
            }
            List<Budget> budgets = (List<Budget>) report.get().getBudgets();
            for (Budget b : budgets) {
                System.out.println(b.toString());
                totalBudget += b.getTotal();
            }

            report.get().setRevenue(totalRevenue);
            report.get().setExpense(totalExpense);
            if (revenues.size() == 0 && expenses.size() == 0 && budgets.size() == 0) {
                reportRepository.delete(report.get());
                return null;
            }
            reportRepository.save(report.get());
        }
        return report.orElse(null);
    }

}
