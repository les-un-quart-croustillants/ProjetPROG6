package Modele.Joueurs;

import java.io.Serializable;

import Utils.Couple;
import Utils.Position;

public class IAshared implements Serializable {

	private static final long serialVersionUID = 9169117411446708506L;
	private Couple<Position,Position> coupCalcule;
	private Position poseCalcule;
	
	public IAshared() {
		this.coupCalcule = null;
		this.poseCalcule = null;
	}
	
	synchronized public void setCoupCalcule(Couple<Position,Position> c) {
		this.coupCalcule = c;
	}
	
	synchronized public void setPoseCalcule(Position c) {
		this.poseCalcule = c;
	}
	
	synchronized public Couple<Position,Position> getCoupCalcule(){
		Couple<Position,Position> res = this.coupCalcule;
		this.coupCalcule = null;
		return res;
	}
	
	synchronized public Position getPoseCalcule() {
		Position res = this.poseCalcule;
		this.poseCalcule = null;
		return res;
	}
	
}
