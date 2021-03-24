package gym_manger_interfaces;

import org.junit.jupiter.api.Test;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

 public class MainTest {

        @Test
        public void testA() throws InterruptedException {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    new JFXPanel(); // Initializes the JavaFx Platform
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                new Main().start(new Stage()); // Create and
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // initialize
                            // your app.

                        }
                    });
                }
            });
            thread.start();// Initialize the thread
            Thread.sleep(40000); // Time to use the app, with out this, the thread
            // will be killed before you can tell.
        }



 }
