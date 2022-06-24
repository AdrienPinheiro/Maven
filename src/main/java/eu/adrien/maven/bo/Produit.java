package eu.adrien.maven.bo;

public class Produit {
    private long refProd;
    private String libelle;
    private String marque;
    private float prixUnitaire;
    private long qteStock;

    public Produit(String marque, String libelle, long qteStock, float prixUnitaire){
        this.libelle = libelle;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.qteStock = qteStock;
    }

    public Produit(long refProd, String libelle, String marque, float prixUnitaire, long qteStock) {
        this.refProd = refProd;
        this.libelle = libelle;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.qteStock = qteStock;
    }

    public Produit(){
    }

    public long getRefProd() {
        return refProd;
    }

    public void setRefProd(long refProd) {
        this.refProd = refProd;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public long getQteStock() {
        return qteStock;
    }

    public void setQteStock(long qteStock) {
        this.qteStock = qteStock;
    }

    @Override
    public String toString() {
        return  this.getClass().getSimpleName() + " [" +
                "libelle=" + libelle +
                ", marque=" + marque +
                ", prixUnitaire=" + prixUnitaire + " euros"+
                ", qteStock=" + qteStock;
    }
}
