package com.prog2.uwugroup;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class StartStageControl implements Initializable {
    private final int IP_MAX_VALUE = 255;
    private final int IP_MIN_VALUE = 0;
    private final String programTitle = "UWU Gruppe";
    private final Integer[] arrayData = {8080, 8443, 9900, 9990};
    private static Integer networkPort = 0;
    @FXML
    private Button connection = new Button();
    @FXML
    private Label networkLabel = new Label();
    @FXML
    private TextField ipField1 = new TextField();
    @FXML
    private TextField ipField2 = new TextField();
    @FXML
    private TextField ipField3 = new TextField();
    @FXML
    private TextField ipField4 = new TextField();

    private boolean[] ipcheck = {false, false, false, false};

    public void Quit(ActionEvent event) {
        javafx.application.Platform.exit();
    }

    public PingTest onConnection() throws UnknownHostException {
        PingTest test = new PingTest(ipField1.getText() + "." + ipField2.getText() + "." + ipField3.getText() + "." + ipField4.getText(), getPort());
        return test;
    }

    public static void rec() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UwU");
        alert.setHeaderText("Message");
        String s = null;
        alert.setContentText("dir wurde ein strick geschenkt <3");
        alert.show();
    }

    public void showIP(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(programTitle);
        alert.setHeaderText("Your IP");
        String s = null;
        try {
            InetAddress ownIP = InetAddress.getLocalHost();
            System.out.println(ownIP.getHostName());
            System.out.println(ownIP.getHostAddress());
            // TODO: REMOVE ME!!!
            s = ownIP.getHostAddress();
        } catch (Exception e) {
            System.out.println("Exception caught =" + e.getMessage());
        }
        alert.setContentText(s);
        alert.show();
    }

    public void enterTestingGrounds(ActionEvent event) throws IOException {
        MyIO.loadXML(event, "MainStage.fxml");
        System.out.println("enterTestingGrounds event triggered.");     //TODO: REMOVE ME!!!
    }

    private void update() {
        networkLabel.setText(": " + networkPort);
        if (ipcheck[0] && ipcheck[1] && ipcheck[2] && ipcheck[3]) {
            connection.setVisible(true);
        } else {
            connection.setVisible(false);
        }
    }

    public void networkHelp(ActionEvent event) {
        List<Integer> dialogData = Arrays.asList(arrayData);

        ChoiceDialog dialog = new ChoiceDialog(dialogData.get(0), dialogData);
        dialog.setTitle(programTitle);
        dialog.setHeaderText("Select your Port");

        Optional<Integer> result = dialog.showAndWait();
        int selected = 8080;

        if (result.isPresent()) {

            selected = result.get();
        }

        networkPort = selected;
        StartStage.server().changePort(networkPort);
        update();
    }

    public void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(programTitle);
        alert.setHeaderText("About");
        String s = "Your IP is: 127.0.0.1 ";//TODO: change about
        alert.setContentText(s);
        alert.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ipField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d+")) {
                    int change = Integer.parseInt(newValue);
                    if (!(change > IP_MIN_VALUE && change <= IP_MAX_VALUE)) {
                        ipField1.setText(oldValue);
                    } else {
                        ipcheck[0] = true;
                        update();
                    }
                } else if (!newValue.isEmpty()) {
                    ipField1.setText(oldValue);
                    ipcheck[0] = false;
                    update();
                } else {
                    ipcheck[0] = false;
                    update();
                }
            }
        });
        //noinspection DuplicatedCode
        ipField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d+")) {
                    int change = Integer.parseInt(newValue);
                    if (!(change >= IP_MIN_VALUE && change <= IP_MAX_VALUE)) {
                        ipField2.setText(oldValue);
                    } else {
                        ipcheck[1] = true;
                        update();
                    }
                } else if (!newValue.isEmpty()) {
                    ipField2.setText(oldValue);
                    ipcheck[1] = false;
                    update();
                } else {
                    ipcheck[1] = false;
                    update();
                }
            }
        });
        //noinspection DuplicatedCode
        ipField3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d+")) {
                    int change = Integer.parseInt(newValue);
                    if (!(change >= IP_MIN_VALUE && change <= IP_MAX_VALUE)) {
                        ipField3.setText(oldValue);
                    } else {
                        ipcheck[2] = true;
                        update();
                    }
                } else if (!newValue.isEmpty()) {
                    ipField3.setText(oldValue);
                    ipcheck[2] = false;
                    update();
                } else {
                    ipcheck[2] = false;
                    update();
                }
            }
        });
        ipField4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d+")) {
                    int change = Integer.parseInt(newValue);
                    if (!(change > IP_MIN_VALUE && change < IP_MAX_VALUE)) {
                        ipField4.setText(oldValue);
                    } else {
                        ipcheck[3] = true;
                        update();
                    }
                } else if (!newValue.isEmpty()) {
                    ipField4.setText(oldValue);
                    ipcheck[3] = false;
                    update();
                } else {
                    ipcheck[3] = false;
                    update();
                }
            }
        });

    }

    private static int getPort() {
        return networkPort;
    }

    private static void setPort(int newPort) {
        networkPort = newPort;
        Server server = new Server(networkPort);
    }
}