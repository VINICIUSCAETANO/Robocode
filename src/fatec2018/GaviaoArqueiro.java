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
    private int deslocamento = 400;

    public void run() {
        //Cores - chassi: branco, arma: cinza-escuro, radar: branco
        setColors(Color.white, Color.darkGray, Color.white);
        //Cor do tiro: rosa
        setBulletColor(Color.pink);

        //ajusta para o canhao ficar independente do chassi
        setAdjustGunForRobotTurn(true);
        setTurnGunRight(Double.POSITIVE_INFINITY);
        //vai para o canto inferior direito
        vaiParaCanto(); 
        //gira o chassi em 60 graus em relacao ao eixo X (360 - 60)
        turnRight(normalAbsoluteAngleDegrees(300 - getHeading()));     
        //vai para o canto inferior direito
        vaiParaCanto();
  
        //movimenta o robo em um V invertido, a ponta do V é quando ele está
        //no centro do campo de batalha
        while (true) {
            
            setTurnGunRight(Double.POSITIVE_INFINITY);
            ahead(deslocamento);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            turnLeft(60);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            ahead(deslocamento);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            back(deslocamento);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            turnRight(60);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            back(deslocamento);
       
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
            //ahead(150);
            return;
        }
        else {
            setTurnGunRight(normalRelativeAngleDegrees(getHeading() - getGunHeading() + e.getBearing()));
            fire(3);
            scan();
        }
    }

    @Override
    public void onWin(WinEvent event) {
        setBodyColor(Color.green);
        turnRight(30);
        ahead(30);
        turnLeft(30);
        back(30);
        setBodyColor(Color.white);
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

}
