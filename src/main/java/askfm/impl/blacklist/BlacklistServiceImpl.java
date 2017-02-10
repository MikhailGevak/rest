package askfm.impl.blacklist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import askfm.api.blacklist.BlacklistService;
import askfm.api.properties.PropertyService;

public class BlacklistServiceImpl implements BlacklistService {
	public static final String BLACKLIST_FILE_NAME = "askfm.blacklist.file";
	
	private static String DELIMITER = "\\W+";

	private final Set<String> blacklist;

	@Inject
	public BlacklistServiceImpl(PropertyService propertyService) {
		String blacklistFileName = propertyService.getPropertyValue(BLACKLIST_FILE_NAME);
		//load blacklist from file
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
					if (bufferedReader != null) bufferedReader.close();
				} catch (IOException e) {
					// DO NOTHING
				}
			}
		} else {
			blacklist = Collections.emptySet();
		}
	}

	@Override
	public String isValid(String text) {
		String[] words = text.toLowerCase().split(DELIMITER);
		for (int i = 0; i < words.length; i++) {
			if (blacklist.contains(words[i])) {
				return words[i];
			}
		}

		return "";
	}

}
