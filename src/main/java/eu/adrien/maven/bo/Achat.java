package eu.adrien.maven.bo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Achat{

    private double montant;
    private List<Ligne> lignesAchat = new ArrayList<>();

    public Achat(Ligne ligne) {
        this.lignesAchat.add(ligne);
    }

    public Achat(double montant, List<Ligne> lignes) {
        this.montant = montant;
        this.lignesAchat = lignes;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public List<Ligne> getLignes() {
        return lignesAchat;
    }

    public void setLignes(List<Ligne> lignes) {
        this.lignesAchat = lignes;
    }

    public Ligne getLigne(int index) {
        return lignesAchat.get(index);
    }

    public void ajouteLigne(Produit prod, int qte) {
        Ligne newLigne = new Ligne(prod, qte);
        this.lignesAchat.add(newLigne);
    }

    public void modifieLigne(int index, int nouvelleQte){
        lignesAchat.get(index).setQte(nouvelleQte);
    }

    public void supprimeLigne(int index){
        lignesAchat.remove(index);
    }

    public double calculMontant(){
        montant = 0;
        for(Ligne ligne : lignesAchat){
            montant += ligne.getProduit().getPrixUnitaire() * ligne.getQte();
        }
        return montant;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Achat :").append("\n");
        DecimalFormat deciFormat = new DecimalFormat("#0.00");
        for(Ligne ligne : lignesAchat){
            sb.append("ligne ").append((lignesAchat.indexOf(ligne))+1).append("  Ligne[");
            sb.append(ligne.toString()).append("\n");
        }
        sb.append("\n");
        sb.append("Total de l'achat : ").append(deciFormat.format(calculMontant())).append(" euros");
        return sb.toString();
    }
}
