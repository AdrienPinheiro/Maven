package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Pain;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PainJBDCImpl implements DAO<Pain> {
    private static final String SQL_INSERT="INSERT INTO produits (libelle, marque, prixUnitaire, qteStock, poids, dateLimiteConso, type) VALUES(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE="UPDATE produits SET libelle=?, marque=?, prixUnitaire=?, qteStock=?, poids=? WHERE refProd=?";
    private static final String SQL_DELETE="DELETE FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_BY_ID="SELECT * FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_ALL="SELECT * FROM produits";
    @Override
    public void insert(Pain data) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);){
            Date date = Date.valueOf(data.getDateLimiteConso());
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setInt(5, data.getPoid());
            pstmt.setDate(6, date);
            pstmt.setString(7, data.getClass().getSimpleName());
            int nbRow = pstmt.executeUpdate();
            if(nbRow==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    data.setRefProd(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la création du pain - id="+data.getRefProd(), e.getCause());
        }
    }

    @Override
    public void delete(Pain data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la suppression du pain - id="+id, e.getCause());
        }
    }

    @Override
    public void update(Pain data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);){
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setInt(5, data.getPoid());
            pstmt.setLong(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la mise à jour du pain - id="+id, e.getCause());
        }
    }

    @Override
    public Pain selectById(long id) throws DALException {
        ResultSet rs;
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Pain(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getLong(5), rs.getInt(10));
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération du pain -id"+id, e.getCause());
        }
        return null;
    }

    @Override
    public List<Pain> selectAll() throws DALException {
        ResultSet rs;
        List<Pain> painList = new ArrayList<>();
        long id = 0;
        try(Connection cnx = JBDCTools.getConnection(); Statement stmt = cnx.createStatement();){
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()){
                if(rs.getString(6).equals("Pain")){
                    Pain p = new Pain(rs.getLong(1), rs.getString(2), rs.getString(3),
                            rs.getFloat(4), rs.getLong(5), rs.getInt(10));
                    id = p.getRefProd();
                    painList.add(p);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération des pains - id="+id, e.getCause());
        }
        return painList;
    }
}
