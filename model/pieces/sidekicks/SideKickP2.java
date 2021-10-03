package model.pieces.sidekicks;

import model.game.Direction;
import model.game.Game;
import exceptions.UnallowedMovementException;

public class SideKickP2 extends SideKick {

	public SideKickP2(Game game, String name) {
		super(game.getPlayer2(), game, name);
	}

	@Override
	public void moveUp() throws UnallowedMovementException {
		throw new UnallowedMovementException("Unallowed Movement Direction",this, Direction.UP);
	}

	@Override
	public void moveUpRight() throws UnallowedMovementException {
		throw new UnallowedMovementException("Unallowed Movement Direction",this, Direction.UPRIGHT);
	}

	@Override
	public void moveUpLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException("Unallowed Movement Direction",this, Direction.UPLEFT);
	}
	@Override
	public String toString() {
		return "<html> SideKickP2" + "<br>"  + "Owner :" + this.getOwner().getName()+ "</html>";
	}
}
