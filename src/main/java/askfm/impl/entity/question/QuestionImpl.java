package askfm.impl.entity.question;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import askfm.api.entity.question.Question;

@DatabaseTable(tableName = "questions")
public class QuestionImpl implements Question {
	public static final String COUNTRY_FIELD = "country";
	
	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private Integer id;
	@DatabaseField
	private String text;
	@DatabaseField(columnName = COUNTRY_FIELD)
	private String country;
	@DatabaseField
	private Date date;

	public QuestionImpl() {
		// ORMLite needs a no-arg constructor
	};

	public QuestionImpl(String text, String country, Date date) {
		this.text = text;
		this.country = country;
		this.date = date;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public Date getDateTime() {
		return date;
	}

}
