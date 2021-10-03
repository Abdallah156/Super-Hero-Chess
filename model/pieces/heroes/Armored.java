package model.pieces.heroes;

import model.game.Game;
import model.game.Player;

public class Armored extends NonActivatablePowerHero {

	private boolean armorUp;

	public Armored(Player player, Game game, String name) {
		super(player, game, name);
		this.armorUp = true;
	}

	public boolean isArmorUp() {
		return armorUp;
	}

	public void setArmorUp(boolean armorUp) {
		this.armorUp = armorUp;
	}
	
	@Override
	public String toString() {
		if(armorUp == true)
			return "<html> Armored" +"<br>"  +"Armore is UP " +"<br>" + "Owner:" + this.getOwner().getName() +"</html>";
		return "<html> Armored" + "<br>" + "Armore is DOWN "  +"<br>" + "Owner:" + this.getOwner().getName()+"</html>";
	}
}
