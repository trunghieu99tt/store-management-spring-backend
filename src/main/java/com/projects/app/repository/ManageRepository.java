package com.projects.app.repository;

import com.projects.app.models.user.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageRepository extends JpaRepository<Manager, Long> {
}
