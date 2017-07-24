package myKettle.model;

import java.io.Serializable;

public class DBMessage implements Serializable {
    private Integer db_id;
    private String name;
    private String ip;
    private String db_name;
    private String table_name;
    private String type;
    private String user;
    private String password;
    private String description;
    private Integer owner_id;

    @Override
    public String toString() {
        return "DBMessage{" +
                "db_id=" + db_id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", db_name='" + db_name + '\'' +
                ", table_name='" + table_name + '\'' +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", owner_id=" + owner_id +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Integer getDb_id() {
        return db_id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getDb_name() {
        return db_name;
    }

    public String getTable_name() {
        return table_name;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }


    public Integer getOwner_id() {
        return owner_id;
    }
}
