package org.cse486.domain;

import javax.persistence.*;

@Table
@Entity
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String desk;
    private boolean isAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}


