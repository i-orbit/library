package com.inmaytide.orbit.library.api;

import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Tag(name = "系统配置")
@RestController
@RequestMapping("/api/system/properties")
public class SystemPropertyResource {

    private final SystemPropertyService service;

    public SystemPropertyResource(SystemPropertyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "查询指定租户的系统配置")
    public List<SystemProperty> getProperties(@RequestParam Long tenantId) {
        Map<String, SystemProperty> properties = service.findByTenant(tenantId).stream().collect(Collectors.toMap(SystemProperty::getKey, Function.identity()));
        Map<String, SystemProperty> globalProperties = service.findByTenant(Constants.NON_TENANT_ID).stream().collect(Collectors.toMap(SystemProperty::getKey, Function.identity()));
        List<SystemProperty> res = new ArrayList<>();
        globalProperties.forEach((key, value) -> {
            if (properties.containsKey(key) && properties.get(key).getExposed() == Is.Y) {
                res.add(properties.get(key));
            } else {
                if (value.getGlobal() == Is.Y && value.getExposed() == Is.Y) {
                    res.add(value);
                } else {
                    res.add(SystemProperty.empty(tenantId, key));
                }
            }
        });
        return res;
    }

}
