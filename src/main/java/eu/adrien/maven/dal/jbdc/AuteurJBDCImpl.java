package eu.adrien.maven.dal.jbdc;

import eu.adrien.maven.bo.Auteur;
import eu.adrien.maven.bo.CartePostale;
import eu.adrien.maven.bo.TypeCartePostale;
import eu.adrien.maven.dal.DALException;
import eu.adrien.maven.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuteurJBDCImpl implements DAO<Auteur> {
    private static final String SQL_INSERT="INSERT INTO auteurs (name, lastname) VALUES(?,?)";
    private static final String SQL_UPDATE="UPDATE auteurs SET name=?, lastname=? WHERE id=?";
    private static final String SQL_DELETE="DELETE FROM auteurs WHERE id=?";
    private static final String SQL_SELECT_BY_ID="SELECT a.id, a.lastname, a.name, GROUP_CONCAT(p.refProd, ',', p.libelle, ',',p.marque, ',', p.qteStock, ',', p.prixUnitaire, ',',p.typeCartePostale) AS produits FROM auteurs a, carteauteur ca, produits p WHERE a.id = ca.refAuteur AND ca.refCartePostale = p.refProd AND a.id=?";
    private static final String SQL_SELECT_ALL="SELECT a.id, a.lastname, a.name, GROUP_CONCAT(p.refProd, ',', p.libelle, ',',p.marque, ',', p.qteStock, ',', p.prixUnitaire, ',',p.typeCartePostale) AS produits FROM auteurs a, carteauteur ca, produits p WHERE a.id = ca.refAuteur AND ca.refCartePostale = p.refProd GROUP BY a.id";

    private static final String SQL_SELECT_ALL_AUTHOR="SELECT * FROM auteurs";
    @Override
    public void insert(Auteur data) throws DALException {
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, data.getPrenom());
            pstmt.setString(2, data.getNom());
            int nbRow = pstmt.executeUpdate();
            if(nbRow==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    data.setRefAuteur(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la création d'un auteur", e.getCause());
        }
    }

    @Override
    public void delete(Auteur data) throws DALException {
        long id = data.getRefAuteur();
        try (Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);){
            pstmt.setLong(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de la suppression d'un auteur - id"+id, e.getCause());
        }
    }

    @Override
    public void update(Auteur data) throws DALException {
        long id = data.getRefAuteur();
        try(Connection cnx = JBDCTools.getConnection();PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);){
            pstmt.setString(1, data.getPrenom());
            pstmt.setString(2, data.getNom());
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur lors de l'update d'un auteur -id"+id, e.getCause());
        }
    }

    @Override
    public Auteur selectById(long id) throws DALException {
        ResultSet rs;
        try(Connection cnx = JBDCTools.getConnection(); PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);){
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                List<CartePostale> cartePostalesList = new ArrayList<>();
                String[] cartePostaleList = rs.getString("produits").split(",");
                TypeCartePostale enumVal = TypeCartePostale.valueOf(cartePostaleList[5]);
                CartePostale newCartePostale = new CartePostale(cartePostaleList[1], cartePostaleList[2], Long.parseLong(cartePostaleList[3]),
                        Float.parseFloat(cartePostaleList[4]), enumVal);
                cartePostalesList.add(newCartePostale);

                return new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Auteur> selectAll() throws DALException {
        List<Auteur> auteursList = new ArrayList<>();
        try(Connection cnx = JBDCTools.getConnection(); Statement stmt = cnx.createStatement(); Statement stmt2 = cnx.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);
            ResultSet rs2 = stmt2.executeQuery(SQL_SELECT_ALL_AUTHOR);

            if(rs.next()) {
                rs.next();

                List<CartePostale> cartePostalesList = new ArrayList<>();
                String[] cartePostales = rs.getString("produits").split(",");
                TypeCartePostale enumVal = TypeCartePostale.valueOf(cartePostales[5]);
                CartePostale newCartePostale = new CartePostale(cartePostales[1], cartePostales[2], Long.parseLong(cartePostales[3]),
                        Float.parseFloat(cartePostales[4]), enumVal);
                cartePostalesList.add(newCartePostale);

                Auteur auteur = new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3));
                auteursList.add(auteur);

                ArrayList<Long> idAuteurs = new ArrayList<>();
                ;
                idAuteurs.add(auteur.getRefAuteur());

                do {
                    for (Auteur auteurId : auteursList) {
                        idAuteurs.add(auteurId.getRefAuteur());
                    }
                    if (auteur.getRefAuteur() != rs.getLong(1) && !idAuteurs.contains(rs.getLong(1))) {
                        List<CartePostale> newCartePostaleList = new ArrayList<>();
                        String[] cartePostaleList = rs.getString("produits").split(",");
                        enumVal = TypeCartePostale.valueOf(cartePostales[5]);
                        newCartePostale = new CartePostale(cartePostaleList[1], cartePostaleList[2], Long.parseLong(cartePostaleList[3]),
                                Float.parseFloat(cartePostaleList[4]), enumVal);
                        newCartePostaleList.add(newCartePostale);
                        auteur = new Auteur(rs.getLong(1), rs.getString(2), rs.getString(3));
                        auteursList.add(auteur);
                    }
                } while (rs.next());
            }
            if(rs2.next()){
                while (rs2.next()){
                    Auteur auteur = new Auteur(rs2.getLong(1), rs2.getString(2), rs2.getString(3));
                    auteursList.add(auteur);
                }
            }

            return auteursList;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
