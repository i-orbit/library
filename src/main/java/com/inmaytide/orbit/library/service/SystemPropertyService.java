package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.commons.domain.SystemProperty;

import java.util.List;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
public interface SystemPropertyService {

    List<SystemProperty> findByTenant(Long tenantId);

    void initializeForTenant(Long tenantId);

    Optional<Integer> getIntValue(Long tenant, String key);

    Optional<String> getValue(Long tenant, String key);
}
