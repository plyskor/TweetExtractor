/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.register.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.register.impl.TimelineReportVolumeRegister;
import es.uam.eps.tweetextractorfx.MainApplication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

/**
 * @author jose
 *
 */
public class ShowRawDataTimelineVolumeReportControl extends ShowRawDataTimelineReportControl {
	private TableColumn<AnalyticsReportRegister,Integer> valueColumn=new TableColumn<>();

	/**
	 * @param scene the scene to set
	 * @param report the report to set
	 */
	public ShowRawDataTimelineVolumeReportControl(Scene scene, AnalyticsReport report) {
		super(scene, report);
	}

	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.getTable().getColumns().add(valueColumn);
		valueColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportVolumeRegister)cellData.getValue()).getValue()).asObject());
		valueColumn.setText("Number of tweets");
	}
	/**
	 * @return the valueColumn
	 */
	public TableColumn<AnalyticsReportRegister, Integer> getValueColumn() {
		return valueColumn;
	}
	/**
	 * @param valueColumn the valueColumn to set
	 */
	public void setValueColumn(TableColumn<AnalyticsReportRegister, Integer> valueColumn) {
		this.valueColumn = valueColumn;
	}

	
}
