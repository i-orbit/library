package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.library.mapper.SystemPropertyMapper;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

    private final SystemPropertyMapper mapper;

    public SystemPropertyServiceImpl(SystemPropertyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<SystemProperty> findByTenant(Long tenantId) {
        LambdaQueryWrapper<SystemProperty> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SystemProperty::getTenantId, tenantId);
        return mapper.selectList(wrapper);
    }

    @Override
    public void initializeForTenant(Long tenantId) {

    }

    @Override
    public Integer getIntValue(Long tenant, String key) {
        String value = getValue(tenant, key);
        if (NumberUtils.isCreatable(value)) {
            return NumberUtils.createInteger(value);
        }
        return null;
    }

    @Override
    public String getValue(Long tenant, String key) {
        LambdaQueryWrapper<SystemProperty> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SystemProperty::getTenantId, tenant);
        wrapper.eq(SystemProperty::getKey, key);
        SystemProperty entity = mapper.selectOne(wrapper);
        return entity == null ? null : entity.getValue();
    }
}
