package com.university.lele.data.model;

public class QRJsonModel {
    public String path;
    public String width;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "QRJsonModel{" +
                "path='" + path + '\'' +
                ", width='" + width + '\'' +
                '}';
    }
}
