package com.inmaytide.orbit.library.api;

import com.inmaytide.exception.web.BadRequestException;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.consts.Is;
import com.inmaytide.orbit.commons.domain.SystemProperty;
import com.inmaytide.orbit.library.configuration.ErrorCode;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Tag(name = "后端微服务调用接口")
@RestController
@RequestMapping("/api/backend")
public class BackendResource {

    private final SystemPropertyService service;

    public BackendResource(SystemPropertyService service) {
        this.service = service;
    }

    @GetMapping("/system/properties")
    @Operation(summary = "通过全局配置初始化租户私有化的系统配置列表, 已存在的不处理")
    public void initializeSystemPropertiesForTenant(@RequestParam Long tenantId) {
        service.initializeForTenant(tenantId);
    }

    @GetMapping("/system/properties/{tenantId}/{key}/value")
    @Operation(summary = "查询指定租户自定配置项值")
    public String getPropertyValue(@PathVariable("tenantId") Long tenantId, @PathVariable("key") String key) {
        Optional<String> value = service.getValue(tenantId, key);
        if (value.isEmpty()) {
            service.initializeForTenant(tenantId);
            value = service.getValue(tenantId, key);
        }
        return value.orElseThrow(() -> new BadRequestException(ErrorCode.E_0x00300002));
    }

}
