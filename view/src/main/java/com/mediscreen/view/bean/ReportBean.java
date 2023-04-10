package com.mediscreen.view.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportBean {
    private PatientBean patientBean;
    private String riskLevel;
}
