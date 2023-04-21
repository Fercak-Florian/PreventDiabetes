package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.ReportBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "report", url = "localhost:8083")
public interface MicroserviceReportProxy {

    @GetMapping("/report/{id}")
    ReportBean getReport(@PathVariable("id") int id);
}
