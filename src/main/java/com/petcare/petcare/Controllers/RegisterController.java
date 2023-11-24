package com.petcare.petcare.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RegisterController {
    Stage thisStage;
    
    public void setStage (Stage stage){
        thisStage = stage;
    }

    public void showStage(){
        thisStage.setTitle("PetCare - Registo");
        thisStage.show();
    }
}
