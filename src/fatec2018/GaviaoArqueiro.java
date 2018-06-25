package fatec2018;

import java.awt.Color;
import robocode.*;
import static robocode.util.Utils.*;

/**
 *Classe que implementa um Robot e possui movimentos e comportamentos
 * inspirados no personagem GaviaoArqueiro da Marvel.
 * 
 * @author Vinicius
 */

public class GaviaoArqueiro extends TeamRobot {

    //dimensoes do campo de batalha
    int WIDTH = 1000;
    int HEIGHT = 1000;
    //true se o robô ainda esta indo para o ponto de início
    boolean caminhada = true;
    //se true o robô está pronto para fazer os movimentos de loop
    boolean set = false;

    public void run() {
        //Cores - chassi: branco, arma: cinza-escuro, radar: branco
        setColors(Color.white, Color.darkGray, Color.white);
        //Cor do tiro: rosa
        setBulletColor(Color.pink);

        while (true) {
            //vai para o ponto x=800, y=200
            if (caminhada) {
                goTo(800, 200);
                execute();
            }
            //se chegou no ponto
            if (Math.abs(getX() - 800) < 0.5 && Math.abs(getY() - 200) < 0.5 && caminhada) {
                //vira o robo de maneira perpendicular ao eixo Y
                turnRight(normalRelativeAngleDegrees(90 - getHeading()));
                //permite a rotação da arma independentemente do chassi
                setAdjustGunForRobotTurn(true);
                //ajusta o canhão para apontar para cima
                turnGunLeft(normalAbsoluteAngleDegrees(180 - getGunHeading()));
                //modifica as flags de controle
                caminhada = false;
                set = true;
            }
            if (set) {
                //no loop anda para trás 500 pixels
                setAhead(-500);
                //espera esse método completar
                waitFor(new MoveCompleteCondition(this));
                //anda para frente 500 pixels
                setAhead(500);
                //espera esse movimento completar
                waitFor(new MoveCompleteCondition(this));
            }
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        //se ele chegou no ponto do inicio do loop e encontrou um inimigo
        if (set && !isTeammate(e.getName())) {
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
        return;
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        //se colidir com outro robô tentamos matá-lo ou buscamos distância
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        //quando melhorarmos a movimentacao do robô dificilmente ele irá colidir
        //com uma parede mas podemos acrescentar um comportamento por precaução
    }

    @Override
    public void onWin(WinEvent event) {
        //faz uma dancinha
    }
    
    //Esse método será removido e foi utilizado somente para testes
    private void goTo(double x, double y) {
        /* Transform our coordinates into a vector */
        x -= getX();
        y -= getY();

        /* Calculate the angle to the target position */
        double angleToTarget = Math.atan2(x, y);

        /* Calculate the turn required get there */
        double targetAngle = normalRelativeAngle(angleToTarget - getHeadingRadians());

        /* 
	 * The Java Hypot method is a quick way of getting the length
	 * of a vector. Which in this case is also the distance between
	 * our robot and the target location.
         */
        double distance = Math.hypot(x, y);

        /* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if (targetAngle == turnAngle) {
            setAhead(distance);
        } else {
            setBack(distance);
        }
    }
}

