package me.smorenburg.jira.db.models.base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Location_1 extends me.smorenburg.jira.db.core.DbBaseModel {

    @Id(autoincrement = true)
    @Unique
    private Long id;

    @ToOne(joinProperty = "webServiceDataId")
    private WebServiceData webServiceData;

    private Long webServiceDataId;

    private Double latitude;
    private String human_address;
    private String needs_recoding;
    private Double longitude;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 787954344)
    private transient Location_1Dao myDao;
    @Generated(hash = 1036214841)
    public Location_1(Long id, Long webServiceDataId, Double latitude,
            String human_address, String needs_recoding, Double longitude) {
        this.id = id;
        this.webServiceDataId = webServiceDataId;
        this.latitude = latitude;
        this.human_address = human_address;
        this.needs_recoding = needs_recoding;
        this.longitude = longitude;
    }
    @Generated(hash = 721246797)
    public Location_1() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getWebServiceDataId() {
        return this.webServiceDataId;
    }
    public void setWebServiceDataId(Long webServiceDataId) {
        this.webServiceDataId = webServiceDataId;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public String getHuman_address() {
        return this.human_address;
    }
    public void setHuman_address(String human_address) {
        this.human_address = human_address;
    }
    public String getNeeds_recoding() {
        return this.needs_recoding;
    }
    public void setNeeds_recoding(String needs_recoding) {
        this.needs_recoding = needs_recoding;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    @Generated(hash = 1523903166)
    private transient Long webServiceData__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1344100690)
    public WebServiceData getWebServiceData() {
        Long __key = this.webServiceDataId;
        if (webServiceData__resolvedKey == null
                || !webServiceData__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WebServiceDataDao targetDao = daoSession.getWebServiceDataDao();
            WebServiceData webServiceDataNew = targetDao.load(__key);
            synchronized (this) {
                webServiceData = webServiceDataNew;
                webServiceData__resolvedKey = __key;
            }
        }
        return webServiceData;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1309143818)
    public void setWebServiceData(WebServiceData webServiceData) {
        synchronized (this) {
            this.webServiceData = webServiceData;
            webServiceDataId = webServiceData == null ? null
                    : webServiceData.getId();
            webServiceData__resolvedKey = webServiceDataId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\nLocation_1{");
        sb.append(" id=").append(id);
        sb.append(" webServiceData=").append(webServiceData);
        sb.append(" webServiceDataId=").append(webServiceDataId);
        sb.append(" latitude=").append(latitude);
        sb.append(" human_address='").append(human_address).append('\'');
        sb.append(" needs_recoding='").append(needs_recoding).append('\'');
        sb.append(" longitude=").append(longitude);
        sb.append('}');
        return sb.toString();
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1148825013)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocation_1Dao() : null;
    }
}
