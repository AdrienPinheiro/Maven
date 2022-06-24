package eu.adrien.maven.dal;

import eu.adrien.maven.bo.*;
import eu.adrien.maven.dal.jbdc.*;

public class DAOFactory {
    public static DAO<Auteur> getAuteurDAO(){
        return new AuteurJBDCImpl();
    }
    public static DAO<CartePostale> getCartePostaleDAO(){
        return new CartePostaleJBDCImpl();
    }
    public static DAO<Glace> getGlaceDAO(){
        return new GlaceJBDCImpl();
    }
    public static DAO<Pain> getPainDAO(){
        return new PainJBDCImpl();
    }
    public static DAO<Stylo> getStyloDAO(){
        return new StyloJBDCImpl();
    }
    public static DAO<Produit> getProduitDAO(){
        return new ProduitsJBDCImpl();
    }
}
