package com.projects.app.repository.specification;

import com.projects.app.models.Revenue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueSpecification implements Specification<Revenue> {
    private long bankAccountID;
    private Date day;

    @Override
    public Predicate toPredicate(Root<Revenue> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new LinkedList<>();
        // filter by bankAccountID;
        if (bankAccountID != -1) {
            predicateList.add(criteriaBuilder.equal(root.get("bankAccountID"), bankAccountID));
        }
        // filter by date
        if (day != null) {
            predicateList.add(criteriaBuilder.equal(root.<Date>get("createdAt"), day));
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));

    }
}
