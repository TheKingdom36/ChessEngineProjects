package ChessEngine.Util;


import GameBoard.ChessBoard.Enums.Color;
import GameBoard.ChessBoard.Models.ChessBoard;
import GameBoard.ChessBoard.Models.ChessPiece;
import GameBoard.ChessBoard.Util.PieceStore;

public class ConvertBoardToWhite {
    public static ChessBoard Convert(ChessBoard chessBoard){
        //Always want the input in perspective of white player. after the neural network completes the output is then planeped to blacks perspective
                ChessBoard flippedChessBoard = chessBoard.copy();

                InvertPieceColors(flippedChessBoard);
                RotateBoard(flippedChessBoard);

                return flippedChessBoard;
    }


    private static void InvertPieceColors(ChessBoard chessBoard) {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(chessBoard.getBoardSquares()[i][j].hasPiece()){
                    if(chessBoard.getBoardSquares()[i][j].getChessPiece().getColor()== Color.White){
                        chessBoard.getBoardSquares()[i][j].setChessPiece(PieceStore.getPiece( chessBoard.getBoardSquares()[i][j].getChessPiece().getType(), Color.Black));
                    }else{
                        chessBoard.getBoardSquares()[i][j].setChessPiece(PieceStore.getPiece( chessBoard.getBoardSquares()[i][j].getChessPiece().getType(), Color.White));
                    }
                }
            }
        }
    }
    private static void RotateBoard(ChessBoard chessBoard){

        SwapRows(chessBoard,0,7);
        SwapRows(chessBoard,1,6);
        SwapRows(chessBoard,2,5);
        SwapRows(chessBoard,3,4);
    }

    private static void SwapRows(ChessBoard chessBoard, int rowNum1, int rowNum2) {
        ChessPiece tempChessPiece;
        for(int i = 0; i<=7; i++){
            if(chessBoard.getBoardSquares()[rowNum2][i].hasPiece() == true){
                tempChessPiece = chessBoard.getBoardSquares()[rowNum2][i].getChessPiece();
            }else {
                tempChessPiece = null;
            }

            if(chessBoard.getBoardSquares()[rowNum1][i].hasPiece() == true){
                chessBoard.getBoardSquares()[rowNum2][i].setChessPiece(chessBoard.getBoardSquares()[rowNum1][i].getChessPiece());
            }else {
                chessBoard.getBoardSquares()[rowNum2][i].clear();
            }

            if(tempChessPiece != null){
                chessBoard.getBoardSquares()[rowNum1][i].setChessPiece(tempChessPiece);
            }else {
                chessBoard.getBoardSquares()[rowNum2][i].clear();
            }





        }
    }

}
