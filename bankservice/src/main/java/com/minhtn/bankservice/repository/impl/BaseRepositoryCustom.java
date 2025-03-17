package com.minhtn.bankservice.repository.impl;

import com.minhtn.bankservice.ultility.Extension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Map;

public class BaseRepositoryCustom {

    public final String ENTITY_GRAPH = "jakarta.persistence.loadgraph";

    @PersistenceContext
    protected EntityManager em;

    protected void setSortField(CriteriaBuilder builder, CriteriaQuery<?> query, Root<?> root, String sortField, boolean isDescSort) {
        if (Extension.isBlankOrNull(sortField)) {
            query.orderBy(builder.desc(root.get("createAt")));
        } else {
            if (isDescSort) {
                query.orderBy(builder.desc(root.get(sortField)));
            } else {
                query.orderBy(builder.asc(root.get(sortField)));
            }
        }
    }

    protected void setSortField(CriteriaBuilder builder, CriteriaQuery<?> query, Root<?> root, Map<String, Boolean> sortField) {
        if (sortField.isEmpty()) {
            query.orderBy(builder.desc(root.get("createAt")));
        } else {
            sortField.forEach((key, value) -> {
                if (value) {
                    query.orderBy(builder.desc(root.get(key)));
                } else {
                    query.orderBy(builder.asc(root.get(key)));
                }
            });
        }
    }
}
