package org.cse486.web;

import org.cse486.domain.MenuOrder;
import org.cse486.service.OrderDao;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ManagedBean
@ViewScoped
public class OrderPageBean {
    private int lastCount;

    @ManagedProperty("#{orderDao}")
    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setStatus(MenuOrder order, int status) {
        order.setStatus(status);

        switch (status) {
            case 1:
                order.setPrepareTime(new Date());
            case 2:
                order.setReceiveTime(new Date());
        }
        orderDao.save(order);
        FacesContext.getCurrentInstance().addMessage("Order status has been changed successfully", new FacesMessage("Order status has been changed successfully"));

    }

    public List<MenuOrder> getOrders() {
        List<MenuOrder> currentOrders = orderDao.getCurrentOrders();
        if (lastCount != 0 && lastCount < currentOrders.size()) {
            FacesContext.getCurrentInstance().addMessage("There is/are new order(s)", new FacesMessage("There is/are new order(s)"));
        }
        this.lastCount = currentOrders.size();
        return currentOrders.stream().sorted(new Comparator<MenuOrder>() {
            @Override
            public int compare(MenuOrder o1, MenuOrder o2) {
                return (int) (o2.getTime().getTime() - o1.getTime().getTime());
            }
        }).collect(Collectors.toList());

    }

    public String getPrepareTime(MenuOrder order) {
        long millis = new Date().getTime() - order.getPrepareTime().getTime();
        if (order.getStatus() == 2) {
            millis = order.getPrepareTime().getTime() - order.getReceiveTime().getTime();

        }
        else{
            return null;
        }
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );

    }

    public String getSeenTime(MenuOrder order) {
        long millis = new Date().getTime() - order.getTime().getTime();
        if (order.getStatus() == 2) {
            millis = order.getTime().getTime() - order.getPrepareTime().getTime();

        }
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );

    }
}
