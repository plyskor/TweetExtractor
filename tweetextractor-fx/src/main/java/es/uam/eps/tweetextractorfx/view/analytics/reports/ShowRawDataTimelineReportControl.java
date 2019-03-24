/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportRegister;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

/**
 * @author jose
 *
 */
public abstract class ShowRawDataTimelineReportControl extends ShowRawDataControl {

	private TableColumn<AnalyticsReportRegister, Integer> dayColumn;
	private TableColumn<AnalyticsReportRegister,Integer> monthColumn;
	private TableColumn<AnalyticsReportRegister,Integer> yearColumn;
	public ShowRawDataTimelineReportControl() {
		super();
	}
	
	@FXML
	private void initialize() {
		this.getTable().getColumns().add(dayColumn);
		dayColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getDay()).asObject());
		this.getTable().getColumns().add(monthColumn);
		monthColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getMonth()).asObject());
		this.getTable().getColumns().add(yearColumn);
		yearColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getYear()).asObject());
	}
	/**
	 * @return the dayColumn
	 */
	public TableColumn<AnalyticsReportRegister, Integer> getDayColumn() {
		return dayColumn;
	}
	/**
	 * @param dayColumn the dayColumn to set
	 */
	public void setDayColumn(TableColumn<AnalyticsReportRegister, Integer> dayColumn) {
		this.dayColumn = dayColumn;
	}
	/**
	 * @return the monthColumn
	 */
	public TableColumn<AnalyticsReportRegister, Integer> getMonthColumn() {
		return monthColumn;
	}
	/**
	 * @param monthColumn the monthColumn to set
	 */
	public void setMonthColumn(TableColumn<AnalyticsReportRegister, Integer> monthColumn) {
		this.monthColumn = monthColumn;
	}
	/**
	 * @return the yearColumn
	 */
	public TableColumn<AnalyticsReportRegister, Integer> getYearColumn() {
		return yearColumn;
	}
	/**
	 * @param yearColumn the yearColumn to set
	 */
	public void setYearColumn(TableColumn<AnalyticsReportRegister, Integer> yearColumn) {
		this.yearColumn = yearColumn;
	}

}
