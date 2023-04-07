package com.mediscreen.report.proxy;

import com.mediscreen.report.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient", url = "localhost:8081")
public interface MicroservicePatientProxy {

    @GetMapping("/patient/{id}")
    PatientBean getPatientById(@PathVariable String id);
}
