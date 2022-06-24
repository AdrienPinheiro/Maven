package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Auteur;
import eu.adrien.maven.bo.CartePostale;
import eu.adrien.maven.bo.TypeCartePostale;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartePostaleJBDCImpl implements DAO<CartePostale> {
    private static final String SQL_INSERT="INSERT INTO produits (libelle, marque, prixUnitaire, qteStock, typeCartePostale, type) VALUES(?,?,?,?,?,?)";
    private static final String SQL_LINK="INSERT INTO `carteauteur` (`refAuteur`, `refCartePostale`) VALUES (?,?)";
    private static final String SQL_UPDATE="UPDATE produits SET libelle=?, marque=?, prixUnitaire=?, qteStock=?, typeCartePostale=? WHERE refProd=?";
    private static final String SQL_DELETE="DELETE FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_BY_ID="SELECT p.refProd, p.libelle, p.marque, p.prixUnitaire, p.qteStock, p.typeCartePostale, GROUP_CONCAT(a.id, ' ',a.lastname, ' ', a.name) AS auteurs FROM produits p, carteauteur ca, auteurs a WHERE p.refProd = ca.refCartePostale AND ca.refAuteur = a.id AND p.refProd=?";
    private static final String SQL_SELECT_ALL="SELECT p.refProd, p.libelle, p.marque, p.prixUnitaire, p.qteStock, p.typeCartePostale, GROUP_CONCAT(a.id, ' ',a.lastname, ' ', a.name) AS auteurs FROM produits p, carteauteur ca, auteurs a WHERE p.refProd = ca.refCartePostale AND ca.refAuteur = a.id GROUP BY p.refProd";
    @Override
    public void insert(CartePostale data) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);){
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4,data.getQteStock());
            pstmt.setString(5, data.getType().toString());
            pstmt.setString(6, data.getClass().getSimpleName());
            int nbRow = pstmt.executeUpdate();
            if(nbRow==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    data.setRefProd(rs.getLong(1));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertLink(long idCartePostale, long idAuteur) throws DALException{
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_LINK);){
            pstmt.setLong(1, idCartePostale);
            pstmt.setLong(2, idAuteur);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(CartePostale data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CartePostale data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);){
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setString(5, data.getType().toString());
            pstmt.setLong(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CartePostale selectById(long id) throws DALException {
        ResultSet rs;
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                TypeCartePostale enumVal = TypeCartePostale.valueOf(rs.getString("typeCartePostale"));
                List<Auteur> auteursList = new ArrayList<>();
                String[] auteurList = rs.getString("auteurs").split(",");
                for(String auteur: auteurList){
                    String[] auteurString = auteur.split(" ");
                    Auteur newAuteur = new Auteur(Long.parseLong(auteurString[0]), auteurString[1], auteurString[2]);
                    auteursList.add(newAuteur);
                }
                return new CartePostale(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getLong(5), auteursList, enumVal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartePostale> selectAll() throws DALException {
        List<CartePostale> cartePostalesList = new ArrayList<>();
        try(Connection cnx = JBDCTools.getConnection(); Statement pstmt = cnx.createStatement();){
            ResultSet rs = pstmt.executeQuery(SQL_SELECT_ALL);

            rs.next();
            TypeCartePostale enumVal = TypeCartePostale.valueOf(rs.getString("typeCartePostale"));

            List<Auteur> auteursList = new ArrayList<>();
            String[] auteurList = rs.getString("auteurs").split(",");
            for(String auteur: auteurList){
                String[] auteurString = auteur.split(" ");
                Auteur newAuteur = new Auteur(Long.parseLong(auteurString[0]), auteurString[1], auteurString[2]);
                auteursList.add(newAuteur);
            }

            CartePostale cartePostale = new CartePostale(rs.getLong(1), rs.getString(2), rs.getString(3),
                    rs.getFloat(4), rs.getLong(5), auteursList, enumVal);
            cartePostalesList.add(cartePostale);

            ArrayList<Long> idCartePostales = new ArrayList<>();;
            idCartePostales.add(cartePostale.getRefProd());

            do{
                for(CartePostale cartePostaleId : cartePostalesList){
                    idCartePostales.add(cartePostaleId.getRefProd());
                }
                if(cartePostale.getRefProd()!=rs.getLong(1) && !idCartePostales.contains(rs.getLong(1))){
                    enumVal = TypeCartePostale.valueOf(rs.getString("typeCartePostale"));
                    List<Auteur> newAuteursList = new ArrayList<>();
                    auteurList = rs.getString("auteurs").split(",");
                    for(String auteur: auteurList){
                        String[] auteurString = auteur.split(" ");
                        Auteur newAuteur = new Auteur(Long.parseLong(auteurString[0]), auteurString[1], auteurString[2]);
                        newAuteursList.add(newAuteur);
                    }
                    cartePostale = new CartePostale(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), newAuteursList, enumVal);
                    cartePostalesList.add(cartePostale);
                }
            } while (rs.next());

            return cartePostalesList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
