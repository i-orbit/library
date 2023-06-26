package com.inmaytide.orbit.library.service;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
public interface GeographicCoordinateService {

    List<GeographicCoordinate> findByAttribution(Long attribution);

    Map<Long, List<GeographicCoordinate>> findByAttributions(List<Long> attributions);

    AffectedResult persist(List<GeographicCoordinate> locations);

}
