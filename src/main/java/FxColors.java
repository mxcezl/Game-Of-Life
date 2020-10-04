import javafx.scene.paint.Color;

// Enumeration pour les couleurs
public enum FxColors {
    COUL_ACTIVE(Color.rgb(103, 247, 68)),
    COUL_DESACTIVE(Color.BLACK),
    COUL_VERS_DESACTIVE(Color.rgb(24, 180, 57)),
    COUL_VERS_ACTIVE(Color.rgb(24, 91, 38));
	
	private Color color;
	
	private FxColors(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
    
}
