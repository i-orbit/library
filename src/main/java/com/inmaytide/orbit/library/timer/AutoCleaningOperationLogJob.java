package com.inmaytide.orbit.library.timer;

import com.inmaytide.orbit.commons.metrics.AbstractJob;
import com.inmaytide.orbit.library.configuration.ApplicationProperties;
import com.inmaytide.orbit.library.mapper.OperationLogCleaningMapper;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作日志自动清理定时任务Job
 *
 * @author inmaytide
 * @since 2023/5/30
 */
public class AutoCleaningOperationLogJob extends AbstractJob {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCleaningOperationLogJob.class);

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private OperationLogCleaningMapper cleaningMapper;

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
        System.out.println(1);
    }
}
