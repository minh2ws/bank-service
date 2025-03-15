package com.minhtn.bankservice.repository.impl;

import com.minhtn.bankservice.entity.Account;
import com.minhtn.bankservice.model.search.ParameterSearchAccount;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.repository.AccountRepositoryCustom;
import com.minhtn.bankservice.ultility.Extension;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepositoryCustomImpl extends BaseRepositoryCustom implements AccountRepositoryCustom {
    @Override
    public ListWrapper<Account> searchAccount(ParameterSearchAccount parameterSearchAccount) {
        HibernateCriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Account> root = query.from(Account.class);
        query.select(root.get("id"));

        //build where condition
        List<Predicate> predicates = new ArrayList<>();
        if (!Extension.isBlankOrNull(parameterSearchAccount.getAccountId())) {
            predicates.add(builder.equal(root.get("accountId"), parameterSearchAccount.getAccountId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getCurrCode())) {
            predicates.add(builder.equal(root.get("currCode"), parameterSearchAccount.getCurrCode()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getAuthStat())) {
            predicates.add(builder.equal(root.get("authStat"), parameterSearchAccount.getAuthStat()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getAuthBy())) {
            predicates.add(builder.equal(root.get("authBy"), parameterSearchAccount.getAuthBy()));
        }
        if (parameterSearchAccount.getCloseDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("closeDate"),
                    parameterSearchAccount.getCloseDateFrom()));
        }
        if (parameterSearchAccount.getCloseDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("closeDate"),
                    parameterSearchAccount.getCloseDateTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getBranchId())) {
            predicates.add(builder.equal(root.get("branch").get("branchId"), parameterSearchAccount.getBranchId()));
        }
        if (parameterSearchAccount.getAuthAtFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("authAt"),
                    parameterSearchAccount.getAuthAtFrom()));
        }
        if (parameterSearchAccount.getAuthAtTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("authAt"),
                    parameterSearchAccount.getAuthAtTo()));
        }
        if (parameterSearchAccount.getCreateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"),
                    parameterSearchAccount.getCreateFrom()));
        }
        if (parameterSearchAccount.getCreateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("createAt"),
                    parameterSearchAccount.getCreateTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getCreateBy())) {
            predicates.add(builder.equal(root.get("createBy"), parameterSearchAccount.getCreateBy()));
        }
        if (parameterSearchAccount.getUpdateAtFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("updateAt"),
                    parameterSearchAccount.getUpdateAtFrom()));
        }
        if (parameterSearchAccount.getUpdateAtTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("updateAt"),
                    parameterSearchAccount.getUpdateAtTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getUpdateBy())) {
            predicates.add(builder.equal(root.get("updateBy"), parameterSearchAccount.getUpdateBy()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getAccountTypeId())) {
            predicates.add(builder.equal(root.get("accountType").get("accountTypeId"), parameterSearchAccount.getAccountTypeId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getCustomerId())) {
            predicates.add(builder.equal(root.get("customer").get("customerId"), parameterSearchAccount.getCustomerId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchAccount.getAccountStatusId())) {
            predicates.add(builder.equal(root.get("accountStatus").get("accountStatusId"), parameterSearchAccount.getAccountStatusId()));
        }

        //Set conditions and get list ids by condition
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]); //parse query to list
        query.where(predicatesArray); //add list query

        //paging
        TypedQuery<String> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(parameterSearchAccount.getPageSize());
        typedQuery.setFirstResult(parameterSearchAccount.getStartIndex().intValue());

        List<String> accountIds = typedQuery.getResultList();

        //get account by Ids
        CriteriaQuery<Account> query1 = builder.createQuery(Account.class);
        Root<Account> root1 = query1.from(Account.class);
        query1.where(root1.get("id").in(accountIds));
        if (parameterSearchAccount.getSortField() == null) {
            parameterSearchAccount.setSortField("accountId");
        }
        setSortField(builder, query1, root1, parameterSearchAccount.getSortField(), parameterSearchAccount.getDescSort());
        //Fetch reference entities
        EntityGraph<Account> accountGraph = em.createEntityGraph(Account.class);

        TypedQuery<Account> typedQuery1 = em.createQuery(query1);
        typedQuery1.setHint(ENTITY_GRAPH, accountGraph);
        List<Account> accounts = typedQuery1.getResultList();

        //get total record
        Long total = em.createQuery(query.createCountQuery()).getSingleResult();
        int pageSize = parameterSearchAccount.getPageSize() == null ? total.intValue() : parameterSearchAccount.getPageSize();
        if (pageSize == 0) pageSize = 1;

        return ListWrapper.<Account>builder()
                .total(total)
                .totalPage((total - 1) / pageSize + 1)
                .pageSize(pageSize)
                .page(parameterSearchAccount.getStartIndex() / pageSize + 1)
                .data(accounts)
                .build();
    }
}
