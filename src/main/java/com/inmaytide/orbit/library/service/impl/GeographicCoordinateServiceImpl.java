package com.inmaytide.orbit.library.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.library.mapper.GeographicCoordinateMapper;
import com.inmaytide.orbit.library.service.GeographicCoordinateService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
@Service
public class GeographicCoordinateServiceImpl implements GeographicCoordinateService {

    private final GeographicCoordinateMapper mapper;

    public GeographicCoordinateServiceImpl(GeographicCoordinateMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<GeographicCoordinate> findByAttribution(Long attribution) {
        return mapper.selectList(Wrappers.<GeographicCoordinate>lambdaQuery().eq(GeographicCoordinate::getAttribution, attribution));
    }

    @Override
    public Map<Long, List<GeographicCoordinate>> findByAttributions(List<Long> attributions) {
        if (attributions.isEmpty()) {
            return Collections.emptyMap();
        }
        return mapper.selectList(Wrappers.<GeographicCoordinate>lambdaQuery().in(GeographicCoordinate::getAttribution, attributions))
                .stream()
                .collect(Collectors.groupingBy(GeographicCoordinate::getAttribution, Collectors.toList()));
    }

    @Override
    public AffectedResult persist(List<GeographicCoordinate> locations) {
        List<Long> attributions = locations.stream().map(GeographicCoordinate::getAttribution).filter(Objects::nonNull).distinct().toList();
        if (attributions.isEmpty()) {
            return AffectedResult.notAffected();
        }
        int affected = mapper.delete(Wrappers.<GeographicCoordinate>lambdaQuery().in(GeographicCoordinate::getAttribution, attributions));
        for (GeographicCoordinate location : locations) {
            affected += mapper.insert(location);
        }
        return AffectedResult.of(affected);
    }
}
