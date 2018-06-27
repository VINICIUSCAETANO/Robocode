package fatec2018;

import java.awt.Color;
import robocode.*;
import static robocode.util.Utils.*;

/**
 * Classe que implementa um Robot e possui movimentos e comportamentos
 * inspirados no personagem Wolverine da Marvel.
 *
 * @author Vinícius
 */
public class Wolverine extends TeamRobot {

    //constante que representa a largura do campo de batalha em pixels
    private final double LARGURA = 1000;
    //constante que representa a altura do campo de batalha em pixels
    private final double ALTURA = 1000;
    //tamanho de cada parte do 8
    private final double tamPasso = 200;

    @Override
    public void run() {
        //Cores - chassi: branco, arma: cinza-escuro, radar: branco
        setColors(Color.white, Color.darkGray, Color.white);
        //Cor do tiro: rosa
        setBulletColor(Color.pink);
        
        //vai para o meio do campo de batalha e vira a 45 graus em relação ao
        //compo de batalha
        vaiParaMeio();
        turnRight(45);
        //permite ao canhao girar independentemente do chassi
        setAdjustGunForRobotTurn(true);

        while (true) {
            //gira o canhao indefinidamente para a direita em um método que
            //não bloqueia o resto do movimento do robô
            setTurnGunRight(Double.POSITIVE_INFINITY);
            //movimenta o robo em um oito em que as metades são dois losangos
            movimentaEmOito(tamPasso);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getEnergy() <= 1) {
            fire(1);
        }
        //se é um robo amigo nao se faz nada e retorna a função
        if (isTeammate(e.getName())) {
            scan();
            return;
        }
        //se é o robo BorderGuard também não faz nada
        if (e.getName().equals("samplesentry.BorderGuard")) {
            return;
        }

        if (e.getName().equals("sample.Walls")) {
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
        else {
            fire(0.5);
        }
        //não gastar energia
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        //se bater em uma parede é porque está muito longe da rota predeterminada
        //então volta para o meio do campo de batalha
        vaiParaMeio();
        turnRight(normalAbsoluteAngleDegrees(45 - getHeading()));
        movimentaEmOito(tamPasso);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        //atingindo outro robo se recua um pouco
        if (isTeammate(e.getName())) {
            back(150);
            return;
        }
    }

    @Override
    public void onWin(WinEvent event) {
        //faz alguma dancinha
    }

    /**
     * Faz o robo se movimentar em dois losangos como um 8.
     *
     * @param passo tamanho de cada passo, que corresponde a uma aresta dos
     * losangos
     */
    public void movimentaEmOito(double passo) {
        ahead(passo);
        turnRight(90);
        ahead(passo);
        turnRight(90);
        ahead(passo);
        turnRight(90);
        ahead(passo);
        ahead(passo);
        turnRight(-90);
        ahead(passo);
        turnRight(-90);
        ahead(passo);
        turnRight(-90);
        ahead(passo);
    }

    /**
     * Movimenta o Robo para o meio do campo de batalha.
     */
    public void vaiParaMeio() {
        //o robo comecou do lado direito do campo de batalha
        if (getX() > LARGURA / 2) {
            turnRight(normalRelativeAngleDegrees(270 - getHeading()));
            ahead(getX() - (LARGURA / 2));
        } //comecou do lado esquerdo
        else {
            turnRight(normalRelativeAngleDegrees(90 - getHeading()));
            ahead((LARGURA / 2) - getX());
        }
        //configura a posicao vertical
        if (getY() > ALTURA / 2) {
            turnRight(normalRelativeAngleDegrees(180 - getHeading()));
            ahead(getY() - (ALTURA / 2));
        } //comecou do lado esquerdo
        else {
            turnRight(normalRelativeAngleDegrees(0 - getHeading()));
            ahead((ALTURA / 2) - getY());
        }
    }
}
