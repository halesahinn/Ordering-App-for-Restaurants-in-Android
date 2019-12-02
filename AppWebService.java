package org.cse486.rest;

import org.cse486.domain.Desk;
import org.cse486.domain.MenuCategory;
import org.cse486.domain.MenuItem;
import org.cse486.domain.MenuOrder;
import org.cse486.rest.request.OrderRequest;
import org.cse486.service.DeskDao;
import org.cse486.service.MenuCategoryDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class AppWebService {


    MenuCategoryDao service = new MenuCategoryDao();
    DeskDao deskDao = new DeskDao();

    @GET
    @Path("/getRootCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public MenuCategory getRootCategory() {
        MenuCategory root = service.getRoot();
        return root;
    }

    @GET
    @Path("/getChildCategories/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuCategory> getChildCategories(@PathParam("categoryId") Long parentId) {
        List<MenuCategory> all = service.getChildNodes(parentId);
        return all;
    }

    @GET
    @Path("/getCategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuCategory> getCategories() {
        List<MenuCategory> all = service.getAll();
        return all;
    }

    @GET
    @Path("/getMenu/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuItem> getMenu(@PathParam("categoryId") long categoryId) {
        return service.getMenuItems(categoryId);
    }

    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MenuOrder orderMenuItem(OrderRequest request) {
        MenuOrder menuOrder = service.insertOrder(request.getMenuItem(), request.getDeskId());
        return menuOrder;
    }

    @GET
    @Path("/desk/open/{deskId}")
    public void OpenDesk(@PathParam("deskId") Long deskId) {
        Desk desk = deskDao.findDesk(deskId);
        desk.setAvailable(false);
        deskDao.addNewDesk(desk);
    }
}


