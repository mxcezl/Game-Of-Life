import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JfxAnimVie extends Application {
	
	/**matrice liee a cet objet graphique*/
	Matrice matrice;
 
	/**elements graphiques représentant les cellules*/
	public static Circle[][] circles;
 
	/**taille d'une cellule en pixel*/
	int espace = 10;
 
	/**taille de la matrice*/
	private int taille;
 
	/**nombre de celluls initialement actives*/
	private double densite;
 
	/**délai en ms entre chaque évolution*/
	private int tempo;
 
	/** nombre de generations generés*/
	private int nbGen = 0;
	
	@Override
	public void start(Stage primaryStage) {
		taille = 100;
		densite = 0.2;
		tempo = 100;
		construireScenePourJeuDeLaVie(primaryStage);
	}
 
	void construireScenePourJeuDeLaVie(Stage primaryStage) {
		int largeur = 800;
		int hauteur = 900;
	  
		// Definir la scene principale
		Group root = new Group();
		Scene scene = new Scene(root, largeur, hauteur, Color.BLACK);
		primaryStage.setTitle("Life...");
		primaryStage.setScene(scene);
		
	  
		// Definir les acteurs
		this.matrice = new Matrice(taille, densite);
	  
		// Definir les costumes
		JfxAnimVie.circles = new Circle[taille][taille];
	  
		// Habiller les acteurs
		dessinMatrice(root);
	 
		// Afficher le theatre
		primaryStage.show();
	  
		// Compteur de generation
		Label compteur = new Label("Generations : " + this.nbGen);
		compteur.setTextFill(Color.WHITE);
		root.getChildren().add(compteur);
		
		// Lancer le timer pour faire vivre la matrice
		Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo),
				event-> { // Pour chaque cycle de (tempo) millisecondes, on fait :
					this.matrice.nextGen(); // 1 - Calculer la prochaine generation
					this.matrice.animGrille(); // 2 - Afficher la grille en conscequences
					compteur.setText("Generations :" + ++this.nbGen); // 3 - Affiche le nombre de generations et incrémente ce nombre
				}));
		littleCycle.setCycleCount(Timeline.INDEFINITE);
		littleCycle.play();
		
		// Des qu'un touche est relachée
        scene.setOnKeyReleased(key -> {
            switch (key.getText().toLowerCase()) {
            	case "d": // Si c'est la touche D, on joue l'animation
	                littleCycle.play();
	                break;
                case "p": // Si c'est la touche P, on mets en pause l'animation
                    littleCycle.pause();
                    break;
                case "e": // Si c'est la touche E, on tue toutes les cellules
                    this.matrice.clear();
                    break;
                case "h": // Si c'est la touche H, on recalcule une nouvelle matrice initiale
                    this.matrice.initHasard();
                    break;
            }
        });
	}
 
 /** creation des cellules et de leurs habits  */
	void dessinMatrice(Group root) {
		Cellule[][] grille = this.matrice.getGrille();
		int rayon = this.espace / 2;
		
		for(int i = 0; i < this.taille; i++)
			for(int j = 0; j < this.taille; j++) {
				Cellule cell = grille[i][j];
				circles[i][j] = new Circle(i * this.espace + rayon, j * this.espace + rayon, rayon);
				cell.setCercle(circles[i][j]);
				if (cell.isVivante()) circles[i][j].setFill(FxColors.COUL_DESACTIVE.getColor());
				else circles[i][j].setFill(FxColors.COUL_DESACTIVE.getColor());
				root.getChildren().add(circles[i][j]);
				circles[i][j].setOnMouseClicked(me -> cell.clicSouris());
			}
	}
 
	public static void main(String[] args) {
		launch(args);	
	}
}
