package Objects;

import Entity.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import Main.GamePanel;

            // S I N G L E T O N
public class OBJ_Heart extends Entity {
    private static OBJ_Heart instance;

    private OBJ_Heart(GamePanel gp) {
        super(gp);
        // Initialization code here
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player1/life/full_heart.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/player1/life/half_heart.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/player1/life/empty_heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OBJ_Heart getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new OBJ_Heart(gp);
        }return instance;
    }
}
