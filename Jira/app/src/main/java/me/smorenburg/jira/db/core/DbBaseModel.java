package me.smorenburg.jira.db.core;


import java.io.Serializable;

public class DbBaseModel implements Serializable{

    enum FLAG {

        DELETE(0),//Will remove from server
        UPDATE(1), //Will update on server
        DOWNLOAD(2), // Will download from server and overwrite existent
        UPLOAD(3); // Will upload  new to server

        private Integer flag;

        FLAG(Integer _flag) {
            flag = _flag;
        }

        @Override
        public String toString() {
            return super.toString();
        }

        public Integer getFlag() {
            return flag;
        }

        public static boolean isAFlag(Integer possibleFlag) {
            if (possibleFlag == null)
                return false;
            return possibleFlag.equals(DELETE.getFlag())
                    || possibleFlag.equals(UPDATE.getFlag())
                    || possibleFlag.equals(DOWNLOAD.getFlag())
                    || possibleFlag.equals(UPLOAD.getFlag());
        }
    }

    @Override
    public String toString() {
        return "\\* "+getClass().getSimpleName() + " Id: " + getId();
    }

    public Long getId() {
        return -1L;
    }
 }
