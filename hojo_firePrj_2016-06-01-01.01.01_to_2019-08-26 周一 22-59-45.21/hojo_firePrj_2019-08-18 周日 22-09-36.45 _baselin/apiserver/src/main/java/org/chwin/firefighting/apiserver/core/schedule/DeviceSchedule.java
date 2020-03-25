package org.chwin.firefighting.apiserver.core.schedule;

import org.chwin.firefighting.apiserver.core.service.DeviceScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeviceSchedule {
    @Autowired
    private DeviceScheduleService deviceScheduleService;

    /**
     * 每天晚上23:50运行一次
     */
    @Scheduled(cron = "0 50 23 * * ?")
    public void deviceStatistics(){
        deviceScheduleService.deviceStatistics();
    }
}
