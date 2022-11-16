package br.com.dxc.cards.model;

public class FileWatch extends JIL{

    private String watchFile;
    private String watchFileMinSize;
    private String watchInterval;

    public FileWatch(String type, String typeJob, String name, String description, String owner, String application, String watchFile, String watchFileMinSize, String watchInterval) {
        super(type, typeJob, name, description, owner, application);
        this.watchFile = watchFile;
        this.watchFileMinSize = watchFileMinSize;
        this.watchInterval = watchInterval;
    }

    public String getWatchFile() {
        return watchFile;
    }

    public void setWatchFile(String watchFile) {
        this.watchFile = watchFile;
    }

    public String getWatchFileMinSize() {
        return watchFileMinSize;
    }

    public void setWatchFileMinSize(String watchFileMinSize) {
        this.watchFileMinSize = watchFileMinSize;
    }

    public String getWatchInterval() {
        return watchInterval;
    }

    public void setWatchInterval(String watchInterval) {
        this.watchInterval = watchInterval;
    }
}

//    fw
//    avg_runtime: 476
//    box_terminator: y
//    term_run_time: 780
//    ----------------------------