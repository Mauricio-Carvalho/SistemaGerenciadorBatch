package br.com.dxc.cards.model;

public class Job extends JIL{

    private String stdOutFile;
    private String stdErrFile;
    private String command;

    public Job(String type, String typeJob, String name, String description, String owner, String application, String stdOutFile, String stdErrFile, String command) {
        super(type, typeJob, name, description, owner, application);
        this.stdOutFile = stdOutFile;
        this.stdErrFile = stdErrFile;
        this.command = command;
    }

    public String getStdOutFile() {
        return stdOutFile;
    }

    public void setStdOutFile(String stdOutFile) {
        this.stdOutFile = stdOutFile;
    }

    public String getStdErrFile() {
        return stdErrFile;
    }

    public void setStdErrFile(String stdErrFile) {
        this.stdErrFile = stdErrFile;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

//    job
//    alarm_if_terminated: y
//    avg_runtime: 5
//    priority: 2
//    job_load: 2
//    ----------------------------