package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Pain;
import eu.adrien.maven.bo.Produit;
import eu.adrien.maven.dal.DALException;

import java.util.List;

public class TestProduitsJBDCImpl {
    public static void main(String[] args) {
        ProduitsJBDCImpl produitsJBDC = new ProduitsJBDCImpl();
        try{
            List<Produit> produitsList = produitsJBDC.selectAll();
            if(produitsList!=null){
                System.out.println("Liste de tous les produits :");
                for(Produit produit: produitsList){
                    System.out.println(produit);
                }
            }

            System.out.println("Création d'un pain : Baguette doré");
            System.out.println("--------------------------------------");
            Pain newPain = new Pain("diablesse", "baguette doré", 1500, 300, 10.50f);
            produitsJBDC.insert(newPain);

            List<Produit> produitsList2 = produitsJBDC.selectAll();
            if(produitsList2!=null){
                System.out.println("Liste de tous les produits :");
                for(Produit produit: produitsList2){
                    System.out.println(produit);
                }
            }

            System.out.println("Selection d'un produit par son id : 13");
            Produit produit = produitsJBDC.selectById(26);
            System.out.println(produit);

        } catch (DALException e){
            e.printStackTrace();
        }
    }
}
