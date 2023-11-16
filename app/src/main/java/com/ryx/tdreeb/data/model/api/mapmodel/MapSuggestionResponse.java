package com.ryx.tdreeb.data.model.api.mapmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapSuggestionResponse {

    @SerializedName("predictions")
    @Expose
    private List<predictions> predictions;

    @SerializedName("status")
    @Expose
    private String status;

    public class predictions {
        @SerializedName("reference")
        @Expose
        private String reference;
        @SerializedName("types")
        @Expose
        private List<String>types;
        @SerializedName("matched_substrings")
        @Expose
        private List<matched_substrings> matched_substrings;
        @SerializedName("terms")
        @Expose
        private List<terms> terms;
        @SerializedName("structured_formatting")
        @Expose
        private structured_formatting structured_formatting;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("place_id")
        @Expose
        private String place_id;

        public class matched_substrings {
            @SerializedName("offset")
            @Expose
            private String offset;
            @SerializedName("length")
            @Expose
            private String length;

            public String getOffset() {
                return offset;
            }

            public void setOffset(String offset) {
                this.offset = offset;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }
        }


        public class terms {
            @SerializedName("offset")
            @Expose
            private String offset;
            @SerializedName("value")
            @Expose
            private String value;

            public String getOffset() {
                return offset;
            }

            public void setOffset(String offset) {
                this.offset = offset;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }


        public class structured_formatting {
            @SerializedName("main_text_matched_substrings")
            @Expose
            private List<main_text_matched_substrings> main_text_matched_substrings;
            @SerializedName("secondary_text")
            @Expose
            private String secondary_text;
            @SerializedName("main_text")
            @Expose
            private String main_text;

            public class main_text_matched_substrings {
                @SerializedName("offset")
                @Expose
                private String offset;
                @SerializedName("length")
                @Expose
                private String length;

                public String getOffset() {
                    return offset;
                }

                public void setOffset(String offset) {
                    this.offset = offset;
                }

                public String getLength() {
                    return length;
                }

                public void setLength(String length) {
                    this.length = length;
                }
            }


            public List<MapSuggestionResponse.predictions.structured_formatting.main_text_matched_substrings> getMain_text_matched_substrings() {
                return main_text_matched_substrings;
            }

            public void setMain_text_matched_substrings(List<MapSuggestionResponse.predictions.structured_formatting.main_text_matched_substrings> main_text_matched_substrings) {
                this.main_text_matched_substrings = main_text_matched_substrings;
            }

            public String getSecondary_text() {
                return secondary_text;
            }

            public void setSecondary_text(String secondary_text) {
                this.secondary_text = secondary_text;
            }

            public String getMain_text() {
                return main_text;
            }

            public void setMain_text(String main_text) {
                this.main_text = main_text;
            }
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public List<MapSuggestionResponse.predictions.matched_substrings> getMatched_substrings() {
            return matched_substrings;
        }

        public void setMatched_substrings(List<MapSuggestionResponse.predictions.matched_substrings> matched_substrings) {
            this.matched_substrings = matched_substrings;
        }

        public List<MapSuggestionResponse.predictions.terms> getTerms() {
            return terms;
        }

        public void setTerms(List<MapSuggestionResponse.predictions.terms> terms) {
            this.terms = terms;
        }

        public MapSuggestionResponse.predictions.structured_formatting getStructured_formatting() {
            return structured_formatting;
        }

        public void setStructured_formatting(MapSuggestionResponse.predictions.structured_formatting structured_formatting) {
            this.structured_formatting = structured_formatting;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }
    }

    public List<MapSuggestionResponse.predictions> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<MapSuggestionResponse.predictions> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}