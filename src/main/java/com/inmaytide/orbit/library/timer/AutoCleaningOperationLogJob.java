package com.inmaytide.orbit.library.timer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inmaytide.orbit.commons.consts.Constants;
import com.inmaytide.orbit.commons.domain.Tenant;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.commons.metrics.AbstractJob;
import com.inmaytide.orbit.commons.service.uaa.TenantService;
import com.inmaytide.orbit.library.domain.OperationLogCleaning;
import com.inmaytide.orbit.library.mapper.OperationLogCleaningMapper;
import com.inmaytide.orbit.library.mapper.OperationLogMapper;
import com.inmaytide.orbit.library.service.SystemPropertyService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * 操作日志自动清理定时任务Job
 *
 * @author inmaytide
 * @since 2023/5/30
 */
public class AutoCleaningOperationLogJob extends AbstractJob {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCleaningOperationLogJob.class);

    @Autowired
    private OperationLogCleaningMapper cleaningMapper;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private SystemPropertyService propertyService;

    @Override
    public Logger getLogger() {
        return LOG;
    }

    @Override
    public String getName() {
        return "job_auto_cleaning_operation_log";
    }

    @Override
    protected void exec(JobExecutionContext context) {
        List<Tenant> tenants = tenantService.all();
        for (Tenant e : tenants) {
            propertyService
                    .getIntValue(e.getId(), Constants.SPK_OP_LOG_RETENTION_TIME_IN_DAYS)
                    .filter(value -> value != -1)
                    .ifPresent(value -> {
                        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
                        wrapper.eq(OperationLog::getTenantId, e.getId());
                        wrapper.apply("operation_time < TIMESTAMPADD(DAY, {0}, now())", value);
                        int affected = operationLogMapper.delete(wrapper);
                        if (affected > 0) {
                            OperationLogCleaning entity = new OperationLogCleaning();
                            entity.setAffected(BigDecimal.valueOf(affected));
                            entity.setTime(Instant.now());
                            entity.setTenantId(e.getId());
                            cleaningMapper.insert(entity);
                        }
                    });
        }

    }
}
