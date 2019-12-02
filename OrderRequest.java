package org.cse486.rest.request;

public class OrderRequest {
    long menuItem;
    long deskId;

    public long getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(long menuItem) {
        this.menuItem = menuItem;
    }

    public long getDeskId() {
        return deskId;
    }

    public void setDeskId(long deskId) {
        this.deskId = deskId;
    }
}