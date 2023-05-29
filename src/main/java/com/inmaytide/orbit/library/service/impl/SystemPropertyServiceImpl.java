package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.library.mapper.SystemPropertyMapper;
import com.inmaytide.orbit.library.service.SystemPropertyService;
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
}
