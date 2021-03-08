
package com.dalochinkhwangwa.jungsoomarket.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class JungsooItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("qrUrl")
    @Expose
    private String qrUrl;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JungsooItem that = (JungsooItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(qrUrl, that.qrUrl) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, qrUrl, thumbnail, name, price);
    }
}
