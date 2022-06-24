package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Glace;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GlaceJBDCImpl implements DAO<Glace> {
    private static final String SQL_INSERT="INSERT INTO produits (libelle, marque, prixUnitaire, qteStock, parfum, temperatureConservation, dateLimiteConso, type) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE="UPDATE produits SET libelle=?, marque=?, prixUnitaire=?, qteStock=?, parfum=?, temperatureConservation=?, dateLimiteConso=? WHERE refProd=?";
    private static final String SQL_DELETE="DELETE FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_BY_ID="SELECT * FROM produits WHERE refProd=?";
    private static final String SQL_SELECT_ALL="SELECT * FROM produits";
    @Override
    public void insert(Glace data) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);){
            Date date = Date.valueOf(data.getDateLimiteConso());
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setString(5, data.getParfum());
            pstmt.setInt(6, data.getTemperatureConservation());
            pstmt.setDate(7, date);
            pstmt.setString(8, data.getClass().getSimpleName());
            int nbRow = pstmt.executeUpdate();
            if(nbRow==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    data.setRefProd(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la création de la glace - id="+data.getRefProd(), e.getCause());
        }
    }

    @Override
    public void delete(Glace data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la suppression de la glace - id="+id, e.getCause());
        }

    }

    @Override
    public void update(Glace data) throws DALException {
        long id = data.getRefProd();
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);){
            Date date = Date.valueOf(data.getDateLimiteConso());
            pstmt.setString(1, data.getLibelle());
            pstmt.setString(2, data.getMarque());
            pstmt.setFloat(3, data.getPrixUnitaire());
            pstmt.setLong(4, data.getQteStock());
            pstmt.setString(5, data.getParfum());
            pstmt.setInt(6, data.getTemperatureConservation());
            pstmt.setDate(7, date);
            pstmt.setLong(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la mise à jour de la glace - id="+id, e.getCause());
        }

    }

    @Override
    public Glace selectById(long id) throws DALException {
        ResultSet rs;
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return new Glace(rs.getDate(9).toLocalDate(), rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getFloat(4), rs.getLong(5),
                        rs.getString(11), rs.getInt(12));
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération de la glace -id"+id, e.getCause());
        }
        return null;
    }

    @Override
    public List<Glace> selectAll() throws DALException {
        ResultSet rs;
        List<Glace> glaceList = new ArrayList<>();
        long id = 0;
        try(Connection cnx = JBDCTools.getConnection(); Statement stmt = cnx.createStatement();){
            rs = stmt.executeQuery(SQL_SELECT_ALL);
            while (rs.next()){
                if(rs.getString(6).equals("Glace")){
                    Glace glc = new Glace(rs.getDate(9).toLocalDate(), rs.getLong(1), rs.getString(2),
                            rs.getString(3), rs.getFloat(4), rs.getLong(5),
                            rs.getString(11), rs.getInt(12));
                    id = glc.getRefProd();
                    glaceList.add(glc);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la récupération des glaces - id"+id, e.getCause());
        }
        return glaceList;
    }
}
