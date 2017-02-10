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
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;

import askfm.api.NoEntityException;
import askfm.api.NotValidEntityException;
import askfm.api.ServiceException;
import askfm.api.ip.IPinfo;
import askfm.api.ip.IpService;
import askfm.api.question.QuestionService;

@Path("/questions")
public class QuestionResource {
	private final QuestionService questionService;
	private final IpService ipService;

	@Inject
	public QuestionResource(QuestionService questionService, IpService ipService) {
		this.questionService = questionService;
		this.ipService = ipService;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return createResponse(() -> questionService.getAll());
	}
	@GET
	@Path("/country/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByCountry(@PathParam("code") String code) {
		return createResponse(() -> questionService.getByCountry(code));
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		return createResponse(() -> questionService.getEntityById(id));
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response create(@Context HttpServletRequest requestContext, String text) throws ServiceException {
		return createResponse(() -> {
			IPinfo ipInfo = ipService.getInfoByIp(requestContext.getRemoteAddr());
			return questionService.createAndSave(text, ipInfo.country.code);
		});
	}

	private <T> Response createResponse(CreateResponse<T> supplier) {
		try {
			return Response.ok(supplier.get()).build();
		} catch (NoEntityException ex) {
			return Response.noContent().entity(ex).build();
		} catch (NotValidEntityException ex) {
			return Response.status(HttpStatus.PRECONDITION_FAILED_412).entity(ex).build();
		} catch (ServiceException ex) {
			if (ex.getCause() != null) {
				ex.printStackTrace();
			}
			return Response.serverError().entity(ex).build();
		}
	}
}
