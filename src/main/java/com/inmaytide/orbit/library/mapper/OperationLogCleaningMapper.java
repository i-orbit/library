package com.inmaytide.orbit.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.library.domain.OperationLogCleaning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author inmaytide
 * @since 2023/5/30
 */
@Mapper
public interface OperationLogCleaningMapper extends BaseMapper<OperationLogCleaning> {

    @Select("select tenant_id, max(time) from operation_log_cleaning group by tenant_id")
    List<OperationLogCleaning> getLastCleanupTimeForEachTenant();

}
