package com.youzheng.zhejiang.robertmoog.Home.bean;

import java.util.List;

public class SearchMeal {



        private List<SelectProductsBean> selectProducts;

        public List<SelectProductsBean> getSelectProducts() {
            return selectProducts;
        }

        public void setSelectProducts(List<SelectProductsBean> selectProducts) {
            this.selectProducts = selectProducts;
        }

        public static class SelectProductsBean {
            /**
             * name : mock
             * code : mock
             * photo : mock
             * price : mock
             * id : mock
             * isSetMeal : true
             * activityName : mock
             * comboDescribe : mock
             * isSpecial : true
             * productList : [{"sku":"mock","name":"mock","photo":"mock","price":"mock"}]
             */

            private String name;
            private String code;
            private String photo;
            private String price;
            private String id;
            private boolean isSetMeal;
            private String activityName;
            private String comboDescribe;
            private boolean isSpecial;
            private List<ProductListBean> productList;
            private boolean isIsexpress;

            public boolean isIsexpress() {
                return isIsexpress;
            }

            public void setIsexpress(boolean isexpress) {
                isIsexpress = isexpress;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsSetMeal() {
                return isSetMeal;
            }

            public void setIsSetMeal(boolean isSetMeal) {
                this.isSetMeal = isSetMeal;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public String getComboDescribe() {
                return comboDescribe;
            }

            public void setComboDescribe(String comboDescribe) {
                this.comboDescribe = comboDescribe;
            }

            public boolean isIsSpecial() {
                return isSpecial;
            }

            public void setIsSpecial(boolean isSpecial) {
                this.isSpecial = isSpecial;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                /**
                 * sku : mock
                 * name : mock
                 * photo : mock
                 * price : mock
                 */

                private String sku;
                private String name;
                private String photo;
                private String price;

                public String getSku() {
                    return sku;
                }

                public void setSku(String sku) {
                    this.sku = sku;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }
            }
        }

}
