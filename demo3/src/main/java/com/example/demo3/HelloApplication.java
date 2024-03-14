package com.example.demo3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    private static final String Gallery = "C:\\Users\\hp\\Desktop\\Javafx\\demo3\\src\\main\\resources\\images";

    private List<File> imageFiles;

    @Override
    public void start(Stage primaryStage) {
        // Load image files from the specified folder
        File folder = new File(Gallery);
        imageFiles = Arrays.stream(folder.listFiles())
                .filter(file -> isImageFile(file.getName()))
                .collect(Collectors.toList());

        if (imageFiles.isEmpty()) {
            System.out.println("No image files found in the specified folder.");
            return;
        }

        // Creating a grid pane to display thumbnails
        GridPane gridPane = new GridPane();
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setPadding(new Insets(10));

        // Populating the grid pane with thumbnail images
        int col = 0;
        int row = 0;
        for (int i = 0; i < imageFiles.size(); i++) {
            File file = imageFiles.get(i);
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
            int finalI = i;
            imageView.setOnMouseClicked(event -> showFullSizeImage(image, finalI));
            gridPane.add(imageView, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Creating a vbox to hold the grid pane
        VBox vbox = new VBox(gridPane);
        vbox.setPadding(new Insets(10));

        // Creating the scene
        Scene scene = new Scene(vbox);

        // Setting the stage
        primaryStage.setTitle("AUTOLAND Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showFullSizeImage(Image image, int currentIndex) {
        // Create ImageView
        ImageView fullImageView = new ImageView(image);
        fullImageView.setFitWidth(540);
        fullImageView.setFitHeight(390);

        // Handle previous button click
        Button prevButton = new Button("Previous");
        prevButton.setStyle("-fx-background-color: purple;");
        prevButton.setOnAction(event -> {
            int prevIndex = (currentIndex - 1 + imageFiles.size()) % imageFiles.size();
            showFullSizeImage(new Image(imageFiles.get(prevIndex).toURI().toString()), prevIndex);
        });

        // Handle next button click
        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: purple;");
        nextButton.setOnAction(event -> {
            int nextIndex = (currentIndex + 1) % imageFiles.size();
            showFullSizeImage(new Image(imageFiles.get(nextIndex).toURI().toString()), nextIndex);
        });

        // Handle exit button click
        Button exitBtn = new Button("Exit");
        exitBtn.setStyle("-fx-background-color: purple;");
        exitBtn.setOnAction(event -> {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        });

        // Creating a vbox to hold the image and navigation buttons
        VBox vbox = new VBox(fullImageView, new HBox(prevButton, nextButton, exitBtn));
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    private boolean isImageFile(String fileName) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        return Arrays.stream(imageExtensions)
                .anyMatch(extension -> fileName.toLowerCase().endsWith(extension));
    }}