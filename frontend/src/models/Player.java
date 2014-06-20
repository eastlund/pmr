package models;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * The player class. Represents a player and contains information
 * about its position, hitbox, velocity et.c.
 */
public class Player {
	
	/* === Fields === */

	private int userID = -1;
	private String playerName = "undefined";
	private Circle hitbox;
	private Vector2f velocity;
	private float acceleration;
	private int score = 0;
	
	/* === Constructors === */
	
	/**
	 * @param x		The player's position on the x-axis
	 * @param y		The player's position on the y-axis
	 * @param playerName	The player name
	 */
	public Player(int x, int y, int radius, String playerName) {
		hitbox = new Circle(x, y, radius);
		velocity = new Vector2f(0, 0);
		acceleration = 0.9973f;
		this.setPlayerName(playerName);
	}
	
	/**
	 * Converts a JsonObject to a Player object.
	 * 
	 * @param object	JsonObject representing a Player.
	 * @return a Player instance corresponding to the JsonValues in object. 
	 */
	public static Player fromJSON(JsonObject object) {
		int userID = object.get("userID").asInt();
		String userName = object.get("userName").toString();
		int score = object.get("score").asInt();
		float xPos = object.get("xPos").asFloat();
		float yPos = object.get("yPos").asFloat();
		float xVel = object.get("xVel").asFloat();
		float yVel = object.get("yVel").asFloat();
		float radius = object.get("radius").asFloat();
		
		Player player = new Player((int) xPos, (int) yPos, (int) radius, userName);
		Vector2f velocityVector = new Vector2f(xVel, yVel);
		
		player.setVelocity(velocityVector);
		player.setScore(score);
		player.setUserID(userID);
		
		return player;
	}
	
	/**
	 * Converts a Player object to JsonObject.
	 * 
	 * @param player Player object representing a Player.
	 * @return a JsonObject representing player. 
	 */
	public static JsonObject toJSON(Player player) {
		int userID = player.getUserID();
		String userName = player.getPlayerName();
		int score = player.getScore();
		float xPos = player.getPosition().getX();
		float yPos = player.getPosition().getY();
		float xVel = player.getVelocity().getX();
		float yVel = player.getVelocity().getY();
		float radius = player.getHitbox().getRadius();
		
		JsonObject jsonObject = new JsonObject()
		.add("userID", userID)
		.add("userName", userName)
		.add("score", score)
		.add("xPos", xPos)
		.add("yPos", yPos)
		.add("xVel", xVel)
		.add("yVel", yVel)
		.add("radius", radius);
		
		return jsonObject;
	}
	
	/* === Accessors === */
	
	/** @return the hitbox circle of the player. */
	public Circle getHitbox() {
		return new Circle(hitbox.getCenterX(), 
				hitbox.getCenterY(), 
				hitbox.getRadius());
	}
	/** @return the velocity vector of the player. */
	public Vector2f getVelocity() {return velocity.copy();}
	/** @return the player name of the player. */
	public String getPlayerName() {return playerName;}
	/** @return the position vector of the player. */
	public Vector2f getPosition() {
		float[] pos = hitbox.getCenter();
		return new Vector2f(pos[0],pos[1]);
	}
	
	/* === Mutators === */
	
	/** @param velocity new velocity vector to set */
	public void setVelocity(Vector2f velocity) {this.velocity = velocity;}
	/** @param playerName new name of the player to set */
	public void setPlayerName(String playerName) {this.playerName = playerName;}
	
	/* === Misc. Functions === */
	
	/**
	 * Updates position of player with the current velocity data.
	 */
	public void update() {
		hitbox.setLocation(hitbox.getLocation().add(velocity));
		velocity.scale(acceleration);
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
