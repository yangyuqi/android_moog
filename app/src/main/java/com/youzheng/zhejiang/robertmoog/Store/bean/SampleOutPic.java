package com.youzheng.zhejiang.robertmoog.Store.bean;

import java.util.List;

public class SampleOutPic {



        /**
         * sampleImgIssueData : {"operator":"15602035888","operationTime":"2019-03-05 18:00:29","list":[{"bigUrl":"https://demo.waycomtech.com/image/detail/originalImg/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png","smallUrl":"https://demo.waycomtech.com/image/detail/100/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png"}]}
         */

        private SampleImgIssueDataBean sampleImgIssueData;

        public SampleImgIssueDataBean getSampleImgIssueData() {
            return sampleImgIssueData;
        }

        public void setSampleImgIssueData(SampleImgIssueDataBean sampleImgIssueData) {
            this.sampleImgIssueData = sampleImgIssueData;
        }

        public static class SampleImgIssueDataBean {
            /**
             * operator : 15602035888
             * operationTime : 2019-03-05 18:00:29
             * list : [{"bigUrl":"https://demo.waycomtech.com/image/detail/originalImg/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png","smallUrl":"https://demo.waycomtech.com/image/detail/100/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png"}]
             */

            private String operator;
            private String operationTime;
            private List<ListBean> list;

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getOperationTime() {
                return operationTime;
            }

            public void setOperationTime(String operationTime) {
                this.operationTime = operationTime;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * bigUrl : https://demo.waycomtech.com/image/detail/originalImg/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png
                 * smallUrl : https://demo.waycomtech.com/image/detail/100/aaf959a3-fe6d-47d5-b6fa-97e52da8347f.png
                 */

                private String bigUrl;
                private String smallUrl;

                public String getBigUrl() {
                    return bigUrl;
                }

                public void setBigUrl(String bigUrl) {
                    this.bigUrl = bigUrl;
                }

                public String getSmallUrl() {
                    return smallUrl;
                }

                public void setSmallUrl(String smallUrl) {
                    this.smallUrl = smallUrl;
                }
            }
        }

}
