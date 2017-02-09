package askfm.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

import askfm.api.ServiceException;
import askfm.api.ip.IPinfo;
import askfm.api.ip.IpService;
import askfm.api.question.Question;
import askfm.api.question.QuestionService;

@Path("/question")
public class QuestionResource {
	private final QuestionService questionService;
	private final IpService ipService;

	@Inject
	public QuestionResource(QuestionService questionService, IpService ipService) {
		this.questionService = questionService;
		this.ipService = ipService;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Question get(@PathParam("id") Integer id) throws ServiceException {
		return questionService.getEntityById(id);
	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Question create(@Context HttpServletRequest requestContext, String text) throws ServiceException {
		IPinfo ipInfo = ipService.getInfoByIp(requestContext.getRemoteAddr());
		return questionService.createAndSave(ipInfo.country.code, text);
	}
}
