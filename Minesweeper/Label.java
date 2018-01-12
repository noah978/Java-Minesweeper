import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Label here.
 * 
 * @Noah Keck
 * @v1.0
 * @1/10/2018
 */
public class Label extends Actor
{
    private final Color transparent = new Color(0,0,0,0);
    private String value;
    private int fontSize;
    private Color lineColor, fillColor;
    
    /**
     * 
     */
    public Label(String value, int fontSize)
    {
        this.value = value;
        this.fontSize = fontSize;
        lineColor = transparent;
        fillColor = Color.BLACK;
        updateImage();
    }
    /**
     * 
     */
    public Label(int value, int fontSize)
    {
        this(Integer.toString(value), fontSize);
    }
    /**
     * 
     */
    public void setValue(String value)
    {
        this.value = value;
        updateImage();
    }
    /**
     * 
     */
    public void setValue(int value)
    {
        this.value = Integer.toString(value);
        updateImage();
    }
    /**
     * 
     */
    public void setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
        updateImage();
    }
    /**
     * 
     */
    public void setLineColor(Color newColor)
    {
        lineColor = newColor;
        updateImage();
    }
    /**
     * 
     */
    public void setFillColor(Color newColor)
    {
        fillColor = newColor;
        updateImage();
    }
    
    private void updateImage()
    {
        setImage(new GreenfootImage(value, fontSize, fillColor, transparent, lineColor));
    }
}
