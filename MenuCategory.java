package org.cse486.domain;


import javax.persistence.*;
import java.util.Set;

@Table
@Entity
public class MenuCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private MenuCategory parent;


    @OneToMany(fetch = FetchType.EAGER)
    private Set<MenuItem> menuItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuCategory getParent() {
        return parent;
    }

    public void setParent(MenuCategory parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", menuItems=" + menuItems +
                '}';
    }
}
