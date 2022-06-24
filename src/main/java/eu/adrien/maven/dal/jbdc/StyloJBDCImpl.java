package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Stylo;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StyloJBDCImpl implements DAO<Stylo> {
    private static final String SQL_INSERT="INSERT INTO produits (libelle, marque, prixUnitaire, qteStock, couleur, typeMine, type) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE="UPDATE produits SET libelle=?, marque=?, prixUnitaire=?, qteStock=?, couleur=?, typeMine=? WHERE refProd=?";
    private static final String SQL_DELETE="DELETE FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_BY_ID="SELECT * FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_ALL="SELECT * FROM produits";

    @Override
    public void insert(Stylo data) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);){
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setString(5, data.getCouleur());
            pstmt.setString(6, data.getTypeMine());
            pstmt.setString(7, data.getClass().getSimpleName());
            int nbRow = pstmt.executeUpdate();
            if(nbRow==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    data.setRefProd(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la création du stylo - id="+data.getRefProd(), e.getCause());
        }
    }

    @Override
    public void delete(Stylo data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la suppression du stylo - id="+id, e.getCause());
        }
    }

    @Override
    public void update(Stylo data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);){
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setString(5, data.getCouleur());
            pstmt.setString(6, data.getTypeMine());
            pstmt.setLong(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la mise à jour du stylo - id="+id, e.getCause());
        }
    }

    @Override
    public Stylo selectById(long id) throws DALException {
        ResultSet rs;
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Stylo(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getLong(5), rs.getString(7),
                        rs.getString(8));
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération du stylo -id"+id, e.getCause());
        }
        return null;
    }

    @Override
    public List<Stylo> selectAll() throws DALException {
        ResultSet rs;
        List<Stylo> styloList = new ArrayList<>();
        long id = 0;
        try(Connection cnx = JBDCTools.getConnection(); Statement stmt = cnx.createStatement();){
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()){
                if(rs.getString(6).equals("Stylo")){
                    Stylo stl = new Stylo(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), rs.getString(7), rs.getString(8));
                    id = stl.getRefProd();
                    styloList.add(stl);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération des stylos - id"+id, e.getCause());
        }
        return styloList;
    }
}
