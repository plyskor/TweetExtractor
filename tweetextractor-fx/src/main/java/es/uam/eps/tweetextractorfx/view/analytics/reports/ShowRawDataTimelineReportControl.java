/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportRegister;
import es.uam.eps.tweetextractor.model.analytics.report.TimelineReportRegister;
import es.uam.eps.tweetextractorfx.MainApplication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

/**
 * @author jose
 *
 */
public abstract class ShowRawDataTimelineReportControl extends ShowRawDataControl {

	private TableColumn<AnalyticsReportRegister, Integer> dayColumn = new TableColumn<>();
	private TableColumn<AnalyticsReportRegister,Integer> monthColumn=new TableColumn<>();
	private TableColumn<AnalyticsReportRegister,Integer> yearColumn=new TableColumn<>();
	public ShowRawDataTimelineReportControl(Scene scene,AnalyticsReport report) {
		super(scene,report);
	}
	
	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		this.getTable().getColumns().add(dayColumn);
		dayColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getDay()).asObject());
		dayColumn.setText("Day");
		this.getTable().getColumns().add(monthColumn);
		monthColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getMonth()).asObject());
		monthColumn.setText("Month");
		this.getTable().getColumns().add(yearColumn);
		yearColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(((TimelineReportRegister<?>)cellData.getValue()).getYear()).asObject());
		yearColumn.setText("Year");
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
