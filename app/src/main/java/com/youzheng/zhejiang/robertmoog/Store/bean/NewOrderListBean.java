package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class NewOrderListBean {



        /**
         * totalSize : 3
         * totalPage : 1
         * pageSize : 10
         * orderList : [{"id":"80","productNum":1,"orderCode":"D1548784201901070011","payAmount":"500","createDate":"2019-01-07 16:14:37","orderItemInfos":[{"photo":"","code":"cs1003","name":"测试商品  马桶"}]},{"id":"81","productNum":1,"orderCode":"D1548784201901070012","payAmount":"8688","createDate":"2019-01-07 19:03:23","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"82","productNum":1,"orderCode":"D1548784201901070013","payAmount":"8688","createDate":"2019-01-07 19:11:35","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"83","productNum":2,"orderCode":"12345601201901070014","payAmount":"18676","createDate":"2019-01-07 19:13:00","orderItemInfos":[{"photo":"","code":"cs1003","name":"测试商品  马桶"},{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"84","productNum":1,"orderCode":"12345601201901070015","payAmount":"17476","createDate":"2019-01-07 19:14:24","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"85","productNum":1,"orderCode":"12345601201901070016","payAmount":"17476","createDate":"2019-01-07 19:14:30","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"86","productNum":1,"orderCode":"D1548784201901070017","payAmount":"8688","createDate":"2019-01-07 19:27:46","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"87","productNum":1,"orderCode":"D1548784201901070018","payAmount":"8688","createDate":"2019-01-07 19:34:38","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"88","productNum":1,"orderCode":"D1548784201901070019","payAmount":"8688","createDate":"2019-01-07 20:11:10","orderItemInfos":[{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]},{"id":"89","productNum":2,"orderCode":"12345601201901070020","payAmount":"17876","createDate":"2019-01-07 20:53:12","orderItemInfos":[{"photo":"","code":"cs1003","name":"测试商品  马桶"},{"photo":null,"code":"moen(cood2)","name":"摩恩（MOEN） 厨房水龙头冷热水槽龙头洗菜盆水龙头精铜"}]}]
         * pageNum : 1
         */

        private int totalSize;
        private int totalPage;
        private int pageSize;
        private int pageNum;
        private List<OrderListBean> orderList;

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

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * id : 80
             * productNum : 1
             * orderCode : D1548784201901070011
             * payAmount : 500
             * createDate : 2019-01-07 16:14:37
             * orderItemInfos : [{"photo":"","code":"cs1003","name":"测试商品  马桶"}]
             */

            private String id;
            private int productNum;
            private String orderCode;
            private String payAmount;
            private String createDate;
            private List<OrderItemInfosBean> orderItemInfos;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public List<OrderItemInfosBean> getOrderItemInfos() {
                return orderItemInfos;
            }

            public void setOrderItemInfos(List<OrderItemInfosBean> orderItemInfos) {
                this.orderItemInfos = orderItemInfos;
            }

            public static class OrderItemInfosBean {
                /**
                 * photo :
                 * code : cs1003
                 * name : 测试商品  马桶
                 */

                private String photo;
                private String code;
                private String name;

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

}
