package entities;
import java.lang.String;
import java.sql.Date;

public class Produit {
    private int idP,StockP, referenceP;
    private String nomP, descriP, image ;
    private float PrixP;
    private Date DateAjoutP;

    public Produit() {
    }
    public Produit(int idP, String nomP, String descriP, float PrixP, int StockP, Date DateAjoutP, String image, int refp) {
        this.idP = idP;
        this.nomP = nomP;
        this.descriP = descriP;
        this.PrixP = PrixP;
        this.StockP = StockP;
        this.DateAjoutP = DateAjoutP;
        this.image= image;
        this.referenceP= refp;
    }

    public Produit(String NomP, String DescriP, float prixP, int stockP, Date dateAjoutP, String Image, int refp) {
        nomP = NomP;
        descriP = DescriP;
        PrixP = prixP;
        StockP = stockP;
        DateAjoutP = dateAjoutP;
        image= Image;
        referenceP =refp;
    }

    public int getReferenceP() {
        return referenceP;
    }

    public void setReferenceP(int referenceP) {
        this.referenceP = referenceP;
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

    public String getImage() {
        return image;
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

    public void setImage(String Image) {image= Image;}

    @Override
    public String toString() {
        return "Produit{" +
                "idP=" + idP +
                ", StockP=" + StockP +
                ", referenceP=" + referenceP +
                ", nomP='" + nomP + '\'' +
                ", descriP='" + descriP + '\'' +
                ", image='" + image + '\'' +
                ", PrixP=" + PrixP +
                ", DateAjoutP=" + DateAjoutP +
                '}';
    }
}
