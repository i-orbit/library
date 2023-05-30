package com.inmaytide.orbit.library.domain;

import com.inmaytide.orbit.commons.domain.pattern.Entity;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2023/5/30
 */
public class OperationLogCleaning extends Entity {

    private Long tenantId;

    private Instant time;

    private BigDecimal affected;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public BigDecimal getAffected() {
        return affected;
    }

    public void setAffected(BigDecimal affected) {
        this.affected = affected;
    }
}
