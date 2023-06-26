package com.inmaytide.orbit.library.api;

import com.inmaytide.exception.web.BadRequestException;
import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.utils.CommonUtils;
import com.inmaytide.orbit.library.configuration.ErrorCode;
import com.inmaytide.orbit.library.service.DictionaryService;
import com.inmaytide.orbit.library.service.GeographicCoordinateService;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Tag(name = "后端微服务调用接口")
@RestController
@RequestMapping("/api/backend")
public class BackendResource {

    private final SystemPropertyService propertyService;

    private final DictionaryService dictionaryService;

    private final GeographicCoordinateService geographicCoordinateService;

    public BackendResource(SystemPropertyService propertyService, DictionaryService dictionaryService, GeographicCoordinateService geographicCoordinateService) {
        this.propertyService = propertyService;
        this.dictionaryService = dictionaryService;
        this.geographicCoordinateService = geographicCoordinateService;
    }

    @GetMapping("/system/properties")
    @Operation(summary = "通过全局配置初始化租户私有化的系统配置列表, 已存在的不处理")
    public void initializeSystemPropertiesForTenant(@RequestParam Long tenantId) {
        propertyService.initializeForTenant(tenantId);
    }

    @GetMapping("/system/properties/{tenantId}/{key}/value")
    @Operation(summary = "查询指定租户自定配置项值")
    public String getPropertyValue(@PathVariable("tenantId") Long tenantId, @PathVariable("key") String key) {
        Optional<String> value = propertyService.getValue(tenantId, key);
        if (value.isEmpty()) {
            propertyService.initializeForTenant(tenantId);
            value = propertyService.getValue(tenantId, key);
        }
        return value.orElseThrow(() -> new BadRequestException(ErrorCode.E_0x00300002));
    }

    @GetMapping("/dictionaries/find-names-by-codes")
    @Operation(summary = "通过数据字典编码查询数据字典名称")
    public Map<String, String> findDictionaryNamesByCodes(@RequestParam("codes") String codes) {
        return dictionaryService.findNamesByCodes(codes);
    }

    @Operation(summary = "查询指定数据对象关联的地理坐标点")
    @GetMapping("/geographic-coordinates/{attribution}")
    public List<GeographicCoordinate> findGeographicCoordinatesByAttribution(@PathVariable Long attribution) {
        return geographicCoordinateService.findByAttribution(attribution);
    }

    @Operation(summary = "查询一组指定数据对象关联的地理坐标点")
    @GetMapping("/geographic-coordinates")
    public Map<Long, List<GeographicCoordinate>> findGeographicCoordinatesByAttributions(@RequestParam("attributions") String attributions) {
        return geographicCoordinateService.findByAttributions(CommonUtils.splitToLongByCommas(attributions));
    }

    @Operation(summary = "批量保存地理坐标点", description = "执行保存前, 会删除对应数据对象已存在的地理坐标点数据")
    @PostMapping("/geographic-coordinates")
    public AffectedResult persistGeographicCoordinates(List<GeographicCoordinate> coordinates) {
        return geographicCoordinateService.persist(coordinates);
    }

}
