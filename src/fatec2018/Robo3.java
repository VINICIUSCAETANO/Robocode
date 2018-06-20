package fatec2018;

import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Classe que implementa um Robot e possui movimentos e comportamentos
 * inspirados no personagem Homem de Ferro da Marvel.
 * 
 * @author Vinícius Caetano
 */

public class Robo3 extends TeamRobot {
// teste dos commits
    //constante que representa a largura do campo de batalha em pixels
    private final double LARGURA = 1000;
    //constante que representa a altura do campo de batalha em pixels
    private final double ALTURA = 1000;
    //tamanho de cada parte do 8
    private double tamPasso = 200;

    @Override
    public void run() {
        //vai para o meio do campo de batalha e vira a 45 graus em relação ao
        //compo de batalha
        vaiParaMeio();
        turnRight(45);

        while (true) {
            //movimenta o robo em um oito em que as metades são dois losangos
            movimentaEmOito(tamPasso);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        out.println(e.getName());
        //se é um robo amigo nao se faz nada e retorna a função
        //não testei ainda esssa parte
        if (isTeammate(e.getName())) {
            return;
        }
        //se a distancia até o inimigo é menor que 50 atira forte
        if (e.getDistance() < 50) {
            fire(3);
        } //podemos colocar mais condições baseadas em mais distancias
        else if (e.getDistance() >= 50 && e.getDistance() < 200){
            fire(2);
        }
        else {
            fire(1);
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        //temos que definir uma estratégia para quando ele bater em um muro
        //por enquanto podemos só recuar um pouco
        back(100);
    }
    
    @Override
    public void onHitRobot(HitRobotEvent e) {
        //se ele antinge outro robô viramos o canhao para ele e atiramos forte
        setAdjustGunForRobotTurn(true);
        turnGunRight(normalRelativeAngleDegrees(e.getBearing() + 
                getHeading() - getGunHeading()));
        fire(3);
        setAdjustGunForRobotTurn(false);
    }
    
    /**
     * Faz o robo se movimentar em dois losangos como um 8.
     * @param passo tamanho de cada passo, que corresponde a uma aresta
     * dos losangos
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
