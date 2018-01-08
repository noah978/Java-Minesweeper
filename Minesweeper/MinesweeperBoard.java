import greenfoot.*;
import static greenfoot.Greenfoot.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
 * 
 * @Noah Keck
 * @v1.0.2
 * @1/7/2018
 */
public class MinesweeperBoard extends World
{
    public static GreenfootImage GraySquare;
    public static GreenfootImage ClickedSquare;
    public static GreenfootImage RedMine;
    public static GreenfootImage Flagged;
    public static GreenfootImage Mine;
    public static GreenfootImage DefusedMine;
    public static GreenfootImage OneSquare;
    public static GreenfootImage TwoSquare;
    public static GreenfootImage ThreeSquare;
    public static GreenfootImage FourSquare;
    public static GreenfootImage FiveSquare;
    public static GreenfootImage SixSquare;
    public static GreenfootImage SevenSquare;
    public static GreenfootImage EightSquare;
    
    private int width;
    private int height;
    private ArrayList<ArrayList<Cell>> cells;
    public boolean playGame;
    
    public MinesweeperBoard()
    {    
        super(60, 30, 16);
        width=60;
        height=30;
        LoadContent();
        CreateCells();
        GenerateMines();
        playGame = true;
    }
    public void act()
    {
        if (playGame)
            checkWin();
        if (Greenfoot.isKeyDown("space"))
            reset();
    }
    public void reset()
    {
        CreateCells();
        GenerateMines();
        playGame = true;
    }
    private void LoadContent()
    {
        GraySquare=new GreenfootImage("GreySquareNormal.png");
        ClickedSquare=new GreenfootImage("DepressedGraySquare.png");
        RedMine=new GreenfootImage("RedMine.png");
        Flagged=new GreenfootImage("FlaggedSquare.png");
        Mine=new GreenfootImage("MineSquare.png");
        DefusedMine= new GreenfootImage("NotMine.png");
        OneSquare=new GreenfootImage("1Square.png");
        TwoSquare=new GreenfootImage("2Square.png");
        ThreeSquare=new GreenfootImage("3Square.png");
        FourSquare=new GreenfootImage("4Square.png");
        FiveSquare=new GreenfootImage("5Square.png");
        SixSquare=new GreenfootImage("6Square.png");
        SevenSquare=new GreenfootImage("7Square.png");
        EightSquare=new GreenfootImage("8Square.png");
    }
    private void GenerateMines()
    {
        for (int x=0; x<=((width*height)/8); x++)
            cells.get(getRandomNumber(width+1)).get(getRandomNumber(height+1)).setCell("mine");
    }
    private void CreateCells()
    {
        cells = new ArrayList<ArrayList<Cell>>();
        while (cells.size() <= width)
            cells.add(new ArrayList<Cell>());
        for (int x=0; x<=60; x++){
            for (int y=0; y<=30; y++){
                cells.get(x).add(new Cell());
                addObject(cells.get(x).get(y), x, y);
            }
        }
    }
    public void revealZeros(int x, int y) {
        if (x < 0 || x > width || y < 0 || y > height)
            return; // check for bounds
        Cell temp = cells.get(x).get(y);
        if (temp.findPublicNumber() == 0 && temp.cellType.equals("normal")) {
            temp.setCell("clicked");
            temp.setImage(ClickedSquare);
            revealZeros( x+1, y );
            revealZeros( x+1, y+1 );
            revealZeros( x-1, y );
            revealZeros( x-1, y-1 );
            revealZeros( x, y-1 );
            revealZeros( x+1, y-1 );
            revealZeros( x, y+1 );
            revealZeros( x-1, y+1 );
        }
        else if (temp.cellType.equals("normal")){
            temp.setCell("clicked");
            temp.assignNumberImage(temp.findPublicNumber());
            return;
        }
        else
            return;
    }
    private void revealMines()
    {
        for (ArrayList<Cell> cellArray : cells){
            for (Cell temp : cellArray){
                if (temp.cellType.equals("mine")){
                    temp.setImage(Mine);
                    delay(1);
                }
                else if (temp.cellType.equals("flaggedMine")){
                    temp.setImage(DefusedMine);
                    delay(1);
                }
            }
        }
    }
    public void checkWin()
    {
        boolean win = true;
        for (ArrayList<Cell> cellArray : cells)
            for (Cell temp : cellArray)
                if (temp.cellType.equals("mine"))
                    win = false;
        if (win){
            playGame = false;
            revealMines();
            showText("Great Game!", (width/2), (height/2));
            stop();
        }
    }
    public void GameOver()
    {
        playGame = false;
        revealMines();
        showText("Game Over", (width/2), (height/2));
        stop();
    }
}
