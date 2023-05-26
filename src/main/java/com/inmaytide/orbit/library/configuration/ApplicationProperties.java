package com.inmaytide.orbit.library.configuration;

import com.inmaytide.orbit.commons.configuration.CommonProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author inmaytide
 * @since 2023/4/12
 */
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties extends CommonProperties {

    private long autoCleaningOperationLogIntervalInDays = 180;

    public long getAutoCleaningOperationLogIntervalInDays() {
        return autoCleaningOperationLogIntervalInDays;
    }

    public void setAutoCleaningOperationLogIntervalInDays(long autoCleaningOperationLogIntervalInDays) {
        this.autoCleaningOperationLogIntervalInDays = autoCleaningOperationLogIntervalInDays;
    }

}
