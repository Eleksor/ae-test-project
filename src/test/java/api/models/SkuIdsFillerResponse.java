package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuIdsFillerResponse {
    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private Contents contents;

        @lombok.Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Contents {
            private List<ProductContent> productContent;

            @lombok.Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class ProductContent {
                private List<Records> records;

                @lombok.Data
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Records {
                    private List<Records> records;
                    private Attributes attributes;

                    @lombok.Data
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Attributes {
                        private List<String> sku_repositoryId;
                    }
                }
            }
        }
    }
}
