package com.projects.app.repository.specification;

import com.projects.app.models.Revenue;
import com.projects.app.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueSpecification implements Specification<Revenue> {
    private Date day;
    private int sortCase;
    private boolean isAscSort;

    @SneakyThrows
    @Override
    public Predicate toPredicate(Root<Revenue> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new LinkedList<>();
        // filter by date
        if (day != null) {
            System.out.println(day);
            Date nextDay = new Date(day.getTime() + 1000 * 60 * 60 * 24);
            predicateList.add(criteriaBuilder.between(root.<Date>get("createdAt"), day, nextDay));
        }
        // sort
        Path orderClause;
        switch (sortCase) {
            case Constant
                    .SORT_BY_REVENUE_QUANTITY:
                orderClause = root.get("quantity");
                break;
            case Constant.SORT_BY_PRICE_UNIT:
                orderClause = root.get("priceUnit");
                break;
            case Constant.SORT_BY_TOTAL:
                orderClause = root.get("total");
                break;
            default:
                orderClause = root.get("name");
        }

        if (isAscSort) {
            criteriaQuery.orderBy(criteriaBuilder.asc(orderClause));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(orderClause));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));


    }
}
