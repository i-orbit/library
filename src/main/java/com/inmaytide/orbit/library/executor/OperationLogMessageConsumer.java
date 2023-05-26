package com.inmaytide.orbit.library.executor;

import com.inmaytide.orbit.commons.consts.Marks;
import com.inmaytide.orbit.commons.consts.RabbitMQ;
import com.inmaytide.orbit.commons.log.OperationLogMessageProducer;
import com.inmaytide.orbit.commons.log.domain.OperationLog;
import com.inmaytide.orbit.library.mapper.OperationLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class OperationLogMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OperationLogMessageConsumer.class);

    private final OperationLogMapper mapper;

    private final Searcher searcher;

    public OperationLogMessageConsumer(OperationLogMapper mapper, Searcher searcher) {
        this.mapper = mapper;
        this.searcher = searcher;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(OperationLogMessageProducer.ROUTE_KEY_OPERATION_LOG),
            exchange = @Exchange(value = RabbitMQ.DIRECT_EXCHANGE),
            key = OperationLogMessageProducer.ROUTE_KEY_OPERATION_LOG
    ))
    public void onReceived(OperationLog log) {
        String ipAddress = log.getIpAddress();
        if (StringUtils.isNotBlank(ipAddress)) {
            log.setIpAddress(ipAddress + "(" + searchIpAddressGeolocation(ipAddress) + ")");
        }
        mapper.insert(log);
    }

    protected String searchIpAddressGeolocation(@Nullable String ipAddress) {
        if (StringUtils.isNotBlank(ipAddress)) {
            if (StringUtils.equalsIgnoreCase(ipAddress, "localhost") || "127.0.0.1".equals(ipAddress)) {
                return Marks.LOCAL.getValue();
            }
            try {
                String region = searcher.search(ipAddress);
                if (StringUtils.isBlank(region)) {
                    return Marks.NOT_APPLICABLE.getValue();
                }
                String[] regions = region.split("\\|");
                String country = regions[0];
                for (int i = regions.length - 2; i >= 0; i--) {
                    if ("内网IP".equalsIgnoreCase(regions[i])) {
                        return Marks.LAN.getValue();
                    }
                    if (!"0".equals(regions[i])) {
                        return i != 0 ? country + "-" + regions[i] : regions[i];
                    }
                }
            } catch (Exception e) {
                if (LOG.isDebugEnabled()) {
                    LOG.error("Failed to search geolocation of ip address \"{}\" with ip2region Searcher, Cause by: \n", ipAddress, e);
                } else {
                    LOG.error("Failed to search geolocation of ip address \"{}\" with ip2region Searcher", ipAddress);
                }
            }
        }
        return Marks.NOT_APPLICABLE.getValue();
    }


}
