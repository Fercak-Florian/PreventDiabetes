package com.mediscreen.view.controller;

import com.mediscreen.view.bean.LightPatientBean;
import com.mediscreen.view.bean.NoteBean;
import com.mediscreen.view.bean.PatientBean;
import com.mediscreen.view.bean.ReportBean;
import com.mediscreen.view.exception.NoteNotFoundException;
import com.mediscreen.view.exception.PatientNotFoundException;
import com.mediscreen.view.model.Note;
import com.mediscreen.view.model.Patient;
import com.mediscreen.view.proxy.MicroserviceNoteProxy;
import com.mediscreen.view.proxy.MicroservicePatientProxy;
import com.mediscreen.view.proxy.MicroserviceReportProxy;
import com.mediscreen.view.utils.FormComment;
import com.mediscreen.view.utils.LightPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ViewController {

    private MicroservicePatientProxy microservicePatientProxy;
    private MicroserviceNoteProxy microserviceNoteProxy;

    private MicroserviceReportProxy microserviceReportProxy;
    private FormComment formComment;

    public ViewController(MicroservicePatientProxy microservicePatientProxy, MicroserviceNoteProxy microserviceNoteProxy, MicroserviceReportProxy microserviceReportProxy, FormComment formComment) {
        this.microservicePatientProxy = microservicePatientProxy;
        this.microserviceNoteProxy = microserviceNoteProxy;
        this.microserviceReportProxy = microserviceReportProxy;
        this.formComment = formComment;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    /*------------------------ patient ------------------------*/
    @GetMapping("/patient/list")
    public String getPatients(Model model) {
        List<PatientBean> patients = microservicePatientProxy.getPatients();
        model.addAttribute("patients", patients);
        log.info("display patient list");
        return "list";
    }

    /*This method only display UI to search a patient*/
    @GetMapping("/patient/search")
    public String showSearchPatientForm(Model model) {
        LightPatient lightPatient = new LightPatient();
        Patient patient = new Patient();
        model.addAttribute("lightPatient", lightPatient);
        model.addAttribute("formComment", formComment);
        log.info("display patient information");
        return "search";
    }

    @GetMapping("/patient/{id}")
    public String showPatientInformation(@PathVariable String id, Model model) {
        PatientBean patientBean = new PatientBean();
        List<NoteBean> notesBeans;
        Boolean idProblem = false;

        try {
            patientBean = microservicePatientProxy.getPatient(id);
        } catch (PatientNotFoundException e) {
            log.warn("patient does not exist");
            patientBean = null;
        }

        try {
            notesBeans = microserviceNoteProxy.getNotesByPatientId(id);
        } catch (NoteNotFoundException e) {
            notesBeans = null;
        }

        if (patientBean == null) {
            log.warn("patient does not exist");
            /*gerer le cas d'un id corrompu*/
            idProblem = true;
            model.addAttribute("idProblem", idProblem);
            return "get";
        } else
            model.addAttribute("idProblem", idProblem);
        model.addAttribute("patient", patientBean);

        if (notesBeans != null) {
            model.addAttribute("notes", notesBeans);
            log.info("display patient information");
            return "get";
        } else {
            log.warn("note list is empty");
            model.addAttribute("notes", new ArrayList<>());
            return "get";
        }
    }

    /*This method is activated when submit POST Search Patient Button*/
    @PostMapping("/patient/get")
    public String getPatient(@Valid LightPatient lightPatient, BindingResult result, Model model) {
        PatientBean patientBean = new PatientBean();
        List<NoteBean> notesBeans = new ArrayList<>();
        if (result.hasErrors()) {
            model.addAttribute("formComment", formComment);
            log.warn("error in user input");
            return "search";
        } else {
            try {
                LightPatientBean lightPatientBean = new LightPatientBean(lightPatient.getFirstName(), lightPatient.getLastName());
                patientBean = microservicePatientProxy.getPatientByFirstNameAndLastName(lightPatientBean);
                return "redirect:/patient/" + patientBean.getId();
            } catch (Exception e) {
                log.warn("patient does not exist");
                formComment.setError(true);
                formComment.setMessage("Patient does not exist");
                model.addAttribute("formComment", formComment);
                return "search";
            }
        }
    }

    /*This method only display the form to add a patient*/
    @GetMapping("/patient/add")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        log.info("display form to add a patient");
        return "add";
    }

    /*This method is activated when submit POST Add Patient Button*/
    @PostMapping("/patient/validate")
    public String validatePatient(@Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("patient not saved");
            return "add";
        } else {
            PatientBean patientBean = new PatientBean(patient.getId(), patient.getLastName(), patient.getFirstName(), patient.getDob(), patient.getSex(), patient.getAddress(), patient.getPhone());
            microservicePatientProxy.addPatient(patientBean);
            log.info("patient saved");
            return "redirect:/patient/list";
        }
    }

    /*This method only display the form to update a patient*/
    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") String id, Model model) {
        PatientBean patientBean = microservicePatientProxy.getPatient(id);
        model.addAttribute("patient", patientBean);
        return "update";
    }

    /*This method is activated when submit POST Update Patient Button*/
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") String id, @Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("patient not updated");
            return "update";
        } else {
            PatientBean patientBean = new PatientBean(patient.getId(), patient.getLastName(), patient.getFirstName(), patient.getDob(), patient.getSex(), patient.getAddress(), patient.getPhone());
            microservicePatientProxy.updatePatient(patientBean, id);
            return "redirect:/patient/" + patient.getId();
        }
    }

    /*This method is used when DELETE link is clicked*/
    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable("id") String id) {
        microservicePatientProxy.deletePatient(id);
        return "redirect:/patient/list";
    }

    /*------------------------ note ------------------------*/

    /*This method displays the form to update a note*/
    @GetMapping("/note/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        NoteBean noteBean = microserviceNoteProxy.getNote(id);
        model.addAttribute("note", noteBean);
        return "note/update";
    }

    @PostMapping("/note/update/{id}")
    public String updateNote(@PathVariable String id, @Valid Note note, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("note not updated");
            return "note/update";
        } else {
            NoteBean noteBean = new NoteBean(note.getId(), note.getPatientId(), note.getDateOfCreation(), note.getContent());
            microserviceNoteProxy.updateNote(noteBean, id);
            log.info("note updated");
            return "redirect:/patient/" + note.getPatientId();
        }
    }

    /*This method only display the form to add a note*/
    @GetMapping("/note/add/{id}")
    public String showAddNoteForm(Model model, @PathVariable String id) {
        model.addAttribute("note", new Note());
        model.addAttribute("patientId", id);
        log.info("display form to add a note");
        return "note/add";
    }

    /*This method is called when SUBMIT Add Note button is clicked*/
    @PostMapping("/note/add")
    public String addNote(@Valid Note note, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("error in user input");
            return "note/add";
        } else {
            NoteBean noteBean = new NoteBean(note.getId(), note.getPatientId(), note.getDateOfCreation(), note.getContent());
            microserviceNoteProxy.addNote(noteBean);
            log.info("note added");
            return "redirect:/patient/" + note.getPatientId();
        }
    }

    /*This method is called when DELETE link is clicked*/
    @GetMapping("note/delete/{id}")
    public String deleteNote(@PathVariable String id) {
        NoteBean noteBean = microserviceNoteProxy.deleteNote(id);
        log.info("note deleted");
        return "redirect:/patient/" + noteBean.getPatientId();
    }

    /*------------------------ report ------------------------*/

    @GetMapping("report/{id}")
    public String displayReport(@PathVariable String id, Model model) {
        ReportBean reportBean = microserviceReportProxy.getReport(id);
        model.addAttribute("report", reportBean);
        return "report/report";
    }
}
