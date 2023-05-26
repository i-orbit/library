package com.inmaytide.orbit.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2023/5/26
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
