package org.cse486.domain;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public class MenuOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private MenuItem order;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;


    @Temporal(TemporalType.TIMESTAMP)
    private Date prepareTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date receiveTime;

    private Long deskId;

    private int status;

    public MenuItem getOrder() {
        return order;
    }

    public void setOrder(MenuItem order) {
        this.order = order;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Date prepareTime) {
        this.prepareTime = prepareTime;
    }
    

}
