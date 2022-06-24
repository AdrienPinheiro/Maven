package eu.adrien.maven.bo;

public class Stylo extends Produit{
    private String couleur;
    private String typeMine;

    public Stylo(String couleur, String typeMine){
        this.couleur = couleur;
        this.typeMine = typeMine;
    }

    public Stylo(String marque, String libelle, long qteStock, float prixUnitaire, String couleur, String typeMine) {
        super(marque, libelle, qteStock, prixUnitaire);
        this.couleur = couleur;
        this.typeMine = typeMine;
    }

    public Stylo(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, String couleur, String typeMine) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.couleur = couleur;
        this.typeMine = typeMine;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTypeMine() {
        return typeMine;
    }

    public void setTypeMine(String typeMine) {
        this.typeMine = typeMine;
    }

    @Override
    public String toString() {
        return  super.toString() +
                ", couleur=" + couleur +
                ", typeMine=" + typeMine +
                ']';
    }
}
