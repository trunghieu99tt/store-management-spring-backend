package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Report;
import com.projects.app.models.Revenue;
import com.projects.app.models.request.RevenueDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.models.user.User;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.ReportRepository;
import com.projects.app.repository.RevenueRepository;
import com.projects.app.repository.UserRepository;
import com.projects.app.repository.specification.RevenueSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ReportRepository reportRepository;

    /**
     * @param day        day to filter
     * @param isAsc      isAsc
     * @param sortBy     sortBy
     * @param pageNumber pageNumber
     * @param pageSize   pageSize
     * @return Page<Revenue>
     */
    public Page<Revenue> getAllRevenue(Date day, int sortBy, boolean isAsc, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        RevenueSpecification revenueSpecification = new RevenueSpecification(day, sortBy, isAsc);
        return revenueRepository.findAll(revenueSpecification, pageable);
    }

    /**
     * @return
     */
    public List<Revenue> getAll() {
        return revenueRepository.findAll();
    }


    /**
     * create new revenue
     *
     * @param revenue - new revenue
     * @return revenue
     */
    public Revenue add(Revenue revenue) {
        return revenueRepository.save(revenue);
    }

    /**
     * Get revenue by ID
     *
     * @param revenueID - id of todo item to be got
     * @return Todo
     */
    public Revenue getRevenue(Long revenueID) {
        Optional<Revenue> revenue = revenueRepository.findById(revenueID);
        return revenue.orElse(null);
    }

    /**
     * Update revenue
     *
     * @param updatedRevenue - new revenue item
     * @return Revenue
     */
    public Revenue updateRevenue(Revenue updatedRevenue) {
        return revenueRepository.save(updatedRevenue);
    }

    /**
     * Delete revenue
     *
     * @param revenueID - id of revenue item need to be deleted
     * @return boolean - true if delete successfully, false otherwise
     */
    @Transactional
    public boolean deleteRevenue(Long revenueID) {
        Revenue revenue = getRevenue(revenueID);
        if (revenue != null) {
            // After remove revenue, we should delete that revenue in all reports
            revenue.getReports().forEach(r -> r.getRevenues().remove(revenue));
            Collection<Report> newReports = new ArrayList<>();
            revenue.getReports().forEach(r -> {
                r.getRevenues().remove(revenue);
                if (r.getExpenses().size() == 0 && r.getRevenues().size() == 0) {
                    reportRepository.delete(r);
                } else {
                    newReports.add(r);
                }
            });
            reportRepository.saveAll(newReports);
            revenueRepository.deleteById(revenueID);
            return true;
        }
        return false;
    }

    /**
     * parse from revenueDTO to revenue
     *
     * @param revenueDTO
     * @return Revenue
     * @throws BackendError custom backend error
     */
    public Revenue parseRevenueDTOToRevenue(RevenueDTO revenueDTO) throws BackendError {
        Revenue revenue = new Revenue();
        revenue.setQuantity(revenueDTO.getQuantity());
        revenue.setName(revenueDTO.getName());
        revenue.setPriceUnit(revenueDTO.getPriceUnit());
        revenue.setTotal(revenueDTO.getTotal());
        revenue.setDescription(revenueDTO.getDescription());
        List<BankAccount> bankAccount =
                bankAccountRepository.findBankAccountByAccountNumber(revenueDTO.getBankAccountNumber());
        if (bankAccount.size() > 0) {
            revenue.setBankAccount(bankAccount.get(0));
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số tài khoản không đúng");
        }
        Optional<User> staff = userRepository.findById(revenueDTO.getStaffID());
        if (staff.isPresent()) {
            revenue.setStaff((Staff) staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Nhân viên đang thực hiện thao tac khong ton tai trong he " +
                    "thong");
        }
        if (revenueDTO.getCreatedAt() != null) {
            revenue.setCreatedAt(revenueDTO.getCreatedAt());
        } else {
            revenue.setCreatedAt(new Date());
        }
        return revenue;
    }

    /**
     * Get statistic between 2 day
     *
     * @param dayStart dayStart
     * @param dayEnd   dayEnd
     * @return List<Revenue>
     */
    public List<Revenue> getStatistic(Date dayStart, Date dayEnd) {
        return revenueRepository.getRevenueByCreatedAtBetween(dayStart, dayEnd);
    }


    /**
     * Get all revenue data in a specific month
     *
     * @param month month
     * @param year  year
     * @return List<Revenue>
     */

    public List<Revenue> getDataInMonth(int month, int year) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate firstDay = ym.atDay(1);
        LocalDate lastDay = ym.atEndOfMonth();
        Date firstDate = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastDate = Date.from(lastDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return revenueRepository.getRevenueByCreatedAtBetween(firstDate, lastDate);
    }

    /**
     * Get all revenue data in a specific month from current month
     *
     * @param monthFromNow :
     * @return List<Revenue>
     */
    public List<Revenue> getDataInMonthFromNow(int monthFromNow) {
        LocalDate todayDate = LocalDate.now();
        YearMonth ym = YearMonth.from(todayDate);
        int month;
        int year;
        YearMonth targetYm;
        if (monthFromNow >= 0) {
            targetYm = ym.plusMonths(monthFromNow);
        } else {
            targetYm = ym.minusMonths(-monthFromNow);
        }
        month = targetYm.getMonthValue();
        year = targetYm.getYear();
        return getDataInMonth(month, year);

    }
}
