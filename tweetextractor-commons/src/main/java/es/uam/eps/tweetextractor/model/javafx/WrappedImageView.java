/**
 * 
 */
package es.uam.eps.tweetextractor.model.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author jose
 *
 */
public class WrappedImageView extends ImageView{
    public WrappedImageView()
    {
        setPreserveRatio(false);
    }

    @Override
    public double minWidth(double height)
    {
        return 40;
    }

    @Override
    public double prefWidth(double height)
    {
        Image i=getImage();
        if (i==null) return minWidth(height);
        return i.getWidth();
    }

    @Override
    public double maxWidth(double height)
    {
        return 16384;
    }

    @Override
    public double minHeight(double width)
    {
        return 40;
    }

    @Override
    public double prefHeight(double width)
    {
        Image i=getImage();
        if (i==null) return minHeight(width);
        return i.getHeight();
    }

    @Override
    public double maxHeight(double width)
    {
        return 16384;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        setFitWidth(width);
        setFitHeight(height);
    }
}