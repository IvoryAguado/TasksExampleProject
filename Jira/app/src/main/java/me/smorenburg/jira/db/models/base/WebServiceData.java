package me.smorenburg.jira.db.models.base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class WebServiceData extends me.smorenburg.jira.db.core.DbBaseModel {

    @Id(autoincrement = true)
    @Unique
    private Long id;

    private String zipcode;

    @ToOne(joinProperty = "location_1Id")
    private Location_1 location_1;

    private Long location_1Id;

    private String item;
    private String business;

    private String farmer_id;

    private String category;
    private Long l;
    private String farm_name;
    private String phone1;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1373910609)
    private transient WebServiceDataDao myDao;

    @Generated(hash = 1166183885)
    public WebServiceData(Long id, String zipcode, Long location_1Id, String item,
                          String business, String farmer_id, String category, Long l,
                          String farm_name, String phone1) {
        this.id = id;
        this.zipcode = zipcode;
        this.location_1Id = location_1Id;
        this.item = item;
        this.business = business;
        this.farmer_id = farmer_id;
        this.category = category;
        this.l = l;
        this.farm_name = farm_name;
        this.phone1 = phone1;
    }

    @Generated(hash = 1244133)
    public WebServiceData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getLocation_1Id() {
        return this.location_1Id;
    }

    public void setLocation_1Id(Long location_1Id) {
        this.location_1Id = location_1Id;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBusiness() {
        return this.business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getFarmer_id() {
        return this.farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getL() {
        return this.l;
    }

    public void setL(Long l) {
        this.l = l;
    }

    public String getFarm_name() {
        return this.farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Generated(hash = 1161830084)
    private transient Long location_1__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 973990732)
    public Location_1 getLocation_1() {
        Long __key = this.location_1Id;
        if (location_1__resolvedKey == null
                || !location_1__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Location_1Dao targetDao = daoSession.getLocation_1Dao();
            Location_1 location_1New = targetDao.load(__key);
            synchronized (this) {
                location_1 = location_1New;
                location_1__resolvedKey = __key;
            }
        }
        return location_1;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2142668020)
    public void setLocation_1(Location_1 location_1) {
        synchronized (this) {
            this.location_1 = location_1;
            location_1Id = location_1 == null ? null : location_1.getId();
            location_1__resolvedKey = location_1Id;
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

    public Location_1 getLocation_1Raw() {
        return location_1;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\n\nWebServiceData{");
        sb.append(" id=").append(id);
        sb.append(" zipcode='").append(zipcode).append('\'');
        sb.append(" location_1=").append(location_1);
        sb.append(" location_1Id=").append(location_1Id);
        sb.append(" item='").append(item).append('\'');
        sb.append(" business='").append(business).append('\'');
        sb.append(" farmer_id='").append(farmer_id).append('\'');
        sb.append(" category='").append(category).append('\'');
        sb.append(" l=").append(l);
        sb.append(" farm_name='").append(farm_name).append('\'');
        sb.append(" phone1='").append(phone1).append('\'');
         sb.append('}');
        return sb.toString();
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1266569355)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWebServiceDataDao() : null;
    }
}
