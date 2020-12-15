package ChessBoard.Util;


import ChessBoard.Enums.Color;
import ChessBoard.Models.ChessBoard;
import ChessBoard.Moves.ChessMove;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    ChessBoard chessBoard;


    Color player = Color.White;
    boolean Checkmate= false;
    List<ChessMove> chessMoves = new ArrayList<>();

    int turnLimit = 20;
    int turnCount = 0;
    long startTime;

    public void RunGame()  {
/*
        MontoCarloTree MontoCarlo.tree = new MontoCarloTree();


        MontoCarloTrainingOutput output;
        List<TrainingSample> allSamples = new ArrayList<>();
        List<TrainingSample> tempSamples = new ArrayList<>();

        int moveCount=0;

        for(int i=0;i<2000;i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Train "+ i+ " Game "+j);
                startTime = System.currentTimeMillis();
                chessBoard = new ChessBoard(new MoveLog());

                Checkmate = false;
                turnCount = 0;
                player = Color.White;
                moveCount = 0;
                ChessMove chessMove;

                do {
                    turnCount++;


                    chessMoves = chessBoard.GetAllAvailableMoves(player);

                    if (chessMoves.size() != 0) {
                        moveCount++;
                        if (moveCount == 10) {
                            output = MontoCarlo.tree.findNextMoveTraining(new BoardState(chessBoard, player),2000);
                            tempSamples.add(output.getSample());
                            chessMove = (ChessMove) output.getNextMove();
                            moveCount = 0;
                        } else {
                            chessMove = MontoCarlo.tree.findNextMove(new BoardState(chessBoard, player),2000);
                        }

                        chessBoard.UpdateBoard(chessMove);

                    } else {

                        Checkmate = true;
                    }

                    if (player == Color.White) {
                        player = Color.Black;
                    } else {
                        player = Color.White;
                    }


                    if (turnCount > turnLimit) {
                        break;
                    }
                } while (Checkmate == false);


                for (TrainingSample sample : tempSamples) {
                    if (turnCount >= turnLimit) {
                        sample.setValue(0);
                    }

                    if (player == Color.White) {
                        if (sample.getPlayerColor() == Color.White) {
                            sample.setValue(1);
                        } else {
                            sample.setValue(-1);
                        }
                    } else {
                        if (sample.getPlayerColor() == Color.Black) {
                            sample.setValue(1);
                        } else {
                            sample.setValue(-1);
                        }
                    }
                }

                allSamples.addAll(tempSamples);
                tempSamples.clear();
                if (turnCount >= turnLimit - 20) {
                    System.out.println("Draw" + turnCount);
                } else {
                    System.out.println("Winner is " + player + turnCount);
                }

                System.out.println("Time taken " + (System.currentTimeMillis() - startTime));

            }

            System.out.println("All samples size" + allSamples.size());
            //BasicNeuralNetwork nn = new BasicNeuralNetwork();
            //nn.Train(allSamples);
            tempSamples.clear();

            allSamples.clear();
        }*/



    }
}




