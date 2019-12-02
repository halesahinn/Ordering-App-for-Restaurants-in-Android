package org.cse486.web;

import org.cse486.domain.MenuCategory;
import org.cse486.domain.MenuItem;
import org.cse486.service.MenuCategoryDao;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.omnifaces.cdi.GraphicImageBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ManagedBean
@ApplicationScoped
public class MenuItemsPageBean implements Serializable {

    private List<MenuItem> menuItems;
    private MenuItem newItem = new MenuItem();
    private UploadedFile newMenuPicture;


    @ManagedProperty("#{menuCategoryDao}")
    private MenuCategoryDao menuCategoryDao;

    @ManagedProperty("#{categoryPageBean}")
    private CategoryPageBean categoryPageBean;


    public List<MenuItem> getMenuItems() {
        if (categoryPageBean.getSelectedNode() == null) {
            categoryPageBean.setSelectedNode(categoryPageBean.getRootNode());
        }
        MenuCategory data = (MenuCategory) categoryPageBean.getSelectedNode().getData();
        menuItems = menuCategoryDao.getMenuItems(data.getId());
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public MenuCategoryDao getMenuCategoryDao() {
        return menuCategoryDao;
    }

    public void setMenuCategoryDao(MenuCategoryDao menuCategoryDao) {
        this.menuCategoryDao = menuCategoryDao;
    }

    public CategoryPageBean getCategoryPageBean() {
        return categoryPageBean;
    }

    public void setCategoryPageBean(CategoryPageBean categoryPageBean) {
        this.categoryPageBean = categoryPageBean;
    }

    public MenuItem getNewItem() {
        return newItem;
    }

    public void setNewItem(MenuItem newItem) {
        this.newItem = newItem;
    }


    public void saveNewMenuItem() {
        this.newItem.setPhoto(this.newMenuPicture.getContents());
        this.newItem.setMenuCategory((MenuCategory) this.categoryPageBean.getSelectedNode().getData());
        menuCategoryDao.insertMenuItem(this.newItem);
        this.newItem = new MenuItem();
        FacesContext.getCurrentInstance().addMessage("Menu Item is added to selected category", new FacesMessage("Menu Item is added to selected category"));
    }

    public UploadedFile getNewMenuPicture() {
        return newMenuPicture;
    }

    public void setNewMenuPicture(UploadedFile newMenuPicture) {
        this.newMenuPicture = newMenuPicture;
    }


    public byte[] findImage(MenuItem item) {
        return item.getPhoto();

    }

    public void deleteItem(MenuItem menuItem) {
        menuCategoryDao.deleteMenuItem(menuItem);
        FacesContext.getCurrentInstance().addMessage("Item is Deleted As Successfully", new FacesMessage("Item is Deleted As Successfully"));
    }

}

