package eu.adrien.maven;

import eu.adrien.maven.bll.BLLException;
import eu.adrien.maven.bll.ProduitManager;
import eu.adrien.maven.bo.Produit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * JavaFX App
 */
public class App extends Application {
    int index = 0;
    @Override
    public void start(Stage stage) throws BLLException {
        TilePane root = new TilePane();
        List<Produit> produitsList = getProduit();

        List<String> typeProd = new ArrayList<>();
        for(Produit produit: produitsList){
            if(!typeProd.contains(produit.getClass().getSimpleName())){
                typeProd.add(produit.getClass().getSimpleName());
            }
        }

        root.setPadding(new Insets(10, 10, 10, 10));
        root.setHgap(20);
        root.setVgap(20);

        HBox prodRefH = new HBox();
        Label prodRef = new Label("Référence :");
        TextField prodRefText = new TextField(String.valueOf(produitsList.get(index).getRefProd()));
        prodRefText.setDisable(true);
        prodRefH.getChildren().addAll(prodRef, prodRefText);

        HBox libelleH = new HBox();
        Label libelle = new Label("Libellé :");
        TextField libelleText = new TextField(produitsList.get(index).getLibelle());
        libelleH.getChildren().addAll(libelle, libelleText);

        HBox marqueH = new HBox();
        Label marque = new Label("Marque :");
        TextField marqueText = new TextField(produitsList.get(index).getMarque());
        marqueH.getChildren().addAll(marque, marqueText);

        HBox prixH = new HBox();
        Label prix = new Label("Prix :");
        TextField prixText = new TextField(String.valueOf(produitsList.get(index).getPrixUnitaire()));
        prixH.getChildren().addAll(prix, prixText);

        HBox quantiteH = new HBox();
        Label quantite = new Label("Quantité :");
        TextField quantiteText = new TextField(String.valueOf(produitsList.get(index).getQteStock()));
        quantiteH.getChildren().addAll(quantite, quantiteText);

        HBox prodTypeH = new HBox();
        Label prodType = new Label("Type de produit :");
        ChoiceBox choiceType = new ChoiceBox(FXCollections.observableArrayList(typeProd));
        prodTypeH.getChildren().addAll(prodType, choiceType);
        choiceType.getSelectionModel().select(0);
        choiceType.setTooltip(new Tooltip("Selectionner un type de produit"));

        Button precedent = new Button("Précédent");
        precedent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(index>=1){
                    index -= 1;
                    prodRefText.setText(String.valueOf(produitsList.get(index).getRefProd()));
                    libelleText.setText(String.valueOf(produitsList.get(index).getLibelle()));
                    marqueText.setText(String.valueOf(produitsList.get(index).getMarque()));
                    prixText.setText(String.valueOf(produitsList.get(index).getPrixUnitaire()));
                    quantiteText.setText(String.valueOf(produitsList.get(index).getQteStock()));
                }
            }
        });
        Button nouveau = new Button("Nouveau");
        Button enregistrer = new Button("Enregistrer");
        Button supprimer = new Button("Supprimer");
        Button suivant = new Button("Suivant");
        suivant.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(index<(produitsList.size()-1)){
                    index += 1;
                    prodRefText.setText(String.valueOf(produitsList.get(index).getRefProd()));
                    libelleText.setText(String.valueOf(produitsList.get(index).getLibelle()));
                    marqueText.setText(String.valueOf(produitsList.get(index).getMarque()));
                    prixText.setText(String.valueOf(produitsList.get(index).getPrixUnitaire()));
                    quantiteText.setText(String.valueOf(produitsList.get(index).getQteStock()));
                }
            }
        });

        HBox btnH = new HBox();
        btnH.getChildren().addAll(precedent, nouveau, enregistrer, supprimer, suivant);

        VBox colum = new VBox();
        colum.getChildren().addAll(prodRefH, libelleH, marqueH, prixH, quantiteH, prodTypeH);

        root.getChildren().addAll(colum, btnH);

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