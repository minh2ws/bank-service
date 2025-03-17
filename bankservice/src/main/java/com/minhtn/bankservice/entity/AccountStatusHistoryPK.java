package com.minhtn.bankservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatusHistoryPK implements Serializable {

    @Column(name = "account_id")
    private String accountId;
    @Column(name = "account_status_id")
    private String accountStatusId;
    @Column(name = "create_at")
    private Date createdAt;
}
