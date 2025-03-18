package com.minhtn.bankservice.repository.impl;

import com.minhtn.bankservice.entity.Customer;
import com.minhtn.bankservice.model.search.ParameterReportCustByLocation;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.repository.CustomerRepositoryCustom;
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
public class CustomerRepositoryCustomImpl extends BaseRepositoryCustom implements CustomerRepositoryCustom {

    @Override
    public ListWrapper<Customer> searchCustomer(ParameterSearchCustomer parameterSearchCustomer) {
        HibernateCriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root.get("id"));

        //build where condition
        List<Predicate> predicates = new ArrayList<>();
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getCustomerId())) {
            predicates.add(builder.equal(root.get("customerId"), parameterSearchCustomer.getCustomerId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getFullName())) {
            predicates.add(builder.like(builder.lower(root.get("fullName")),
                    "%" + parameterSearchCustomer.getFullName().toLowerCase() + "%"));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getEngName())) {
            predicates.add(builder.like(builder.lower(root.get("engName")),
                    "%" + parameterSearchCustomer.getEngName().toLowerCase() + "%"));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getEmail())) {
            predicates.add(builder.like(root.get("email"), "%" + parameterSearchCustomer.getEmail() + "%"));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getPhone())) {
            predicates.add(builder.like(root.get("phone"), "%" + parameterSearchCustomer.getPhone() + "%"));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getAddress())) {
            predicates.add(builder.like(builder.lower(root.get("address")),
                    "%" + parameterSearchCustomer.getAddress().toLowerCase() + "%"));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getIdType())) {
            predicates.add(builder.equal(root.get("idType"), parameterSearchCustomer.getIdType()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getIdNumber())) {
            predicates.add(builder.like(root.get("idNumber"),
                    "%" + parameterSearchCustomer.getIdNumber() + "%"));
        }
        if (parameterSearchCustomer.getIdIssueDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("idIssueDate"),
                    parameterSearchCustomer.getIdIssueDateFrom()));
        }
        if (parameterSearchCustomer.getIdIssueDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("idIssueDate"),
                    parameterSearchCustomer.getIdIssueDateTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getIdIssuePlace())) {
            predicates.add(builder.like(builder.lower(root.get("idIssuePlace")),
                    "%" + parameterSearchCustomer.getIdIssuePlace().toLowerCase() + "%"));
        }
        if (parameterSearchCustomer.getIdExpireDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("idExpireDate"),
                    parameterSearchCustomer.getIdExpireDateFrom()));
        }
        if (parameterSearchCustomer.getIdExpireDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("idExpireDate"),
                    parameterSearchCustomer.getIdExpireDateTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getRecordStat())) {
            predicates.add(builder.equal(root.get("recordStat"), parameterSearchCustomer.getRecordStat()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getAuthStat())) {
            predicates.add(builder.equal(root.get("authStat"), parameterSearchCustomer.getAuthStat()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getAuthBy())) {
            predicates.add(builder.equal(root.get("authBy"), parameterSearchCustomer.getAuthBy()));
        }
        if (parameterSearchCustomer.getAuthAtFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("authAt"),
                    parameterSearchCustomer.getAuthAtFrom()));
        }
        if (parameterSearchCustomer.getAuthAtTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("authAt"),
                    parameterSearchCustomer.getAuthAtTo()));
        }
        if (parameterSearchCustomer.getCreateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"),
                    parameterSearchCustomer.getCreateFrom()));
        }
        if (parameterSearchCustomer.getCreateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("createAt"),
                    parameterSearchCustomer.getCreateTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getCreateBy())) {
            predicates.add(builder.equal(root.get("createBy"), parameterSearchCustomer.getCreateBy()));
        }
        if (parameterSearchCustomer.getUpdateAtFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("updateAt"),
                    parameterSearchCustomer.getUpdateAtFrom()));
        }
        if (parameterSearchCustomer.getUpdateAtTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("updateAt"),
                    parameterSearchCustomer.getUpdateAtTo()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getUpdateBy())) {
            predicates.add(builder.equal(root.get("updateBy"), parameterSearchCustomer.getUpdateBy()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getCountryCode())) {
            predicates.add(builder.equal(root.get("country").get("countryCode"), parameterSearchCustomer.getCountryCode()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getProvinceId())) {
            predicates.add(builder.equal(root.get("province").get("provinceId"), parameterSearchCustomer.getProvinceId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getDistrictId())) {
            predicates.add(builder.equal(root.get("district").get("districtId"), parameterSearchCustomer.getDistrictId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getWardId())) {
            predicates.add(builder.equal(root.get("ward").get("wardId"), parameterSearchCustomer.getWardId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getCustomerTypeId())) {
            predicates.add(builder.equal(root.get("customerType").get("customerTypeId"), parameterSearchCustomer.getCustomerTypeId()));
        }
        if (!Extension.isBlankOrNull(parameterSearchCustomer.getBranchId())) {
            predicates.add(builder.equal(root.get("branch").get("branchId"), parameterSearchCustomer.getBranchId()));
        }

        //Set conditions and get list ids by condition
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]); //parse query to list
        query.where(predicatesArray); //add list query

        //paging
        TypedQuery<String> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(parameterSearchCustomer.getPageSize());
        typedQuery.setFirstResult(parameterSearchCustomer.getStartIndex().intValue());

        List<String> customerIds = typedQuery.getResultList();

        //get customer by Ids
        CriteriaQuery<Customer> query1 = builder.createQuery(Customer.class);
        Root<Customer> root1 = query1.from(Customer.class);
        query1.where(root1.get("id").in(customerIds));
        if (parameterSearchCustomer.getSortField() == null) {
            parameterSearchCustomer.setSortField("customerId");
        }
        setSortField(builder, query1, root1, parameterSearchCustomer.getSortField(), parameterSearchCustomer.getDescSort());
        //Fetch reference entities
        EntityGraph<Customer> customerGraph = em.createEntityGraph(Customer.class);
        customerGraph.addAttributeNodes("accounts");
        customerGraph.addAttributeNodes("country");
        customerGraph.addAttributeNodes("province");
        customerGraph.addAttributeNodes("district");
        customerGraph.addAttributeNodes("ward");
        customerGraph.addAttributeNodes("customerType");
        customerGraph.addAttributeNodes("branch");

        TypedQuery<Customer> typedQuery1 = em.createQuery(query1);
        typedQuery1.setHint(ENTITY_GRAPH, customerGraph);
        List<Customer> customers = typedQuery1.getResultList();

        //get total record
        Long total = em.createQuery(query.createCountQuery()).getSingleResult(); //getTotalCount(builder, query, root);
        int pageSize = parameterSearchCustomer.getPageSize() == null ? total.intValue() : parameterSearchCustomer.getPageSize();
        if (pageSize == 0) pageSize = 1;

        return ListWrapper.<Customer>builder()
                .total(total)
                .totalPage((total - 1) / pageSize + 1)
                .pageSize(pageSize)
                .page(parameterSearchCustomer.getStartIndex() / pageSize + 1)
                .data(customers)
                .build();
    }

    @Override
    public Long reportCustByLocation(ParameterReportCustByLocation parameterReportCustByLocation) {
        HibernateCriteriaBuilder builder = em.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Customer> root = query.from(Customer.class);
        query.select(root.get("id"));

        //build where condition
        List<Predicate> predicates = new ArrayList<>();
        if (!Extension.isBlankOrNull(parameterReportCustByLocation.getCountryCode())) {
            predicates.add(builder.equal(root.get("country").get("countryCode"), parameterReportCustByLocation.getCountryCode()));
        }
        if (!Extension.isBlankOrNull(parameterReportCustByLocation.getProvinceId())) {
            predicates.add(builder.equal(root.get("province").get("provinceId"), parameterReportCustByLocation.getProvinceId()));
        }
        if (!Extension.isBlankOrNull(parameterReportCustByLocation.getDistrictId())) {
            predicates.add(builder.equal(root.get("district").get("districtId"), parameterReportCustByLocation.getDistrictId()));
        }
        if (!Extension.isBlankOrNull(parameterReportCustByLocation.getWardId())) {
            predicates.add(builder.equal(root.get("ward").get("wardId"), parameterReportCustByLocation.getWardId()));
        }

        //Set conditions and get list ids by condition
        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]); //parse query to list
        query.where(predicatesArray); //add list query

        //paging
        TypedQuery<String> typedQuery = em.createQuery(query);

        //get total record
        return em.createQuery(query.createCountQuery()).getSingleResult();
    }
}
