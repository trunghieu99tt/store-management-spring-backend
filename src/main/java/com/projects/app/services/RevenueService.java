package com.projects.app.services;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.models.BankAccount;
import com.projects.app.models.Revenue;
import com.projects.app.models.request.RevenueDTO;
import com.projects.app.models.user.Staff;
import com.projects.app.models.user.User;
import com.projects.app.repository.BankAccountRepository;
import com.projects.app.repository.RevenueRepository;
import com.projects.app.repository.UserRepository;
import com.projects.app.repository.specification.RevenueSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * @param bankAccountID id of bank account
     * @param day           day to filter
     * @param pageNumber    pageNumber
     * @param pageSize      pageSize
     * @return Page<Revenue>
     */
    public Page<Revenue> getAllRevenue(Long bankAccountID, Date day, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        RevenueSpecification revenueSpecification = new RevenueSpecification(bankAccountID, day);
        return revenueRepository.findAll(revenueSpecification, pageable);
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
    public boolean deleteRevenue(Long revenueID) {
        if (getRevenue(revenueID) != null) {
            revenueRepository.deleteById(revenueID);
            return true;
        }
        return false;
    }

    public Revenue parseRevenueDTOToRevenue(RevenueDTO revenueDTO) throws BackendError {
        Revenue revenue = new Revenue();
        revenue.setQuantity(revenueDTO.getQuantity());
        revenue.setName(revenueDTO.getName());
        revenue.setPriceUnit(revenueDTO.getPriceUnit());
        revenue.setTotal(revenueDTO.getTotal());
        revenue.setDescription(revenueDTO.getDescription());
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(revenueDTO.getBankAccountID());
        if (bankAccount.isPresent()) {
            revenue.setBankAccount(bankAccount.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid bank account ID");
        }
        Optional<User> staff = userRepository.findById(revenueDTO.getStaffID());
        if (staff.isPresent()) {
            revenue.setStaff((Staff) staff.get());
        } else {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Invalid staff ID");
        }
        return revenue;
    }

}
