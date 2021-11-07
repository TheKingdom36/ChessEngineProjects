package ChessEngine;

import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import MontoCarlo.PolicyOutput;
import MontoCarlo.PolicyPredictor;
import NeuralNetwork.Networks.ConvNetwork;
import NeuralNetwork.Utils.Dim3Struct;

public class ChessPolicyPredictor extends PolicyPredictor<Dim3Struct,ChessBoardState> {

    ConvNetwork neuralNetwork;

    public ChessPolicyPredictor(ConvNetwork network){
        this.neuralNetwork = network;
    }

    @Override
    protected Dim3Struct ConvertToPolicyInput(ChessBoardState state) {

        ChessBoard chessBoard = state.getBoard().copy();

        Dim3Struct input = new Dim3Struct(8,8,21);

        int plane_index=0;

        //Create pieces planes for each state
        for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                if(chessBoard.getBoardSquares()[j][i].hasPiece()){
                    if(chessBoard.getBoardSquares()[j][i].getChessPiece().getColor() == Color.White){
                        switch (chessBoard.getBoardSquares()[j][i].getChessPiece().getType()){
                            case Pawn:{
                                input.setValue(1,i,j,plane_index);
                                break;
                            }
                            case Rook:{
                                input.setValue(1,i,j,plane_index+1);
                                break;
                            }
                            case Bishop:{
                                input.setValue(1,i,j,plane_index+2);

                                break;
                            }
                            case Knight:{
                                input.setValue(1,i,j,plane_index+3);

                                break;
                            }
                            case Queen:{
                                input.setValue(1,i,j,plane_index+4);
                                break;
                            }
                            case King:{
                                input.setValue(1,i,j,plane_index+5);
                                break;
                            }
                        }
                    }else{
                        switch (chessBoard.getBoardSquares()[j][i].getChessPiece().getType()){
                            case Pawn:{
                                input.setValue(1,i,j,plane_index+7);

                                break;
                            }
                            case Rook:{

                                input.setValue(1,i,j,plane_index+8);

                                break;
                            }
                            case Bishop:{
                                input.setValue(1,i,j,plane_index+9);
                                break;
                            }
                            case Knight:{
                                input.setValue(1,i,j,plane_index+10);
                                break;
                            }
                            case Queen:{
                                input.setValue(1,i,j,plane_index+11);
                                break;
                            }
                            case King:{
                                input.setValue(1,i,j,plane_index+12);
                                break;
                            }

                        }
                    }
                }
            }
        }

        //Set Rep count
        //input[plane_index+6].setValue(i,j,repCount);
        //Set Rep Count
        //input[plane_index+13].setValue(i,j,repCount);


        plane_index =14;

        //Plane_index is now equal to 14*boardsCopy.length

        //Color
        if(state.getPlayerColor()== Color.White){
            input.setValue(1,0,1,plane_index);

        }else {
            input.setValue(1,1,0,plane_index);

        }

        //Total move count
        input.setValue(state.getTotalMoveCount(),0,0,plane_index+1);


        //P1 casting queen side
        if(state.isCastleQueenSide()==true){
            input.setValue(1,0,0,plane_index+2);
        }else{
            input.setValue(0,0,0,plane_index+2);
        }

        //P1 castling king side
        if(state.isCastleQueenSide()==true){
            input.setValue(1,0,0,plane_index+3);
        }else{
            input.setValue(0,0,0,plane_index+3);
        }

        //P2 castling queen side
        if(state.isOppCastleQueenSide()==true){
            input.setValue(1,0,0,plane_index+4);
        }else{
            input.setValue(0,0,0,plane_index+4);
        }

        //P2 castling king side
        if(state.isOppCastleKingSide()==true){
            input.setValue(1,0,0,plane_index+5);
        }else{
            input.setValue(0,0,0,plane_index+5);
        }

        //no progress count
        input.setValue(state.getNoProgressCount(),0,0,plane_index+6);

        return input;
    }

    @Override
    protected PolicyOutput evaluate(Dim3Struct dim3Struct) {

        //TODO

        return null;
    }

    @Override
    public int getNumOfOutputStates() {
        return AllPieceMoveOptions.getMoveOptions().size();
    }
}
