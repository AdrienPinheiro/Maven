package eu.adrien.maven.bll;

import eu.adrien.maven.bo.Pain;
import eu.adrien.maven.bo.Produit;

import java.util.List;

public class TestBLL {
    public static void main(String[] args) {
        ProduitManager monTest = ProduitManager.getInstance();
        try{
            List<Produit> produitList = monTest.getProduits();
            for(Produit produit: produitList){
                System.out.println(produit);
            }

            System.out.println("Création d'un pain : Baguette classique");
            System.out.println("--------------------------------------");
            Pain newPain = new Pain("ange", "baguette classique", 150, 200, 1.2f);
            monTest.addProduit(newPain);

            List<Produit> produitList2 = monTest.getProduits();
            for(Produit produit: produitList2){
                System.out.println(produit);
            }

            monTest.deleteProduit(newPain);
            System.out.println("-------------------------------------");
            List<Produit> produitList3 = monTest.getProduits();
            for(Produit produit: produitList3){
                System.out.println(produit);
            }

        } catch (BLLException e){
            e.printStackTrace();
        }
    }
}
