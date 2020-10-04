// Mail à envoyer le TP :
// david.dupont1234@gmail.com
//
// Maxence Zolnieruck

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Cellule {
	
	private boolean vivante;
	private boolean nextEtat;
	private boolean isTransition;
	private Cellule[][] grille;
	private Circle cercle;
	private int x;
	private int y;
	
	/* ---------------------------- Constructeur --------------------------------- */
	
	public Cellule(Cellule[][] grille, int x, int y, boolean vivante) {
		super();
		this.setVivante(vivante);
		this.setGrille(grille);
		this.setNextEtat(true);
		this.setTransition(false);
		this.setX(x);
		this.setY(y);
	}
	
	/* ------------------------------ Methodes ----------------------------------- */
	
	/* change la valeur de la cellule par rapport à son voisinage
	On prendra un monde sphérique. ’taille’ étant la dimension de la grille carrée,
	la case en haut à gauche de la case (0,0) est la case (taille-1, taille-1).
	*/
	public void evoluer() {
		
        int nbCellAlive = 0; // Nombre de cellules actives autour de la cellule courante
        int tailleMat = this.getGrille().length; // Taille de la matrice
        
        // Check l'état des cellules autour de la cellule courante
        for (int i = -1; i <= 1; i++) { // i = -1, 0, 1
        	
        	int xFormated = ((this.getX() + i) + tailleMat) % tailleMat; // Pour pas que le x dépasse la taille du tableau
        	
            for (int j = -1; j <= 1; j++) { // j = -1, 0 ,1
            	
                if (i == 0 && j == 0) { // On vérifie pas notre cellule
                    continue;  // Passe a la valeur j suivante direcetement
                }
                
                int yFormated = ((y + j) + tailleMat) % tailleMat; // Pour pas que le y dépasse la taille du tableau
                if (this.grille[xFormated][yFormated].vivante) { // Si la cellule a verifier est vivante
                	nbCellAlive++; // Alors on ajoute 1 au nombre de cellules actives autour de la matrice
                }
            }
        }
        
        // Conditions du jeu de la vie
        if (this.vivante && (nbCellAlive < 2 || nbCellAlive > 3)) {
        	this.setNextEtat(false);
        	this.setTransition(true);
        } else {
            if (nbCellAlive == 3) {
            	this.setNextEtat(true);
            	this.setTransition(true);
            }
        }
        
        // On change la couleur en conscéquences
        this.changerCouleur();
	}
	
	// Permet de tuer la cellule (utilisé pour clear toute la grille)
    public void clear() {
        this.setVivante(false);
        this.setNextEtat(false);
        this.getCercle().setFill(FxColors.COUL_DESACTIVE.getColor());
    }
    
    // Quand on clic sur une cellule, on change son etat (vivante -> morte / morte -> vivante)
    public void clicSouris() {
        this.setNextEtat(!this.nextEtat);
        this.changerCouleur();
    }
    
    // Change la couleur d'une cellule en fonction de son prochain etat, etat courant
    public void changerCouleur() {
    	Color c;
        if (this.isTransition()) {
            if (this.nextEtat) {
                c = FxColors.COUL_VERS_ACTIVE.getColor();
            } else {
                c = FxColors.COUL_VERS_DESACTIVE.getColor();
            }
            this.setTransition(!this.isTransition);
        } else {
            if (this.nextEtat) {
                c = FxColors.COUL_ACTIVE.getColor();
            } else {
                c = FxColors.COUL_DESACTIVE.getColor();
            }
        }
        
        // Estompe la couleur graduellement (100 ms)
        new FillTransition(Duration.millis(100), this.getCercle(), (Color) this.getCercle().getFill(), c).play();
    }
    
    // On passe a l'état suivant
    public void nextGen() {
    	this.setVivante(this.nextEtat);
    }
	/* --------------------------- Getters / Setters ----------------------------- */
	
    public boolean isTransition() {
		return isTransition;
	}

	public void setTransition(boolean isTransition) {
		this.isTransition = isTransition;
	}

	public void setNextEtat(boolean nextEtat) {
		this.nextEtat = nextEtat;
	}

	public boolean isVivante() {
		return vivante;
	}

	public void setVivante(boolean vivante) {
		this.vivante = vivante;
	}

	public Cellule[][] getGrille() {
		return grille;
	}

	public void setGrille(Cellule[][] grille) {
		this.grille = grille;
	}

	public Circle getCercle() {
		return cercle;
	}

	public void setCercle(Circle cercle) {
		this.cercle = cercle;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
