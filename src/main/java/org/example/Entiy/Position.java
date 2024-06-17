package org.example.Entiy;

public class Position {
    private int line = 1;
    private int posInLine = 1;

    public Position() {}
    public Position(Position position){
        this.line = position.line;
        this.posInLine = position.posInLine;
    }

    public String getPosString(){
        return String.format("%d:%d",line,posInLine);
    }

    public void lineBreak(){
        line++;
        resetToZeroPosInLine();
    }
    public void resetToZeroPosInLine(){posInLine=0;}

    public void addToPosInLine(int offset){posInLine+=offset;}



}
