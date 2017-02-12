package askfm.impl.question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import askfm.api.properties.PropertyService;
import askfm.api.question.BlacklistValidationService;
import askfm.api.question.Question;

@Singleton
public class BlacklistValidationServiceImpl implements BlacklistValidationService {
	public static final String BLACKLIST_FILE_NAME = "askfm.blacklist.file";

	private static String DELIMITER = "\\W+";

	private final Set<String> blacklist;

	@Inject
	public BlacklistValidationServiceImpl(PropertyService propertyService) {
		this(propertyService.getPropertyValue(BLACKLIST_FILE_NAME));
	}

	public BlacklistValidationServiceImpl(String blacklistFileName) {
		// load blacklist from file
		if (StringUtils.isNotEmpty(blacklistFileName)) {
			BufferedReader bufferedReader = null;
			blacklist = new HashSet<>();
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(blacklistFileName)));
				bufferedReader.lines().forEach(x -> blacklist.add(x.toLowerCase()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null)
						bufferedReader.close();
				} catch (IOException e) {
					// DO NOTHING
				}
			}
		} else {
			blacklist = Collections.emptySet();
		}
	}

	@Override
	public String doValidation(Question question) {
		String text = question.getText();

		if (StringUtils.isNotEmpty(text)) {
			String[] words = text.toLowerCase().split(DELIMITER);
			for (int i = 0; i < words.length; i++) {
				if (blacklist.contains(words[i])) {
					return words[i];
				}
			}
		}
		return "";
	}

}
