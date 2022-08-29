package com.example.demo.com.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class WorklogProject {

    String accountName;
    String projectCode;
    String workTitle;
    String serviceType;
    String workerName;
    LocalDateTime dateOfWork;
    Double workTime;
    Double employeeFee;
    Double workCost;
    boolean toBeInvoiced;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public LocalDateTime getDateOfWork() {
        return dateOfWork;
    }

    public void setDateOfWork(LocalDateTime dateOfWork) {
        this.dateOfWork = dateOfWork;
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }

    public Double getEmployeeFee() {
        return employeeFee;
    }

    public void setEmployeeFee(Double employeeFee) {
        this.employeeFee = employeeFee;
    }

    public Double getWorkCost() {
        return employeeFee*workTime;
    }

    public void setWorkCost(Double workCost) {
        this.workCost = workCost;
    }

    public boolean isToBeInvoiced() {
        return toBeInvoiced;
    }

    public void setToBeInvoiced(boolean toBeInvoiced) {
        this.toBeInvoiced = toBeInvoiced;
    }

    public WorklogProject() {
    }

    public WorklogProject(String accountName, String projectCode, String workTitle, String serviceType, String workerName, LocalDateTime dateOfWork, Double workTime, Double employeeFee, Double workCost, boolean toBeInvoiced) {
        this.accountName = accountName;
        this.projectCode = projectCode;
        this.workTitle = workTitle;
        this.serviceType = serviceType;
        this.workerName = workerName;
        this.dateOfWork = dateOfWork;
        this.workTime = workTime;
        this.employeeFee = employeeFee;
        this.workCost = workCost;
        this.toBeInvoiced = toBeInvoiced;
    }
}
