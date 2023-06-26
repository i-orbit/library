package com.inmaytide.orbit.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
@Mapper
public interface GeographicCoordinateMapper extends BaseMapper<GeographicCoordinate> {
}
