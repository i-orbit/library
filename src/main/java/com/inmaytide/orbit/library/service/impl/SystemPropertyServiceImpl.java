package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.library.mapper.SystemPropertyMapper;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<String, SystemProperty> exist = findByTenant(tenantId).stream().collect(Collectors.toMap(SystemProperty::getKey, Function.identity()));
        Map<String, SystemProperty> global = findByTenant(Constants.NON_TENANT_ID).stream().collect(Collectors.toMap(SystemProperty::getKey, Function.identity()));
        for (String key : global.keySet()) {
            if (exist.containsKey(key)) {
                continue;
            }
            SystemProperty property = global.get(key);
            property.setTenantId(tenantId);
            if (property.getGlobal() == Is.N) {
                property.setValue(null);
            }
            mapper.insert(property);
        }
    }

    @Override
    public Optional<Integer> getIntValue(Long tenant, String key) {
        Optional<String> value = getValue(tenant, key);
        return value.isPresent() && NumberUtils.isCreatable(value.get()) ? Optional.of(NumberUtils.createInteger(value.get())) : Optional.empty();
    }

    @Override
    public Optional<String> getValue(Long tenant, String key) {
        LambdaQueryWrapper<SystemProperty> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SystemProperty::getTenantId, tenant);
        wrapper.eq(SystemProperty::getKey, key);
        SystemProperty entity = mapper.selectOne(wrapper);
        return entity == null ? Optional.empty() : Optional.of(entity.getValue());
    }
}
