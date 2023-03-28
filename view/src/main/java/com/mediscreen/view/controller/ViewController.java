package com.mediscreen.view.controller;

import com.mediscreen.view.bean.PatientBean;
import com.mediscreen.view.proxy.MicroservicePatientProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private MicroservicePatientProxy microservicePatientProxy;

    public ViewController(MicroservicePatientProxy microservicePatientProxy){
        this.microservicePatientProxy = microservicePatientProxy;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/patient/list")
    public String getPatients(Model model){
        List<PatientBean> patients = microservicePatientProxy.getPatients();
        model.addAttribute("patients", patients);
        return "list";
    }
}
