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
        margem();

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

        //se a energia do nosso robo é maior que 40 atira forte
        if (getEnergy() > 40) {

            fire(3);
        } //se a energia do nosso robo é menor que 40 e maior
        //que 30 atira com força média
        else if (getEnergy() <= 40 && getEnergy() > 30) {
            fire(2);
        } //se está com energia menor que 30 não atira para
        //não gastar energia
        else {

        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitWall(HitWallEvent e) {
        //retorna para a margem esquerda superior
        margem();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitRobot(HitRobotEvent e) {
        //se for do mesmo time volta pra trás
        if (isTeammate(e.getName())) {
            back(150);
        } //se for inimigo atira forte
        else {
            setTurnGunRight(normalRelativeAngleDegrees(getHeading() - getGunHeading() + e.getBearing()));
            fire(3);
        }

    }

    /**
     * inicia-se indo a margem esquerda superior
     */
    public void margem() {
        //gira o canhão do Robo infinitamente
        setTurnGunRight(Double.POSITIVE_INFINITY);
        //começa a se mover para a posição inicial
        turnRight(normalRelativeAngleDegrees(270 - getHeading()));
        ahead(getX() - margem);
        turnRight(90);
        ahead((ALTURA - margem) - getY());
        turnRight(90);
        turnGunRight(90);
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
