package tkv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class NezetController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML import">
    @FXML
    TextField vezeteknevInput, keresztnevInput, emailInput, mentesInput;

    @FXML
    TableView adatTablaNezet;

    @FXML
    Pane kontaktPane, exportPane;

    Button felveszButton;
    @FXML
    Button mentesButton;

    @FXML
    StackPane menuPane;

    @FXML
    AnchorPane anchorA;

    @FXML
    SplitPane splitS;

    //@FXML
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Osztályváltozók">
    DB db = new DB();
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="STATIKUS értékek">
    private final String MENU_LISTA = "Lista";
    private final String MENU_EXIT = "Kilépés";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_KONTACT = "Kontatktok";
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Objektum lista ami kompatibilis string tipust használ a tábla nézettel való adatcserére az Ember pojo használatával összeköttetés az adatbázis elemeivel">
    private final ObservableList<Ember> adat = FXCollections.observableArrayList();
//</editor-fold>
    @FXML
    private Button mentesButoon;

    //<editor-fold defaultstate="collapsed" desc="A program indulásakor a táblanézet létrehozása és megjelenítése eseménykezeléssel dinamikusan létrehozva">
    public void tablabeallitas() {
        TableColumn elsonevCella = new TableColumn("Vezetéknév");
        elsonevCella.setMinWidth(130);
        elsonevCella.setCellFactory(TextFieldTableCell.forTableColumn());
        elsonevCella.setCellValueFactory(new PropertyValueFactory<Ember, String>("vezeteknev"));

        elsonevCella.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ember, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ember, String> t) {

                Ember aktualisEmber = (Ember) t.getTableView().getItems().get(t.getTablePosition().getRow());
                aktualisEmber.setVezeteknev(t.getNewValue());

                db.Update(aktualisEmber);
            }

        }
        );

        TableColumn masodiknevCella = new TableColumn("Keresztnév");
        masodiknevCella.setMinWidth(130);
        masodiknevCella.setCellFactory(TextFieldTableCell.forTableColumn());
        masodiknevCella.setCellValueFactory(new PropertyValueFactory<Ember, String>("keresztnev"));

        masodiknevCella.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ember, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ember, String> t) {

                Ember aktualisEmber = (Ember) t.getTableView().getItems().get(t.getTablePosition().getRow());
                aktualisEmber.setKeresztnev(t.getNewValue());

                db.Update(aktualisEmber);
            }

        }
        );
        TableColumn emailCella = new TableColumn("Emailcím");
        emailCella.setMinWidth(250);
        emailCella.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCella.setCellValueFactory(new PropertyValueFactory<Ember, String>("emailcim"));

        emailCella.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Ember, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Ember, String> t) {

                Ember aktualisEmber = (Ember) t.getTableView().getItems().get(t.getTablePosition().getRow());
                aktualisEmber.setEmailcim(t.getNewValue());

                db.Update(aktualisEmber);
            }

        }
        );

        //törlés gomb dinamikus hozzáadása a tábla megjelenítőhöz
        TableColumn removeCol = new TableColumn("Törlés");
        emailCella.setMinWidth(100);

        Callback<TableColumn<Ember, String>, TableCell<Ember, String>> cellFactory
                = new Callback<TableColumn<Ember, String>, TableCell<Ember, String>>() {
            @Override
            public TableCell call(final TableColumn<Ember, String> param) {
                final TableCell<Ember, String> cell = new TableCell<Ember, String>() {
                    final Button btn = new Button("Törlés");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((ActionEvent event)
                                    -> {
                                Ember ember = getTableView().getItems().get(getIndex());
                                adat.remove(ember);
                                db.removeKontakt(ember);

                            });
                            setGraphic(btn);
                            setText(null);
                        }

                    }
                };
                return cell;
            }

        };

        removeCol.setCellFactory(cellFactory);

        adatTablaNezet.getColumns().addAll(elsonevCella, masodiknevCella, emailCella,removeCol);
        adat.addAll(db.getAllUsers());
        adatTablaNezet.setItems(adat);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Menuszerkezet dinamikus felépítése és működtetése">
    private void menubeallitas() {
        TreeItem<String> fomenu = new TreeItem<>("Menü");
        TreeView<String> menu = new TreeView<>(fomenu);
        menu.setShowRoot(false);
        Node kilepesNode = new ImageView(new Image(getClass().getResourceAsStream("/close.jpg")));
        
        kilepesNode.setOpacity(0.5);
        kilepesNode.setRotate(180);
        TreeItem<String> almenuA = new TreeItem(MENU_KONTACT);
        TreeItem<String> almenuB = new TreeItem(MENU_EXIT, kilepesNode);
        
        almenuA.setExpanded(true);

        Node kontaktNode = new ImageView(new Image(getClass().getResourceAsStream("/kont.jpg")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/ment.gif")));

        TreeItem<String> almenuA1 = new TreeItem(MENU_LISTA, kontaktNode);
        TreeItem<String> almenuA2 = new TreeItem(MENU_EXPORT, exportNode);

        almenuA.getChildren().addAll(almenuA1, almenuA2);

        fomenu.getChildren().addAll(almenuA, almenuB);

        menuPane.getChildren().add(menu);

        menu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_KONTACT:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                        case MENU_LISTA:
                            kontaktPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXPORT:
                            kontaktPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                    }
                }

            }
        });

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Adatabevitel">
    @FXML
    public void felvitel(ActionEvent event) {
        System.out.println("felvitel");
        String email = emailInput.getText();
        System.out.println("" + vezeteknevInput.getText());
        System.out.println("" + keresztnevInput.getText());
        if (email.length() > 5 && email.contains("@") && email.contains(".")) {

            Ember ujember = new Ember("", vezeteknevInput.getText(), keresztnevInput.getText(), email);
            adat.add(ujember);

            db.addUser(ujember); //adattáblához ad

            vezeteknevInput.clear();
            keresztnevInput.clear();
            emailInput.clear();
        } else {
            uzenetek("Adj meg egy helyes email címet");

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PDFbe mentés ">
    @FXML
    public void pdfexportalas(ActionEvent event) {
        String ell = mentesInput.getText();
        ell = ell.replace("\\s+", "");
        if (ell != null && !ell.equals("")) {
            PDFgeneralas pdfmentes = new PDFgeneralas();
            pdfmentes.pdfgenerator(ell, adat);
        } else {
            uzenetek("Adj meg egy fájl nevet");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="A felhasználónak küldött üzenetek dinamikusan létrehozva">
    private void uzenetek(String uzenet) {
        splitS.setDisable(true);
        splitS.setOpacity(0.4);
        Label label = new Label(uzenet);
        Button okButton = new Button("OK");
        VBox vbox = new VBox(label, okButton);
        vbox.setAlignment(Pos.CENTER);

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                splitS.setDisable(false);
                splitS.setOpacity(1);
                vbox.setVisible(false);

            }
        });
        anchorA.getChildren().add(vbox);
        anchorA.setTopAnchor(vbox, 300.0);
        anchorA.setLeftAnchor(vbox, 300.0);

    }
//</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tablabeallitas();
        menubeallitas();

    }

}
