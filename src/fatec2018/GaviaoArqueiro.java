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
    private int turn = 0;
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
        turnLeft(60);

        //movimenta o robo em um V invertido, a ponta do V é quando ele está
        //no centro do campo de batalha
        while (true) {

            ahead(frente);
            turnLeft(60);
            ahead(400);

            if (turn % 5 == 0) {
                setTurnGunRight(90);
            }

            back(frente);
            turnRight(60);
            back(400);
            //
            turn++;
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        //se ele chegou no ponto do inicio do loop e encontrou um inimigo
        if (!isTeammate(e.getName()) && !e.getName().equals("samplesentry.BorderGuard")) {
            //gira o canhåo em direcao a esse nimigo e tranca nele
            setTurnGunRight(normalRelativeAngleDegrees(getHeading() - getGunHeading() + e.getBearing()));
        }
        //se é um robô do mesmo tiro não atira
        if (isTeammate(e.getName())) {
            return;
        }
        //se é o robô BorderGuard não atira
        if (e.getName().equals("samplesentry.BorderGuard")) {
            return;
        }
        //atira com força inversamente proporcional à distancia
        setFire(400 / e.getDistance());
        if (e.getEnergy() <= 1) {
            setTurnGunRight(180);
        }
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
        //batendo na parede se torna mais conservador em relacao aos movimentos
        frente -= 50;
    }

    @Override
    public void onWin(WinEvent event) {
        //faz uma dancinha
    }

    /**
     * Movimenta o robo para o canto direito inferior
     */
    public void vaiParaCanto() {
        //vai para canto direito
        if (getX() < WIDTH - margem) {
            turnRight(normalRelativeAngleDegrees(90 - getHeading()));
            ahead(Math.abs(getX() - (WIDTH - margem)));
        }
        //vai para a parte inferior do campo de batalha
        turnLeft(90);
        back(Math.abs(getY() - (0 + margem)));
    }
}
