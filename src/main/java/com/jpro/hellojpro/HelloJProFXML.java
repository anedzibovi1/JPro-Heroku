package com.jpro.hellojpro;

import com.jpro.hellojpro.controller.StudyntFXMLController;
import com.jpro.webapi.JProApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloJProFXML extends JProApplication
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        //load user interface as FXML file
        StudyntFXMLController studyntFXMLController = new StudyntFXMLController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/HelloJPro.fxml"));
        loader.setController(studyntFXMLController);
        Scene scene = null;
        try
        {
            Parent root = loader.load();
            StudyntFXMLController controller = loader.getController();
            controller.init(this);

            //create JavaFX scene
            scene = new Scene(root);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        stage.setTitle("Hello jpro!");
        stage.setScene(scene);

        //open JavaFX window
        stage.show();
    }
}
