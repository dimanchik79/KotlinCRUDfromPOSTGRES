package gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class FXMLApplication : Application() {
    override fun start(primaryStage: Stage) {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/resource/authorize.fxml"))
        val root = fxmlLoader.load<Parent>()
        val scene = Scene(root, 600.0, 470.0)

        primaryStage.title = "Authorize"
        primaryStage.scene = scene
        primaryStage.show()
    }
}