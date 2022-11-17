package br.com.dxc.cards.model;

import java.util.Date;
import java.util.List;

public class JIL {

    private String type;

    private String name;
    private String typeJob;
    private String description;
    private String machine;
    private String owner;
    private String application;

    private String boxName;

    private String condition;

    private boolean dateCondition;
    private Date timeZone;
    private List<String> dayWeek;
    private List<Integer> startMinute;
    private String calendar;

    private String permission;
    private String maxRunAlarm;
    private String alarmFail;
    private String alarmTerminated;
    private String sendNotification;
    private String msgNotification;
    private String emailNotification;
    private String svcDesc;
    private String svcAttr;
    private int svcSev;

    public JIL(String type, String typeJob, String name, String description, String owner, String application) {
        this.type = type;
        this.typeJob = typeJob;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.application = application;
    }

    public JIL() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeJob() {
        return typeJob;
    }

    public void setTypeJob(String typeJob) {
        this.typeJob = typeJob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public boolean isDateCondition() {
        return dateCondition;
    }

    public void setDateCondition(boolean dateCondition) {
        this.dateCondition = dateCondition;
    }

    public Date getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Date timeZone) {
        this.timeZone = timeZone;
    }

    public List<String> getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(List<String> dayWeek) {
        this.dayWeek = dayWeek;
    }

    public List<Integer> getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(List<Integer> startMinute) {
        this.startMinute = startMinute;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getMaxRunAlarm() {
        return maxRunAlarm;
    }

    public void setMaxRunAlarm(String maxRunAlarm) {
        this.maxRunAlarm = maxRunAlarm;
    }

    public String getAlarmFail() {
        return alarmFail;
    }

    public void setAlarmFail(String alarmFail) {
        this.alarmFail = alarmFail;
    }

    public String getAlarmTerminated() {
        return alarmTerminated;
    }

    public void setAlarmTerminated(String alarmTerminated) {
        this.alarmTerminated = alarmTerminated;
    }

    public String getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(String sendNotification) {
        this.sendNotification = sendNotification;
    }

    public String getMsgNotification() {
        return msgNotification;
    }

    public void setMsgNotification(String msgNotification) {
        this.msgNotification = msgNotification;
    }

    public String getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getSvcDesc() {
        return svcDesc;
    }

    public void setSvcDesc(String svcDesc) {
        this.svcDesc = svcDesc;
    }

    public String getSvcAttr() {
        return svcAttr;
    }

    public void setSvcAttr(String svcAttr) {
        this.svcAttr = svcAttr;
    }

    public int getSvcSev() {
        return svcSev;
    }

    public void setSvcSev(int svcSev) {
        this.svcSev = svcSev;
    }
}

