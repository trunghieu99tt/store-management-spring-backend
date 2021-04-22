package com.projects.app.repository.specification;

import com.projects.app.models.Revenue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
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
    private Date day;

    @SneakyThrows
    @Override
    public Predicate toPredicate(Root<Revenue> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new LinkedList<>();


        // filter by date
        if (day != null) {
            Date nextDay = new Date(day.getTime() + +1000 * 60 * 60 * 24);
            predicateList.add(criteriaBuilder.between(root.<Date>get("createdAt"), day, nextDay));
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));

    }
}
