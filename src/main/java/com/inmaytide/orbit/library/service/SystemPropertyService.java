package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.commons.domain.SystemProperty;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public interface SystemPropertyService {

    List<SystemProperty> findByTenant(Long tenantId);

    void initializeForTenant(Long tenantId);

    Integer getIntValue(Long tenant, String key);

    String getValue(Long tenant, String key);
}
