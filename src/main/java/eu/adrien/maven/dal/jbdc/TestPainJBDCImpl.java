package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Pain;
import eu.adrien.maven.dal.DALException;

import java.util.List;

public class TestPainJBDCImpl {
    public static void main(String[] args) {
        PainJBDCImpl painJBDC = new PainJBDCImpl();
        try{
            System.out.println("--------------------------------------");
            System.out.println("Création d'un pain : Baguette classique");
            System.out.println("--------------------------------------");
            Pain newPain = new Pain("ange", "baguette classique", 150, 200, 1.2f);
            painJBDC.insert(newPain);

            List<Pain> painList = painJBDC.selectAll();
            if(painList!=null){
                System.out.println("Liste de tous les pains :");
                for(Pain pain: painList){
                    System.out.println(pain);
                }
                System.out.println("--------------------------------------");
                System.out.println("Dernier pain créé :");
                int lastIndex = painList.size() -1;
                Pain lastPain = painList.get(lastIndex);
                System.out.println(lastPain);

                System.out.println("--------------------------------------");
                System.out.println("Update du dernier pain : Baguette campagnarde");
                lastPain.setLibelle("baguette campagnarde");
                lastPain.setPoid(250);
                painJBDC.update(lastPain);
                System.out.println("Dernier pain modifié :");
                System.out.println(lastPain);

                System.out.println("--------------------------------------");
                System.out.println("Suppression du dernier pain créé");
                System.out.println("--------------------------------------");
                painJBDC.delete(lastPain);
            }
            List<Pain> painListAfterDelete = painJBDC.selectAll();
            if(painListAfterDelete!=null){
                System.out.println("Liste de tous les pains après suppression du dernier pain :");
                for(Pain pain: painListAfterDelete){
                    System.out.println(pain);
                }

                System.out.println("--------------------------------------");
                System.out.println("Selection d'un pain par un id : 13");
                Pain painById = painJBDC.selectById(13);
                System.out.println(painById);
                System.out.println("--------------------------------------");
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
