package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Auteur;
import eu.adrien.maven.bo.CartePostale;
import eu.adrien.maven.bo.TypeCartePostale;
import eu.adrien.maven.dal.DALException;

import java.util.ArrayList;
import java.util.List;

public class TestCartePostaleJBDCImpl {
    public static void main(String[] args) {
        CartePostaleJBDCImpl cartePostaleJBDC = new CartePostaleJBDCImpl();
        AuteurJBDCImpl auteurJbdc = new AuteurJBDCImpl();
        try {
            System.out.println("--------------------------------------");
            System.out.println("Création d'une carte postale : Nantes");
            System.out.println("--------------------------------------");
            List<Auteur> auteursList = new ArrayList<>();
            Auteur auteur1 = auteurJbdc.selectById(74);
            Auteur auteur2 = auteurJbdc.selectById(76);
            auteursList.add(auteur1);
            auteursList.add(auteur2);
            CartePostale newCartePostale = new CartePostale("Nantes", "vistaprint",
                    182065, 2.5f, TypeCartePostale.Paysage);
            cartePostaleJBDC.insert(newCartePostale);
            for(Auteur auteur : auteursList){
                cartePostaleJBDC.insertLink(auteur.getRefAuteur(), newCartePostale.getRefProd());
            }

            List<CartePostale> cartePostaleList = cartePostaleJBDC.selectAll();
            if (cartePostaleList != null) {
                System.out.println("Liste de toutes les cartes :");
                for (CartePostale cartePostale : cartePostaleList) {
                    System.out.println(cartePostale);
                }
                System.out.println("--------------------------------------");
                System.out.println("Dernière carte postale créée :");
                int lastIndex = cartePostaleList.size()-1;
                CartePostale lastCartePostale = cartePostaleList.get(lastIndex);
                System.out.println(lastCartePostale);

                System.out.println("--------------------------------------");
                System.out.println("Update de la dernière carte postale : Paris");
                lastCartePostale.setLibelle("Paris");
                lastCartePostale.setType(TypeCartePostale.Portrait);
                cartePostaleJBDC.update(lastCartePostale);
                System.out.println("Dernière carte postale modifiée :");
                System.out.println(lastCartePostale);

                System.out.println("--------------------------------------");
                System.out.println("Suppression de la dernière carte postale créée");
                System.out.println("--------------------------------------");
                cartePostaleJBDC.delete(lastCartePostale);
            }
            List<CartePostale> cartePostaleListAfterDelete = cartePostaleJBDC.selectAll();
            if(cartePostaleListAfterDelete!=null){
                System.out.println("Liste de toutes les cartes postales après suppression de la dernière carte postale créée :");
                for(CartePostale cartePostale: cartePostaleListAfterDelete){
                    System.out.println(cartePostale);
                }

                System.out.println("--------------------------------------");
                System.out.println("Selection d'une carte postales par un id : 27");
                CartePostale cartePostaleById = cartePostaleJBDC.selectById(27);
                System.out.println(cartePostaleById);
                System.out.println("--------------------------------------");
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
