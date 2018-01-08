import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Cells here.
 * 
 * @Noah Keck
 * @v1.0.1
 * @1/7/2018
 */
public class Cell extends Actor
{
    public String cellType;
    public int publicNumber;
    private MinesweeperBoard board;
    public Cell()
    {
        board = (MinesweeperBoard)getWorld();
        setImage(board.GraySquare);
        cellType = "normal";
    }
    public void act() 
    {
        board = (MinesweeperBoard)getWorld();
        if (board.playGame)
            clickCheck();
    }
    public void setCell(String type)
    {
        cellType = type;
    }
    private void clickCheck()
    {
        MouseInfo ms = Greenfoot.getMouseInfo();
        if (ms!=null){
            if (ms.getX()==this.getX() && ms.getY()==this.getY()){
                if (cellType.equals("normal") && ms.getButton()==1 && Greenfoot.mouseClicked(this)){
                    setImage(board.ClickedSquare);
                    findNumber();
                }
                else if (cellType.equals("flagged") && ms.getButton()==3 && Greenfoot.mouseClicked(this)){
                    setImage(board.GraySquare);
                    cellType="normal";
                }
                else if (cellType.equals("normal") && ms.getButton()==3 && Greenfoot.mouseClicked(this)){
                    setImage(board.Flagged);
                    cellType="flagged";
                }
                else if (cellType.equals("mine") && ms.getButton()==1 && Greenfoot.mouseClicked(this)){
                    setImage(board.RedMine);
                    cellType="activatedMine";
                    MinesweeperBoard world = (MinesweeperBoard)getWorld();
                    world.GameOver();
                }
                else if (cellType.equals("mine") && ms.getButton()==3 && Greenfoot.mouseClicked(this)){
                    setImage(board.Flagged);
                    cellType="flaggedMine";
                }
                else if (cellType.equals("flaggedMine") && ms.getButton()==3 && Greenfoot.mouseClicked(this)){
                    setImage(board.GraySquare);
                    cellType="mine";
                }
            }
        }
    }
    private void findNumber()
    {
        int number = findPublicNumber();
        if (number>0)
            assignNumberImage(number);
        else
            board.revealZeros(this.getX(), this.getY());
        cellType="clicked";
    }
    public void assignNumberImage(int number)
    {
        if (number==1)
            setImage(board.OneSquare);
        else if (number==2)
            setImage(board.TwoSquare);
        else if (number==3)
            setImage(board.ThreeSquare);
        else if (number==4)
            setImage(board.FourSquare);
        else if (number==5)
            setImage(board.FiveSquare);
        else if (number==6)
            setImage(board.SixSquare);
        else if (number==7)
            setImage(board.SevenSquare);
        else if (number==8)
            setImage(board.EightSquare);
    }
    public int findPublicNumber()
    {
        List<Cell> neighbors = getNeighbours(1, true, Cell.class);
        publicNumber = 0;
        for (Cell neighbor : neighbors)
            if (neighbor.cellType.equals("mine") || neighbor.cellType.equals("flaggedMine"))
                publicNumber++;
        return publicNumber;
    }
}
