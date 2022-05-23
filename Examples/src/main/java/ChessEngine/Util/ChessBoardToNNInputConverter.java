package ChessEngine.Util;


import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import ChessEngine.ChessBoardState;
import Common.Plane;


/*
* Pawn
* Rook
* Bishop
* Knight
* Queen
* King
* RepetitionCount
*
* OppPawn
* OppRook
* OppBishop
* OppKnight
* OppQueen
* OppKing
* OppRepetitionCount
*
* ...repeat 4 more times
*
* Color
*
* Total move count
*
* P1 casting queen side
*
* P1 casting king side
*
* P2 casting queen side
*
* P2 casting king side
*
* no progress count
* */

/**
 * Used to transform a chessBoard into a stack of planes
 */
public class ChessBoardToNNInputConverter {


    public static Plane[] ConvertChessBoardToInput(ChessBoardState chessBoardState) {

        ChessBoard chessBoard = chessBoardState.getBoard().copy();


        Plane[] input = new Plane[21];

        for(int i=0;i<input.length;i++){
            input[i] = new Plane(8,8);
        }

        int plane_index=0;

        //Create pieces planes for each state


            for(int j=0;j<8;j++){
                for(int i=0;i<8;i++){
                    if(chessBoard.getBoardSquares()[j][i].hasPiece()){
                        if(chessBoard.getBoardSquares()[j][i].getChessPiece().getColor() == Color.White){
                            switch (chessBoard.getBoardSquares()[j][i].getChessPiece().getType()){
                                case Pawn:{

                                    input[plane_index].setValue(i,j,1);
                                    break;
                                }
                                case Rook:{
                                    input[plane_index+1].setValue(i,j,1);
                                    break;
                                }
                                case Bishop:{
                                    input[plane_index+2].setValue(i,j,1);
                                    break;
                                }
                                case Knight:{
                                    input[plane_index+3].setValue(i,j,1);
                                    break;
                                }
                                case Queen:{
                                    input[plane_index+4].setValue(i,j,1);
                                    break;
                            }
                            case King:{
                                input[plane_index+5].setValue(i,j,1);
                                break;
                            }
                        }
                        }else{
                            switch (chessBoard.getBoardSquares()[j][i].getChessPiece().getType()){
                                case Pawn:{
                                    input[plane_index+7].setValue(i,j,1);
                                    break;
                                }
                                case Rook:{
                                    input[plane_index+8].setValue(i,j,1);
                                    break;
                                }
                                case Bishop:{
                                    input[plane_index+9].setValue(i,j,1);
                                    break;
                                }
                                case Knight:{
                                    input[plane_index+10].setValue(i,j,1);
                                    break;
                                }
                                case Queen:{
                                    input[plane_index+11].setValue(i,j,1);
                                    break;
                                }
                                case King:{
                                    input[plane_index+12].setValue(i,j,1);
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
        if(chessBoardState.getPlayerColor()== Color.White){
            input[plane_index].setValue(0,1,1);
        }else {
            input[plane_index].setValue(1,0,1);
        }

        //Total move count
        input[plane_index+1].setValue(0,0, chessBoardState.getTotalMoveCount());

        //P1 casting queen side
        if(chessBoardState.isCastleQueenSide()==true){
            input[plane_index+2].setValue(0,0,1);
        }else{
            input[plane_index+2].setValue(0,0,0);
        }

        //P1 castling king side
        if(chessBoardState.isCastleQueenSide()==true){
            input[plane_index+3].setValue(0,0,1);
        }else{
            input[plane_index+3].setValue(0,0,0);
        }

        //P2 castling queen side
        if(chessBoardState.isOppCastleQueenSide()==true){
            input[plane_index+4].setValue(0,0,1);
        }else{
            input[plane_index+4].setValue(0,0,0);
        }

        //P2 castling king side
        if(chessBoardState.isOppCastleKingSide()==true){
            input[plane_index+5].setValue(0,0,1);
        }else{
            input[plane_index+5].setValue(0,0,0);
        }

        //no progress count
        input[plane_index+6].setValue(0,0, chessBoardState.getNoProgressCount());

        return input;
    }



}
