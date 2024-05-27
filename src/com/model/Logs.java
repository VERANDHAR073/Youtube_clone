/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;

/**
 *
 * @author sanar
 */
public class Logs {

    public Logger logger = Logger.getLogger("logger");

    public Logs() {
        setLogger();
    }

    private void setLogger() {
        try {
            FileHandler fieFileHandler = new FileHandler("fualStation.log", true);
            fieFileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fieFileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
