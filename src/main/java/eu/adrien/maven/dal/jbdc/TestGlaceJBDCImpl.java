package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Glace;
import eu.adrien.maven.dal.DALException;

import java.time.LocalDate;
import java.util.List;

public class TestGlaceJBDCImpl {
    public static void main(String[] args) {
        GlaceJBDCImpl glaceJBDC = new GlaceJBDCImpl();
        try{
            System.out.println("--------------------------------------");
            System.out.println("Création d'une glace : glace bueno");
            System.out.println("--------------------------------------");
            Glace newGlace = new Glace(LocalDate.now().plusYears(1), "kinder", "glace bueno", -10, "chocolat", 240000, 1.5f);
            glaceJBDC.insert(newGlace);

            List<Glace> glaceList = glaceJBDC.selectAll();
            if(glaceList!=null){
                System.out.println("Liste de toutes les glaces :");
                for(Glace glace: glaceList){
                    System.out.println(glace);
                }
                System.out.println("--------------------------------------");
                System.out.println("Dernière glace créée :");
                int lastIndex = glaceList.size() -1;
                Glace lastGlace = glaceList.get(lastIndex);
                System.out.println(lastGlace);

                System.out.println("--------------------------------------");
                System.out.println("Update de la dernière glace : glace bueno white");
                lastGlace.setParfum("chocolat blanc");
                lastGlace.setTemperatureConservation(-2);
                glaceJBDC.update(lastGlace);
                System.out.println("Dernière glace modifiée :");
                System.out.println(lastGlace);

                System.out.println("--------------------------------------");
                System.out.println("Suppression de la dernière glace créée");
                System.out.println("--------------------------------------");
                glaceJBDC.delete(lastGlace);
            }
            List<Glace> glaceListAfterDelete = glaceJBDC.selectAll();
            if(glaceListAfterDelete!=null){
                System.out.println("Liste de toutes les glaces après suppression de la dernière glace créée :");
                for(Glace glace: glaceListAfterDelete){
                    System.out.println(glace);
                }

                System.out.println("--------------------------------------");
                System.out.println("Selection d'une glace par un id : 13");
                Glace glaceById = glaceJBDC.selectById(21);
                System.out.println(glaceById);
                System.out.println("--------------------------------------");
            }
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
