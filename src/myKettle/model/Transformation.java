package myKettle.model;

/**
 * Created by zhangzhimin on 6/19/17.
 * 转换类
 */
public class Transformation {
    private int id;
    private String trans_name;
    private String meta_data;
    private String meta_data_desc;
    private String cron_expression;
    private String cron_desc;
    private boolean schedule_stat;


    public String getCron_desc() {
        return cron_desc;
    }

    public void setCron_desc(String cron_desc) {
        this.cron_desc = cron_desc;
    }

    public String getCron_expression() {
        return cron_expression;
    }

    public void setCron_expression(String cron_expression) {
        this.cron_expression = cron_expression;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(String meta_data) {
        this.meta_data = meta_data;
    }

    public String getMeta_data_desc() {
        return meta_data_desc;
    }

    public void setMeta_data_desc(String meta_data_desc) {
        this.meta_data_desc = meta_data_desc;
    }

    public boolean isSchedule_stat() {
        return schedule_stat;
    }

    public void setSchedule_stat(boolean schedule_stat) {
        this.schedule_stat = schedule_stat;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

}
