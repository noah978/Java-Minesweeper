import greenfoot.*;
import static greenfoot.Greenfoot.*;
import java.util.List;
import java.util.ArrayList;


/**
 * Write a description of class MyWorld here.
 * 
 * @Noah Keck
 * @v1.4.1
 * @2/4/2018
 */
public class MinesweeperBoard extends World
{
    public static GreenfootImage GraySquare;
    public static GreenfootImage ClickedSquare;
    public static GreenfootImage RedMine;
    public static GreenfootImage QuestionMark;
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
    
    private long startTime;
    private int width, height, titleOffset, scoreCount;
    private ArrayList<ArrayList<Cell>> cells;
    private Label title, score, timer;
    public boolean playGame, isBlack;
    /**
     * The default values for the game board are stored in this constructor.
     */
    public MinesweeperBoard()
    {    
        this(20, 20, 4);
    }
    public MinesweeperBoard(int width, int height, int offset)
    {
        super(width, height+offset, 16);
        titleOffset=offset;
        this.width=width;
        this.height=height;
        
        title=new Label("Minesweeper", width);
        addObject(title, width/2, titleOffset/2-1);
        timer=new Label(0, width);
        addObject(timer, width-3, titleOffset/2-1);
        score=new Label(0, width);
        addObject(score, 3, titleOffset/2-1);
        
        scoreCount=0;
        isBlack=false;
        
        LoadContent();
        CreateCells();
        GenerateMines();
        playGame=true;
    }
    public void act()
    {
        if (startTime == 0)
            startTime=System.nanoTime();
        if (playGame){
            checkWin();
            timer.setValue(String.format("%,d",(System.nanoTime()-startTime)/1000000000));
            score.setValue(String.format("%,d",scoreCount));
        }
        if (isKeyDown("enter"))
            newGameBoard();
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
    public void newGameBoard()
    {
        try{ 
            title.setValue("Width and Height\nBelow 101 and above 4");
            title.setFontSize(titleOffset*5);
            do{
                width = Integer.parseInt(ask("Input width"));
                height = Integer.parseInt(ask("Input height"));
                title.setValue("MUST BE\nBelow 101 and above 4");
            }while(width < 5 || width > 100 || height < 5 || height > 100);
        }
        catch (Exception ex){
            System.out.println("Issue with input. Please try again.");
        }
        if (width > 80)
            titleOffset = 8;
        else if (width > 60)
            titleOffset = 7;
        else if (width > 40)
            titleOffset = 6;
        else if (width > 20)
            titleOffset = 5;
        else
            titleOffset = 4;
        setWorld(new MinesweeperBoard(width, height, titleOffset));
    }
    public void reset()
    {
        removeObjects(getObjects(Cell.class));
        title.setValue("Minesweeper");
        title.setFontSize(width);
        CreateCells();
        GenerateMines();
        startTime=System.nanoTime();
        playGame=true;
    }
    private void LoadContent()
    {
        if (isBlack){
            GraySquare=new GreenfootImage("BW-GraySquareNormal.png");
            ClickedSquare=new GreenfootImage("BW-DepressedGraySquare.png");
            RedMine=new GreenfootImage("BW-RedMine.png");
            QuestionMark=new GreenfootImage("BW-QuestionMark.png");
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
            QuestionMark=new GreenfootImage("QuestionMark.png");
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
            incScore();
        }
        else if (temp.cellType.equals("normal")){
            temp.setCell("clicked");
            temp.assignNumberImage(temp.findPublicNumber());
            incScore();
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
                if (temp.cellType.equals("mine") || temp.cellType.equals("normal") || temp.cellType.equals("flagged"))
                    win = false;
        if (win){
            playGame = false;
            revealMines();
            title.setValue("Great Game");
            title.setFontSize(width);
            scoreCount+=1000;
            score.setValue(String.format("%,d",scoreCount));
            //stop();
        }
    }
    public void GameOver()
    {
        playGame = false;
        revealMines();
        title.setValue("Game Over");
        title.setFontSize(width);
        //stop();
    }
    public void incScore()
    {
        scoreCount+=5;
    }
}
