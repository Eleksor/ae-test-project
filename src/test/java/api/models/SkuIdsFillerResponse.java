package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuIdsFillerResponse {
    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private List<Content> contents;
        public List<Content> getListOfContents() {
            List<Content> result = new ArrayList<>();
            result.add(this.contents.get(0));
            return result;
        }

        public Content getContentFromList() {
            return this.contents.get(0);
        }

        @lombok.Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Content {
            private List<ProductContent> productContent;
            public List<ProductContent> getListOfProductContents() {
                List<ProductContent> result = new ArrayList<>();
                result.add(this.productContent.get(0));
                return result;
            }
            public ProductContent getProductContentFromList() {
                return this.productContent.get(0);
            }

            @lombok.Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ProductContent {
                private List<Record> records;
                public List<String> getListOfRecords() {
                    List<String> result = new ArrayList<>();
                    for (int i = 0; i < records.size(); i++) {
                        result.add(records.get(i).getAttributes().getSkuIdFromList());
                    }
                    return result;
                }

                @lombok.Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Record {
                    private Attributes attributes;

                    @lombok.Data
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Attributes {
                        private List<String> sku_repositoryId;
                        public String getSkuIdFromList() {
                            String result = this.sku_repositoryId.get(0);
                            return result;
                        }
                    }
                }
            }
        }
    }
}

