package ChessBoard.Models;


import ChessBoard.Moves.*;
import ChessBoard.Util.PieceStore;
import ChessBoard.Common.Interfaces.Board;
import ChessBoard.Common.MoveLog;
import ChessBoard.Enums.Color;
import ChessBoard.Enums.Type;
import ChessBoard.MoveCheckers.CastleCheck;
import ChessBoard.MoveCheckers.SquareSaftyCheck;
import ChessBoard.Util.ChessPosStore;


import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Board<ChessMove> {
    private BoardSquare[][] boardSquares;
    private Position WhiteKing;
    private Position BlackKing;
    private MoveLog moveLog;

    public void setBoardSquares(BoardSquare[][] boardSquares) {
        this.boardSquares = boardSquares;
    }

    @Override
    public MoveLog getMoveLog() {
        return moveLog;
    }

    @Override
    public void setMoveLog(MoveLog moveLog) {
        this.moveLog = moveLog;
    }

    public BoardSquare[][] getBoardSquares() {
        return boardSquares;
    }

    public ChessBoard(MoveLog moveLog){
            Setup();
            this.moveLog = moveLog.Copy();
    }

    public ChessBoard(){
            Setup();
            this.moveLog = new MoveLog<ChessMove>();
    }

    public ChessBoard(BoardSquare[][] boardSquares, List<ChessMove> lastChessMoves)  {
        this.boardSquares = new BoardSquare[8][8];
        this.moveLog = moveLog;

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                this.boardSquares[i][j] = new BoardSquare();
                if(boardSquares[i][j].hasPiece()==true){

                    if(boardSquares[i][j].getChessPiece().getType() == Type.King){
                        if(boardSquares[i][j].getChessPiece().getColor() == Color.White){
                            WhiteKing = ChessPosStore.getPostion(i,j);
                        }else {
                            BlackKing = ChessPosStore.getPostion(i,j);
                        }
                    }

                    this.boardSquares[i][j].setChessPiece(PieceStore.getPiece(boardSquares[i][j].getChessPiece().getType(),boardSquares[i][j].getChessPiece().getColor()));

                }
            }
        }

        moveLog = new MoveLog();

        for(ChessMove m: lastChessMoves){
            if(m instanceof NormalChessMove){

                moveLog.AddMove(new NormalChessMove(m.getTo(),m.getFrom(), PieceStore.getPiece(m.getChessPiece().getType(),m.getChessPiece().getColor())));
            }else if(m instanceof TakeChessMove){
                moveLog.AddMove(new TakeChessMove(m.getTo(),m.getFrom(),
                        PieceStore.getPiece(m.getChessPiece().getType(),m.getChessPiece().getColor()),
                        PieceStore.getPiece(((TakeChessMove) m).getChessPieceTaken().getType(),((TakeChessMove) m).getChessPieceTaken().getColor())));
            }else if(m instanceof NormalPromotionChessMove){
                NormalPromotionChessMove NPMove = new NormalPromotionChessMove(m.getTo(),m.getFrom(), PieceStore.getPiece(m.getChessPiece().getType(),m.getChessPiece().getColor()));
                NPMove.setPromotedTo(PieceStore.getPiece(((NormalPromotionChessMove)m).getPromotedTo().getType(),((NormalPromotionChessMove)m).getPromotedTo().getColor()));
                moveLog.AddMove(NPMove);
            }else if(m instanceof TakePromotionChessMove){
                TakePromotionChessMove TPMove = new TakePromotionChessMove(m.getTo(),m.getFrom(),
                        PieceStore.getPiece(m.getChessPiece().getType(),m.getChessPiece().getColor()),
                        PieceStore.getPiece(((TakePromotionChessMove) m).getChessPieceTaken().getType(),((TakePromotionChessMove) m).getChessPieceTaken().getColor()));
                TPMove.setPromotedTo(PieceStore.getPiece(((TakePromotionChessMove)m).getPromotedTo().getType(),((TakePromotionChessMove)m).getPromotedTo().getColor()));
                moveLog.AddMove(TPMove);
            }else if(m instanceof CastleChessMove){
                moveLog.AddMove(new CastleChessMove(m.getTo(),
                        m.getFrom(),
                        ((CastleChessMove)m).getRookToPos(),
                        ((CastleChessMove)m).getRookToPos(),
                        PieceStore.getPiece(m.getChessPiece().getType(),m.getChessPiece().getColor()),
                        PieceStore.getPiece(((CastleChessMove) m).getSwappedRook().getType(),((CastleChessMove) m).getSwappedRook().getColor())));
            }
        }


    }

    @Override
    public List<ChessMove> GetAllAvailableMoves(Color color)  {

        return CheckForMovesToProtectKing(color);
    }

    private boolean isKingSafe(Color color) {
        if(color == Color.White){
            return SquareSaftyCheck.isSquareSafe(WhiteKing,color,boardSquares);
        }else{
            return SquareSaftyCheck.isSquareSafe(BlackKing,color,boardSquares);
        }
    }

    private List<ChessMove> CheckForMovesToProtectKing(Color color)  {
        List<ChessMove> chessMoves = CheckMovesOfAllPieces(color);
        chessMoves.addAll(CastleCheck.Check(this,moveLog,color));
        List<ChessMove> safeChessMoves = new ArrayList<>();



        for(ChessMove m: chessMoves){

            this.UpdateBoard(m);

            if(this.isKingSafe(color)==true){
                safeChessMoves.add(m);
            }
            this.UndoMove(m);

        }

        return safeChessMoves;
    }

    @Override
    public void UndoMove(ChessMove chessMove) {
        moveLog.getPastMoves().remove(moveLog.getPastMoves().size()-1);

        if(chessMove instanceof NormalPromotionChessMove){
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].setChessPiece(((NormalPromotionChessMove) chessMove).getChessPiece());
            boardSquares[chessMove.getTo().x][chessMove.getTo().y].clear();
        }else if(chessMove instanceof TakePromotionChessMove){
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].setChessPiece(((TakePromotionChessMove) chessMove).getChessPiece());
            boardSquares[chessMove.getTo().x][chessMove.getTo().y].setChessPiece(((TakePromotionChessMove) chessMove).getChessPieceTaken());
        }else if(chessMove instanceof CastleChessMove){
            boardSquares[chessMove.getFrom().getX()][chessMove.getFrom().getY()].setChessPiece(chessMove.getChessPiece());
            boardSquares[chessMove.getTo().getX()][chessMove.getTo().getY()].clear();

            boardSquares[((CastleChessMove) chessMove).getRookFromPos().getX()][((CastleChessMove) chessMove).getRookFromPos().getY()].setChessPiece(((CastleChessMove) chessMove).getSwappedRook());
            boardSquares[((CastleChessMove) chessMove).getRookToPos().getX()][((CastleChessMove) chessMove).getRookToPos().getY()].clear();
        }else {
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].setChessPiece(chessMove.getChessPiece());
            if(chessMove instanceof TakeChessMove){
                boardSquares[chessMove.getTo().x][chessMove.getTo().y].setChessPiece(((TakeChessMove) chessMove).getChessPieceTaken());
            }else {
                boardSquares[chessMove.getTo().x][chessMove.getTo().y].clear();
            }
        }

        if(chessMove.getChessPiece().getType() == Type.King){
            if(chessMove.getChessPiece().getColor() == Color.White){
                WhiteKing = chessMove.getFrom();
            }else{
                BlackKing = chessMove.getFrom();
            }
        }


    }

    private List<ChessMove> CheckMovesOfAllPieces(Color color)  {
        List<ChessMove> chessMoves = new ArrayList<>();



        for(int i=0;i<boardSquares.length;i++){
            for(int j=0;j<boardSquares[i].length;j++){
                if(boardSquares[i][j].hasPiece()){
                    if(boardSquares[i][j].getChessPiece().getColor()==color){
                        chessMoves.addAll(boardSquares[i][j].getChessPiece().GetAvailableMoves(ChessPosStore.getPostion(i,j),boardSquares));

                    }
                }

            }
        }
        return chessMoves;
    }

    @Override
    public void UpdateBoard(ChessMove chessMove){
        moveLog.AddMove(chessMove);

        if(chessMove instanceof NormalPromotionChessMove){
            boardSquares[chessMove.getTo().x][chessMove.getTo().y].setChessPiece(((NormalPromotionChessMove) chessMove).getPromotedTo());
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].clear();
        }else if(chessMove instanceof TakePromotionChessMove){
            boardSquares[chessMove.getTo().x][chessMove.getTo().y].setChessPiece(((TakePromotionChessMove) chessMove).getPromotedTo());
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].clear();
        }else if(chessMove instanceof CastleChessMove){
            boardSquares[chessMove.getTo().getX()][chessMove.getTo().getY()].setChessPiece(((CastleChessMove) chessMove).getChessPiece());
            boardSquares[chessMove.getFrom().getX()][chessMove.getFrom().getY()].clear();
            boardSquares[((CastleChessMove) chessMove).getRookToPos().getX()][((CastleChessMove) chessMove).getRookToPos().getY()].setChessPiece(((CastleChessMove) chessMove).getSwappedRook());
            boardSquares[((CastleChessMove) chessMove).getRookFromPos().getX()][((CastleChessMove) chessMove).getRookFromPos().getY()].clear();
        }else {
            boardSquares[chessMove.getTo().x][chessMove.getTo().y].setChessPiece(chessMove.getChessPiece());
            boardSquares[chessMove.getFrom().x][chessMove.getFrom().y].clear();
        }

        if(chessMove.getChessPiece().getType() == Type.King){
            if(chessMove.getChessPiece().getColor() == Color.White){
                WhiteKing = chessMove.getTo();
            }else{
                BlackKing = chessMove.getTo();
            }
        }
    }

    @Override
    public void PrintBoard(){
        System.out.println();
        for(int i=boardSquares.length-1;i>=0;i--){
            System.out.println();
            for(int j=0;j<boardSquares[i].length;j++){
                if(boardSquares[i][j].hasPiece()==true){
                    System.out.print(" " +boardSquares[i][j].getChessPiece().getType().name().charAt(0)+ boardSquares[i][j].getChessPiece().getType().name().charAt(1) + boardSquares[i][j].getChessPiece().getColor().name().charAt(0));
                }else{
                    System.out.print(" Emp");
                }

            }
        }
    }

    @Override
    public void Reset() {
        for(int i=0;i<boardSquares.length;i++){
            for(int j=0;j<boardSquares[i].length;j++){
                boardSquares[i][j].clear();
            }
        }

        for(int i=0;i<8;i++){
            boardSquares[1][i].setChessPiece(PieceStore.getPiece(Type.Pawn, Color.White));
        }
        boardSquares[0][0].setChessPiece(PieceStore.getPiece(Type.Rook, Color.White));
        boardSquares[0][1].setChessPiece(PieceStore.getPiece(Type.Knight, Color.White));
        boardSquares[0][2].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.White));
        boardSquares[0][3].setChessPiece(PieceStore.getPiece(Type.King, Color.White));
        boardSquares[0][4].setChessPiece(PieceStore.getPiece(Type.Queen, Color.White));
        boardSquares[0][5].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.White));
        boardSquares[0][6].setChessPiece(PieceStore.getPiece(Type.Knight, Color.White));
        boardSquares[0][7].setChessPiece(PieceStore.getPiece(Type.Rook, Color.White));

        for(int i=0;i<8;i++){
            boardSquares[6][i].setChessPiece(PieceStore.getPiece(Type.Pawn, Color.Black));
        }
        boardSquares[7][0].setChessPiece(PieceStore.getPiece(Type.Rook, Color.Black));
        boardSquares[7][1].setChessPiece(PieceStore.getPiece(Type.Knight, Color.Black));
        boardSquares[7][2].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.Black));
        boardSquares[7][3].setChessPiece(PieceStore.getPiece(Type.King, Color.Black));
        boardSquares[7][4].setChessPiece(PieceStore.getPiece(Type.Queen, Color.Black));
        boardSquares[7][5].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.Black));
        boardSquares[7][6].setChessPiece(PieceStore.getPiece(Type.Knight, Color.Black));
        boardSquares[7][7].setChessPiece(PieceStore.getPiece(Type.Rook, Color.Black));

    }

    @Override
    public void Setup() {
        boardSquares = new BoardSquare[8][8];

        for(int i=0;i<boardSquares.length;i++){
            for(int j=0;j<boardSquares[i].length;j++){
                boardSquares[i][j] = new BoardSquare();
            }
        }

        for(int i=0;i<8;i++){
            boardSquares[1][i].setChessPiece(PieceStore.getPiece(Type.Pawn, Color.White));
        }
        boardSquares[0][0].setChessPiece(PieceStore.getPiece(Type.Rook, Color.White));
        boardSquares[0][1].setChessPiece(PieceStore.getPiece(Type.Knight, Color.White));
        boardSquares[0][2].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.White));
        boardSquares[0][3].setChessPiece(PieceStore.getPiece(Type.Queen, Color.White));
        boardSquares[0][4].setChessPiece(PieceStore.getPiece(Type.King, Color.White));
        boardSquares[0][5].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.White));
        boardSquares[0][6].setChessPiece(PieceStore.getPiece(Type.Knight, Color.White));
        boardSquares[0][7].setChessPiece(PieceStore.getPiece(Type.Rook, Color.White));

        WhiteKing = ChessPosStore.getPostion(0,4);

        for(int i=0;i<8;i++){
            boardSquares[6][i].setChessPiece(PieceStore.getPiece(Type.Pawn, Color.Black));
        }
        boardSquares[7][0].setChessPiece(PieceStore.getPiece(Type.Rook, Color.Black));
        boardSquares[7][1].setChessPiece(PieceStore.getPiece(Type.Knight, Color.Black));
        boardSquares[7][2].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.Black));
        boardSquares[7][3].setChessPiece(PieceStore.getPiece(Type.Queen, Color.Black));
        boardSquares[7][4].setChessPiece(PieceStore.getPiece(Type.King, Color.Black));
        boardSquares[7][5].setChessPiece(PieceStore.getPiece(Type.Bishop, Color.Black));
        boardSquares[7][6].setChessPiece(PieceStore.getPiece(Type.Knight, Color.Black));
        boardSquares[7][7].setChessPiece(PieceStore.getPiece(Type.Rook, Color.Black));

        BlackKing = ChessPosStore.getPostion(7,4);

    }

    @Override
    public ChessBoard Copy()  {
        BoardSquare[][] newBoardSquares = new BoardSquare[8][8];


        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                newBoardSquares[i][j] = new BoardSquare();
                if(this.boardSquares[i][j].hasPiece()==true){
                    boardSquares[i][j].getChessPiece();
                    boardSquares[i][j].getChessPiece().getColor();
                    boardSquares[i][j].getChessPiece().getType();
                    PieceStore.getPiece(boardSquares[i][j].getChessPiece().getType(),boardSquares[i][j].getChessPiece().getColor());
                    newBoardSquares[i][j].setChessPiece(PieceStore.getPiece(boardSquares[i][j].getChessPiece().getType(),boardSquares[i][j].getChessPiece().getColor()));
                }
            }
        }

        MoveLog newMoveLog= this.moveLog.Copy();



        return new ChessBoard(newBoardSquares, newMoveLog.getPastMoves());
    }

    @Override
    public int compareTo(Board otherBoard) {

        if(otherBoard.getClass() != ChessBoard.class){
            return 1;
        }

        for(int i=0;i<7;i++){
            for(int j=0;j<7;j++){
                //if a boardSquare is not equal the boards cannot be equal
                if(1 == this.boardSquares[i][j].compareTo(((ChessBoard)otherBoard).getBoardSquares()[i][j])){
                    return 1;
                }
            }
        }

        //if all boardSquares are equal then the board is equal
        return 0;
    }
}