package modele;

import modele.Cercle;
import modele.Rectangle;

public interface VisiteurDeFormes {
	public void visiteForme(Cercle c);
	public void visiteForme(Rectangle r);
}
