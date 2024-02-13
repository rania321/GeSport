package entities;

import java.util.Date;

public class Produit {
    private int idP;
    private String nomP;
    private String descriP;
    private Float PrixP;
    private int StockP;
    private Date DateAjoutP;

    public Produit() {
    }
    public Produit(int idP, String nomP, String descriP, Float PrixP, int StockP, Date DateAjoutP) {
        idP = idP;
        nomP = nomP;
        descriP = descriP;
        PrixP = PrixP;
        StockP = StockP;
        DateAjoutP = DateAjoutP;
    }

    public Produit(String nomP, String descriP, Float PrixP, int StockP, Date dateAjoutP) {
        nomP = nomP;
        descriP = descriP;
        PrixP = PrixP;
        StockP = StockP;
        DateAjoutP = dateAjoutP;
    }

    public int getIdP() {
        return idP;
    }

    public String getNomP() {
        return nomP;
    }

    public String getDescriP() {
        return descriP;
    }

    public Float getPrixP() {
        return PrixP;
    }

    public int getStockP() {
        return StockP;
    }

    public Date getDateAjoutP() {
        return DateAjoutP;
    }

    public void setProduitId(int idP) {
        idP = idP;
    }

    public void setNomP(String nomP) {
        nomP = nomP;
    }

    public void setDescriP(String descriP) {
        descriP = descriP;
    }

    public void setPrixP(Float PrixP) {
        PrixP = PrixP;
    }

    public void setStockP(int StockP) {
        StockP = StockP;
    }

    public void setDateAjoutP(Date dateAjoutP) {
        DateAjoutP = dateAjoutP;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "ProduitId=" + idP +
                ", NomProduit='" + nomP + '\'' +
                ", DescriptionProduit='" + descriP + '\'' +
                ", PrixUnitaire=" + PrixP +
                ", StockDisponible=" + StockP +
                ", DateAjout=" + DateAjoutP +
                '}';
    }
}
