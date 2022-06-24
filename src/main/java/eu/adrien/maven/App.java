package eu.adrien.maven;

import eu.adrien.maven.bll.BLLException;
import eu.adrien.maven.bll.ProduitManager;
import eu.adrien.maven.bo.Produit;
import eu.adrien.maven.dal.DALException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


/**
 * JavaFX App
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws BLLException {
        TilePane root = new TilePane();
        List<Produit> produitsList = getProduit();

        root.setPadding(new Insets(10, 10, 10, 10));
        root.setHgap(20);
        root.setVgap(20);

        HBox prodRefH = new HBox();
        Label prodRef = new Label("Référence :");
        TextField prodRefText = new TextField(String.valueOf(produitsList.get(0).getRefProd()));
        prodRefText.setDisable(true);
        prodRefH.getChildren().addAll(prodRef, prodRefText);

        HBox libelleH = new HBox();
        Label libelle = new Label("Libellé :");
        TextField libelleText = new TextField();
        libelleH.getChildren().addAll(libelle, libelleText);

        HBox marqueH = new HBox();
        Label marque = new Label("Marque :");
        TextField marqueText = new TextField();
        marqueH.getChildren().addAll(marque, marqueText);

        HBox prixH = new HBox();
        Label prix = new Label("Prix :");
        TextField prixText = new TextField();
        prixH.getChildren().addAll(prix, prixText);

        HBox quantiteH = new HBox();
        Label quantite = new Label("Quantité :");
        TextField quantiteText = new TextField();
        quantiteH.getChildren().addAll(quantite, quantiteText);

        HBox prodTypeH = new HBox();
        Label prodType = new Label("Type de produit :");
        ChoiceBox choiceType = new ChoiceBox(FXCollections.observableArrayList());
        prodTypeH.getChildren().addAll(prodType, choiceType);

        VBox colum = new VBox();
        colum.getChildren().addAll(prodRefH, libelleH, marqueH, prixH, quantiteH, prodTypeH);
        root.getChildren().add(colum);

        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Produit");
        stage.setScene(scene);
        stage.show();
    }

    public List<Produit> getProduit() throws BLLException {
        return ProduitManager.getInstance().getProduits();
    }

    public static void main(String[] args) {
        launch();
    }

}