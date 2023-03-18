package com.demo.kafka.context;

import lombok.extern.slf4j.Slf4j;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author ksssss
 * @Date: 21-2-3 15:57
 * @Description:
 */
@Slf4j
public class MbeanServerUtil {
    private static MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    public static void registerMBean(Object obj, String name) {
        try {
            ObjectName objectName = new ObjectName(name);
            server.registerMBean(obj, objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            log.error(e.getMessage());
        }
    }
}
