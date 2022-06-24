package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Stylo;
import eu.adrien.maven.dal.DALException;

import java.util.List;

public class TestStyloJBDCImpl {
    public static void main(String[] args) {
        StyloJBDCImpl styloJBDC = new StyloJBDCImpl();
        try{
            System.out.println("--------------------------------------");
            System.out.println("Création d'un stylo : Le stylo trompe d'éléphant");
            System.out.println("--------------------------------------");
            Stylo newStylo = new Stylo("BIC", "Le stylo trompe d'éléphant", 150000, 1.8f, "bleu", "épaisse");
            styloJBDC.insert(newStylo);

            List<Stylo> styloList = styloJBDC.selectAll();
            if(styloList!=null){
                System.out.println("Liste de tous les stylos :");
                for(Stylo stylo: styloList){
                    System.out.println(stylo);
                }
                System.out.println("--------------------------------------");
                System.out.println("Dernier stylo créé :");
                int lastIndex = styloList.size() -1;
                Stylo lastStylo = styloList.get(lastIndex);
                System.out.println(lastStylo);

                System.out.println("--------------------------------------");
                System.out.println("Update du dernier stylo : Le stylo qui jete de l'eau");
                lastStylo.setLibelle("Le stylo qui jete de l'eau");
                lastStylo.setTypeMine("fine");
                styloJBDC.update(lastStylo);
                System.out.println("Dernier stylo modifié :");
                System.out.println(lastStylo);

                System.out.println("--------------------------------------");
                System.out.println("Suppression du dernier stylo créé");
                System.out.println("--------------------------------------");
                styloJBDC.delete(lastStylo);
            }
            List<Stylo> styloListAfterDelete = styloJBDC.selectAll();
            if(styloListAfterDelete!=null){
                System.out.println("Liste de tous les stylos après suppression du dernier stylo :");
                for(Stylo stylo: styloListAfterDelete){
                    System.out.println(stylo);
                }

                System.out.println("--------------------------------------");
                System.out.println("Selection d'un stylo par un id : 1");
                Stylo styloById = styloJBDC.selectById(1);
                System.out.println(styloById);
                System.out.println("--------------------------------------");
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
