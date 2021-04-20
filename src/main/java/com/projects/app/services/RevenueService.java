package com.projects.app.services;

import com.projects.app.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    public void getAll(Integer limit) {
    }
}
