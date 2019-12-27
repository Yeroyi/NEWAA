package com.example.hongligs.bean;

import java.util.List;

public class TreeBean {


    private List<HollowlistBean> hollowlist;

    public List<HollowlistBean> getHollowlist() {
        return hollowlist;
    }

    public void setHollowlist(List<HollowlistBean> hollowlist) {
        this.hollowlist = hollowlist;
    }

    public static class HollowlistBean {
        /**
         * uname : 嘿嘿嘿
         * plnum : 4
         * id : 8fc5b575-1273-11ea-9699-00d861a225ad
         * time : 1575009813000
         * content : 测试！~~
         */

        private String uname;
        private int plnum;
        private String id;
        private long time;
        private String content;

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getPlnum() {
            return plnum;
        }

        public void setPlnum(int plnum) {
            this.plnum = plnum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
