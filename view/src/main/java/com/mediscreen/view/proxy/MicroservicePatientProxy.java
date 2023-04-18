package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.LightPatientBean;
import com.mediscreen.view.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "patient", url = "localhost:8081")
public interface MicroservicePatientProxy {

    /*SIGNATURES DES METHODES PRESENTES DANS PatientController*/

    @RequestMapping(value = "/patient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<PatientBean> getPatients();

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean getPatientById(@PathVariable("id") int id);

    @RequestMapping(value = "/patient/lightPatient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean getPatientByFirstNameAndLastName(@RequestBody LightPatientBean lightPatientBean);

    @RequestMapping(value = "/patient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean addPatient(@RequestBody PatientBean patientBean);

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean updatePatient(@RequestBody PatientBean patientBean, @PathVariable int id);

    @RequestMapping(value = "/patient/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    PatientBean deletePatient(@PathVariable int id);
}
