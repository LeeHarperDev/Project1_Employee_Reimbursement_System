package com.ex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "ticket_determination")
public class TicketDetermination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(mappedBy = "determination")
    @JsonIgnore
    private Ticket ticket;
    @OneToOne()
    @JoinColumn(name = "determination_type", referencedColumnName = "id")
    private DeterminationType determinationType;
    @OneToOne()
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;
    @Column(name = "reimbursement_amount")
    private double reimbursementAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeterminationType getDeterminationType() {
        return determinationType;
    }

    public void setDeterminationType(DeterminationType determinationType) {
        this.determinationType = determinationType;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public double getReimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(double reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
