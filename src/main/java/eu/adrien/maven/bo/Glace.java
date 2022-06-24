package eu.adrien.maven.bo;

import java.time.LocalDate;

public class Glace extends ProduitPerissable{
    private String parfum;
    private int temperatureConservation;

    public Glace(String parfum, int temperatureConservation){
        this.parfum = parfum;
        this.temperatureConservation = temperatureConservation;
    }

    public Glace(LocalDate dateLimiteConso, String marque, String libelle, int temperatureConservation, String parfum, long qteStock, float prixUnitaire) {
        super(marque, libelle, qteStock, prixUnitaire, dateLimiteConso);
        this.parfum = parfum;
        this.temperatureConservation = temperatureConservation;
    }

    public Glace(LocalDate dateLimiteConso, long refProd, String libelle, String marque, float prixUnitaire, long qteStock , String parfum, int temperatureConservation) {
        super(refProd, libelle, marque, prixUnitaire, qteStock, dateLimiteConso);
        this.parfum = parfum;
        this.temperatureConservation = temperatureConservation;
    }

    public String getParfum() {
        return parfum;
    }

    public void setParfum(String parfum) {
        this.parfum = parfum;
    }

    public int getTemperatureConservation() {
        return temperatureConservation;
    }

    public void setTemperatureConservation(int temperatureConservation) {
        this.temperatureConservation = temperatureConservation;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", parfum=" + parfum +
                ", temperatureConservation=" + temperatureConservation +
                ", date=" + getDateLimiteConso() +
                ']';
    }
}
