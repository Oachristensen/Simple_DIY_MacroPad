package com.owen.macropadgui;

import com.owen.macropadgui.handlers.KeyMapNameJsonHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import jssc.SerialPort;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KeyMapSelectionController implements Initializable {
    @FXML
    private ListView<Pair<String, String>> keyMapList;

    @FXML
    private Button selectButton;

    private static final SerialPort port = new SerialPort("COM3");

    private static PortListener portListener;


    public void checkIfKeyMapSelected(ActionEvent event) {
        if (GlobalData.getInstance().selectedKeyMap != null) {
            switchToDeviceSelection(event);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Select a Keymap");
            alert.setHeaderText("No Keymap selected");
            alert.setContentText("Select a Keymap to continue");
            alert.show();
        }
    }

    public void switchToDeviceSelection(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeviceSelection.fxml"));
            Parent root = loader.load();

            DeviceSelectionController deviceSelectionController = loader.getController();
            String name = keyMapList.getSelectionModel().getSelectedItem().getValue();
            System.out.println(name);
            deviceSelectionController.setKeyMapName(name);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            stage.show();
            if (!port.isOpened()) {
                port.openPort();
                port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                // port.setParams(9600, 8, 1, 0); // alternate technique
                int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
                port.setEventsMask(mask);
                port.addEventListener(portListener = new PortListener(port));
            }
            portListener.setKeyData();
            portListener.setPortListener(deviceSelectionController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyMapList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Pair<String, String> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (empty || pair == null || pair.getKey() == null) {
                    setText(null);
                } else {
                    setText(pair.getValue());
                }
            }
        });
        keyMapList.getChildrenUnmodifiable().addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
            }
        });
        KeyMapNameJsonHandler keyMapNameJsonHandler = new KeyMapNameJsonHandler(this);
        ArrayList<Pair<String, String>> list = keyMapNameJsonHandler.getList();
        keyMapList.getItems().addAll(list);
        keyMapList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Pair<String, String>> observableValue, Pair<String, String> pair, Pair<String, String> selectedValue) {
//                System.out.println(selectedValue);
                GlobalData.getInstance().selectedKeyMap = selectedValue.getKey();
//                System.out.println(GlobalData.getInstance().selectedKeyMap);
            }

        });
        keyMapList.getSelectionModel().select(0);

    }
}
