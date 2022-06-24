package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.*;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsJBDCImpl implements DAO<Produit> {
    private static final String SQL_SELECT_ALL_PRODUITS="SELECT * FROM produits";
    private static final String SQL_SELECT_CARTE_BY_ID="SELECT p.refProd, p.libelle, p.marque, p.prixUnitaire, p.qteStock, p.typeCartePostale, GROUP_CONCAT(a.id, ' ',a.lastname, ' ', a.name) AS auteurs FROM produits p, carteauteur ca, auteurs a WHERE p.refProd = ca.refCartePostale AND ca.refAuteur = a.id AND p.refProd=?";
    private static final String SQL_DELETE="DELETE FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_BY_ID="SELECT * FROM produits WHERE refProd=?";
    @Override
    public void insert(Produit data) throws DALException {
        if(data instanceof Pain){
            PainJBDCImpl painJBDC = new PainJBDCImpl();
            painJBDC.insert((Pain) data);
        }
        if(data instanceof Glace){
            GlaceJBDCImpl glaceJBDC = new GlaceJBDCImpl();
            glaceJBDC.insert((Glace) data);
        }
        if(data instanceof Stylo){
            StyloJBDCImpl styloJBDC = new StyloJBDCImpl();
            styloJBDC.insert((Stylo) data);
        }
        if(data instanceof CartePostale){
            CartePostaleJBDCImpl cartePostaleJBDC = new CartePostaleJBDCImpl();
            cartePostaleJBDC.insert((CartePostale) data);
        }
    }
    @Override
    public void delete(Produit data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la suppression d'un produit' - id="+id, e.getCause());
        }
    }
    @Override
    public void update(Produit data) throws DALException {
        if(data instanceof Pain){
            PainJBDCImpl painJBDC = new PainJBDCImpl();
            painJBDC.update((Pain) data);
        }
        if(data instanceof Glace){
            GlaceJBDCImpl glaceJBDC = new GlaceJBDCImpl();
            glaceJBDC.update((Glace) data);
        }
        if(data instanceof Stylo){
            StyloJBDCImpl styloJBDC = new StyloJBDCImpl();
            styloJBDC.update((Stylo) data);
        }
        if(data instanceof CartePostale){
            CartePostaleJBDCImpl cartePostaleJBDC = new CartePostaleJBDCImpl();
            cartePostaleJBDC.update((CartePostale) data);
        }
    }
    @Override
    public Produit selectById(long id) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                if(rs.getString(6).equals("Pain")){
                    return new Pain(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), rs.getInt(10));
                }
                if(rs.getString(6).equals("Glace")){
                    return new Glace(rs.getDate(9).toLocalDate(), rs.getLong(1), rs.getString(2),
                            rs.getString(3), rs.getFloat(4), rs.getLong(5),
                            rs.getString(11), rs.getInt(12));
                }
                if(rs.getString(6).equals("Stylo")){
                    return new Stylo(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), rs.getString(7),
                            rs.getString(8));
                }
                if(rs.getString(6).equals("CartePostale")){
                    try(PreparedStatement pstmt2 = cnx.prepareStatement(SQL_SELECT_CARTE_BY_ID);){
                        pstmt2.setLong(1, id);
                        ResultSet rs2 = pstmt2.executeQuery();
                        if(rs2.next()){
                            TypeCartePostale enumVal = TypeCartePostale.valueOf(rs2.getString("typeCartePostale"));
                            List<Auteur> auteursList = new ArrayList<>();
                            String[] auteurList = rs2.getString("auteurs").split(",");
                            for(String auteur: auteurList){
                                String[] auteurString = auteur.split(" ");
                                Auteur newAuteur = new Auteur(Long.parseLong(auteurString[0]), auteurString[1], auteurString[2]);
                                auteursList.add(newAuteur);
                            }
                            return new CartePostale(rs2.getLong(1), rs2.getString(2), rs2.getString(3),
                                    rs2.getFloat(4), rs2.getLong(5), auteursList, enumVal);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération du pain -id"+id, e.getCause());
        }
        return null;
    }
    @Override
    public List<Produit> selectAll() throws DALException {
        List<Produit> produitsList = new ArrayList<>();
        long id = 0;
        try(Connection cnx = JBDCTools.getConnection(); Statement stmt = cnx.createStatement();){
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_PRODUITS);
            while (rs.next()) {
                if (rs.getString(6).equals("Pain")) {
                    Pain p = new Pain(rs.getLong("refProd"), rs.getString("libelle"), rs.getString("marque"),
                            rs.getFloat("prixUnitaire"), rs.getLong("qteStock"), rs.getInt("poids"));
                    id = p.getRefProd();
                    produitsList.add(p);
                }
                if(rs.getString(6).equals("Glace")){
                    Glace glc = new Glace(rs.getDate(9).toLocalDate(), rs.getLong(1), rs.getString(2),
                            rs.getString(3), rs.getFloat(4), rs.getLong(5),
                            rs.getString(11), rs.getInt(12));
                    id = glc.getRefProd();
                    produitsList.add(glc);
                }
                if(rs.getString(6).equals("Stylo")){
                    Stylo stl = new Stylo(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), rs.getString(7), rs.getString(8));
                    id = stl.getRefProd();
                    produitsList.add(stl);
                }
                if(rs.getString(6).equals("CartePostale")){
                    long idCp = rs.getLong(1);
                    try(PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_CARTE_BY_ID);){
                        pstmt.setLong(1, idCp);
                        ResultSet rs2 = pstmt.executeQuery();
                        if(rs2.next()){
                            TypeCartePostale enumVal = TypeCartePostale.valueOf(rs2.getString("typeCartePostale"));
                            List<Auteur> auteursList = new ArrayList<>();
                            String[] auteurList = rs2.getString("auteurs").split(",");
                            for(String auteur: auteurList){
                                String[] auteurString = auteur.split(" ");
                                Auteur newAuteur = new Auteur(Long.parseLong(auteurString[0]), auteurString[1], auteurString[2]);
                                auteursList.add(newAuteur);
                            }
                            CartePostale cp = new CartePostale(rs2.getLong(1), rs2.getString(2), rs2.getString(3),
                                    rs2.getFloat(4), rs2.getLong(5), auteursList, enumVal);
                            produitsList.add(cp);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e){
            throw new DALException("Erreur lors de la récupération de tout les produits => produit problématique id="+id, e.getCause());
        }
        return produitsList;
    }
}
