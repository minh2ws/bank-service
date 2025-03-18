package com.minhtn.bankservice.repository.impl;

import com.minhtn.bankservice.entity.Transaction;
import com.minhtn.bankservice.model.report.AcctTransByAvlRange;
import com.minhtn.bankservice.model.report.ReportAcctTransByAvlRange;
import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.repository.TransactionRepositoryCustom;
import com.minhtn.bankservice.ultility.Extension;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryCustomImpl extends BaseRepositoryCustom implements TransactionRepositoryCustom {
    @Override
    public ListWrapper<Transaction> searchTransaction(ParameterSearchTransaction parameterSearchTransaction) {
        HibernateCriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Transaction> root = query.from(Transaction.class);
        query.select(root.get("id"));

        //build where condition
        List<Predicate> predicates = new ArrayList<>();
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getTranId())) {
            predicates.add(builder.equal(root.get("tranId"), parameterSearchTransaction.getTranId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getTraceNo())) {
            predicates.add(builder.equal(root.get("traceNo"), parameterSearchTransaction.getTraceNo()));
        }
        if (parameterSearchTransaction.getTranTimeFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("tranTime"),
                    parameterSearchTransaction.getTranTimeFrom()));
        }
        if (parameterSearchTransaction.getTranTimeTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("tranTime"),
                    parameterSearchTransaction.getTranTimeTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getRefNo())) {
            predicates.add(builder.equal(root.get("refNo"), parameterSearchTransaction.getRefNo()));
        }
        if (parameterSearchTransaction.getTranAmountFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("tranAmount"),
                    parameterSearchTransaction.getTranAmountFrom()));
        }
        if (parameterSearchTransaction.getTranAmountTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("tranAmount"),
                    parameterSearchTransaction.getTranAmountTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getCurrCode())) {
            predicates.add(builder.equal(root.get("currCode"), parameterSearchTransaction.getCurrCode()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getDestinationAcct())) {
            predicates.add(builder.equal(root.get("destinationAcct"), parameterSearchTransaction.getDestinationAcct()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getDestinationName())) {
            predicates.add(builder.equal(root.get("destinationName"), parameterSearchTransaction.getDestinationName()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getUserCreate())) {
            predicates.add(builder.equal(root.get("userCreate"), parameterSearchTransaction.getUserCreate()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getUserApprove())) {
            predicates.add(builder.equal(root.get("userApprove"), parameterSearchTransaction.getUserApprove()));
        }
        if (parameterSearchTransaction.getApproveTimeFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("approveTime"),
                    parameterSearchTransaction.getApproveTimeFrom()));
        }
        if (parameterSearchTransaction.getApproveTimeTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("approveTime"),
                    parameterSearchTransaction.getApproveTimeTo()));
        }
        if (parameterSearchTransaction.getStatus() != null) {
            predicates.add(builder.equal(root.get("status"), parameterSearchTransaction.getStatus()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getMerchantId())) {
            predicates.add(builder.equal(root.get("merchantId"), parameterSearchTransaction.getMerchantId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getTerminalId())) {
            predicates.add(builder.equal(root.get("terminalId"), parameterSearchTransaction.getTerminalId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getBranchId())) {
            predicates.add(builder.equal(root.get("branch").get("branchId"), parameterSearchTransaction.getBranchId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getTrancode())) {
            predicates.add(builder.equal(root.get("trancode").get("trancodeId"), parameterSearchTransaction.getTrancode()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getAccountId())) {
            predicates.add(builder.equal(root.get("account").get("accountId"), parameterSearchTransaction.getAccountId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getProvinceId())) {
            predicates.add(builder.equal(root.get("provinceId"), parameterSearchTransaction.getProvinceId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getDistrictId())) {
            predicates.add(builder.equal(root.get("districtId"), parameterSearchTransaction.getDistrictId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getWardId())) {
            predicates.add(builder.equal(root.get("wardId"), parameterSearchTransaction.getWardId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchTransaction.getAcquireBank())) {
            predicates.add(builder.equal(root.get("acquireBank"), parameterSearchTransaction.getAcquireBank()));
        }

        //Set conditions and get list ids by condition
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]); //parse query to list
        query.where(predicatesArray); //add list query

        //paging
        TypedQuery<String> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(parameterSearchTransaction.getPageSize());
        typedQuery.setFirstResult(parameterSearchTransaction.getStartIndex().intValue());

        List<String> transactionIds = typedQuery.getResultList();

        //get transaction by Ids
        CriteriaQuery<Transaction> query1 = builder.createQuery(Transaction.class);
        Root<Transaction> root1 = query1.from(Transaction.class);
        query1.where(root1.get("id").in(transactionIds));
        if (parameterSearchTransaction.getSortField() == null) {
            parameterSearchTransaction.setSortField("tranId");
        }
        setSortField(builder, query1, root1, parameterSearchTransaction.getSortField(), parameterSearchTransaction.getDescSort());

        //Fetch reference entities
        EntityGraph<Transaction> transactionGraph = em.createEntityGraph(Transaction.class);
        transactionGraph.addAttributeNodes("account");
        transactionGraph.addAttributeNodes("trancode");
        transactionGraph.addAttributeNodes("branch");

        TypedQuery<Transaction> typedQuery1 = em.createQuery(query1);
        typedQuery1.setHint(ENTITY_GRAPH, transactionGraph);
        List<Transaction> transactions = typedQuery1.getResultList();

        //get total record
        Long total = em.createQuery(query.createCountQuery()).getSingleResult();
        int pageSize = parameterSearchTransaction.getPageSize() == null ? total.intValue() : parameterSearchTransaction.getPageSize();
        if (pageSize == 0) pageSize = 1;

        return ListWrapper.<Transaction>builder()
                .total(total)
                .totalPage((total - 1) / pageSize + 1)
                .pageSize(pageSize)
                .page(parameterSearchTransaction.getStartIndex() / pageSize + 1)
                .data(transactions)
                .build();
    }

    @Override
    public List<AcctTransByAvlRange> reportAcctTransByRange(BigDecimal maxRange, BigDecimal minRange) {
        String max = "maxRange";
        String min = "minRange";
        Query query = em.createNativeQuery("select b.total_acct, d.total_tran from\n" +
                "(select count(a.account_id) total_acct from account a where a.avl_bal > :maxRange) b,\n" +
                "(select count(t.tran_id) total_tran from public.transaction t where t.account_id in (select a.account_id from account a where a.avl_bal > :maxRange)) d\n" +
                "union all\n" +
                "select b.total_acct, d.total_tran from\n" +
                "(select count(a.account_id) total_acct from account a where a.avl_bal <= :maxRange and a.avl_bal >= :minRange) b,\n" +
                "(select count(t.tran_id) total_tran from public.transaction t where t.account_id in (select a.account_id from account a where a.avl_bal <= :maxRange and a.avl_bal >= :minRange)) d\n" +
                "union all\n" +
                "select b.total_acct, d.total_tran from\n" +
                "(select count(a.account_id) total_acct from account a where a.avl_bal < :minRange) b,\n" +
                "(select count(t.tran_id) total_tran from public.transaction t where t.account_id in (select a.account_id from account a where a.avl_bal < :minRange)) d");
        query.setParameter(max, maxRange);
        query.setParameter(min, minRange);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
                .map(result -> AcctTransByAvlRange.builder()
                        .totalAcct(Double.parseDouble(result[0].toString()))
                        .totalTran(Double.parseDouble(result[1].toString()))
                        .build())
                .toList();
    }
}
