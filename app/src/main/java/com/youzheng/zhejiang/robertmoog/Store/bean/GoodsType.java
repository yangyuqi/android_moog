package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class GoodsType {

        private List<ListDataBean> listData;

        public List<ListDataBean> getListData() {
            return listData;
        }

        public void setListData(List<ListDataBean> listData) {
            this.listData = listData;
        }

        public static class ListDataBean {
            /**
             * id : 1
             * name : mock
             */

            private Integer id;
            private String name;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

}
