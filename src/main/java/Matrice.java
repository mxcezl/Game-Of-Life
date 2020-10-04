public class Matrice {
	
	private int taille;
	private double densite;
	private Cellule[][] grille;
	private Cellule[][] grilleAncienne;
	
	/* ---------------------------- Constructeur --------------------------------- */
	
	public Matrice(int taille, double densite) {
		super();
		this.setTaille(taille);
		this.setDensite(densite);
		this.setGrille(new Cellule[taille][taille]);
		this.setGrilleAncienne(new Cellule[taille][taille]);
		this.init();
		this.initHasard();
	}
	
	/* ------------------------------ Methodes ----------------------------------- */
	
	// Cree des cellules inactives dans la grille et les recopie dans la grilleAncinne
	public void init() {
		
		// Avec un for x, y car on a besoin des index pour initialiser la cellule
		for(int x = 0; x < this.getGrille().length; x++) {
			  for(int y = 0; y < this.getGrille()[x].length; y++) {
				  this.getGrille()[x][y] = new Cellule(this.grille, x, y, false);
			}
		}
		
		// On recopie la grille initialisée dans l'ancienne grille
		this.copieGrilles();
	}

	
	// Recopie grille dans ancienne grille
	public void copieGrilles() {
		
		// Avec un for x, y car on a besoin des index pour acceder au même element des deux grilles
		for(int i = 0; i < this.getGrille().length; i++)
			  for(int j = 0; j < this.getGrille()[i].length; j++)
			    this.getGrilleAncienne()[i][j] = this.getGrille()[i][j]; // Copie element par element
	}
	
	
	// Active densité de cellule dans la grille
	public void initHasard() {
		for (Cellule[] lineCellule : this.getGrille()) {
			for (Cellule cellule : lineCellule) {
				if (Math.random() < this.getDensite()) { // On a (densite)% de chance que la cellule soit active
		    		cellule.setVivante(true);
		    		cellule.setNextEtat(true);
		    	} else { // Sinon elle est inactive
		    		cellule.setVivante(false);
		    		cellule.setNextEtat(false);
		    	}
			}
		}
		
		// Et dans l'ancienne grille
		this.copieGrilles();
	}
	
	// Methode pour clear toute la matrice (setVivant -> false pour nextGen)
    public void clear() {
        for (Cellule[] lineCellule : this.getGrille()) {
            for (Cellule cellule : lineCellule) {
                cellule.clear(); // Appel a Cellule.clear()
            }
        }
    }
    
    
	// Demande à chaque cellule d’évoluer
	public void animGrille() {
        for (Cellule[] lineCellule : this.getGrille()) {
            for (Cellule cellule : lineCellule) {
            	cellule.evoluer();
            }
        }
	}
	
	// Calcule la prochaine generation
	public void nextGen() {
		for (Cellule[] lineCellule : this.getGrille()) {
			for (Cellule cellule : lineCellule) {
				cellule.nextGen();
			}
		}
	}
	
	/* --------------------------- Getters / Setters ----------------------------- */
	
	public Cellule[][] getGrille() {
		return grille;
	}
	public void setGrille(Cellule[][] grille) {
		this.grille = grille;
	}
	public Cellule[][] getGrilleAncienne() {
		return grilleAncienne;
	}
	public void setGrilleAncienne(Cellule[][] grilleAncienne) {
		this.grilleAncienne = grilleAncienne;
	}
	public double getDensite() {
		return densite;
	}
	public void setDensite(double densite) {
		this.densite = densite;
	}
	public int getTaille() {
		return taille;
	}
	public void setTaille(int taille) {
		this.taille = taille;
	}	
}
