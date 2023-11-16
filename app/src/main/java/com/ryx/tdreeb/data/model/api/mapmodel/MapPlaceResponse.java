package com.ryx.tdreeb.data.model.api.mapmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapPlaceResponse {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("html_attributions")
    @Expose
    private String[] html_attributions;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error_message")
    @Expose
    private String error_message;

    public class Result {
        @SerializedName("utc_offset")
        @Expose
        private String utc_offset;
        @SerializedName("formatted_address")
        @Expose
        private String formatted_address;
        @SerializedName("types")
        @Expose
        private List<String> types;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("address_components")
        @Expose
        private List<Address_components> address_components;
        @SerializedName("photos")
        @Expose
        private List<Photos> photos;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("reference")
        @Expose
        private String reference;
        @SerializedName("scope")
        @Expose
        private String scope;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("vicinity")
        @Expose
        private String vicinity;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("adr_address")
        @Expose
        private String adr_address;
        @SerializedName("place_id")
        @Expose
        private String place_id;

        public class Photos {
            @SerializedName("photo_reference")
            @Expose
            private String photo_reference;
            @SerializedName("width")
            @Expose
            private String width;
            @SerializedName("html_attributions")
            @Expose
            private String[] html_attributions;
            @SerializedName("height")
            @Expose
            private String height;

            public String getPhoto_reference() {
                return photo_reference;
            }

            public void setPhoto_reference(String photo_reference) {
                this.photo_reference = photo_reference;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String[] getHtml_attributions() {
                return html_attributions;
            }

            public void setHtml_attributions(String[] html_attributions) {
                this.html_attributions = html_attributions;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }

        public class Geometry {
            @SerializedName("viewport")
            @Expose
            private Viewport viewport;
            @SerializedName("location")
            @Expose
            private Location location;

            public class Viewport {
                @SerializedName("southwest")
                @Expose
                private Southwest southwest;
                @SerializedName("northeast")
                @Expose
                private Northeast northeast;

                public class Southwest {
                    @SerializedName("lng")
                    @Expose
                    private String lng;
                    @SerializedName("lat")
                    @Expose
                    private String lat;

                    public String getLng() {
                        return lng;
                    }

                    public void setLng(String lng) {
                        this.lng = lng;
                    }

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }
                }

                public class Northeast {
                    @SerializedName("lng")
                    @Expose
                    private String lng;
                    @SerializedName("lat")
                    @Expose
                    private String lat;

                    public String getLng() {
                        return lng;
                    }

                    public void setLng(String lng) {
                        this.lng = lng;
                    }

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }
                }

                public Southwest getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(Southwest southwest) {
                    this.southwest = southwest;
                }

                public Northeast getNortheast() {
                    return northeast;
                }

                public void setNortheast(Northeast northeast) {
                    this.northeast = northeast;
                }
            }

            public Viewport getViewport() {
                return viewport;
            }

            public void setViewport(Viewport viewport) {
                this.viewport = viewport;
            }

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }
        }


        public class Location {
            @SerializedName("lng")
            @Expose
            private String lng;
            @SerializedName("lat")
            @Expose
            private String lat;

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }

        public class Address_components {
            @SerializedName("types")
            @Expose
            private List<String> types;
            @SerializedName("short_name")
            @Expose
            private String short_name;
            @SerializedName("long_name")
            @Expose
            private String long_name;

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }
        }

        public String getUtc_offset() {
            return utc_offset;
        }

        public void setUtc_offset(String utc_offset) {
            this.utc_offset = utc_offset;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Address_components> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<Address_components> address_components) {
            this.address_components = address_components;
        }

        public List<Photos> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photos> photos) {
            this.photos = photos;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdr_address() {
            return adr_address;
        }

        public void setAdr_address(String adr_address) {
            this.adr_address = adr_address;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
