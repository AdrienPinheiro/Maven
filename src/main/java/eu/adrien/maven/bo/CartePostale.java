package eu.adrien.maven.bo;

import java.util.List;

public class CartePostale extends Produit{
    private eu.adrien.maven.bo.TypeCartePostale TypeCartePostale;
    private List<Auteur> auteurs;

    public CartePostale(TypeCartePostale type, List<Auteur> auteurs){
        this.TypeCartePostale = type;
        this.auteurs = auteurs;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> auteurs, TypeCartePostale type) {
        super(marque, libelle, qteStock, prixUnitaire);
        this.TypeCartePostale = type;
        this.auteurs = auteurs;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, TypeCartePostale typeCartePostale) {
        super(marque, libelle, qteStock, prixUnitaire);
        TypeCartePostale = typeCartePostale;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, TypeCartePostale typeCartePostale, List<Auteur> auteurs) {
        super(marque, libelle, qteStock, prixUnitaire);
        TypeCartePostale = typeCartePostale;
        this.auteurs = auteurs;
    }

    public CartePostale(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, List<Auteur> auteurs, TypeCartePostale type) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.TypeCartePostale = type;
        this.auteurs = auteurs;
    }

    public TypeCartePostale getType() {
        return TypeCartePostale;
    }

    public void setType(TypeCartePostale type) {
        this.TypeCartePostale = type;
    }

    public List<Auteur> getAuteur() {
        return auteurs;
    }

    public void setAuteur(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", auteur(s)=");
        for (Auteur auteur: auteurs) {
            sb.append(" auteur").append((auteurs.indexOf(auteur))+1).append("= ");
            sb.append(auteur.getNom()).append(" ").append(auteur.getPrenom());
        }
        sb.append(", type de carte portale=").append(getType()).append(']');
        return sb.toString();
        };
    }
