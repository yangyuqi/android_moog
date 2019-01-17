package com.youzheng.zhejiang.robertmoog.Count.bean;

import java.util.List;

public class GoodsTypeRankingList {

        /**
         * pageNum : 1
         * pageSize : 10
         * totalSize : 25
         * totalPage : 3
         * categoryList : [{"id":"mock","categoryName":"mock","count":"mock"}]
         */

        private int pageNum;
        private int pageSize;
        private int totalSize;
        private int totalPage;
        private List<CategoryListBean> categoryList;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public static class CategoryListBean {
            /**
             * id : mock
             * categoryName : mock
             * count : mock
             */

            private int id;
            private String categoryName;
            private long count;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public long getCount() {
                return count;
            }

            public void setCount(long count) {
                this.count = count;
            }
        }

}
