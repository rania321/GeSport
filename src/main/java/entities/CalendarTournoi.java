package entities;

import java.util.Date;

public class CalendarTournoi {
    private Date startDate;
    private Date endDate;
    private String nomT; // Utilisez le nom du tournoi à la place de clientName
    private Integer idT; // Utilisez l'ID du tournoi à la place de serviceNo

    public CalendarTournoi(Date startDate, Date endDate, String nomT, Integer idT) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.nomT = nomT;
        this.idT = idT;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNomT() {
        return nomT;
    }

    public void setNomT(String nomT) {
        this.nomT = nomT;
    }

    public Integer getIdT() {
        return idT;
    }

    public void setIdT(Integer idT) {
        this.idT = idT;
    }

    @Override
    public String toString() {
        return "CalendarTournoi{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", nomT='" + nomT + '\'' +
                ", idT=" + idT +
                '}';
    }
}