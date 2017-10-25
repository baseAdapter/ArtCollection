package com.tsutsuku.artcollection.model;

/**
 * Created by DengChao on 2017/10/25.
 */

public class ShareModel extends BaseModel{

    /**
     * list : {"invite_code":"38AJXY","invite_url":"http://ysscwx.51urmaker.com/thinkphp/index.php?m=home&c=SMS&a=index&code=","total_nums":"0","total_gold":"0"}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * invite_code : 38AJXY
         * invite_url : http://ysscwx.51urmaker.com/thinkphp/index.php?m=home&c=SMS&a=index&code=
         * total_nums : 0
         * total_gold : 0
         */

        private String invite_code;
        private String invite_url;
        private String total_nums;
        private String total_gold;

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getInvite_url() {
            return invite_url;
        }

        public void setInvite_url(String invite_url) {
            this.invite_url = invite_url;
        }

        public String getTotal_nums() {
            return total_nums;
        }

        public void setTotal_nums(String total_nums) {
            this.total_nums = total_nums;
        }

        public String getTotal_gold() {
            return total_gold;
        }

        public void setTotal_gold(String total_gold) {
            this.total_gold = total_gold;
        }
    }
}
