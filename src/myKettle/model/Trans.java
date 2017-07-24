package myKettle.model;

import java.io.Serializable;

public class Trans implements Serializable {
    private Integer trans_id;
    private String metadata;
    private String metadata_desc;
    private String cron_expression;
    private String cron_desc;
    private String trans_name;
    private Integer schedule_stat;
    private Integer owner_id;

    private String steps;
    private String hops;
    private String dbs;

    public String getDbs() {
        return dbs;
    }

    public void setDbs(String dbs) {
        this.dbs = dbs;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getHops() {
        return hops;
    }

    public void setHops(String hops) {
        this.hops = hops;
    }

    public Integer getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(Integer trans_id) {
        this.trans_id = trans_id;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata_desc() {
        return metadata_desc;
    }

    public void setMetadata_desc(String metadata_desc) {
        this.metadata_desc = metadata_desc;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
        this.cron_expression = cron_expression;
    }

    public String getCron_desc() {
        return cron_desc;
    }

    public void setCron_desc(String cron_desc) {
        this.cron_desc = cron_desc;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public Integer getSchedule_stat() {
        return schedule_stat;
    }

    public void setSchedule_stat(Integer schedule_stat) {
        this.schedule_stat = schedule_stat;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }
}
