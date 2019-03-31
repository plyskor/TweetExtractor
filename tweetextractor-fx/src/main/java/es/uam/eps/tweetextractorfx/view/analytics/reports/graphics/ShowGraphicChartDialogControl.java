/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports.graphics;
import java.io.ByteArrayInputStream;

import es.uam.eps.tweetextractor.model.analytics.graphics.AnalyticsReportImage;
import es.uam.eps.tweetextractor.model.javafx.WrappedImageView;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.dialog.TweetExtractorFXDialogController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

/**
 * @author jose
 *
 */
public class ShowGraphicChartDialogControl extends TweetExtractorFXDialogController {
	private AnalyticsReportImage graphic;
	@FXML 
	private WrappedImageView imageView;

	/**
	 * 
	 */
	public ShowGraphicChartDialogControl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see es.uam.eps.tweetextractorfx.view.TweetExtractorFXController#setMainApplication(es.uam.eps.tweetextractorfx.MainApplication)
	 */
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		if(this.graphic!=null&&this.imageView!=null) {
			Image image = new Image(new ByteArrayInputStream(graphic.getImage()));
			imageView.setImage(image);
		}
	}

	/**
	 * @return the graphic
	 */
	public AnalyticsReportImage getGraphic() {
		return graphic;
	}
	/**
	 * @param graphic the graphic to set
	 */
	public void setGraphic(AnalyticsReportImage graphic) {
		this.graphic = graphic;
	}
	/**
	 * @return the imageView
	 */
	public WrappedImageView getImageView() {
		return imageView;
	}
	/**
	 * @param imageView the imageView to set
	 */
	public void setImageView(WrappedImageView imageView) {
		this.imageView = imageView;
	}
}
