package entities;
import java.lang.String;
import java.sql.Date;

public class Produit {
    private int idP,StockP;
    private String nomP, descriP;
    private float PrixP;
    private Date DateAjoutP;

    public Produit() {
    }
    public Produit(int idP, String nomP, String descriP, float PrixP, int StockP, Date DateAjoutP) {
        this.idP = idP;
        this.nomP = nomP;
        this.descriP = descriP;
        this.PrixP = PrixP;
        this.StockP = StockP;
        this.DateAjoutP = DateAjoutP;
    }

    public Produit(String NomP, String DescriP, float prixP, int stockP, Date dateAjoutP) {
        nomP = NomP;
        descriP = DescriP;
        PrixP = prixP;
        StockP = stockP;
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

    public float getPrixP() {
        return this.PrixP;
    }

    public int getStockP() {
        return StockP;
    }

    public java.sql.Date getDateAjoutP() {
        return (java.sql.Date) DateAjoutP;
    }

    public void setIdP(int IdP) {
        idP = IdP;
    }

    public void setNomP(String NomP) {
        nomP = NomP;
    }

    public void setDescriP(String DescriP) {
        descriP = DescriP;
    }

    public void setPrixP(float prixP) {
        PrixP = prixP;
    }

    public void setStockP(int stockP) {
        StockP = stockP;
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
