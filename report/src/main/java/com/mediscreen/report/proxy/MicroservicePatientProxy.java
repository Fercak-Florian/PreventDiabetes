package com.mediscreen.report.proxy;

import com.mediscreen.report.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "patient", url = "localhost:8081")
public interface MicroservicePatientProxy {

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean getPatientById(@PathVariable("id") int id);
}
