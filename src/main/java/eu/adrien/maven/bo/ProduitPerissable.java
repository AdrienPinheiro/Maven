package eu.adrien.maven.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ProduitPerissable extends Produit{
    private LocalDate dateLimiteConso;

    public ProduitPerissable(LocalDate dateLimiteConso){
        this.dateLimiteConso = dateLimiteConso;
    }

    public ProduitPerissable(String marque, String libelle, long qteStock, float prixUnitaire, LocalDate dateLimiteConso) {
        super(marque, libelle, qteStock, prixUnitaire);
        this.dateLimiteConso = dateLimiteConso;
    }

    public ProduitPerissable(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, LocalDate dateLimiteConso) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.dateLimiteConso = dateLimiteConso;
    }

    public ProduitPerissable(){
    }
    public LocalDate getDateLimiteConso() {
        return dateLimiteConso ;
    }

    public void setDateLimiteConso(LocalDate dateLimiteConso) {
        this.dateLimiteConso = dateLimiteConso;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", dateLimiteConso=" + dateLimiteConso.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }
}
