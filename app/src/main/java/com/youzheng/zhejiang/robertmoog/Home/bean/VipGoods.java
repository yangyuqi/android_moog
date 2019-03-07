package com.youzheng.zhejiang.robertmoog.Home.bean;

import java.util.List;

public class VipGoods {



        private List<IntentInfoListBean> intentInfoList;

        public List<IntentInfoListBean> getIntentInfoList() {
            return intentInfoList;
        }

        public void setIntentInfoList(List<IntentInfoListBean> intentInfoList) {
            this.intentInfoList = intentInfoList;
        }

        public static class IntentInfoListBean {
            /**
             * id : mock
             * name : mock
             * businessRole : mock
             * remark : mock
             * intentId : mock
             * productList : [{"id":"mock","name":"mock","price":"mock","sku":"mock","photo":"mock"}]
             */

            private String id;
            private String name;
            private String businessRole;
            private String remark;
            private int intentId;
            private List<ProductListBean> productList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBusinessRole() {
                return businessRole;
            }

            public void setBusinessRole(String businessRole) {
                this.businessRole = businessRole;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getIntentId() {
                return intentId;
            }

            public void setIntentId(int intentId) {
                this.intentId = intentId;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                /**
                 * id : mock
                 * name : mock
                 * price : mock
                 * sku : mock
                 * photo : mock
                 */

                private String id;
                private String name;
                private String price;
                private String sku;
                private String photo;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }
            }
        }

}
