package model.gui;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.game.Cell;
import model.game.Game;
import model.pieces.Piece;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;

public class ButtonCells extends JButton  {
	private Cell c;
	private Piece p;
	private Image ranged =  ImageIO.read(new File("Ranged.png"));
	private Image armored =  ImageIO.read(new File("Armored.png"));
	private Image sidekick =  ImageIO.read(new File("SideKick.png"));
	private Image medic =  ImageIO.read(new File("Medic.png"));
	private Image tech =  ImageIO.read(new File("Tech.png"));
	private Image speedster =  ImageIO.read(new File("Speedster.png"));
	private Image superr =  ImageIO.read(new File("Super.png"));
	private Image ranged2 =  ImageIO.read(new File("Ranged2.png"));
	private Image armored2 =  ImageIO.read(new File("Armored2.png"));
	private Image sidekick2 =  ImageIO.read(new File("SideKick2.png"));
	private Image medic2 =  ImageIO.read(new File("Medic2.png"));
	private Image tech2 =  ImageIO.read(new File("Tech2.png"));
	private Image speedster2 =  ImageIO.read(new File("Speedster2.png"));
	private Image superr2 =  ImageIO.read(new File("Super2.png"));
	
	public ButtonCells(Cell c) throws IOException {
		super("");
		this.c = c;
		if(getC().getPiece() instanceof Ranged && c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(ranged.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Medic&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(medic.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Armored&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(armored.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Speedster&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(speedster.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Tech&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(tech.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Super&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(superr.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof SideKick&& c.getPiece().getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(sidekick.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Armored&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(armored2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Tech&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(tech2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Medic&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(medic2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Super&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(superr2.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Speedster&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(speedster2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof Ranged&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(ranged2.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(getC().getPiece() instanceof SideKick&& c.getPiece().getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(sidekick2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		
		
		else
			this.setForeground(Color.white);
	}
	
	public ButtonCells(Piece p) throws IOException  {
		super("");
		this.p = p;
		if(p instanceof Ranged && p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(ranged.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Medic&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(medic.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Armored&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(armored.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Speedster&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(speedster.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Tech&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(tech.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Super&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(superr.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof SideKick&& p.getOwner().equals(base.getGame().getPlayer1()))
			this.setIcon(new ImageIcon(sidekick.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Armored&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(armored2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Tech&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(tech2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Medic&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(medic2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Super&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(superr2.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Speedster&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(speedster2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof Ranged&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(ranged2.getScaledInstance(200, 75, Image.SCALE_SMOOTH)));
		else if(p instanceof SideKick&& p.getOwner().equals(base.getGame().getPlayer2()))/////
			this.setIcon(new ImageIcon(sidekick2.getScaledInstance(75, 75, Image.SCALE_SMOOTH)));
		
		
		else
			this.setForeground(Color.white);
		
	}

	public Cell getC() {
		return c;
	}
	
	public Piece getP() {
		return p;
	}
	
}
