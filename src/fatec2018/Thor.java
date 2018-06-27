package fatec2018;

import java.awt.Color;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Classe que implementa um Robot e possui movimentos e comportamentos
 * inspirados no personagem Thor da Marvel.
 *
 * @author Willian
 */
public class Thor extends TeamRobot {

    int margem = 200;
    final double LARGURA = 1000;
    final double ALTURA = 1000;

    public void run() {
        //Cores - chassi: branco, arma: cinza-escuro, radar: branco
        setColors(Color.white, Color.darkGray, Color.white);
        //Cor do tiro: rosa
        setBulletColor(Color.pink);

        //comeca indo para a margem esquerda superior
        turnRight(normalRelativeAngleDegrees(270 - getHeading()));
        ahead(getX() - margem);
        turnRight(90);
        ahead((ALTURA - margem) - getY());
        turnRight(90);
        turnGunRight(90);

        //chegou na posicao inicial
        turnRight(45);
        while (true) {
            setTurnGunRight(Double.POSITIVE_INFINITY);
            direita();
            esquerda();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        out.println(e.getName());
        //se é um robo amigo nao se faz nada e retorna a função
        if (isTeammate(e.getName())) {
            scan();
            return;
        }
        //se é o robo BorderGuard também não faz nada
        if (e.getName().equals("samplesentry.BorderGuard")) {
            scan();
            return;
        }

        if (getOthers() == 1) {
            fire(1);
            return;
        }

        //se a energia do nosso robo é maior que 20 atira forte
        if (getEnergy() > 20) {

            fire(3);
        } //se a energia do nosso robo é menor que 20 e maior
        //que 10 atira com força média
        else if (getEnergy() <= 20 && getEnergy() > 10) {
            fire(2);
        } //se está com energia menor que 10 atira bem fraco para
        //não gastar energia
        else {
            //fire(0.1);
        }
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

    }

    /**
     * Movimenta-se em zigzag para a direita.
     */
    public void direita() {
        int x = 150;
        ahead(x);
        turnRight(90);
        back(x);
        turnRight(90);
        back(x);
        turnRight(90);
        ahead(x);
        turnRight(90);
        ahead(x);
        turnRight(90);
        back(x);

    }

    /**
     * Movimenta-se em zigzag para a esquerda.
     */
    public void esquerda() {
        int x = 150;
        ahead(150);
        fire(3);
        turnRight(-90);
        back(x);
        turnRight(-90);
        back(x);
        turnRight(-90);
        ahead(x);
        turnRight(-90);
        ahead(x);
        turnRight(-90);
        back(x);

    }
}
