package eu.adrien.maven.bo;

public class Ligne {
    private int quantite;
    private Produit produit;

    public Ligne(Produit produit, int quantite) {
        this.quantite = quantite;
        this.produit = produit;
    }

    public int getQte() {
        return quantite;
    }

    public void setQte(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getPrix(){
        return this.getQte() * produit.getPrixUnitaire();
    }

    @Override
    public String toString() {
        return  "produit=" + produit +
                ", qte=" + quantite +
                ", prix=" + getPrix() + ']';
    }
}
