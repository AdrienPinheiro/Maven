package eu.adrien.maven.bll;

import eu.adrien.maven.bo.*;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;
import eu.adrien.maven.dal.DAOFactory;

import java.time.LocalDate;
import java.util.List;

public class ProduitManager {
    private static volatile ProduitManager instance;
    private static DAO<Produit> impl;
    private ProduitManager(){
        impl = DAOFactory.getProduitDAO();
    }
    public final static ProduitManager getInstance(){
        if(ProduitManager.instance==null){
            synchronized (ProduitManager.class){
                if(ProduitManager.instance==null){
                    ProduitManager.instance = new ProduitManager();
                }
            }
        }
        return ProduitManager.instance;
    }
    public List<Produit> getProduits() throws BLLException{
        List<Produit> lesProduits;
        try{
            lesProduits = impl.selectAll();
        } catch (DALException e){
            throw new BLLException("erreur récupération des produits", e.getCause());
        }
        return lesProduits;
    }
    public void deleteProduit(Produit produit) throws BLLException{
        controlProduit(produit);
        try{
            impl.delete(produit);
        } catch (DALException e){
            throw new BLLException("erreur lors de la suppression d'un produit", e.getCause());
        }
    }

    public void addProduit(Produit produit) throws BLLException{
        if(produit.getRefProd()!=0){
            throw new BLLException("produit déjà existant");
        }
        controlProduit(produit);
        try{
            impl.insert(produit);
        } catch (DALException e) {
            throw new BLLException("erreur lors de l'ajout d'un produit", e.getCause());
        }
    }
    public void updateProduit(Produit produit) throws BLLException{
        controlProduit(produit);
        try{
            impl.update(produit);
        } catch (DALException e) {
            throw new BLLException("erreur lors de la mise à jour d'un produit", e.getCause());
        }
    }
    public Produit getOneProduit(long id) throws BLLException{
        Produit produit;
        try{
            produit = impl.selectById(id);
        } catch (DALException e) {
            throw new BLLException("erreur lors de la récupération d'un produit - id="+id, e.getCause());
        }
        return produit;
    }
    public void controlProduit(Produit produit) throws BLLException{
        boolean valide = true;
        StringBuilder sb = new StringBuilder();
        if(produit==null){
            throw new BLLException("le produit est null");
        } else if (produit instanceof Pain && ((Pain) produit).getPoid()<=0) {
            sb.append("un pain ne peut pas avoir un poid négatif ou de 0g");
            valide = false;
        } else if (produit instanceof Pain && ((Pain) produit).getDateLimiteConso().isBefore(LocalDate.now())) {
            sb.append("un pain ne peux pas avoir une date limite de consomation inférieur à la date actuel");
            valide = false;
        } else if (produit instanceof Glace && ((Glace) produit).getDateLimiteConso().isBefore(LocalDate.now())) {
            sb.append("une glace ne peux pas avoir une date limite de consomation inférieur à la date actuel");
            valide = false;
        } else if (produit instanceof Glace && ((Glace) produit).getTemperatureConservation()>0) {
            sb.append("une glace ne peux pas être conserver au dessus de 0 degré");
        } else if(produit instanceof CartePostale && ((CartePostale) produit).getType()==null){
            sb.append("la carte postale doit posséder un type");
            valide = false;
        } else if(produit instanceof Stylo && ((Stylo) produit).getCouleur()==null){
            sb.append("le stylo doit posseder une couleur");
            valide = false;
        } else if (produit instanceof Stylo && ((Stylo) produit).getTypeMine()==null){
            sb.append("le stylo doit posseder un type de mine");
            valide = false;
        } else if (produit.getLibelle()==null) {
            sb.append("le produit doit avoir un titre");
            valide = false;
        } else if (produit.getMarque()==null){
            sb.append("le produit doit posseder une marque");
            valide = false;
        } else if (produit.getPrixUnitaire()<=0){
            sb.append("le produit doit avoir un prix unitaire supérieur à 0");
            valide = false;
        } else if (produit.getQteStock()<1){
            sb.append("le produit doit avoir au moins une unité en stock");
            valide = false;
        }
        if(!valide){
            throw new BLLException(sb.toString());
        }
    }

}
