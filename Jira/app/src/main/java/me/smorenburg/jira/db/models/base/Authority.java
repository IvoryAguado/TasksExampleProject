package me.smorenburg.jira.db.models.base;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "AUTHORITY".
 */
@Entity
public class Authority extends me.smorenburg.jira.db.core.DbBaseModel  {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private Long authority_id;

    @Unique
    private String authority_name;

    public Authority(Long id) {
        this.id = id;
    }


    @Generated(hash = 585114009)
    public Authority(Long id, Long authority_id, String authority_name) {
        this.id = id;
        this.authority_id = authority_id;
        this.authority_name = authority_name;
    }


    @Generated(hash = 1400823891)
    public Authority() {
    }


    @Override
    public String toString() {
        return authority_name;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getAuthority_id() {
        return this.authority_id;
    }


    public void setAuthority_id(Long authority_id) {
        this.authority_id = authority_id;
    }


    public String getAuthority_name() {
        return this.authority_name;
    }


    public void setAuthority_name(String authority_name) {
        this.authority_name = authority_name;
    }

}
