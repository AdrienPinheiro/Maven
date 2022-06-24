package eu.adrien.maven.bo;

import java.time.LocalDate;

public class Pain extends ProduitPerissable{

    private int poid;

    public Pain(int poid) {
        this.poid = poid;
    }

    public Pain(String marque, String libelle, long qteStock, int poid, float prixUnitaire) {
        super(marque, libelle, qteStock, prixUnitaire, LocalDate.now().plusDays(2));
        this.poid = poid;
    }

    public Pain(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, int poid) {
        super(refProd, libelle, marque, prixUnitaire, qteStock, LocalDate.now().plusDays(2));
        this.poid = poid;
    }

    public int getPoid() {
        return poid;
    }

    public void setPoid(int poid) {
        this.poid = poid;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", poids=" + poid +
                "g]";
    }
}
