package com.zerobase.zbcharger.domain.charger.dao.custom.impl;

import static com.zerobase.zbcharger.domain.charger.entity.QCompany.company;

import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.zbcharger.domain.charger.dao.custom.CustomCompanyRepository;
import com.zerobase.zbcharger.domain.charger.dto.SearchCompanyCondition;
import com.zerobase.zbcharger.domain.charger.entity.Company;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> findAll(Pageable pageable, SearchCompanyCondition request) {
        Predicate predicate = toPredicate(request);

        List<Company> contents = queryFactory.selectFrom(company)
            .where(predicate)
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.select(company.count())
            .from(company)
            .where(predicate)
            .fetchFirst();

        return new PageImpl<>(contents, pageable, total);
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
            .map(order -> {
                if (order.getProperty().equals("id")) {
                    if (order.isAscending()) {
                        return company.id.asc();
                    }
                    return company.id.desc();
                }

                if (order.getProperty().equals("name")) {
                    if (order.isAscending()) {
                        return company.name.asc();
                    }
                    return company.name.desc();
                }

                return new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, NullHandling.Default);
            })
            .toArray(OrderSpecifier[]::new);
    }

    private static Predicate toPredicate(SearchCompanyCondition request) {
        return deletedYn(request.deletedYn());
    }

    private static BooleanExpression deletedYn(Boolean deletedYn) {
        if (deletedYn == null) {
            return null;
        }

        if (deletedYn) {
            return company.deletedAt.isNotNull();
        }

        return company.deletedAt.isNull();
    }
}
