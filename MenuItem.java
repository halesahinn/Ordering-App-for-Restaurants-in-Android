package org.cse486.domain;

import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private byte[] photo;
    private String name;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MenuCategory.class)
    private MenuCategory menuCategory;


    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }


}
