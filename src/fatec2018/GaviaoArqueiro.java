package fatec2018;

import java.awt.Color;
import robocode.*;
import static robocode.util.Utils.*;

/**
 * Classe que implementa um Robot e possui movimentos e comportamentos
 * inspirados no personagem GaviaoArqueiro da Marvel.
 *
 * @author Vinicius
 */
public class GaviaoArqueiro extends TeamRobot {

    //dimensoes do campo de batalha
    private final int WIDTH = 1000;
    private final int HEIGHT = 1000;
    //margem das bordas
    private final int margem = 150;
    //dimensao dos movimentos laterais
    private int frente = 400;

    public void run() {
        //Cores - chassi: branco, arma: cinza-escuro, radar: branco
        setColors(Color.white, Color.darkGray, Color.white);
        //Cor do tiro: rosa
        setBulletColor(Color.pink);

        //vai para o canto inferior direito
        vaiParaCanto();
        //ajusta para o canhao ficar independente do chassi
        setAdjustGunForRobotTurn(true);
        turnRight(normalAbsoluteAngleDegrees(300 - getHeading()));

      
        while (true) {

            setTurnGunRight(Double.POSITIVE_INFINITY);
            movimenta();

        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        //se ele chegou no ponto do inicio do loop e encontrou um inimigo
        //se é um robô do mesmo tiro não atira
        if (isTeammate(e.getName())) {
            return;
        }
        //se é o robô BorderGuard não atira
        if (e.getName().equals("samplesentry.BorderGuard")) {
            return;
        }
        if (e.getName().equals("sample.Walls")) {
            fire(1);
            return;
        }

        //gira o canhåo em direcao a esse nimigo e tranca nele
        setTurnGunRight(normalRelativeAngleDegrees(getHeading() - getGunHeading() + e.getBearing()));

        //atira com força inversamente proporcional à distancia
        setFire(400 / e.getDistance());
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        if (isTeammate(e.getName())) {
            back(150);
            return;
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        vaiParaCanto();
        turnRight(normalAbsoluteAngleDegrees(300 - getHeading()));
    }

    @Override
    public void onWin(WinEvent event) {
        //faz uma dancinha
    }
    
    /**
    *Movimenta o robo em um V invertido, a ponta do V é quando ele está
    *no centro do campo de batalha.
    */
    public void movimenta() {
        ahead(frente);
        turnLeft(60);
        ahead(frente);
        back(frente);
        turnRight(60);
        back(frente);
    }

    /**
     * Movimenta o robo para o canto direito inferior.
     */
    public void vaiParaCanto() {
        //comecou do lado esquerdo
        if (getX() < WIDTH - margem) {
            turnRight(normalRelativeAngleDegrees(90 - getHeading()));
            ahead(Math.abs(getX() - (WIDTH - margem)));
        } //comecou do lado direito da margem
        else {
            turnRight(normalRelativeAngleDegrees(270 - getHeading()));
            ahead(Math.abs(getX() - (WIDTH - margem)));
        }
        //comecou em cima da margem
        if (getY() > margem) {
            turnRight(normalRelativeAngleDegrees(180 - getHeading()));
            ahead(Math.abs(getY() - (0 + margem)));
        } //comecou em baixo da margem
        else {
            turnRight(normalRelativeAngleDegrees(0 - getHeading()));
            ahead(Math.abs(getY() - (0 + margem)));
        }
    }

    /**
     * Verifica se o robo nao ultrapassou as bordas.
     *
     * @return true se esta ultrapassando as bordas e false se dentro
     */
    public boolean verificaForaBorda() {
        boolean foraBordas = false;
        if (getX() < margem) {
            return true;
        }
        if (getX() > WIDTH - margem) {
            return true;
        }
        if (getY() < margem) {
            return true;
        }
        if (getY() > HEIGHT - margem) {
            return true;
        }
        return foraBordas;
    }

}
