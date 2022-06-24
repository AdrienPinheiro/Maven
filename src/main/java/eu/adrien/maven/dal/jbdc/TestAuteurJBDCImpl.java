package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Auteur;
import eu.adrien.maven.dal.DALException;

import java.util.List;

public class TestAuteurJBDCImpl {
    public static void main(String[] args) {
        AuteurJBDCImpl auteurJbdc = new AuteurJBDCImpl();
        try{
            System.out.println("--------------------------------------");
            System.out.println("Création d'un auteur : Pierre Tremblay");
            System.out.println("--------------------------------------");
            Auteur pierre = new Auteur("Pierre", "Tremblay");
            auteurJbdc.insert(pierre);

            List<Auteur> auteurList = auteurJbdc.selectAll();
            if(auteurList!=null){
                System.out.println("Liste de tous les auteurs :");
                for(Auteur auteur: auteurList){
                    System.out.println(auteur);
                }
                System.out.println("--------------------------------------");
                System.out.println("Dernier auteur créé :");
                int index = auteurList.size()-1;
                Auteur lastAuteur = auteurList.get(index);
                System.out.println(lastAuteur);

                System.out.println("--------------------------------------");
                System.out.println("Update du dernier auteur : Julien Picquet");
                lastAuteur.setNom("Picquet");
                lastAuteur.setPrenom("Julien");
                auteurJbdc.update(lastAuteur);
                System.out.println("Dernier auteur modifié :");
                System.out.println(lastAuteur);

                System.out.println("--------------------------------------");
                System.out.println("Suppression du dernier auteur créé");
                System.out.println("--------------------------------------");
                auteurJbdc.delete(lastAuteur);
            }
            List<Auteur> auteurListAfterDelete = auteurJbdc.selectAll();
            if(auteurListAfterDelete!=null){
                System.out.println("Liste de tous les auteurs après suppression du dernier auteur :");
                for(Auteur auteur: auteurListAfterDelete){
                    System.out.println(auteur);
                }

                System.out.println("--------------------------------------");
                System.out.println("Selection d'un auteur par un id : 76");
                Auteur auteurById = auteurJbdc.selectById(76);
                System.out.println(auteurById);
                System.out.println("--------------------------------------");
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
