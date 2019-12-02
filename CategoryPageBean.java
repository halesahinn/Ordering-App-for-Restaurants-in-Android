package org.cse486.web;

import org.cse486.domain.MenuCategory;
import org.cse486.service.MenuCategoryDao;
import org.omnifaces.cdi.GraphicImageBean;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean(name = "categoryPageBean")
@ApplicationScoped
public class CategoryPageBean {

    @ManagedProperty("#{menuCategoryDao}")
    private MenuCategoryDao dao;

    private TreeNode rootNode;
    private TreeNode selectedNode;


    public TreeNode createTree(MenuCategory treeObj, TreeNode rootNode) {
        TreeNode newNode = new DefaultTreeNode(treeObj, rootNode);
        newNode.setExpanded(true);
        List<MenuCategory> childNodes1 = dao.getChildNodes(treeObj.getId());

        for (MenuCategory tt : childNodes1) {
            TreeNode newNode2 = createTree(tt, newNode);
        }

        return newNode;
    }

    @PostConstruct
    public void init() {
        MenuCategory root = dao.getRoot();
        rootNode = new DefaultTreeNode(root, null);
        rootNode.setExpanded(true);
        TreeNode t1 = createTree(root, rootNode);

        selectedNode = rootNode;

    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public MenuCategoryDao getDao() {
        return dao;
    }

    public void setDao(MenuCategoryDao dao) {
        this.dao = dao;
    }
}
