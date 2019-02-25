package com.youzheng.zhejiang.robertmoog.Home.bean;

import java.util.List;

public class HomeDatas {



        /**
         * homePageData : {"newPromotion":false,"newCombo":false,"bannerImageData":[{"bannerImageUrl":"https://demo.waycomtech.com/image/detail/originalImg/c59f78cb-9983-4043-bf01-87e45bb67434.png","forwardUrl":"111111111111","description":""},{"bannerImageUrl":"https://demo.waycomtech.com/image/detail/originalImg/02d9d18b-7268-4e8d-8863-79be518fc690.jpg","forwardUrl":" ","description":""}]}
         */

        private HomePageDataBean homePageData;

        public HomePageDataBean getHomePageData() {
            return homePageData;
        }

        public void setHomePageData(HomePageDataBean homePageData) {
            this.homePageData = homePageData;
        }

        public static class HomePageDataBean {
            /**
             * newPromotion : false
             * newCombo : false
             * bannerImageData : [{"bannerImageUrl":"https://demo.waycomtech.com/image/detail/originalImg/c59f78cb-9983-4043-bf01-87e45bb67434.png","forwardUrl":"111111111111","description":""},{"bannerImageUrl":"https://demo.waycomtech.com/image/detail/originalImg/02d9d18b-7268-4e8d-8863-79be518fc690.jpg","forwardUrl":" ","description":""}]
             */

            private boolean newPromotion;
            private boolean newCombo;
            private List<BannerImageDataBean> bannerImageData;

            public boolean isNewPromotion() {
                return newPromotion;
            }

            public void setNewPromotion(boolean newPromotion) {
                this.newPromotion = newPromotion;
            }

            public boolean isNewCombo() {
                return newCombo;
            }

            public void setNewCombo(boolean newCombo) {
                this.newCombo = newCombo;
            }

            public List<BannerImageDataBean> getBannerImageData() {
                return bannerImageData;
            }

            public void setBannerImageData(List<BannerImageDataBean> bannerImageData) {
                this.bannerImageData = bannerImageData;
            }

            public static class BannerImageDataBean {
                /**
                 * bannerImageUrl : https://demo.waycomtech.com/image/detail/originalImg/c59f78cb-9983-4043-bf01-87e45bb67434.png
                 * forwardUrl : 111111111111
                 * description :
                 */

                private String bannerImageUrl;
                private String forwardUrl;
                private String description;

                public String getBannerImageUrl() {
                    return bannerImageUrl;
                }

                public void setBannerImageUrl(String bannerImageUrl) {
                    this.bannerImageUrl = bannerImageUrl;
                }

                public String getForwardUrl() {
                    return forwardUrl;
                }

                public void setForwardUrl(String forwardUrl) {
                    this.forwardUrl = forwardUrl;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }
        }

}
