import greenfoot.*;
import static greenfoot.Greenfoot.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
 * 
 * @Noah Keck
 * @v1.2
 * @1/11/2018
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
    
    private int width, height, titleOffset;
    private ArrayList<ArrayList<Cell>> cells;
    private Label title;
    public boolean playGame, isBlack;
    /**
     * In order to create a square field, the height needs to be equivalent to the width + title offset
     */
    public MinesweeperBoard()
    {    
        super(20, 24, 16);
        titleOffset=4;
        width=this.getWidth();
        height=this.getHeight()-titleOffset;
        
        title = new Label("Welcome to Minesweeper", width*3/2);
        addObject(title, width/2, titleOffset - 3);
        isBlack=false;
        
        LoadContent();
        CreateCells();
        GenerateMines();
        playGame=true;
    }
    public void act()
    {
        if (playGame)
            checkWin();
        if (isKeyDown("space"))
            reset();
        if (isKeyDown("c")){
            isBlack=false;
            LoadContent();
            reset();
        }
        if (isKeyDown("b")){
            isBlack=true;
            LoadContent();
            reset();
        }
    }
    public void reset()
    {
        removeObjects(getObjects(Cell.class));
        showText(null, (width/2), (height/2));
        CreateCells();
        GenerateMines();
        playGame=true;
    }
    private void LoadContent()
    {
        if (isBlack){
            GraySquare=new GreenfootImage("BW-GraySquareNormal.png");
            ClickedSquare=new GreenfootImage("BW-DepressedGraySquare.png");
            RedMine=new GreenfootImage("BW-RedMine.png");
            Flagged=new GreenfootImage("BW-FlaggedSquare.png");
            Mine=new GreenfootImage("BW-MineSquare.png");
            DefusedMine= new GreenfootImage("BW-NotMine.png");
            OneSquare=new GreenfootImage("BW-1Square.png");
            TwoSquare=new GreenfootImage("BW-2Square.png");
            ThreeSquare=new GreenfootImage("BW-3Square.png");
            FourSquare=new GreenfootImage("BW-4Square.png");
            FiveSquare=new GreenfootImage("BW-5Square.png");
            SixSquare=new GreenfootImage("BW-6Square.png");
            SevenSquare=new GreenfootImage("BW-7Square.png");
            EightSquare=new GreenfootImage("BW-8Square.png");
        }
        else{
            GraySquare=new GreenfootImage("GraySquareNormal.png");
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
    }
    private void GenerateMines()
    {
        for (int x=0; x<((width*height)/8); x++)
            cells.get(getRandomNumber(width)).get(getRandomNumber(height)).setCell("mine");
    }
    private void CreateCells()
    {
        cells = new ArrayList<ArrayList<Cell>>();
        while (cells.size() < width)
            cells.add(new ArrayList<Cell>());
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                cells.get(x).add(new Cell());
                addObject(cells.get(x).get(y), x, y + titleOffset);
            }
        }
    }
    public void revealZeros(int x, int y) {
        if (x < 0 || x > width-1 || y < 0 + titleOffset || y > height-1 + titleOffset)
            return; // check for bounds
        Cell temp = cells.get(x).get(y - titleOffset);
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
