package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dtos.Content;
import helpers.LogHelper;

@RestController
@RequestMapping("/api")
public class ContentTestController {

	@Autowired
	LogHelper logger;

	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	public @ResponseBody Content getContent(@PathVariable("id") long id) {
		LogHelper.getLogger().logInfo("calling: /test/ " + id);

		Content content = new Content();
		content.setId(id);
		content.setData("paramiter: " + id);
		content.setDescription("That's works");
		content.setStatus(200);
		content.setError(null);

		return content;

	}

	@RequestMapping(value = "/jbossdir", method = RequestMethod.GET)
	public @ResponseBody Content getDirVar() {
		LogHelper.getLogger().logInfo("calling: /jbossdir");

		Content content = new Content();
		content.setId(System.currentTimeMillis());
		content.setData("jboss.server.home.dir: " + System.getProperty("jboss.server.home.dir"));
		content.setDescription("get jboss.server.home.dir");
		content.setStatus(200);
		content.setError(null);

		return content;

	}

	@RequestMapping(value = "/logfilesList", method = RequestMethod.GET)
	public @ResponseBody Content logfilesList(HttpServletResponse response) {
		LogHelper.getLogger().logInfo("calling: /logfilesList");
		String contentLog = System.getProperty("jboss.server.home.dir") + "/log";
		File pFile = new File(contentLog);
		List<String> fileLists = null;
		Content content = new Content();
		content.setId(System.currentTimeMillis());
		content.setDescription("get logs files");

		if (pFile.isDirectory() && pFile.exists()) {
			File[] fl = pFile.listFiles();
			if (fl != null && fl.length > 0) {
				fileLists = new ArrayList<>();

				for (File f : fl) {
					fileLists.add(f.getName());
				}

				response.setStatus(200);
				content.setData(fileLists);
				content.setStatus(200);
				content.setError(null);

			} else {
				response.setStatus(404);
				content.setData(null);
				content.setStatus(404);
				content.setError("Log directory " + contentLog + " empty");
			}
		} else {
			response.setStatus(404);
			content.setData(null);
			content.setStatus(404);
			content.setError("Log directory " + contentLog + " not found");
		}
		return content;

	}

	@RequestMapping(value = "/logger/change/{level}", method = RequestMethod.GET)
	public @ResponseBody Content changeLogLevel(@PathVariable("level") String logLevel, HttpServletRequest request,
			HttpServletResponse response) {
		logger.logInfo("calling /logger/change/" + logLevel);
		Content content = new Content();
		content.setId(System.currentTimeMillis());
		try {
			content.setDescription("changing logger level");
			if (logLevel != null && !logLevel.isEmpty()) {
				if (logLevel != null && !logLevel.isEmpty()) {
					Level lev = Level.INFO;
					boolean isLevelSet = false;
					if (logLevel.equalsIgnoreCase("info")) {
						lev = Level.INFO;
						isLevelSet = true;
					} else if (logLevel.equalsIgnoreCase("error")) {
						lev = Level.ERROR;
						isLevelSet = true;
					} else if (logLevel.equalsIgnoreCase("debug")) {
						lev = Level.DEBUG;
						isLevelSet = true;
					} else if (logLevel.equalsIgnoreCase("wern")) {
						lev = Level.WARN;
						isLevelSet = true;
					} else if (logLevel.equalsIgnoreCase("off")) {
						lev = Level.OFF;
						isLevelSet = true;
					} else if (logLevel.equalsIgnoreCase("trace")) {
						lev = Level.TRACE;
						isLevelSet = true;
					} else {
						content.setStatus(401);
						content.setData(false);
						content.setStatus(401);
						content.setError("Error: wrong paramiters value");
						response.setStatus(401);
					}
					if (isLevelSet) {
						logger.changeLogLevel(lev);
						content.setData(true);
						content.setStatus(200);
						response.setStatus(200);
						content.setError(null);
					}
				} else {
					content.setStatus(401);
					content.setData(false);
					content.setError("error: must specify the level paramiter");
					response.setStatus(401);
				}

			} else {
				content.setStatus(401);
				content.setData(false);
				content.setError("error: must specify the level paramiter");
				response.setStatus(401);
			}
		} catch (Exception e) {
			logger.logException(e);
			content.setStatus(500);
			content.setData(false);
			content.setError("error: " + e.getMessage());
			response.setStatus(500);
		}
		return content;
	}
}