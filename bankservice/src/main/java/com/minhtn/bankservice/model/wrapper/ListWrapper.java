package com.minhtn.bankservice.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListWrapper<T> {
    private long total;
    private long totalPage;
    private long page;
    private int pageSize;
    private List<T> data;
}
