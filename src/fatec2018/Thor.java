package fatec2018;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Thor - a robot by (your name here)
 */
public class Thor extends Robot
{
	  int margem = 200;
    final double LARGURA = 1000; 
    final double ALTURA = 1000;
    int direcao = 1;
    
    public void run() {
        //setar as cores
        
        //comeca indo para a margem esquerda superior
        
        turnRight(normalRelativeAngleDegrees(270 - getHeading()));
        ahead(getX() - margem);
        turnRight(90);
        ahead((ALTURA - margem) - getY());
        turnRight(90);
        turnGunRight(90);
        //setAdjustGunForRobotTurn(true);
        //setAdjustRadarForRobotTurn(true);
        //chegou na posicao inicial
		turnRight(45);
        while(true) {
			direita();
			esquerda();
            //ahead(LARGURA - (margem * 2));
			//back(LARGURA - (margem * 2));
			
			
        }
    }  

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(3);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		/*turnRight(normalRelativeAngleDegrees(270 - getHeading()));
        ahead(getX() - margem);
        turnRight(90);
        ahead((ALTURA - margem) - getY());
        turnRight(90);
        turnGunRight(90);*/
	}
	
	public void direita() {
		int x = 150;
        ahead(x);
		fire(3);
		turnRight(90);
        back(x);
		fire(3);
		turnRight(90);
		back(x);
		fire(3);
		turnRight(90);
		ahead(x);
		fire(3);
		turnRight(90);
		ahead(x);
		fire(3);
		turnRight(90);
		back(x);
		fire(3);
		/*turnRight(90);
		back(x);
		fire(3);
		turnRight(90);
		ahead(x);
		fire(3);
		turnRight(90);
		ahead(x);
		fire(3);
		turnRight(90);*/
    }
	public void esquerda() {
		int x = 150;
        ahead(150);
		fire(3);
		turnRight(-90);
		back(x);
		fire(3);
		turnRight(-90);
		back(x);
		fire(3);
		turnRight(-90);
		ahead(x);
		fire(3);
		turnRight(-90);
		ahead(x);
		fire(3);
		turnRight(-90);
		back(x);
		fire(3);
		/*turnRight(-90);
		ahead(x);
		fire(3);
		turnRight(-90);
		ahead(x);
		fire(3);
		turnRight(-90);*/
    }
    
    public void movimenta() {
        if(getX() >= LARGURA - margem) {
            ahead(-LARGURA);
        }
        if(getX() <= margem) {
            ahead(LARGURA);
        }
    
    }	
}
