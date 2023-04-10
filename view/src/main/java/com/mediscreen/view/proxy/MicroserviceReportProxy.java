package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.ReportBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "report", url = "localhost:8083")
public interface MicroserviceReportProxy {

    @GetMapping("/report")
    ReportBean getReport();
}
