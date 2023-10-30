package com.example.phase4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controller class for the Admin Flags view.
 */
public class AdminFlagsController implements Initializable {

    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/database1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "lapiz2026";
    private static final String SELECT_QUERY =
            "SELECT entry_username AS user, entry_email AS email, city.name AS city, entry.note AS note, " +
                    "reason.reason AS reason, username AS flagger, email AS flaggerEmail, locationID, " +
                    "entry.date as Date, rating " +
                    "FROM reason " +
                    "NATURAL JOIN entry " +
                    "NATURAL JOIN flags " +
                    "NATURAL JOIN city " +
                    "ORDER BY entry_username, location_ID;";

    @FXML
    private TableView<adminPageEntry> tableViewAdminFlags;
    @FXML
    private TableColumn<adminPageEntry, String> columnUser;
    @FXML
    private TableColumn<adminPageEntry, String> columnCity;
    @FXML
    private TableColumn<adminPageEntry, String> columnNote;
    @FXML
    private TableColumn<adminPageEntry, String> columnReason;
    @FXML
    private TableColumn<adminPageEntry, String> columnFlagger;
    @FXML
    private Button buttonLogOff;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTableColumns();
        loadTableData();
        setEventHandlers();
    }

    /**
     * Configures the table columns with the appropriate property bindings.
     */
    private void setUpTableColumns() {
        columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        columnReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        columnFlagger.setCellValueFactory(new PropertyValueFactory<>("flagger"));
    }

    /**
     * Loads data from the database and adds it to the table view.
     */
    private void loadTableData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<adminPageEntry> entries = FXCollections.observableArrayList();
            while (resultSet.next()) {
                adminPageEntry entry = new adminPageEntry(
                        resultSet.getString("user"),
                        resultSet.getString("city"),
                        resultSet.getString("note"),
                        resultSet.getString("reason"),
                        resultSet.getString("flagger"),
                        resultSet.getDate("Date").toString(),
                        resultSet.getInt("rating"),
                        resultSet.getString("email"),
                        resultSet.getString("flaggerEmail"),
                        resultSet.getInt("locationID"));
                entries.add(entry);
            }
            tableViewAdminFlags.setItems(entries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the event handlers for various components in the view.
     */
    private void setEventHandlers() {
        tableViewAdminFlags.setOnMouseClicked(this::handleTableRowClick);
        buttonLogOff.setOnAction(this::handleLogOffAction);
    }

    /**
     * Event handler for clicking on a table row.
     *
     * @param event MouseEvent that triggered the handler.
     */
    private void handleTableRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            adminPageEntry selectedEntry = tableViewAdminFlags.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                DBUtils.flaggedEntry = selectedEntry;
                DBUtils.changeScene(event, "review-flags.fxml", "Review", null);
            }
        }
    }

    /**
     * Event handler for the log-off button action.
     *
     * @param event ActionEvent that triggered the handler.
     */
    private void handleLogOffAction(ActionEvent event) {
        DBUtils.changeScene(event, "login.fxml", "Log in", null);
    }
}
